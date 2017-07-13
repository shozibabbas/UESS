package com.ufone.uess;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private ProgressBar progressBar;
    private TextView loggingText;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        loggingText = (TextView) findViewById(R.id.loggingText);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            progressBar.setVisibility(View.VISIBLE);
            loggingText.setVisibility(View.VISIBLE);
            mLoginFormView.setVisibility(View.GONE);
            DataFetcher df = new DataFetcher();
            df.setRequestPath("UserAuthenticated");
            df.setResponsePath("UserAuthenticatedResult");
            Map<String, String> m = new HashMap<>();
            m.put("username", email);
            m.put("pwd", password);
            df.setRequestParams(m);
            df.execute();
            String response = df.getResponse();
            loggingText.setText(response);
            if(response.equals("true")) {
                progressBar.setVisibility(View.GONE);
                loggingText.setVisibility(View.GONE);
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            }
            else {
                progressBar.setVisibility(View.GONE);
                loggingText.setVisibility(View.GONE);
                mLoginFormView.setVisibility(View.VISIBLE);
                mEmailView.setError(getString(R.string.error_incorrect));
                mPasswordView.setError(getString(R.string.error_incorrect));
            }

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains(".") && (!email.contains("@"));
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1 /*&& (!password.matches("[A-Za-z0-9 ]*")) && (!(password.contains("AND") || password.contains("NOT"))) && (!password.equals(password.toLowerCase())) && (!password.equals(password.toUpperCase()))*/;
    }
}

