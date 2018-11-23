package net.solmis.cityparking;

import net.solmis.cityparking.requests.*;

import java.util.Currency;

public class RequestService {
    private static RequestService ourInstance = new RequestService();

    public static RequestService getInstance() {
        return ourInstance;
    }

    private RequestService() {
    }

    public StartParkingResponse serveStartParkingRequest(StartParkingRequest request) {
        Ticket newTicket = Ticket.createTicketAndSetStartTimestamp(request.parkedVehicle);
        newTicket.save();
        return StartParkingResponse.from(newTicket);
    }

    public EndParkingResponse serveEndParkingRequest(EndParkingRequest request) {
        Ticket correspondingTicket = Ticket.get(request.parkingTicketId);
        if (correspondingTicket == null)
            return EndParkingResponse.createInvalidTicketResponse(request.parkingTicketId);
        else {
            correspondingTicket.setEndTimestamp();
            correspondingTicket.save();
            return EndParkingResponse.from(correspondingTicket);
        }
    }

    public CheckVehicleResponse serveCheckVehicleRequest(CheckVehicleRequest request) {
        return CheckVehicleResponse.from(request.parkedVehicle);
    }

    public GetParkingReceiptResponse serveGetParkingReceiptRequest(GetParkingReceiptRequest request) {
        Ticket correspondingTicket = Ticket.get(request.parkingTicketId);
        if (correspondingTicket == null)
            return GetParkingReceiptResponse.createInvalidTicketResponse(request.parkingTicketId,
                    "No corresponding ticket for given ID.");
        else if (correspondingTicket.isActive())
            return GetParkingReceiptResponse.createInvalidTicketResponse(request.parkingTicketId,
                    "First end parking before trying to get a receipt.");
        else {
            Currency currency = Application.getInstance().getDefaultCurrency();
            return createGetParkingReceiptResponse(correspondingTicket, request.driverType, currency);
        }
    }

    private GetParkingReceiptResponse createGetParkingReceiptResponse(Ticket correspondingTicket, String driverType,
                                                                      Currency currency) {
        if (driverType.equals(GetParkingReceiptRequest.DRIVER_REGULAR))
            return GetParkingReceiptResponse.createForRegular(correspondingTicket, currency);
        else if (driverType.equals(GetParkingReceiptRequest.DRIVER_DISABLED))
            return GetParkingReceiptResponse.createForDisabled(correspondingTicket, currency);
        else
            return GetParkingReceiptResponse.createInvalidDriverTypeResponse(correspondingTicket.getId(), driverType);
    }
}
