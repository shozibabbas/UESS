package com.ufone.uess;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.BoolRes;

/**
 * Created by sayyed.shozib on 7/13/2017.
 */

public class StorageController {
    private static SharedPreferences sharedPref = null;

    public static void setSharedPref(SharedPreferences sharedPref) {
        StorageController.sharedPref = sharedPref;
    }
    public static boolean writeData(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    public static boolean writeData(String key, Long value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, value);
        return editor.commit();
    }
    public static boolean writeData(String key, int value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        return editor.commit();
    }
    public static boolean writeData(String key, float value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }
    public static boolean writeData(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static String readString(String key) {
        return sharedPref.getString(key, "undefined");
    }
    public static int readInt(String key) {
        return sharedPref.getInt(key, -9999);
    }
    public static Long readLong(String key) {
        return sharedPref.getLong(key, -99999);
    }
    public static float readFloat(String key) {
        return sharedPref.getFloat(key, -9999);
    }
    public static Boolean readBoolean(String key) {
        return sharedPref.getBoolean(key, false);
    }

    public static Boolean remove(String key) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key);
        editor.apply();
        return true;
    }
}
