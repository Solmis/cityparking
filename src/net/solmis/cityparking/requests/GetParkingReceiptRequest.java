package net.solmis.cityparking.requests;

public class GetParkingReceiptRequest extends Request {
    public int parkingTicketId;

    public GetParkingReceiptRequest(int parkingTicketId) {
        this.parkingTicketId = parkingTicketId;
    }
}
