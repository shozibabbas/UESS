package com.ufone.uess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AskHRActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_hr);

        ((TextView) findViewById(R.id.titlebarTitle)).setText(R.string.askHR);
        ((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);
    }

    public void openCreateHR(View v) {
        startActivity(new Intent(AskHRActivity.this, AskHRQueryActivity.class));
    }
}
