package net.solmis.cityparking.controllers;

import net.solmis.cityparking.Authenticator;
import net.solmis.cityparking.requests.CheckVehicleRequest;
import net.solmis.cityparking.requests.CheckVehicleResponse;
import net.solmis.cityparking.requests.ErrorResponse;
import net.solmis.cityparking.requests.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CheckVehicleController {

    @PostMapping("/checkVehicle")
    public ResponseEntity<Response> checkVehicle(@Valid @RequestBody CheckVehicleRequest request) {

        if (!Authenticator.tokenIsValid(request.secretToken))
            return new ResponseEntity<>(new ErrorResponse("Incorrect secretToken."), HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(CheckVehicleResponse.from(request.parkedVehicle), HttpStatus.OK);
    }

}
