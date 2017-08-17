package com.ufone.uess;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Sayyed Shozib Abbas on 8/16/2017.
 */

public class AsyncJsonFetcher extends AsyncTask<String, String, String> {
    protected static String requestPath = null;
    protected static Map<String, String> requestParams = null; // for keeping parameters to be POSTed to the request path
    protected static String response = null;

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
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
        String result = null;
        try {
            URL url = new URL(requestPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Scanner s = new Scanner(in).useDelimiter("\\A");
            result = s.next();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}
