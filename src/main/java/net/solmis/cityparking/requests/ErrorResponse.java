package net.solmis.cityparking.requests;

public class ErrorResponse extends Response {

    public String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

}
