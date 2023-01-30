package com.svenhandt.app.cinemaapp.presentationsms.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

public class DataTypeConversionUtil {

    private static final String HOUR_OF_DAY_NOT_VALID = "hour of day not valid: ";
    private static final String MINUTE_NOT_VALID = "minute not valid: ";

    public static Date getFromWeekAndDay(String dayOfWeekStr, String startTimeStr)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, getDayOfWeek(dayOfWeekStr));
        String[] startTimeArr = StringUtils.split(startTimeStr, ":");
        Validate.isTrue(startTimeArr.length == 2, "Start time format invalid: " + startTimeStr);
        calendar.set(Calendar.HOUR_OF_DAY, getHourOfDay(startTimeArr[0]));
        calendar.set(Calendar.MINUTE, getMinute(startTimeArr[1]));
        return calendar.getTime();
    }

    public static String getDayOfWeekAbbrev(Date date)
    {
        String result = StringUtils.EMPTY;
        Validate.notNull(date, "date of presentation must not be null");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case Calendar.MONDAY:
                result = "Mo";
                break;
            case Calendar.TUESDAY:
                result = "Di";
                break;
            case Calendar.WEDNESDAY:
                result = "Mi";
                break;
            case Calendar.THURSDAY:
                result = "Do";
                break;
            case Calendar.FRIDAY:
                result = "Fr";
                break;
            case Calendar.SATURDAY:
                result = "Sa";
                break;
            case Calendar.SUNDAY:
                result = "So";
                break;
        }
        return result;
    }

    public static String getTimeOfDayFormatted(Date date)
    {
        Validate.notNull(date, "date of presentation must not be null");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumIntegerDigits(2);
        return numberFormat.format(calendar.get(Calendar.HOUR_OF_DAY)) + ":" + numberFormat.format(calendar.get(Calendar.MINUTE));
    }

    public static BigDecimal getPrice(String priceStr)
    {
        Validate.matchesPattern(priceStr, "\\d+\\.\\d{2}");
        BigDecimal price = new BigDecimal(priceStr);
        return price;
    }

    private static int getDayOfWeek(String dayOfWeekStr)
    {
        int dayOfWeek;
        if(StringUtils.equals(dayOfWeekStr, "Mo"))
        {
            dayOfWeek = Calendar.MONDAY;
        }
        else if(StringUtils.equals(dayOfWeekStr, "Di"))
        {
            dayOfWeek = Calendar.TUESDAY;
        }
        else if(StringUtils.equals(dayOfWeekStr, "Mi"))
        {
            dayOfWeek = Calendar.WEDNESDAY;
        }
        else if(StringUtils.equals(dayOfWeekStr, "Do"))
        {
            dayOfWeek = Calendar.THURSDAY;
        }
        else if(StringUtils.equals(dayOfWeekStr, "Fr"))
        {
            dayOfWeek = Calendar.FRIDAY;
        }
        else if(StringUtils.equals(dayOfWeekStr, "Sa"))
        {
            dayOfWeek = Calendar.SATURDAY;
        }
        else if(StringUtils.equals(dayOfWeekStr, "So"))
        {
            dayOfWeek = Calendar.SUNDAY;
        }
        else
        {
            throw new IllegalArgumentException("Day of week invalid: " + dayOfWeekStr);
        }
        return dayOfWeek;
    }

    private static int getHourOfDay(String hourOfDayStr)
    {
        Validate.matchesPattern(hourOfDayStr, "\\d{2}", HOUR_OF_DAY_NOT_VALID + hourOfDayStr);
        int hourOfDay = Integer.parseInt(hourOfDayStr);
        Validate.inclusiveBetween(0, 23, hourOfDay, HOUR_OF_DAY_NOT_VALID + hourOfDayStr);
        return hourOfDay;
    }

    private static int getMinute(String minuteStr)
    {
        Validate.matchesPattern(minuteStr, "\\d{2}", MINUTE_NOT_VALID + minuteStr);
        int minute = Integer.parseInt(minuteStr);
        Validate.inclusiveBetween(0, 59, minute, MINUTE_NOT_VALID);
        return minute;
    }

}
