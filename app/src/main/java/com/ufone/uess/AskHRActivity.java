package com.ufone.uess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AskHRActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_hr);

        ((TextView) findViewById(R.id.titlebarTitle)).setText(R.string.askHR);
        ((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);

        GetQueryDetails saq = new GetQueryDetails();
    }

    public void openCreateHR(View v) {
        finish();
        startActivity(new Intent(AskHRActivity.this, AskHRQueryActivity.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void expandView(View v) {

    }

    public void backButton(View v) {
        startActivity(new Intent(AskHRActivity.this, DashboardActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AskHRActivity.this, DashboardActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    // creating sign out box
    public void backPress(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(AskHRActivity.this);
        builder.setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UserAuthentication.unauthenticate();
                        Intent intent = new Intent(AskHRActivity.this, LoginActivity.class);
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

    public void viewComments(View v) {
        CustomDialogClass cdd = new CustomDialogClass(AskHRActivity.this);
        cdd.show();
    }

    private class ShowAllQueries implements AsyncResponse {
        ShowAllQueries() {
            AsyncDataFetcher df = new AsyncDataFetcher();
            df.delegate = ShowAllQueries.this;
            df.setURL("http://172.16.105.190/AskHR.asmx");
            df.setRequestPath("Get_All_Comments");
            df.setResponsePath("Get_All_CommentsResult");
            Map<String, String> m = new HashMap<>();
            m.put("ask_id", "3");
            m.put("key", StorageController.readString("Emp_No"));
            df.setRequestParams(m);
            df.execute();
        }

        @Override
        public void processFinish(String output) {
            Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
        }
    }

    private class GetQueryDetails implements AsyncResponse {
        GetQueryDetails() {
            AsyncDataFetcher df = new AsyncDataFetcher();
            df.delegate = GetQueryDetails.this;
            df.setURL("http://172.16.105.190/AskHR.asmx");
            df.setRequestPath("Get_Employee_Queries_Details");
            df.setResponsePath("Get_Employee_Queries_DetailsResult");
            Map<String, String> m = new HashMap<>();
            m.put("emp_No", "3939");
            m.put("key", StorageController.readString("Emp_No"));
            df.setRequestParams(m);
            df.execute();
        }

        @Override
        public void processFinish(String output) {
            Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
        }
    }
}
