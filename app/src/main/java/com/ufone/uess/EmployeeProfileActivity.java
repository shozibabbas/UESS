package com.ufone.uess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EmployeeProfileActivity extends Activity implements AsyncResponse {

    ProgressBar e_p_ProgressBar;
    TextView e_p_EmpNo;
    TextView e_p_NameView;
    TextView e_p_Designation;
    TextView e_p_Location;
    TextView e_p_CNIC;
    TextView e_p_DOB;
    TextView e_p_ServiceNumber;
    TextView e_p_Status;
    TextView e_p_Email;
    TextView e_p_PAddress;
    TextView e_p_TAddress;
    TextView e_p_JoiningDate;
    TextView e_p_LineManager;
    TextView e_p_Department;
    TextView e_p_SubDepartment;
    LinearLayout e_p_Container;
    AlertDialog signoutAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        //user authentication
        if(!UserAuthentication.authenticate())
            startActivity(new Intent(EmployeeProfileActivity.this, LoginActivity.class));

        e_p_Container = (LinearLayout) findViewById(R.id.e_p_Container);
        e_p_ProgressBar = (ProgressBar) findViewById(R.id.progressBarMain);
        e_p_EmpNo = (TextView) findViewById(R.id.e_p_EmpNo);
        e_p_NameView = (TextView) findViewById(R.id.e_p_NameView);
        e_p_Designation = (TextView) findViewById(R.id.e_p_Designation);
        e_p_Location = (TextView) findViewById(R.id.e_p_Location);
        e_p_CNIC = (TextView) findViewById(R.id.e_p_CNIC);
        e_p_DOB = (TextView) findViewById(R.id.e_p_DOB);
        e_p_PAddress = (TextView) findViewById(R.id.e_p_PAddress);
        e_p_ServiceNumber = (TextView) findViewById(R.id.e_p_ServiceNumber);
        e_p_Email = (TextView) findViewById(R.id.e_p_Email);
        e_p_TAddress = (TextView) findViewById(R.id.e_p_TAddress);
        e_p_JoiningDate = (TextView) findViewById(R.id.e_p_JoiningDate);
        e_p_Status = (TextView) findViewById(R.id.e_p_Status);
        e_p_LineManager = (TextView) findViewById(R.id.e_p_LineManager);
        e_p_Department = (TextView) findViewById(R.id.e_p_Department);
        e_p_SubDepartment = (TextView) findViewById(R.id.e_p_SubDepartment);

        TabHost tabHost = null;
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();
        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Organization");
        host.addTab(spec);
        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Personal");
        host.addTab(spec);

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Are you sure?")
                .setTitle("This is some title");
        signoutAlert = builder.create();

        ((TextView) findViewById(R.id.titlebarTitle)).setText(R.string.empProfile);
        e_p_ProgressBar.setVisibility(View.VISIBLE);

        // data request
        AsyncDataFetcher df =new AsyncDataFetcher();
        df.delegate = this;
        df.setRequestPath("GetEmpProfileInfo");
        df.setResponsePath("GetEmpProfileInfoResult");
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

    // function for displaying the sign out box when the user clicks sign out button
    public void backPress(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(EmployeeProfileActivity.this);
        builder.setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UserAuthentication.unauthenticate();
                        Intent intent = new Intent(EmployeeProfileActivity.this, LoginActivity.class);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EmployeeProfileActivity.this, DashboardActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    //this override the implemented method from asyncTask
    @Override
    public void processFinish(String output){
        if (output.equals("")) {
            Toast.makeText(getApplicationContext(), "Error: Cannot fetch data", Toast.LENGTH_LONG).show();
            finish();
            return;
        } else
        try {
            //((ScrollView) findViewById(R.id.sv)).setVisibility(View.VISIBLE);
            JSONObject response = ((JSONArray) new JSONArray(output)).getJSONObject(0);
            //SpannableString uContent = new SpannableString("Emp # " + (Integer.parseInt(response.get("Emp_No").toString())));
            //uContent.setSpan(new UnderlineSpan(), 0, uContent.length(), 0);
            //e_p_EmpNo.setText(response.toString());
            //e_p_EmpNo.setText(uContent);
            e_p_EmpNo.setText("" + (Integer.parseInt(response.get("Emp_No").toString())));
            e_p_NameView.setText(response.get("Name").toString());
            e_p_Designation.setText(response.get("Position_Title").toString() + ", " + response.get("Business_Unit").toString());
            //e_p_Location.setText("Human Resource, Technical, "+ response.get("Location").toString());
            e_p_Status.setText(response.get("Emp_Status").toString().toLowerCase());
            e_p_LineManager.setText(response.get("Line_Manager").toString());
            e_p_Department.setText(response.get("Department").toString());
            e_p_SubDepartment.setText(response.get("Sub_Department").toString());
            e_p_CNIC.setText(response.get("CNIC_No").toString());
            //e_p_Email.setText("Service # " + response.get("Service_No").toString() + "\n" + response.get("Email").toString());
            e_p_ServiceNumber.setText("65618489");
            e_p_Email.setText("sayyed.shozib@ufone.com");
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
            Date dateValue = input.parse(response.get("DOB").toString());
            SimpleDateFormat o = new SimpleDateFormat("MMMM dd, yyyy");
            e_p_DOB.setText(o.format(dateValue));
            e_p_PAddress.setText(response.get("Permanent_Residence").toString());
            e_p_TAddress.setText(response.get("Present_Address").toString());

            input = new SimpleDateFormat("yyyy-MM-dd");
            dateValue = input.parse(response.get("DOJ").toString());
            o = new SimpleDateFormat("MMMM dd, yyyy");
            e_p_JoiningDate.setText(o.format(dateValue));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        e_p_ProgressBar.setVisibility(View.GONE);
        e_p_Container.setVisibility(View.VISIBLE);
    }

    public void showHDateTo(View v) {
        Toast.makeText(getApplicationContext(), "Joining Date", Toast.LENGTH_SHORT).show();
    }
    public void showHEmail(View v) {
        Toast.makeText(getApplicationContext(), "Email Address", Toast.LENGTH_SHORT).show();
    }
    public void showHOrg(View v) {
        Toast.makeText(getApplicationContext(), "Organization Details", Toast.LENGTH_SHORT).show();
    }
    public void showHCNIC(View v) {
        Toast.makeText(getApplicationContext(), "CNIC", Toast.LENGTH_SHORT).show();
    }
    public void showHDOB(View v) {
        Toast.makeText(getApplicationContext(), "Date of Birth", Toast.LENGTH_SHORT).show();
    }
    public void showHTAddress(View v) {
        Toast.makeText(getApplicationContext(), "Present Address", Toast.LENGTH_SHORT).show();
    }
    public void showHPAddress(View v) {
        Toast.makeText(getApplicationContext(), "Permanent Address", Toast.LENGTH_SHORT).show();
    }

    public void backButton(View v) {
        startActivity(new Intent(EmployeeProfileActivity.this, DashboardActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
