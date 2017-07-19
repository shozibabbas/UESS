package com.ufone.uess;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EmployeeProfileActivity extends Activity implements AsyncResponse {

    ProgressBar e_p_ProgressBar;
    TextView e_p_EmpNo;
    TextView e_p_NameView;
    TextView e_p_Designation;
    TextView e_p_Location;
    TextView e_p_Organization;
    TextView e_p_CNIC;
    TextView e_p_DOB;
    TextView e_p_Email;
    TextView e_p_AdditionalInfo;
    TextView e_p_PAddress;
    TextView e_p_TAddress;
    TextView e_p_JoiningDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        e_p_ProgressBar = (ProgressBar) findViewById(R.id.e_p_ProgressBar);
        e_p_EmpNo = (TextView) findViewById(R.id.e_p_EmpNo);
        e_p_NameView = (TextView) findViewById(R.id.e_p_NameView);
        e_p_AdditionalInfo = (TextView) findViewById(R.id.e_p_AdditionalInfo);
        e_p_AdditionalInfo.setMovementMethod(new ScrollingMovementMethod());
        e_p_Designation = (TextView) findViewById(R.id.e_p_Designation);
        e_p_Location = (TextView) findViewById(R.id.e_p_Location);
        e_p_Organization = (TextView) findViewById(R.id.e_p_Organization);
        e_p_CNIC = (TextView) findViewById(R.id.e_p_CNIC);
        e_p_DOB = (TextView) findViewById(R.id.e_p_DOB);
        e_p_PAddress = (TextView) findViewById(R.id.e_p_PAddress);
        e_p_Email = (TextView) findViewById(R.id.e_p_Email);
        e_p_TAddress = (TextView) findViewById(R.id.e_p_TAddress);
        e_p_JoiningDate = (TextView) findViewById(R.id.e_p_JoiningDate);

        e_p_ProgressBar.setVisibility(View.VISIBLE);
        AsyncDataFetcher df =new AsyncDataFetcher();
        df.delegate = this;
        df.setRequestPath("GetEmpProfileInfo");
        df.setResponsePath("GetEmpProfileInfoResult");
        Map<String, String> m = new HashMap<>();
        m.put("key", StorageController.readString("Emp_No"));
        m.put("emp_no", "3602");
        df.setRequestParams(m);
        df.execute();
    }

    public void backPress(View v) {
        finish();
    }

    //this override the implemented method from asyncTask
    @Override
    public void processFinish(String output){
        try {
            ((ScrollView) findViewById(R.id.sv)).setVisibility(View.VISIBLE);
            JSONObject response = ((JSONArray) new JSONArray(output)).getJSONObject(0);
            e_p_EmpNo.setText("Emp # " + response.get("Emp_No").toString());
            e_p_NameView.setText(response.get("Name").toString());
            e_p_Designation.setText(response.get("Position_Title").toString() + ", " + response.get("Business_Unit").toString());
            //e_p_Location.setText("Human Resource, Technical, "+ response.get("Location").toString());
            e_p_Organization.setText(response.get("Emp_Status").toString() + "\nLine Manager: " + response.get("Line_Manager").toString()+ "\n" + "Human Resource, Technical\n"+ response.get("Location").toString());
            e_p_CNIC.setText(response.get("CNIC_No").toString());
            //e_p_Email.setText("Service # " + response.get("Service_No").toString() + "\n" + response.get("Email").toString());
            e_p_Email.setText("Service # " + "65618489" + "\n" + "sayyed.shozib@ufone.com");
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

            //e_p_Location.setText(response.get("Department").toString() + ", " + response.get("Sub_Department").toString());
            Iterator<String> keys = response.keys();
            int i = 0;
            while( keys.hasNext() ) {
                while(i < 2) {
                    i = i + 1;
                    keys.next();
                }
                String key = (String)keys.next();
                e_p_AdditionalInfo.append(Html.fromHtml("<p><small><b>" + key + " : </b></small><br />" + response.get(key) + "<br /></p>"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        e_p_ProgressBar.setVisibility(View.GONE);
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

}
