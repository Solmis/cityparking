package net.solmis.cityparking.requests;

import net.solmis.cityparking.Ticket;
import net.solmis.cityparking.VehicleId;

import java.time.ZonedDateTime;

public class StartParkingResponse extends Response {
    public int ticketId;
    public ZonedDateTime startTimestamp;
    public VehicleId vehicleId;

    private StartParkingResponse(int ticketId, ZonedDateTime startTimestamp, VehicleId vehicleId) {
        this.ticketId = ticketId;
        this.startTimestamp = startTimestamp;
        this.vehicleId = vehicleId;
        this.responseCode = RESPONSE_OK;
    }

    public static StartParkingResponse from(Ticket ticket) {
        return new StartParkingResponse(ticket.getId(), ticket.getStartTimestamp(), ticket.getVehicleId());
    }
}
