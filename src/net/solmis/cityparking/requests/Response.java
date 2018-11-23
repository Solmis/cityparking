package net.solmis.cityparking.requests;

public abstract class Response {
    public static int RESPONSE_OK = 200;
    public static int RESPONSE_INVALID_INPUT = 400;
    public int responseCode;
    public String message;
}
