package net.solmis.cityparking.requests;

import net.solmis.cityparking.VehicleId;

public class StartParkingRequest {
    public VehicleId parkedVehicle;

    public StartParkingRequest(VehicleId parkedVehicle) {
        this.parkedVehicle = parkedVehicle;
    }
}
