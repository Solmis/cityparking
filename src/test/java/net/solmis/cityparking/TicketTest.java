package net.solmis.cityparking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    public void saveShouldChangeIdToPositive() {
        Ticket ticket = Ticket.createTicketAndSetStartTimestamp(null);
        assertEquals(0, ticket.getId());
        ticket.save();
        assertNotEquals(0, ticket.getId());
    }

    @Test
    public void createdTicketShouldBeActive() {
        Ticket ticket = Ticket.createTicketAndSetStartTimestamp(null);
        assertTrue(ticket.isActive());
    }

    @Test
    public void shouldBeInactiveAfterSettingTimestamp() {
        Ticket ticket = Ticket.createTicketAndSetStartTimestamp(null);
        ticket.setEndTimestamp();
        assertFalse(ticket.isActive());
    }

    @Test
    public void parkingTimeShouldBeZeroOrPositive() {
        Ticket ticket = Ticket.createTicketAndSetStartTimestamp(null);
        assertTrue(ticket.getParkingTimeInSeconds() >= 0);
        ticket.setEndTimestamp();
        assertTrue(ticket.getParkingTimeInSeconds() >= 0);
    }

}