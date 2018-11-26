package net.solmis.cityparking.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Positive;

public class EndParkingRequest {

    @Positive(message="Attribute parkingTicketId should be a positive number.")
    public long parkingTicketId;

    @JsonCreator
    public EndParkingRequest(@JsonProperty("parkingTicketId") final long parkingTicketId) {
        this.parkingTicketId = parkingTicketId;
    }
}
