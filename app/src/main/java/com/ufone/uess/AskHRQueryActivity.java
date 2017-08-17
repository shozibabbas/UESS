package com.ufone.uess;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AskHRQueryActivity extends Activity {

    Spinner ask_hr_Spinner;
    EditText ask_hr_Query;

    protected ArrayList<Integer> typesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_hrquery);

        ((TextView) findViewById(R.id.titlebarTitle)).setText(R.string.askHRQuery);
        ((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);

        ask_hr_Spinner = (Spinner) findViewById(R.id.ask_hr_Spinner);
        ask_hr_Query = (EditText) findViewById(R.id.ask_hr_Query);

        typesArray = new ArrayList<>();
        FillQueryTypes fqt = new FillQueryTypes();
    }


    public void submitHRQuery(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(AskHRQueryActivity.this);
        AlertDialog show = builder.setTitle("Send Query")
                .setMessage("Are you sure you want to send your query?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SubmitQuery sq = new SubmitQuery();
                        Toast.makeText(getApplicationContext(), "Query Sent. You will be contacted shortly.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AskHRQueryActivity.this, AskHRActivity.class));
                        finish();
                        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "\"No\" selected", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public void backButton(View v) {
        startActivity(new Intent(AskHRQueryActivity.this, AskHRActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AskHRQueryActivity.this, AskHRActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    // creating sign out box
    public void backPress(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(AskHRQueryActivity.this);
        builder.setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UserAuthentication.unauthenticate();
                        Intent intent = new Intent(AskHRQueryActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "\"No\" selected", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private class FillQueryTypes implements AsyncResponse {
        FillQueryTypes() {
            AsyncDataFetcher df = new AsyncDataFetcher();
            df.delegate = FillQueryTypes.this;
            df.setRequestPath("Get_query_types");
            df.setResponsePath("Get_query_typesResult");
            Map<String, String> m = new HashMap<>();
            m.put("key", StorageController.readString("Emp_No"));
            df.setRequestParams(m);
            df.setURL("http://172.16.105.190/AskHR.asmx");
            df.execute();
        }

        @Override
        public void processFinish(String output) {
            try {
                JSONArray ja = new JSONArray(output);
                ArrayList<String> names = new ArrayList<>();
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jObject = ja.getJSONObject(i);
                    names.add(jObject.get("query_name").toString());
                    typesArray.add(Integer.parseInt(jObject.get("query_id").toString()));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AskHRQueryActivity.this, R.layout.spinner_item, names.toArray(new String[0]));
                ask_hr_Spinner.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class SubmitQuery implements AsyncResponse {
        SubmitQuery() {
            try {
                AsyncDataFetcher df = new AsyncDataFetcher();
                df.delegate = SubmitQuery.this;
                df.setRequestPath("Insert_Details_Employee");
                df.setResponsePath("Insert_Details_EmployeeResponse");
                df.setURL("http://172.16.105.190/AskHR.asmx");
                Map<String, String> m = new HashMap<>();
                m.put("key", StorageController.readString("Emp_No"));
                m.put("emp_No", "885");
                m.put("created_by", StorageController.readString("Emp_No"));
                m.put("request_type", ask_hr_Spinner.getSelectedItem().toString());
                m.put("comments_text", ask_hr_Query.getText().toString());
                df.setRequestParams(m);
                df.execute();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void processFinish(String output) {
            return;
        }
    }
}
