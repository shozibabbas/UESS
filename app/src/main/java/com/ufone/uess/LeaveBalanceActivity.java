package com.ufone.uess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LeaveBalanceActivity extends Activity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_balance);

        ((TextView) findViewById(R.id.titlebarTitle)).setText(R.string.leaveBalance);
        //((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);

        // user authentication
        if (!UserAuthentication.authenticate())
            startActivity(new Intent(LeaveBalanceActivity.this, LoginActivity.class));

        AsyncDataFetcher df =new AsyncDataFetcher();
        df.delegate = this;
        df.setRequestPath("GetEmpLeaves");
        df.setResponsePath("GetEmpLeavesResult");
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
        if (output.equals("")) {
            Toast.makeText(getApplicationContext(), "Error: Cannot fetch data", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        Map<String, String> assocArray = new HashMap<String, String>();
        assocArray.put("Start_Date", "Start Date");
        assocArray.put("End_Date", "End Date");
        assocArray.put("Leave_Type", "Leave Type");
        assocArray.put("Carry_Fwd_Balance", "Leaves Brought Forward");
        assocArray.put("Current_Year_Entitlement", "Leaves Allowed");
        assocArray.put("Leave_Availed", "Leaves Availed");
        assocArray.put("Avail_Balance", "Available Balance");

        ArrayList<ArrayList<Integer>> colorArray = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> colorScheme = new ArrayList<Integer>();
        colorScheme.add(R.color.bg_row_1_1);
        colorScheme.add(R.color.bg_row_1_2);
        colorArray.add(colorScheme);
        colorScheme = new ArrayList<Integer>();
        colorScheme.add(R.color.bg_row_2_1);
        colorScheme.add(R.color.bg_row_2_2);
        colorArray.add(colorScheme);
        colorScheme = new ArrayList<Integer>();
        colorScheme.add(R.color.bg_row_3_1);
        colorScheme.add(R.color.bg_row_3_2);
        colorArray.add(colorScheme);

        //TextView tv = (TextView) findViewById(R.id.textView4);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.l_b_TableLayout);


        try {
            JSONArray ja = new JSONArray(output);

            int color = 0;
            ((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);
            for(int i = 0; i < ja.length(); i++) {
                JSONObject jObject = ja.getJSONObject(i);
                Iterator<?> keys = jObject.keys();

                ArrayList<Integer> colors = colorArray.get(color % 1);

                int palette = 0;

                TableRow trSp = new TableRow(LeaveBalanceActivity.this);
                trSp.setPadding(8,5,5,8);
                tableLayout.addView(trSp);

                TableRow trTitle = new TableRow(LeaveBalanceActivity.this);
                trTitle.setPadding(0,3,8,3);
                TextView title = new TextView(LeaveBalanceActivity.this);
                SpannableString uContent = new SpannableString(jObject.get("Leave_Type").toString());
                uContent.setSpan(new UnderlineSpan(), 0, uContent.length(), 0);
                title.setTextAppearance(LeaveBalanceActivity.this, android.R.style.TextAppearance_DeviceDefault_Small);
                title.setTextColor(Color.parseColor("#FF000000"));
                title.setTypeface(null, Typeface.BOLD);
                title.setText(uContent);
                trTitle.addView(title);
                tableLayout.addView(trTitle);

                while( keys.hasNext() ) {
                    TableRow tr = new TableRow(LeaveBalanceActivity.this);
                    tr.setBackgroundResource(colors.get(palette % 2));
                    tr.setPadding(8,6,8,6);

                    TextView keyTv = new TextView(LeaveBalanceActivity.this);
                    keyTv.setPadding(0,0,25,0);
                    keyTv.setTypeface(null, Typeface.BOLD);
                    TextView valueTv = new TextView(LeaveBalanceActivity.this);
                    valueTv.setLayoutParams(new TableRow.LayoutParams(1));

                    String key = (String)keys.next();
                    keyTv.setText(assocArray.get(key));

                    if(key.equals("Leave_Type"))
                        continue;

                    if(key.equals("Start_Date") || key.equals("End_Date")) {
                        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateValue = input.parse(jObject.get(key).toString());
                        SimpleDateFormat o = new SimpleDateFormat("MMM dd, yy");
                        valueTv.setText(o.format(dateValue));
                    }
                    else
                        valueTv.setText(jObject.get(key).toString());
                    tr.addView(keyTv);
                    tr.addView(valueTv);
                    tableLayout.addView(tr);
                    palette++;
                }

                color++;
                //tv.append("\n--\n");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            TextView tv = new TextView(LeaveBalanceActivity.this);
            tv.setText(e.toString());
            tableLayout.addView(tv);
        }

    }

    public void backButton(View v) {
        startActivity(new Intent(LeaveBalanceActivity.this, DashboardActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LeaveBalanceActivity.this, DashboardActivity.class));
        finish();
    }
}
