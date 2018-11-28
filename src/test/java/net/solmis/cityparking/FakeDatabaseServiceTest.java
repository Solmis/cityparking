package net.solmis.cityparking;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FakeDatabaseServiceTest {

    static FakeDatabaseService fakeDb;

    @BeforeAll
    public static void setUp() {
        fakeDb = FakeDatabaseService.getInstance();
    }

    @Test
    public void insertAndGetTicket() {
        Ticket ticket = Ticket.createTicketAndSetStartTimestamp(null);
        ticket.save();
        Ticket sameTicket = fakeDb.getTicket(ticket.getId());
        assertNotNull(sameTicket);
        assertEquals(ticket.getId(), sameTicket.getId());
        assertEquals(ticket.getVehicleId(), sameTicket.getVehicleId());
        assertEquals(ticket.getStartTimestamp(), sameTicket.getStartTimestamp());
    }

    @Test
    public void insertAndGetPayment() {
        Payment payment = new Payment();
        payment.save();
        Payment samePayment = fakeDb.getPayment(payment.getId());
        assertNotNull(samePayment);
        assertEquals(payment.getId(), samePayment.getId());
    }

}