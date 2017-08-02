package com.ufone.uess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

    private static final String objUrl = "http://172.16.105.190/hospitalsList.txt";
    private JSONObject hospitalData;
    private ArrayList<String> tempArray;
    private int regionId;
    private int cityId;
    private int hospitalId;

    private ArrayList<String> regionArray;
    private ArrayList<String> cityArray;
    private ArrayList<String> hospitalArray;


    private Spinner regionSpinner;
    private Spinner citySpinner;
    private Spinner hospitalSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_service);
        ((TextView) findViewById(R.id.titlebarTitle)).setText(R.string.medicalServiceRecord);
        ((TextView) findViewById(R.id.titlebarDownloadHelp)).setVisibility(View.VISIBLE);

        tempArray = new ArrayList<String>();
        regionArray = new ArrayList<String>();
        cityArray = new ArrayList<String>();

        regionSpinner = (Spinner) findViewById(R.id.regionSpinner);
        citySpinner = (Spinner) findViewById(R.id.citySpinner);
        hospitalSpinner = (Spinner) findViewById(R.id.hospitalSpinner);

        MyAsyncTask tsk = new MyAsyncTask();
        try {
            tsk.execute();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MedicalServiceActivity.this, DashboardActivity.class));
        finish();
    }

    public void requestMS(View v) throws Exception {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(MedicalServiceActivity.this);
        builder.setTitle("Request Details")
                .setMessage("You have selected " + hospitalData.getJSONObject("region").getJSONObject(regionArray.get(regionId)).getJSONObject("cities").getJSONObject(cityArray.get(cityId)).getJSONObject("hospitals").getJSONObject(hospitalArray.get(hospitalId)).get("name").toString())
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String shortId = hospitalData.getJSONObject("region").getJSONObject(regionArray.get(regionId)).getJSONObject("cities").getJSONObject(cityArray.get(cityId)).getJSONObject("hospitals").getJSONObject(hospitalArray.get(hospitalId)).get("id").toString();
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage("03009849845", null, shortId, null, null);
                            Toast.makeText(getApplicationContext(), "Request Sent", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "\"No\" selected", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }

    // creating sign out box
    public void backPress(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(MedicalServiceActivity.this);
        builder.setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UserAuthentication.unauthenticate();
                        finish();
                        Intent intent = new Intent(MedicalServiceActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "\"No\" selected", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void backButton(View v) {
        startActivity(new Intent(MedicalServiceActivity.this, DashboardActivity.class));
        finish();
    }

    public void helpDownload(View v) {
        startActivity(new Intent(MedicalServiceActivity.this, HelpActivity.class));
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
            if (result.equals("")) {
                Toast.makeText(getApplicationContext(), "Error: Cannot fetch data", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
            ((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);

            try {
                hospitalData = new JSONObject(result);
                ArrayList<String> names = new ArrayList<>();
                names.add("Select Region");
                regionArray.add("0");
                JSONObject regions = hospitalData.getJSONObject("region");
                Iterator<?> keys = regions.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    names.add(regions.getJSONObject(key).get("name").toString());
                    regionArray.add(key);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MedicalServiceActivity.this, R.layout.spinner_item, names.toArray(new String[0]));

                regionSpinner.setOnItemSelectedListener(this);
                regionSpinner.setAdapter(adapter);

                ((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.m_s_Container)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.m_s_Buttons)).setVisibility(View.VISIBLE);
                ((Spinner) findViewById(R.id.regionSpinner)).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.regionName)).setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
            try {
                switch (parent.getId()) {
                    case R.id.regionSpinner:
                        switch (pos) {
                            case 0:
                                ((Button) findViewById(R.id.m_s_Submit)).setEnabled(false);
                                ((Button) findViewById(R.id.m_s_Submit)).setClickable(false);
                                ((Spinner) findViewById(R.id.citySpinner)).setVisibility(View.GONE);
                                ((TextView) findViewById(R.id.cityName)).setVisibility(View.GONE);
                                ((Spinner) findViewById(R.id.hospitalSpinner)).setVisibility(View.GONE);
                                ((TextView) findViewById(R.id.hospName)).setVisibility(View.GONE);
                                break;
                            default:
                                regionId = pos;
                                JSONObject cities = hospitalData.getJSONObject("region").getJSONObject(regionArray.get(pos)).getJSONObject("cities");
                                ArrayList<String> names = new ArrayList<>();
                                names.add("Select City");
                                cityArray = new ArrayList<>();
                                cityArray.add("0");
                                Iterator<?> keys = cities.keys();
                                while (keys.hasNext()) {
                                    String key = (String) keys.next();
                                    if (!cities.getJSONObject(key).has("name"))
                                        continue;
                                    names.add(cities.getJSONObject(key).get("name").toString());
                                    cityArray.add(key);
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MedicalServiceActivity.this, R.layout.spinner_item, names.toArray(new String[0]));

                                citySpinner.setOnItemSelectedListener(this);
                                citySpinner.setAdapter(adapter);

                                ((Spinner) findViewById(R.id.citySpinner)).setVisibility(View.VISIBLE);
                                ((TextView) findViewById(R.id.cityName)).setVisibility(View.VISIBLE);
                                ((Spinner) findViewById(R.id.hospitalSpinner)).setVisibility(View.GONE);
                                ((TextView) findViewById(R.id.hospName)).setVisibility(View.GONE);
                                ((Button) findViewById(R.id.m_s_Submit)).setEnabled(false);
                                ((Button) findViewById(R.id.m_s_Submit)).setClickable(false);
                                break;
                        }
                        break;
                    case R.id.citySpinner:
                        switch (pos) {
                            case 0:
                                ((Button) findViewById(R.id.m_s_Submit)).setEnabled(false);
                                ((Button) findViewById(R.id.m_s_Submit)).setClickable(false);
                                ((Spinner) findViewById(R.id.hospitalSpinner)).setVisibility(View.GONE);
                                ((TextView) findViewById(R.id.hospName)).setVisibility(View.GONE);
                                break;
                            default:
                                cityId = pos;
                                JSONObject hospitals = hospitalData.getJSONObject("region").getJSONObject(regionArray.get(regionId)).getJSONObject("cities").getJSONObject(cityArray.get(pos)).getJSONObject("hospitals");
                                ArrayList<String> names = new ArrayList<>();
                                names.add("Select Hospital");
                                hospitalArray = new ArrayList<>();
                                hospitalArray.add("0");
                                Iterator<?> keys = hospitals.keys();
                                while (keys.hasNext()) {
                                    String key = (String) keys.next();
                                    names.add(hospitals.getJSONObject(key).get("name").toString());
                                    hospitalArray.add(key);
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MedicalServiceActivity.this, R.layout.spinner_item, names.toArray(new String[0]));

                                hospitalSpinner.setOnItemSelectedListener(this);
                                hospitalSpinner.setAdapter(adapter);

                                ((Spinner) findViewById(R.id.hospitalSpinner)).setVisibility(View.VISIBLE);
                                ((TextView) findViewById(R.id.hospName)).setVisibility(View.VISIBLE);
                                ((Button) findViewById(R.id.m_s_Submit)).setEnabled(false);
                                ((Button) findViewById(R.id.m_s_Submit)).setClickable(false);
                                break;
                        }
                        break;
                    case R.id.hospitalSpinner:
                        switch (pos) {
                            case 0:
                                ((Button) findViewById(R.id.m_s_Submit)).setEnabled(false);
                                ((Button) findViewById(R.id.m_s_Submit)).setClickable(false);
                                break;
                            default:
                                hospitalId = pos;
                                ((Button) findViewById(R.id.m_s_Submit)).setEnabled(true);
                                ((Button) findViewById(R.id.m_s_Submit)).setClickable(true);
                                break;
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }


    }
}
