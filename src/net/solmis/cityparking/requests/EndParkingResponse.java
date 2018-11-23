package net.solmis.cityparking.requests;

import net.solmis.cityparking.Ticket;

public class EndParkingResponse extends  Response {
    public long ticketId;

    private EndParkingResponse(long ticketId) {
        this.ticketId = ticketId;
    }

    public static EndParkingResponse from(Ticket ticket) {
        EndParkingResponse response =  new EndParkingResponse(ticket.getId());
        response.responseCode = RESPONSE_OK;
        return response;
    }

    public static EndParkingResponse createInvalidTicketResponse(long ticketId) {
        EndParkingResponse response = new EndParkingResponse(ticketId);
        response.responseCode = RESPONSE_INVALID_INPUT;
        response.message = "Invalid ticket ID passed.";
        return response;
    }
}
