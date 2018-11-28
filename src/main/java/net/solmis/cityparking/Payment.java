package net.solmis.cityparking;

import java.time.ZonedDateTime;

public class Payment {
    private long id;
    public ZonedDateTime timestamp;
    public String currencyCode;
    public long amountInCents;
    public long amountInDefaultCurrency;

    public Payment() {
    }

    public void save() {
        if (isRecord())
            FakeDatabaseService.getInstance().updatePayment(this);
        else
            this.id = FakeDatabaseService.getInstance().insertPayment(this);
    }

    public long getId() {
        return this.id;
    }

    private boolean isRecord() {
        return this.id > 0;
    }
}
