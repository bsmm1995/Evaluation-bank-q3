package com.bp.cbe.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Date {
    private Date() {
        throw new IllegalStateException("Utility class");
    }

    public static LocalDate addDaysWithoutSaturdayAndSunday(int days) {
        LocalDate dateTime = LocalDate.now();
        int addedDays = 0;
        while (addedDays < days) {
            dateTime = dateTime.plusDays(1);
            if (!(dateTime.getDayOfWeek() == DayOfWeek.SATURDAY
                    || dateTime.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                addedDays++;
            }
        }
        return dateTime;
    }

    public static LocalDate addDays(int days) {
        return LocalDate.now().plusDays(days);
    }
}