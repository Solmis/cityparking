package net.solmis.cityparking;

import java.util.HashMap;
import java.util.Map;

public class FakeDatabaseService {
    private static FakeDatabaseService ourInstance = new FakeDatabaseService();
    private Map<Integer, Ticket> tickets;
    private int nextTicketId;

    public static FakeDatabaseService getInstance() {
        return ourInstance;
    }

    private FakeDatabaseService() {
        this.nextTicketId = 1;
        this.tickets = new HashMap<>();
    }

    public int insert(Ticket ticket) {
        int id = this.nextTicketId;
        this.nextTicketId++;
        this.tickets.put(id, ticket);
        return id;
    }

    public void update(Ticket ticket) {
        if (this.tickets.containsKey(ticket.getId()))
            this.tickets.replace(ticket.getId(), ticket);
        else
            handleRecordNotFound();
    }

    public Ticket getTicket(int id) {
        return this.tickets.get(id);
    }

    public boolean existsActiveTicketForVehicleId(VehicleId vehicleId) {
        // Very slow, but it's just a fake DB
        for (Ticket t: this.tickets.values()) {
            if (t.getVehicleId().equals(vehicleId) && t.isActive())
                return true;
        }
        return false;
    }

    private void handleRecordNotFound() {
        //TODO: logger
    }
}
