package net.solmis.cityparking.requests;

import net.solmis.cityparking.RateCalculator;
import net.solmis.cityparking.Ticket;
import net.solmis.cityparking.exceptions.CurrencyNotSupportedException;

import java.util.Currency;

public class GetParkingReceiptResponse extends Response {

    public long ticketId;
    public String driverType;
    public String currency;
    public long totalPriceInCents;
    public long totalTimeInSeconds;

    private GetParkingReceiptResponse(long ticketId) {
        this.ticketId = ticketId;
    }

    public static GetParkingReceiptResponse createForRegular(Ticket ticket, Currency currency) {
        GetParkingReceiptResponse response = new GetParkingReceiptResponse(ticket.getId());
        response.responseCode = RESPONSE_OK;
        response.driverType = GetParkingReceiptRequest.DRIVER_REGULAR;
        response.calculateAndFillRegularReceiptData(ticket, currency);
        return response;
    }

    public static GetParkingReceiptResponse createForDisabled(Ticket ticket, Currency currency) {
        GetParkingReceiptResponse response = new GetParkingReceiptResponse(ticket.getId());
        response.responseCode = RESPONSE_OK;
        response.driverType = GetParkingReceiptRequest.DRIVER_DISABLED;
        response.calculateAndFillDisabledReceiptData(ticket, currency);
        return response;
    }

    public static GetParkingReceiptResponse createInvalidTicketResponse(long ticketId, String errorMsg) {
        GetParkingReceiptResponse errResponse = new GetParkingReceiptResponse(ticketId);
        errResponse.responseCode = RESPONSE_INVALID_INPUT;
        errResponse.message = errorMsg;
        return errResponse;
    }

    public static GetParkingReceiptResponse createInvalidDriverTypeResponse(long ticketId, String driverType) {
        GetParkingReceiptResponse errResponse = new GetParkingReceiptResponse(ticketId);
        errResponse.responseCode = RESPONSE_INVALID_INPUT;
        errResponse.message = "Invalid driverType `" + driverType + "`.";
        return errResponse;
    }

    private void calculateAndFillRegularReceiptData(Ticket ticket, Currency currency) {
        this.currency = currency.getCurrencyCode();
        this.totalTimeInSeconds = ticket.getParkingTimeInSeconds();
        try {
            this.totalPriceInCents = RateCalculator.calculateRegularCostInCents(this.totalTimeInSeconds, currency);
        } catch (CurrencyNotSupportedException e) {
            this.makeInvalidCurrencyResponse(currency);
        }
    }

    private void calculateAndFillDisabledReceiptData(Ticket ticket, Currency currency) {
        this.currency = currency.getCurrencyCode();
        this.totalTimeInSeconds = ticket.getParkingTimeInSeconds();
        try {
            this.totalPriceInCents = RateCalculator.calculateDisabledCostInCents(this.totalTimeInSeconds, currency);
        } catch (CurrencyNotSupportedException e) {
            this.makeInvalidCurrencyResponse(currency);
        }
    }

    private void makeInvalidCurrencyResponse(Currency currency) {
        this.responseCode = RESPONSE_INVALID_INPUT;
        this.message = "Currency `" + currency.getCurrencyCode() + "` is not supported.";
    }
}
