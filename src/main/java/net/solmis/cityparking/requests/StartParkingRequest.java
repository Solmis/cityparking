package net.solmis.cityparking.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.solmis.cityparking.VehicleId;

import javax.validation.constraints.NotNull;

public class StartParkingRequest {

    @NotNull(message="Attribute parkedVehicle is required.")
    public VehicleId parkedVehicle;

    @JsonCreator
    public StartParkingRequest(@JsonProperty("parkedVehicle") final String vehicleId) {
        if (vehicleId != null)
            this.parkedVehicle = new VehicleId(vehicleId);
    }
}
