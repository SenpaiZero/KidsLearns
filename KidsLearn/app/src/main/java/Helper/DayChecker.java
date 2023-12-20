package Helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DayChecker {

    private static final String PREF_NAME = "MyPrefs";
    private static final String LAST_CHECKED_DATE = "last_checked_date";

    public static boolean isNewDay(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedDate = prefs.getString(LAST_CHECKED_DATE, "");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentDate = new Date();
        String formattedCurrentDate = dateFormat.format(currentDate);

        if (!formattedCurrentDate.equals(savedDate)) {
            // Update the stored date
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(LAST_CHECKED_DATE, formattedCurrentDate);
            editor.apply();
            return true; // It's a new day
        } else {
            return false; // It's not a new day
        }
    }
}