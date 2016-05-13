package com.harun.offloader002;

import com.harun.offloader002.adapters.DetailsCursorAdapter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by HARUN on 5/13/2016.
 */
public class DateHelper {
    public static final String LOG_TAG = DateHelper.class.getSimpleName();

    public static String getFormattedDateTimeString(DetailsCursorAdapter detailsCursorAdapter, long dateInMillis){

        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd/MMMM/yyyy h:mm a");
        DateTime dateTime = new DateTime(Long.valueOf(dateInMillis), DateTimeZone.UTC);
//        Log.w(LOG_TAG, "DateTime: " + dateTime);
        return dateTimeFormat.print(dateTime);
    }

    public static String getFormattedDayString(long dateInMillis){
        DateTimeFormatter dayFormat = DateTimeFormat.forPattern("dd/MMMM/yyyy");
        DateTime dateTime = new DateTime(Long.valueOf(dateInMillis), DateTimeZone.UTC);
        return dayFormat.print(dateTime);
    }
    public static String getFormattedTimeString(long dateInMillis){
        DateTimeFormatter dayFormat = DateTimeFormat.forPattern(" h:mm a");
        DateTime dateTime = new DateTime(Long.valueOf(dateInMillis), DateTimeZone.UTC);
        return dayFormat.print(dateTime);
    }
}
