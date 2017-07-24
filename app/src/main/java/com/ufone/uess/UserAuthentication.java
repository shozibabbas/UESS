package com.ufone.uess;

import android.util.Log;

/**
 * Created by sayyed.shozib on 7/13/2017.
 */

public class UserAuthentication {
    public static boolean authenticate() {
        if(StorageController.readFloat("lastCallTime") == 0.0f) {
            return false;
        }
        float lastCallTime = StorageController.readFloat("lastCallTime");
        Log.d("LastCallTime", String.valueOf(lastCallTime));
        float temp = System.currentTimeMillis() - lastCallTime;
        Log.d("UserAuthNew", String.valueOf(temp));
        if(temp < 50000)
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
