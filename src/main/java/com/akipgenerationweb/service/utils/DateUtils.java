package com.akipgenerationweb.service.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final DateTimeFormatter brDateTimeFormatterWithZone = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm (z)");

    public static final DateTimeFormatter brDateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String getFormattedCurrentDateBrt() {
        ZonedDateTime nowBrt = ZonedDateTime.now(ZoneId.of("Brazil/East"));
        return brDateTimeFormatterWithZone.format(nowBrt);
    }

    public static LocalDateTime getLocalDateTimeBrt() {
        ZonedDateTime nowBrt = ZonedDateTime.now(ZoneId.of("Brazil/East"));
        return nowBrt.toLocalDateTime();
    }
}
