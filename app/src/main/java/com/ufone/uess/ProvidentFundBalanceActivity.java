package com.ufone.uess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProvidentFundBalanceActivity extends Activity implements AsyncResponse {

    TextView p_f_b_ProvFund;
    TextView p_f_b_StartDate;
    TextView p_f_b_EndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provident_fund_balance_1);
        ((TextView) findViewById(R.id.titlebarTitle)).setText(R.string.providentFundBalance);

        // user authentication
        if (!UserAuthentication.authenticate())
            startActivity(new Intent(ProvidentFundBalanceActivity.this, LoginActivity.class));

        p_f_b_ProvFund = (TextView) findViewById(R.id.p_f_b_ProvFund);
        p_f_b_StartDate = (TextView) findViewById(R.id.p_f_b_StartDate);
        p_f_b_EndDate = (TextView) findViewById(R.id.p_f_b_EndDate);

        AsyncDataFetcher df = new AsyncDataFetcher();
        df.delegate = this;
        df.setRequestPath("GetEmpPF");
        df.setResponsePath("GetEmpPFResult");
        Map<String, String> m = new HashMap<>();
        m.put("key", StorageController.readString("Emp_No"));
        m.put("emp_no", "885");
        df.setRequestParams(m);
        try {
            df.execute();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    // creating sign out box
    public void backPress(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(ProvidentFundBalanceActivity.this);
        builder.setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UserAuthentication.unauthenticate();
                        finish();
                        Intent intent = new Intent(ProvidentFundBalanceActivity.this, LoginActivity.class);
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
        if (output.equals("")) {
            Toast.makeText(getApplicationContext(), "Error: Cannot fetch data", Toast.LENGTH_LONG).show();
            return;
        }
        ((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);
        ((TableLayout) findViewById(R.id.p_f_b_Container)).setVisibility(View.VISIBLE);

        try {
            JSONArray ja = new JSONArray(output);
            JSONObject jObject = ja.getJSONObject(0);

            String n = NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(jObject.get("Net_Prov_Fund").toString()));
            p_f_b_ProvFund.setText("PKR " + n);

            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
            Date dateValue = input.parse(jObject.get("Start_Date").toString());
            SimpleDateFormat o = new SimpleDateFormat("MMM dd, yy");
            p_f_b_StartDate.setText(o.format(dateValue));

            dateValue = input.parse(jObject.get("End_Date").toString());
            p_f_b_EndDate.setText(o.format(dateValue));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void backButton(View v) {
        startActivity(new Intent(ProvidentFundBalanceActivity.this, DashboardActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProvidentFundBalanceActivity.this, DashboardActivity.class));
        finish();
    }
}
