package net.solmis.cityparking.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.solmis.cityparking.VehicleId;

public class CheckVehicleRequest {
    public VehicleId parkedVehicle;

    @JsonCreator
    public CheckVehicleRequest(@JsonProperty("parkedVehicle") final String vehicleId) {
        this.parkedVehicle = new VehicleId(vehicleId);
    }
}
