package net.solmis.cityparking.requests;

import net.solmis.cityparking.Ticket;

public class GetParkingReceiptResponse {
    private GetParkingReceiptResponse() {
        //
    }

    public static GetParkingReceiptResponse from(Ticket ticket) {
        return new GetParkingReceiptResponse();
        // TODO: IMPLEMENT
    }

    public static GetParkingReceiptResponse createInvalidTicketResponse(int ticketId) {
        return new GetParkingReceiptResponse();
        // TODO: IMPLEMENT
    }
}
