package net.solmis.cityparking.requests;

import net.solmis.cityparking.Ticket;
import net.solmis.cityparking.VehicleId;

public class CheckVehicleResponse extends Response {
    public VehicleId vehicleId;
    public boolean hasTicket;

    private CheckVehicleResponse(VehicleId vehicleId, boolean hasTicket) {
        this.vehicleId = vehicleId;
        this.hasTicket = hasTicket;
    }

    public static Response from(VehicleId parkedVehicle) {
        return new CheckVehicleResponse(parkedVehicle, Ticket.existsFor(parkedVehicle));
    }
}
