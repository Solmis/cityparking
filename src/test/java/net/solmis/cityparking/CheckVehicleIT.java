package net.solmis.cityparking;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckVehicleIT extends RequestIT {

    public static String requestURL;

    @BeforeAll
    public static void setUp() {
        setUpRequestConfiguration();
        requestURL = checkVehicleURL;
    }

    @Test
    public void testUrlExists() throws IOException {
        urlExists(requestURL);
    }

    @Test
    public void testShouldReturnOKForCorrectParameters() throws IOException {
        StringEntity correctParams = new StringEntity("{\"parkedVehicle\":\"WE12345\", \"secretToken\":\"" +
                                                        secretToken + "\"}");
        HttpResponse response = makeRequest(requestURL, correctParams);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testShouldReturnUnauthorizedForIncorrectToken() throws IOException {
        StringEntity params = new StringEntity("{\"parkedVehicle\":\"WE12345\", \"secretToken\":\"" +
                secretToken + "foo\"}");
        HttpResponse response = makeRequest(requestURL, params);
        assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusLine().getStatusCode());
    }

}
