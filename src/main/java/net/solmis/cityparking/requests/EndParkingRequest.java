package net.solmis.cityparking.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EndParkingRequest {
    public long parkingTicketId;

    @JsonCreator
    public EndParkingRequest(@JsonProperty("parkingTicketId") final long parkingTicketId) {
        this.parkingTicketId = parkingTicketId;
    }
}
