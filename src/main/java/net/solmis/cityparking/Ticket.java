package net.solmis.cityparking;

import java.time.ZonedDateTime;

public class Ticket {
    private long id;
    private VehicleId vehicleId;
    private ZonedDateTime startTimestamp;
    private ZonedDateTime endTimestamp;

    private Ticket(VehicleId vehicleId) {
        this.vehicleId = vehicleId;
    }

    public static Ticket createTicketAndSetStartTimestamp(VehicleId vehicleId) {
        Ticket newTicket = new Ticket(vehicleId);
        newTicket.startTimestamp = ZonedDateTime.now();
        return newTicket;
    }

    public static Ticket get(long id) {
        return FakeDatabaseService.getInstance().getTicket(id);
    }

    public static boolean existsFor(VehicleId vehicleId) {
        return FakeDatabaseService.getInstance().existsActiveTicketForVehicleId(vehicleId);
    }

    public void setEndTimestamp() {
        this.endTimestamp = ZonedDateTime.now();
    }

    public void save() {
        if (isRecord())
            FakeDatabaseService.getInstance().updateTicket(this);
        else
            this.id = FakeDatabaseService.getInstance().insertTicket(this);
    }

    public long getParkingTimeInSeconds() {
        return this.endTimestamp.toEpochSecond() - this.startTimestamp.toEpochSecond();
    }

    public long getId() {
        return this.id;
    }

    public ZonedDateTime getStartTimestamp() {
        return this.startTimestamp;
    }

    public ZonedDateTime getEndTimestamp() {
        return this.endTimestamp;
    }

    public VehicleId getVehicleId() {
        return this.vehicleId;
    }

    public boolean isActive() {
        return endTimestamp == null;
    }

    private boolean isRecord() {
        return this.id > 0;
    }
}
