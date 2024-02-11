package com.mfw.myfilewatcher.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@UtilityClass
public class DateUtils {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public static LocalDateTime dateToLocalDateTime(Date dateToConvert) {
        Instant instant = dateToConvert.toInstant();
        return LocalDateTime.ofInstant(
                instant, ZoneId.systemDefault());
    }
}
