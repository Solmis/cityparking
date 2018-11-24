package net.solmis.cityparking.requests;

public class EndParkingRequest {
    public long parkingTicketId;

    public EndParkingRequest(long parkingTicketId) {
        this.parkingTicketId = parkingTicketId;
    }
}
