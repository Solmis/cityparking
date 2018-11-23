package net.solmis.cityparking.requests;

public class GetParkingReceiptRequest {

    public static String DRIVER_DISABLED = "disabled";
    public static String DRIVER_REGULAR = "regular";

    public long parkingTicketId;
    public String driverType;

    public GetParkingReceiptRequest(long parkingTicketId) {
        this.parkingTicketId = parkingTicketId;
    }
}
