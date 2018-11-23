package net.solmis.cityparking;

import java.util.Currency;

public class Application {

    private Currency defaultCurrency;

    private static Application ourInstance = new Application();

    public static Application getInstance() {
        return ourInstance;
    }

    private Application() {
        this.defaultCurrency = Currency.getInstance("PLN");
    }

    public static void main(String args[]) {
    }

    public Currency getDefaultCurrency() {
        return this.defaultCurrency;
    }
}
