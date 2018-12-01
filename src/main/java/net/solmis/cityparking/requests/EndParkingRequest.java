package net.solmis.cityparking.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Positive;

public class EndParkingRequest {

    @Positive(message="Attribute ticketId should be a positive number.")
    public long ticketId;

    @JsonCreator
    public EndParkingRequest(@JsonProperty("ticketId") final long ticketId) {
        this.ticketId = ticketId;
    }
}
