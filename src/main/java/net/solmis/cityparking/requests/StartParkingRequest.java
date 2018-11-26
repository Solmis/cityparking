package net.solmis.cityparking.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.solmis.cityparking.VehicleId;

public class StartParkingRequest {
    public VehicleId parkedVehicle;

    @JsonCreator
    public StartParkingRequest(@JsonProperty("parkedVehicle") final String vehicleId) {
        this.parkedVehicle = new VehicleId(vehicleId);
    }
}
