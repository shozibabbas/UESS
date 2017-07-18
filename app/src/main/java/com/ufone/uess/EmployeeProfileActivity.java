package com.ufone.uess;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EmployeeProfileActivity extends Activity implements AsyncResponse {

    ProgressBar e_p_ProgressBar;
    TextView e_p_EmpNo;
    TextView e_p_NameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        e_p_ProgressBar = (ProgressBar) findViewById(R.id.e_p_ProgressBar);
        e_p_EmpNo = (TextView) findViewById(R.id.e_p_EmpNo);
        e_p_NameView = (TextView) findViewById(R.id.e_p_NameView);

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

    //this override the implemented method from asyncTask
    @Override
    public void processFinish(String output){
        try {
            JSONObject response = ((JSONArray) new JSONArray(output)).getJSONObject(0);
            e_p_EmpNo.setText("Emp # " + response.get("Emp_No").toString());
            e_p_NameView.setText(response.get("Name").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        e_p_ProgressBar.setVisibility(View.GONE);
    }
}
