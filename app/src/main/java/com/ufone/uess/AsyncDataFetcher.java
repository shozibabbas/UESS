package com.ufone.uess;

import android.os.AsyncTask;

import com.ufone.transport.Connection;

import org.ksoap2.serialization.SoapObject;

import java.util.Map;

/**
 * Created by sayyed.shozib on 7/18/2017.
 *
 * This class is for making SOAP calls with the desired parameters and return text as output
 */

public class AsyncDataFetcher extends AsyncTask<String, String, String> {

    protected static String requestPath = null;
    protected static String responsePath = null;
    protected static Map<String, String> requestParams = null; // for keeping parameters to be POSTed to the request path
    protected static String response = null;
    protected String URL = null;

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

    public void setURL(String URL) {
        this.URL = URL;
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
        Connection connection = new Connection(requestPath);
        if (this.URL != null)
            connection.URL = this.URL;
        if (requestParams != null)

            // for adding parameters to the connection
            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                connection.addProperties(entry.getKey(), entry.getValue());
            }
        connection.connectForSingleNode(); // making connection and retrieving results
        SoapObject soapObject = connection.Result();
        String result = null;
        if (soapObject == null)
            result = "";
        else
            result = soapObject.getPropertyAsString(responsePath);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}
