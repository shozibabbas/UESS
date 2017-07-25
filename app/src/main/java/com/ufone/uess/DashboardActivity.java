package com.ufone.uess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // authentication
        if(!UserAuthentication.authenticate()) {
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        }
    }

    public void openEmployeeProfile(View v) {
        startActivity(new Intent(DashboardActivity.this, EmployeeProfileActivity.class));
        finish();
    }

    public void openLeaveBalance(View v) {
        startActivity(new Intent(DashboardActivity.this, LeaveBalanceActivity.class));
        finish();
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
        }

    }


}
