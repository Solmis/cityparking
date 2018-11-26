package net.solmis.cityparking.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetParkingReceiptRequest {

    public static String DRIVER_DISABLED = "disabled";
    public static String DRIVER_REGULAR = "regular";

    public long parkingTicketId;
    public String driverType;

    @JsonCreator
    public GetParkingReceiptRequest(@JsonProperty("parkedVehicle") final long parkingTicketId,
                                    @JsonProperty("driverType") final String driverType) {
        this.parkingTicketId = parkingTicketId;
        this.driverType = driverType;
    }
}
