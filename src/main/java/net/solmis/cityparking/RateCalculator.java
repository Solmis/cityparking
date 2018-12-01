package net.solmis.cityparking;

import net.solmis.cityparking.exceptions.CurrencyNotSupportedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Currency;

@Component
public class RateCalculator {

    private static long centsForUnit = 100;
    private static String defaultCurrencyCode;

    @Value("${config.defaultCurrency}")
    public void setDefaultCurrencyCode(String code) {
        defaultCurrencyCode = code;
    }

    public static long calculateRegularCostInCents(long totalSeconds, Currency currency) throws CurrencyNotSupportedException {
        long parkingHours = RateCalculator.toParkingHours(totalSeconds);
        try {
            double currencyFactor = RateCalculator.getCurrencyFactor(currency);
            return calculateCost(parkingHours, currencyFactor, centsForUnit, 2*centsForUnit, 1.5);
        } catch (CurrencyNotSupportedException e) {
            throw e;
        }
    }

    public static long calculateDisabledCostInCents(long totalSeconds, Currency currency) throws CurrencyNotSupportedException {
        long parkingHours = RateCalculator.toParkingHours(totalSeconds);
        try {
            double currencyFactor = RateCalculator.getCurrencyFactor(currency);
            return calculateCost(parkingHours, currencyFactor, 0, 2 * centsForUnit, 1.2);
        } catch (CurrencyNotSupportedException e) {
            throw e;
        }
    }

    private static long toParkingHours(long seconds) {
        long secondsInHour = 3600;
        long fullHours = seconds / secondsInHour;
        long secondsMinusFullHours = seconds % secondsInHour;
        if (secondsMinusFullHours > 0)
            return fullHours + 1;
        else
            return fullHours;
    }

    // Function implementing price table
    private static long calculateCost(long totalHours, double currencyFactor, long firstHourPrice, long secondHourPrice,
                                      double nextHourMultiplier) {
        double resultCost = 0.0;

        if (totalHours >= 1)
            resultCost += firstHourPrice;
        if (totalHours >= 2)
            resultCost += secondHourPrice;

        double lastHourCost = secondHourPrice;
        for (long i = 0; i < totalHours - 2; i++) {
            lastHourCost *= nextHourMultiplier;
            resultCost += lastHourCost;
        }

        return (long) Math.ceil(resultCost * currencyFactor);
    }

    private static double getCurrencyFactor(Currency currency) throws CurrencyNotSupportedException {
        if (currency.getCurrencyCode().equals(defaultCurrencyCode))
            return 1.0;
        else
            throw new CurrencyNotSupportedException();
    }
}
