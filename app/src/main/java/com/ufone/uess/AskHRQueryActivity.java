package com.ufone.uess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;

public class AskHRQueryActivity extends Activity {

    Spinner ask_hr_Spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_hrquery);

        ((TextView) findViewById(R.id.titlebarTitle)).setText(R.string.askHR);
        ((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);

        ask_hr_Spinner = (Spinner) findViewById(R.id.ask_hr_Spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);

        ask_hr_Spinner.setAdapter(adapter);
    }
}
