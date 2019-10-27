package com.example.tendertest1.custom;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    private static SharedPreferences sharedPreferences;
    private static String POSITION = "position";
    private static String PREF = "gallery";
    public Preference (Context context) {
        sharedPreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public static void setGalleryLastPosition (int position) {
        sharedPreferences.edit().putInt(POSITION, position).commit();
    }

    public static int getGalleryLastPosition () {
        return sharedPreferences.getInt(POSITION, 0);
    }
}
