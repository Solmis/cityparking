package net.solmis.cityparking.requests;

import net.solmis.cityparking.VehicleId;

public class CheckVehicleRequest {
    public VehicleId parkedVehicle;

    public CheckVehicleRequest(VehicleId parkedVehicle) {
        this.parkedVehicle = parkedVehicle;
    }
}
