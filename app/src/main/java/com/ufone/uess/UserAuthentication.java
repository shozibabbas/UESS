package com.ufone.uess;

import android.util.Log;

/**
 * Created by sayyed.shozib on 7/13/2017.
 */

public class UserAuthentication {

    // change this to true for debugging without authentication
    private final static boolean debugMode = true;

    public static boolean authenticate() {
        if (debugMode)
            return true;
        if(StorageController.readFloat("lastCallTime") == 0.0f) {
            return false;
        }
        float lastCallTime = StorageController.readFloat("lastCallTime");
        Log.d("LastCallTime", String.valueOf(lastCallTime));
        float temp = System.currentTimeMillis() - lastCallTime;
        Log.d("UserAuthNew", String.valueOf(temp));
        if (temp < 500000) // timeout of 50 seconds
            return true;
        else
            return false;
    }

    public static boolean unauthenticate() {
        StorageController.remove("lastCallTime");
        StorageController.remove("Emp_No");
        StorageController.remove("AD_Name");
        return true;
    }
}
