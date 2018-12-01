package net.solmis.cityparking.controllers;

import net.solmis.cityparking.Authenticator;
import net.solmis.cityparking.FakeDatabaseService;
import net.solmis.cityparking.Payment;
import net.solmis.cityparking.Ticket;
import net.solmis.cityparking.exceptions.CurrencyNotSupportedException;
import net.solmis.cityparking.requests.ErrorResponse;
import net.solmis.cityparking.requests.GetDayIncomeRequest;
import net.solmis.cityparking.requests.GetDayIncomeResponse;
import net.solmis.cityparking.requests.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
public class GetDayIncomeController {

    @Value("${config.defaultCurrency}")
    private String defaultCurrencyCode;

    @PostMapping("/getDayIncome")
    public ResponseEntity<Response> getDayIncome(@Valid @RequestBody GetDayIncomeRequest request) {

        if (!Authenticator.tokenIsValid(request.secretToken))
            return new ResponseEntity<>(new ErrorResponse("Incorrect secretToken."), HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(createResponse(request.day), HttpStatus.OK);

    }

    private GetDayIncomeResponse createResponse(LocalDate day) {
        long dayTotalIncomeInCents = FakeDatabaseService.getInstance().getTotalIncomeByDay(day);
        GetDayIncomeResponse response =  new GetDayIncomeResponse();
        response.currency = defaultCurrencyCode;
        response.day = day.toString();
        response.totalIncomeInCents = dayTotalIncomeInCents;
        return response;
    }
}
