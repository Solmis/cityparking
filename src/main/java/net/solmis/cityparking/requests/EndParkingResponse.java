package net.solmis.cityparking.requests;

import net.solmis.cityparking.Ticket;

import java.time.ZonedDateTime;

public class EndParkingResponse extends  Response {

    public long ticketId;
    public ZonedDateTime endTimestamp;

    private EndParkingResponse(long ticketId, ZonedDateTime endTimestamp) {
        this.ticketId = ticketId;
        this.endTimestamp = endTimestamp;
    }

    public static EndParkingResponse from(Ticket ticket) {
        return new EndParkingResponse(ticket.getId(), ticket.getEndTimestamp());
    }
}
