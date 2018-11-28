package net.solmis.cityparking;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FakeDatabaseService {
    private static FakeDatabaseService ourInstance = new FakeDatabaseService();
    private Map<Long, Ticket> tickets;
    private Map<Long, Payment> payments;
    private long nextTicketId;
    private long nextPaymentId;

    public static FakeDatabaseService getInstance() {
        return ourInstance;
    }

    private FakeDatabaseService() {
        this.nextTicketId = 1;
        this.nextPaymentId = 1;
        this.tickets = new HashMap<>();
        this.payments = new HashMap<>();
    }

    public long insertTicket(Ticket ticket) {
        long id = this.nextTicketId;
        this.nextTicketId++;
        this.tickets.put(id, ticket);
        return id;
    }

    public long insertPayment(Payment payment) {
        long id = this.nextPaymentId;
        this.nextPaymentId++;
        this.payments.put(id, payment);
        return id;
    }

    public void updateTicket(Ticket ticket) {
        if (this.tickets.containsKey(ticket.getId()))
            this.tickets.replace(ticket.getId(), ticket);
        else
            handleRecordNotFound();
    }

    public void updatePayment(Payment payment) {
        if (this.payments.containsKey(payment.getId()))
            this.payments.replace(payment.getId(), payment);
        else
            handleRecordNotFound();
    }

    public Ticket getTicket(long id) {
        return this.tickets.get(id);
    }

    public Payment getPayment(long id) {
        return this.payments.get(id);
    }

    public boolean existsActiveTicketForVehicleId(VehicleId vehicleId) {
        // Very slow, but it's just a fake DB
        for (Ticket t: this.tickets.values()) {
            if (t.getVehicleId().equals(vehicleId) && t.isActive())
                return true;
        }
        return false;
    }

    public long getTotalIncomeByDay(LocalDate day) {
        // Very slow, but it's just a fake DB
        long sum = 0;
        for (Payment p: this.payments.values()) {
            if (p.timestamp.toLocalDate().equals(day))
                sum += p.amountInDefaultCurrency;
        }
        return sum;
    }

    private void handleRecordNotFound() {
        //TODO: logger
    }
}
