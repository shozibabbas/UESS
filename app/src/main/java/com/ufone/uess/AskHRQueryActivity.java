package com.ufone.uess;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class AskHRQueryActivity extends Activity {

    Spinner ask_hr_Spinner;
    EditText ask_hr_Query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_hrquery);

        ((TextView) findViewById(R.id.titlebarTitle)).setText(R.string.askHRQuery);
        ((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);

        ask_hr_Spinner = (Spinner) findViewById(R.id.ask_hr_Spinner);
        ask_hr_Query = (EditText) findViewById(R.id.ask_hr_Query);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, R.layout.spinner_item);

        ask_hr_Spinner.setAdapter(adapter);
    }


    public void submitHRQuery(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(AskHRQueryActivity.this);
        builder.setTitle("Send Query")
                .setMessage("Are you sure you want to send your query?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Query Sent. You will be contacted shortly.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AskHRQueryActivity.this, AskHRActivity.class));
                        finish();
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

    public void backButton(View v) {
        startActivity(new Intent(AskHRQueryActivity.this, AskHRActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AskHRQueryActivity.this, AskHRActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    // creating sign out box
    public void backPress(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(AskHRQueryActivity.this);
        builder.setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UserAuthentication.unauthenticate();
                        Intent intent = new Intent(AskHRQueryActivity.this, LoginActivity.class);
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
                .show();
    }
}
