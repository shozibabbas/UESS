package com.ufone.uess;

import android.os.AsyncTask;

import com.ufone.transport.Connection;

import org.ksoap2.serialization.SoapObject;

import java.util.Map;

/**
 * Created by sayyed.shozib on 7/18/2017.
 */

public class AsyncDataFetcher extends AsyncTask<String, String, String> {

    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... params) {
        try {
            Connection connection = new Connection("GetEmpProfileInfo");
            connection.addProperties("key", StorageController.readString("Emp_No"));
            connection.addProperties("emp_no", "3602");
            connection.connectForSingleNode();
            SoapObject soapObject = connection.Result();

            String result = soapObject.getPropertyAsString("GetEmpProfileInfoResult");
            return result;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}
