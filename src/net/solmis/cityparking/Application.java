package net.solmis.cityparking;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Application {
    public static void main(String args[])
    {
        ZoneId zone = ZoneId.of("Europe/Warsaw");
        ZonedDateTime z = ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, zone);
        System.out.println("Seconds in PL on 01/01/1970: " + z.toEpochSecond());
    }
}
