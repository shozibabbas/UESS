package com.ufone.uess;

/**
 * Created by sayyed.shozib on 7/18/2017.
 *
 * This interface is the communication between AsyncDataFetcher and any activity on which the request is being called
 */

public interface AsyncResponse {
    void processFinish(String output);
}
