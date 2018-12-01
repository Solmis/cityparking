package net.solmis.cityparking;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.solmis.cityparking.requests.GetParkingReceiptRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AllRequestsIT extends RequestIT {

    @BeforeAll
    public static void setUp() {
        setUpRequestConfiguration();
    }

    @Test
    public void testStartAndEndParkingScenario() throws IOException {
        StringEntity correctParams = new StringEntity("{\"parkedVehicle\":\"WE12345\"}");
        long ticketId = testStartParkingAndReturnTicketId(correctParams);

        correctParams = new StringEntity("{\"ticketId\":" + Long.toString(ticketId) + "}");
        JsonNode responseContent = testEndParkingAndReturnJsonNode(correctParams);
        assertEquals(ticketId, responseContent.get("ticketId").asLong());
    }

    @Test
    public void testSecondEndParkingShouldNotChangeEndTimestamp() throws IOException {
        StringEntity correctParams = new StringEntity("{\"parkedVehicle\":\"WE12345\"}");
        long ticketId = testStartParkingAndReturnTicketId(correctParams);

        correctParams = new StringEntity("{\"ticketId\":" + Long.toString(ticketId) + "}");
        JsonNode responseContent = testEndParkingAndReturnJsonNode(correctParams);
        assertEquals(ticketId, responseContent.get("ticketId").asLong());

        JsonNode secondResponseContent = testEndParkingAndReturnJsonNode(correctParams);
        assertEquals(ticketId, secondResponseContent.get("ticketId").asLong());
        assertEquals(responseContent.get("endTimestamp").asText(), secondResponseContent.get("endTimestamp").asText());
    }

    @Test
    public void testCheckParkedVehicleWithActiveTicket() throws IOException {
        StringEntity correctParams = new StringEntity("{\"parkedVehicle\":\"WN1337\"}");
        testStartParkingAndReturnTicketId(correctParams);

        correctParams = new StringEntity("{\"parkedVehicle\":\"WN1337\", \"secretToken\":\"" + secretToken + "\"}");
        assertTrue(testCheckVehicleAndReturnHasTicket(correctParams));
    }

    @Test
    public void testCheckParkedVehicleWithInactiveTicket() throws IOException {
        StringEntity correctParams = new StringEntity("{\"parkedVehicle\":\"WB420\"}");
        long ticketId = testStartParkingAndReturnTicketId(correctParams);

        correctParams = new StringEntity("{\"ticketId\":" + Long.toString(ticketId) + "}");
        testEndParkingAndReturnJsonNode(correctParams);

        correctParams = new StringEntity("{\"parkedVehicle\":\"WB420\", \"secretToken\":\"" + secretToken + "\"}");
        assertFalse(testCheckVehicleAndReturnHasTicket(correctParams));
    }

    @Test
    public void testGetParkingReceiptBeforeStoppingTicket() throws IOException {
        StringEntity correctParams = new StringEntity("{\"parkedVehicle\":\"WZ1344\"}");
        long ticketId = testStartParkingAndReturnTicketId(correctParams);

        correctParams = new StringEntity("{\"ticketId\":" + Long.toString(ticketId) + ", \"driverType\": \"" +
                GetParkingReceiptRequest.DRIVER_REGULAR + "\"}");
        HttpResponse response = makeRequest(getParkingReceiptURL, correctParams);
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
    }

    private long testStartParkingAndReturnTicketId(StringEntity params) throws IOException {
        HttpResponse response = makeRequest(startParkingURL, params);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        JsonNode responseContent = getContentFromResponse(response);
        long ticketId = responseContent.get("ticketId").asLong();
        assertTrue(ticketId > 0);
        return ticketId;
    }

    private JsonNode testEndParkingAndReturnJsonNode(StringEntity params) throws IOException {
        HttpResponse response = makeRequest(endParkingURL, params);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        JsonNode responseContent = getContentFromResponse(response);
        assertTrue(responseContent.has("ticketId"));
        assertTrue(responseContent.get("ticketId").isInt());
        assertTrue(responseContent.has("endTimestamp"));
        assertTrue(responseContent.get("endTimestamp").isTextual());
        return responseContent;
    }

    private boolean testCheckVehicleAndReturnHasTicket(StringEntity params) throws IOException {
        HttpResponse response = makeRequest(checkVehicleURL, params);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        JsonNode responseContent = getContentFromResponse(response);
        assertTrue(responseContent.has("hasTicket"));
        assertTrue(responseContent.get("hasTicket").isBoolean());
        return responseContent.get("hasTicket").asBoolean();
    }

    private JsonNode getContentFromResponse(HttpResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        return objectMapper.readTree(json);
    }

}
