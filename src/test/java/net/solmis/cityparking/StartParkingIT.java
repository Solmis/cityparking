package net.solmis.cityparking;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartParkingIT extends RequestIT {

    public static String requestURL;

    @BeforeAll
    public static void setUp() {
        setUpRequestConfiguration();
        requestURL = startParkingURL;
    }

    @Test
    public void startParkingUrlExists() throws IOException {
        urlExists(requestURL);
    }

    @Test
    public void startParkingReturnsOKWithCorrectParameter() throws IOException {
        StringEntity correctParams = new StringEntity("{\"parkedVehicle\":\"WE12345\"}");
        HttpResponse response = makeRequest(requestURL, correctParams);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

}
