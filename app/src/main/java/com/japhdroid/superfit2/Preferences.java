package com.japhdroid.superfit2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 23.06.2016.
 */
public class Preferences {

    public static void setCacheTimeout(Context context, long timeout) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("cacheTimeout", timeout);
        editor.commit();
    }

    public static long getCacheTimeout(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        long cacheTimeout = sharedPref.getLong("cacheTimeout", 0);
        return cacheTimeout;
    }

    public static void setLastDownloadTime(Context context, long timestamp) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("lastDownloadTime", timestamp);
        editor.commit();
    }

    public static long getLastDownloadTime(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        long timestamp = sharedPref.getLong("lastDownloadTime", 0);
        return timestamp;
    }
}
