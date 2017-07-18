package com.ufone.uess;

import android.os.AsyncTask;

import com.ufone.transport.Connection;

import org.ksoap2.serialization.SoapObject;

import java.util.Map;

/**
 * Created by sayyed.shozib on 7/18/2017.
 */

public class AsyncDataFetcher extends AsyncTask<String, String, String> {

    protected static String requestPath = null;
    protected static String responsePath = null;
    protected static Map<String, String> requestParams = null;
    protected static String response = null;

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getResponsePath() {
        return responsePath;
    }

    public void setResponsePath(String responsePath) {
        this.responsePath = responsePath;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams;
    }

    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... params) {
        try {
            Connection connection = new Connection(requestPath);
            if(requestParams != null)
                for (Map.Entry<String, String> entry : requestParams.entrySet())
                {
                    connection.addProperties(entry.getKey(), entry.getValue());
                }
            connection.connectForSingleNode();
            SoapObject soapObject = connection.Result();

            String result = soapObject.getPropertyAsString(responsePath);
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
