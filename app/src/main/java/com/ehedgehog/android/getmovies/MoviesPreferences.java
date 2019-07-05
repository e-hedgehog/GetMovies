package com.ehedgehog.android.getmovies;

import android.content.Context;
import android.preference.PreferenceManager;

public class MoviesPreferences {

    private static final String PREF_TYPE = "type";
    private static final String PREF_QUERY = "query";

    public static int getStoredType(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_TYPE, 0);
    }

    public static void setStoredType(Context context, int type) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_TYPE, type)
                .apply();
    }

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_QUERY, "");
    }

    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_QUERY, query)
                .apply();
    }

}
