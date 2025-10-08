package com.example.flashcard.utils;

import java.util.Calendar;
import java.util.Date;

public class DateComparaison {
    public static boolean isAtLeastOneMonthApart(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        long diffInMillis = Math.abs(date2.getTime() - date1.getTime());
        long daysDiff = diffInMillis / (1000 * 60 * 60 * 24);
        return daysDiff >= 30;
    }
}
