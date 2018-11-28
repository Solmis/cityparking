package net.solmis.cityparking.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;

public class GetParkingReceiptRequest {

    public static String DRIVER_DISABLED = "disabled";
    public static String DRIVER_REGULAR = "regular";

    private static ArrayList<String> driverTypes;

    @Positive(message="Attribute ticketId should be a positive number.")
    public long ticketId;

    @NotNull(message="Attribute driverType is required.")
    public String driverType;

    @JsonCreator
    public GetParkingReceiptRequest(@JsonProperty("ticketId") final long ticketId,
                                    @JsonProperty("driverType") final String driverType) {
        this.ticketId = ticketId;
        this.driverType = driverType;
    }

    public static ArrayList<String> getDriverTypes() {
        if (driverTypes == null) {
            driverTypes = new ArrayList<>();
            driverTypes.add(DRIVER_DISABLED);
            driverTypes.add(DRIVER_REGULAR);
        }
        return driverTypes;
    }
}
