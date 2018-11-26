package net.solmis.cityparking.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.solmis.cityparking.VehicleId;

import javax.validation.constraints.NotNull;

public class CheckVehicleRequest {

    @NotNull(message="Attribute parkedVehicle is required.")
    public VehicleId parkedVehicle;

    @NotNull(message="Attribute secretToken is required.")
    public String secretToken;

    @JsonCreator
    public CheckVehicleRequest(@JsonProperty("parkedVehicle") final String vehicleId,
                               @JsonProperty("secretToken") final String secretToken) {
        if (vehicleId != null)
            this.parkedVehicle = new VehicleId(vehicleId);
        this.secretToken = secretToken;
    }
}
