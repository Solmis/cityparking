package net.solmis.cityparking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    public void saveShouldChangeIdToPositive() {
        Payment payment = new Payment();
        assertEquals(0, payment.getId());
        payment.save();
        assertNotEquals(0, payment.getId());
    }

}