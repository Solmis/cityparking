package net.solmis.cityparking.controllers;

import net.solmis.cityparking.Ticket;
import net.solmis.cityparking.requests.EndParkingRequest;
import net.solmis.cityparking.requests.EndParkingResponse;
import net.solmis.cityparking.requests.ErrorResponse;
import net.solmis.cityparking.requests.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EndParkingController {

    @PostMapping("/endParking")
    public ResponseEntity<Response> endParking(@Valid @RequestBody EndParkingRequest request) {

        Ticket correspondingTicket = Ticket.get(request.parkingTicketId);
        if (correspondingTicket == null)
            return new ResponseEntity<>(new ErrorResponse("Invalid ticket ID."), HttpStatus.BAD_REQUEST);

        if (correspondingTicket.isActive()) {
            correspondingTicket.setEndTimestamp();
            correspondingTicket.save();
        }

        return new ResponseEntity<>(EndParkingResponse.from(correspondingTicket), HttpStatus.OK);

    }

}
