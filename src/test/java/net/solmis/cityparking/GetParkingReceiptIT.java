package net.solmis.cityparking;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GetParkingReceiptIT extends RequestIT {

    public static String requestURL;

    @BeforeAll
    public static void setUp() {
        setUpRequestConfiguration();
        requestURL = getParkingReceiptURL;
    }

    @Test
    public void testUrlExists() throws IOException {
        urlExists(requestURL);
    }

}
