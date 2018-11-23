package net.solmis.cityparking.requests;

public class EndParkingRequest extends Request {
    public int parkingTicketId;

    public EndParkingRequest(int parkingTicketId) {
        this.parkingTicketId = parkingTicketId;
    }
}
