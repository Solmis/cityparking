package net.solmis.cityparking;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public abstract class RequestIT {

    protected static String baseURL;

    protected static String startParkingURL;
    protected static String endParkingURL;
    protected static String checkVehicleURL;
    protected static String getParkingReceiptURL;
    protected static String getDayIncomeURL;

    protected static String secretToken;

    protected static void setUpRequestConfiguration() {
        baseURL = "http://localhost:8080";
        startParkingURL =       "/startParking";
        endParkingURL =         "/endParking";
        checkVehicleURL =       "/checkVehicle";
        getParkingReceiptURL =  "/getParkingReceipt";
        getDayIncomeURL =       "/getDayIncome";
        secretToken = "el3dkje93kfdk3odk8wpvg73ndkie8";
    }

    protected void urlExists(String requestURL) throws IOException {
        HttpPost request = new HttpPost(baseURL  + requestURL);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assertNotEquals(HttpStatus.SC_NOT_FOUND, httpResponse.getStatusLine().getStatusCode());
    }

    protected HttpResponse makeRequest(String requestURL, StringEntity params) throws IOException {
        HttpPost request = new HttpPost(baseURL  + requestURL);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);

        return HttpClientBuilder.create().build().execute(request);
    }

}
