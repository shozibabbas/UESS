package com.ufone.uess;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ufone.transport.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.serialization.SoapObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by sayyed.shozib on 7/12/2017.
 */

public class DataFetcher {
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

    public String getResponse() {
        return response;
    }

    public JSONArray getJSONArray() {
        try {
            return new JSONArray(this.response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void execute() {
        try {
            response = new MyAync().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    protected static class MyAync extends AsyncTask<String, String, String>
    {
        String result = "";

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

                result = soapObject.getPropertyAsString(responsePath);
                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
