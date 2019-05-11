package com.avadhesh.firedemotask;

import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;


/**
 * @author avadhesh mourya
 * Created by ubuntu on 1/2/18.
 */

public class Utility {
    private static final String TAG = Utility.class.getSimpleName();





    public static String getFormattedDate(long sosTimeInMillis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(sosTimeInMillis);
        TimeZone tz = smsTime.getTimeZone();
        Calendar now = Calendar.getInstance();
        now.setTimeZone(tz);
        Log.d("timezone", "" + smsTime.getTime());
        final String timeFormatString = "hh:mm aa";
        final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";

        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "Today " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("EEE hh:mm aa", smsTime).toString();
        }
    }


}
