package com.ufone.uess;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MedicalServiceActivity extends Activity {

    //private static final String objUrl = "http://172.16.105.190/hospitalsList.txt";
    private static final String objUrl = "http://cyberdev.me/hospitalslist.json";

    String[] languages = {"C", "C++", "Java", "C#", "PHP", "JavaScript", "jQuery", "AJAX", "JSON"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_service);


        MyAsyncTask tsk = new MyAsyncTask();
        tsk.execute();
    }

    class MyAsyncTask extends AsyncTask<String, String, String> implements AdapterView.OnItemSelectedListener {


        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                URL url = new URL(objUrl);
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
            ((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);

            try {
                JSONObject jo = new JSONObject(result);
                ArrayList<String> names = new ArrayList<>();
                names.add("Select Region");
                JSONObject regions = jo.getJSONObject("region");
                Iterator<?> keys = regions.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    names.add(regions.getJSONObject(key).get("name").toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MedicalServiceActivity.this, android.R.layout.select_dialog_item, names.toArray(new String[0]));
                Spinner spnr = (Spinner) findViewById(R.id.spinner);
                spnr.setOnItemSelectedListener(this);
                spnr.setAdapter(adapter);
                //AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.languages);
                //acTextView.setThreshold(1);
                //acTextView.setAdapter(adapter);
            } catch (Exception e) {
                ((TextView) findViewById(R.id.textView3)).setText(e.toString());
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
            switch (pos) {
                case 0:
                    Toast.makeText(parent.getContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
