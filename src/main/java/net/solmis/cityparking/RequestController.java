package net.solmis.cityparking;

import net.solmis.cityparking.requests.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;

@RestController
public class RequestController {

    @Value("${config.defaultCurrency}")
    private String defaultCurrencyCode;

    public RequestController() {
    }

    @PostMapping("/startParking")
    public StartParkingResponse serveStartParkingRequest(@RequestBody StartParkingRequest request) {
        Ticket newTicket = Ticket.createTicketAndSetStartTimestamp(request.parkedVehicle);
        newTicket.save();
        return StartParkingResponse.from(newTicket);
    }

    @PostMapping("/endParking")
    public EndParkingResponse serveEndParkingRequest(@RequestBody EndParkingRequest request) {
        Ticket correspondingTicket = Ticket.get(request.parkingTicketId);
        if (correspondingTicket == null)
            return EndParkingResponse.createInvalidTicketResponse(request.parkingTicketId);
        else {
            correspondingTicket.setEndTimestamp();
            correspondingTicket.save();
            return EndParkingResponse.from(correspondingTicket);
        }
    }

    @PostMapping("/checkVehicle")
    public CheckVehicleResponse serveCheckVehicleRequest(@RequestBody CheckVehicleRequest request) {
        return CheckVehicleResponse.from(request.parkedVehicle);
    }

    @PostMapping("/getParkingReceipt")
    public GetParkingReceiptResponse serveGetParkingReceiptRequest(@RequestBody GetParkingReceiptRequest request) {
        Ticket correspondingTicket = Ticket.get(request.parkingTicketId);
        if (correspondingTicket == null)
            return GetParkingReceiptResponse.createInvalidTicketResponse(request.parkingTicketId,
                    "No corresponding ticket for given ID.");
        else if (correspondingTicket.isActive())
            return GetParkingReceiptResponse.createInvalidTicketResponse(request.parkingTicketId,
                    "First end parking before trying to get a receipt.");
        else
        {
            Currency defaultCurrency = Currency.getInstance(defaultCurrencyCode);
            return createGetParkingReceiptResponse(correspondingTicket, request.driverType, defaultCurrency);
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
