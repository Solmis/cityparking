package net.solmis.cityparking.controllers;

import net.solmis.cityparking.Ticket;
import net.solmis.cityparking.requests.Response;
import net.solmis.cityparking.requests.StartParkingRequest;
import net.solmis.cityparking.requests.StartParkingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class StartParkingController {

    @PostMapping("/startParking")
    public ResponseEntity<Response> startParking(@Valid @RequestBody StartParkingRequest request) {

        Ticket newTicket = Ticket.createTicketAndSetStartTimestamp(request.parkedVehicle);
        newTicket.save();
        return new ResponseEntity<>(StartParkingResponse.from(newTicket), HttpStatus.OK);

    }

}
