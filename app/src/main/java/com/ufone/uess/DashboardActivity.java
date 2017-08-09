package com.ufone.uess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // authentication
        if(!UserAuthentication.authenticate()) {
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    public void openEmployeeProfile(View v) {
        startActivity(new Intent(DashboardActivity.this, EmployeeProfileActivity.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    public void openLeaveBalance(View v) {
        startActivity(new Intent(DashboardActivity.this, LeaveBalanceActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void openProvidentFundBalance(View v) {
        startActivity(new Intent(DashboardActivity.this, ProvidentFundBalanceActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void openMedicalService(View v) {
        startActivity(new Intent(DashboardActivity.this, MedicalServiceActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void openAskHR(View v) {
        startActivity(new Intent(DashboardActivity.this, AskHRActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    // creating sign out box
    public void backPress(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UserAuthentication.unauthenticate();
                        finish();
                        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
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

    @Override
    public void onBackPressed() {
        if(UserAuthentication.authenticate()) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        else {
            finish();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        }

    }


}
