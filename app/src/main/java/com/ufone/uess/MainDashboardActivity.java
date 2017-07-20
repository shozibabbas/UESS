package com.ufone.uess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainDashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        // authentication
        if(!UserAuthentication.authenticate()) {
            finish();
            startActivity(new Intent(MainDashboardActivity.this, LoginActivity.class));
        }
    }

    public void openEmployeeProfile(View v) {
        finish();
        startActivity(new Intent(MainDashboardActivity.this, EmployeeProfileActivity.class));
    }

    @Override
    public void onBackPressed() {
        if(UserAuthentication.authenticate()) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        else {
            finish();
            startActivity(new Intent(MainDashboardActivity.this, LoginActivity.class));
        }

    }


}
