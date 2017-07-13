package com.ufone.uess;

/**
 * Created by sayyed.shozib on 7/13/2017.
 */

public class UserAuthentication {
    public static boolean authenticate() {
        float lastCallTime = StorageController.readFloat("lastCallTime");
        return System.currentTimeMillis() - lastCallTime < 300000;
    }
}
