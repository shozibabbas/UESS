package com.ufone.uess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
                        Intent intent = new Intent(LeaveBalanceActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        //finish();
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

    @SuppressWarnings("ResourceType")
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

        ArrayList<Integer> imageArray = new ArrayList<>();
        imageArray.add(R.drawable.leavebg1);
        imageArray.add(R.drawable.leavebg3);
        imageArray.add(R.drawable.leavebg2);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.svll);

        try {
            JSONArray ja = new JSONArray(output);


            int imageCount = 0;

            ((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);

            for(int i = 0; i < ja.length(); i++) {
                JSONObject jObject = ja.getJSONObject(i);
                Iterator<?> keys = jObject.keys();

                CardView cardView = new CardView(LeaveBalanceActivity.this);
                {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    int m = getResources().getDimensionPixelSize(R.dimen.space_8);
                    lp.setMargins(m, m, m, 0);
                    lp.gravity = Gravity.CENTER;
                    cardView.setLayoutParams(lp);
                }
                cardView.setCardBackgroundColor(ContextCompat.getColor(LeaveBalanceActivity.this, R.color.maingreen));
                cardView.setRadius(4);
                cardView.setCardElevation(5);
                linearLayout.addView(cardView);

                RelativeLayout relativeLayout = new RelativeLayout(LeaveBalanceActivity.this);
                {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    relativeLayout.setLayoutParams(lp);
                }
                cardView.addView(relativeLayout);

                ImageView imageView = new ImageView(LeaveBalanceActivity.this);
                imageView.setId(1);
                {
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.card_height));
                    imageView.setLayoutParams(lp);
                }
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(imageArray.get(imageCount++ % 3));
                imageView.setColorFilter(Color.parseColor("#99f58220"));
                relativeLayout.addView(imageView);

                TextView textViewLeaveType = new TextView(LeaveBalanceActivity.this);
                textViewLeaveType.setId(2);
                {
                    int m = getResources().getDimensionPixelSize(R.dimen.space_8);
                    textViewLeaveType.setPadding(m, m, m, m);
                    textViewLeaveType.setGravity(Gravity.LEFT);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    textViewLeaveType.setLayoutParams(lp);
                    textViewLeaveType.setText(jObject.get("Leave_Type").toString());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textViewLeaveType.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Large);
                    } else {
                        textViewLeaveType.setTextAppearance(LeaveBalanceActivity.this, android.R.style.TextAppearance_DeviceDefault_Large);
                    }
                    textViewLeaveType.setTextColor(ContextCompat.getColor(LeaveBalanceActivity.this, android.R.color.white));
                    textViewLeaveType.setTypeface(null, Typeface.BOLD);
                    lp.addRule(RelativeLayout.ALIGN_TOP, imageView.getId());
                    relativeLayout.addView(textViewLeaveType, lp);
                }

                TextView textViewDates = new TextView(LeaveBalanceActivity.this);
                textViewDates.setId(3);
                {
                    int m = getResources().getDimensionPixelSize(R.dimen.space_8);
                    textViewDates.setPadding(m, 0, m, m);
                    textViewDates.setGravity(Gravity.LEFT);
                    textViewDates.setTypeface(null, Typeface.BOLD);

                    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateValue1 = input.parse(jObject.get("Start_Date").toString());
                    Date dateValue2 = input.parse(jObject.get("End_Date").toString());
                    SimpleDateFormat o = new SimpleDateFormat("MMM dd, yy");
                    textViewDates.setText(o.format(dateValue1) + " to " + o.format(dateValue2));
                    textViewDates.setTextColor(ContextCompat.getColor(LeaveBalanceActivity.this, android.R.color.white));

                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.ALIGN_LEFT, imageView.getId());
                    lp.addRule(RelativeLayout.BELOW, textViewLeaveType.getId());
                    relativeLayout.addView(textViewDates, lp);
                }

                TableLayout tableLayout = new TableLayout(LeaveBalanceActivity.this);
                {
                    int m = getResources().getDimensionPixelSize(R.dimen.space_8);
                    tableLayout.setPadding(m, m, m, m);
                    tableLayout.setBackgroundColor(Color.parseColor("#ffffffff"));
                    tableLayout.setColumnStretchable(1, true);
                    //tableLayout.setBackgroundColor(Color.parseColor("#669bba3b"));
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    lp.addRule(RelativeLayout.ALIGN_BOTTOM, imageView.getId());
                    //lp.addRule(RelativeLayout.BELOW, textViewDates.getId());
                    relativeLayout.addView(tableLayout, lp);
                }

                while (keys.hasNext()) {
                    String key = (String)keys.next();

                    if (key.equals("Leave_Type") || key.equals("Start_Date") || key.equals("End_Date"))
                        continue;

                    TableRow tableRow = new TableRow(LeaveBalanceActivity.this);
                    {
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        int m = getResources().getDimensionPixelSize(R.dimen.space_3);
                        tableRow.setPadding(0, m, 0, m);

                        tableLayout.addView(tableRow);
                    }

                    {
                        TextView textView = new TextView(LeaveBalanceActivity.this);
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        textView.setTextColor(ContextCompat.getColor(LeaveBalanceActivity.this, R.color.mainorange));
                        textView.setTypeface(null, Typeface.BOLD);
                        textView.setText(assocArray.get(key));
                        tableRow.addView(textView);
                    }

                    {
                        TextView textView = new TextView(LeaveBalanceActivity.this);
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        textView.setTextColor(ContextCompat.getColor(LeaveBalanceActivity.this, R.color.cardText));
                        textView.setGravity(Gravity.RIGHT);
                        //textView.setTypeface(null, Typeface.BOLD);
                        textView.setText(jObject.get(key).toString());
                        tableRow.addView(textView);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backButton(View v) {
        startActivity(new Intent(LeaveBalanceActivity.this, DashboardActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LeaveBalanceActivity.this, DashboardActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
