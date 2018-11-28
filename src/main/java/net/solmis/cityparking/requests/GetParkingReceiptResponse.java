package net.solmis.cityparking.requests;

public class GetParkingReceiptResponse extends Response {

    public long ticketId;
    public String driverType;
    public String currency;
    public String defaultCurrency;
    public long totalPriceInCents;
    public long totalTimeInSeconds;
    public long totalPriceInDefaultCurrency;

}
