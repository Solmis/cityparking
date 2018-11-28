package net.solmis.cityparking.controllers;

import net.solmis.cityparking.Payment;
import net.solmis.cityparking.RateCalculator;
import net.solmis.cityparking.Ticket;
import net.solmis.cityparking.exceptions.CurrencyNotSupportedException;
import net.solmis.cityparking.requests.ErrorResponse;
import net.solmis.cityparking.requests.GetParkingReceiptRequest;
import net.solmis.cityparking.requests.GetParkingReceiptResponse;
import net.solmis.cityparking.requests.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Currency;

@RestController
public class GetParkingReceiptController {

    @Value("${config.defaultCurrency}")
    private String defaultCurrencyCode;

    @PostMapping("/getParkingReceipt")
    public ResponseEntity<Response> getParkingReceipt(@Valid @RequestBody GetParkingReceiptRequest request) {

        Ticket correspondingTicket = Ticket.get(request.ticketId);
        if (correspondingTicket == null)
            return new ResponseEntity<>(new ErrorResponse("No corresponding ticket for given ID."),
                    HttpStatus.BAD_REQUEST);
        if (correspondingTicket.isActive())
            return new ResponseEntity<>(new ErrorResponse("First end parking before trying to get a receipt."),
                    HttpStatus.BAD_REQUEST);
        if (!correctDriverType(request.driverType))
            return new ResponseEntity<>(new ErrorResponse("Incorrect driver type."), HttpStatus.BAD_REQUEST);

        Currency defaultCurrency = Currency.getInstance(defaultCurrencyCode);
        try {
            GetParkingReceiptResponse response = createResponse(correspondingTicket, request.driverType, defaultCurrency);
            createFakePayment(response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CurrencyNotSupportedException e) {
            return new ResponseEntity<>(new ErrorResponse("Currency not supported."), HttpStatus.BAD_REQUEST);
        }
    }

    private GetParkingReceiptResponse createResponse(Ticket correspondingTicket, String driverType,
                                                     Currency currency) throws CurrencyNotSupportedException {
        if (driverType.equals(GetParkingReceiptRequest.DRIVER_REGULAR))
            return createForRegular(correspondingTicket, currency);
        else if (driverType.equals(GetParkingReceiptRequest.DRIVER_DISABLED))
            return createForDisabled(correspondingTicket, currency);
        return null;
    }

    // TODO: Remove it after payment handling is added
    private void createFakePayment(GetParkingReceiptResponse response) {
        Payment fakePayment = new Payment();
        fakePayment.currencyCode = response.currency;
        fakePayment.timestamp = ZonedDateTime.now();
        fakePayment.amountInCents = response.totalPriceInCents;
        fakePayment.amountInDefaultCurrency = response.totalPriceInDefaultCurrency;
        fakePayment.save();
    }

    private GetParkingReceiptResponse createForRegular(Ticket ticket, Currency currency)
            throws CurrencyNotSupportedException {
        GetParkingReceiptResponse response = createResponseDriverIndependent(ticket, currency);
        response.driverType = GetParkingReceiptRequest.DRIVER_REGULAR;
        response.totalPriceInCents = RateCalculator.calculateRegularCostInCents(response.totalTimeInSeconds, currency);
        response.totalPriceInDefaultCurrency = RateCalculator.calculateRegularCostInCents(response.totalTimeInSeconds,
                                                Currency.getInstance(defaultCurrencyCode));
        return response;
    }

    private GetParkingReceiptResponse createForDisabled(Ticket ticket, Currency currency)
            throws CurrencyNotSupportedException {
        GetParkingReceiptResponse response = createResponseDriverIndependent(ticket, currency);
        response.driverType = GetParkingReceiptRequest.DRIVER_DISABLED;
        response.totalPriceInCents = RateCalculator.calculateDisabledCostInCents(response.totalTimeInSeconds, currency);
        response.totalPriceInDefaultCurrency = RateCalculator.calculateDisabledCostInCents(response.totalTimeInSeconds,
                                                Currency.getInstance(defaultCurrencyCode));
        return response;
    }

    private GetParkingReceiptResponse createResponseDriverIndependent(Ticket ticket, Currency currency) {
        GetParkingReceiptResponse response = new GetParkingReceiptResponse();
        response.ticketId = ticket.getId();
        response.currency = currency.getCurrencyCode();
        response.defaultCurrency = defaultCurrencyCode;
        response.totalTimeInSeconds = ticket.getParkingTimeInSeconds();
        return response;
    }

    private boolean correctDriverType(String driverType) {
        return GetParkingReceiptRequest.getDriverTypes().contains(driverType);
    }
}
