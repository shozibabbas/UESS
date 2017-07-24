package com.ufone.uess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LeaveBalanceActivity extends Activity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_balance);

        ((TextView) findViewById(R.id.titlebarTitle)).setText("Leave Balance");

        AsyncDataFetcher df =new AsyncDataFetcher();
        df.delegate = this;
        df.setRequestPath("GetEmpLeaves");
        df.setResponsePath("GetEmpLeavesResult");
        Map<String, String> m = new HashMap<>();
        m.put("key", StorageController.readString("Emp_No"));
        m.put("emp_no", "885");
        df.setRequestParams(m);
        df.execute();
    }

    public void backPress(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(LeaveBalanceActivity.this);
        builder.setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UserAuthentication.unauthenticate();
                        finish();
                        Intent intent = new Intent(LeaveBalanceActivity.this, LoginActivity.class);
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

    @Override
    public void processFinish(String output) {
        TextView tv = (TextView) findViewById(R.id.textView4);

        try {
            JSONArray ja = new JSONArray(output);


            for(int i = 0; i < ja.length(); i++) {
                JSONObject jObject = ja.getJSONObject(i);
                Iterator<?> keys = jObject.keys();
                while( keys.hasNext() ) {
                    String key = (String)keys.next();
                    tv.append("\n" + key + " : " + jObject.get(key));
                }
                tv.append("\n--\n");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void backButton(View v) {
        startActivity(new Intent(LeaveBalanceActivity.this, MainDashboardActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LeaveBalanceActivity.this, MainDashboardActivity.class));
        finish();
    }
}
