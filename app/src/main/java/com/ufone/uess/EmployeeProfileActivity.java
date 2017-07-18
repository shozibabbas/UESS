package com.ufone.uess;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Map;

public class EmployeeProfileActivity extends Activity implements AsyncResponse {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);


        et = (EditText) findViewById(R.id.editText2);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        AsyncDataFetcher asyncTask =new AsyncDataFetcher();
        asyncTask.delegate = this;
        asyncTask.setRequestPath("GetEmpProfileInfo");
        asyncTask.setResponsePath("GetEmpProfileInfoResult");
        Map<String, String> m = new HashMap<>();
        m.put("key", StorageController.readString("Emp_No"));
        m.put("emp_no", "3602");
        asyncTask.setRequestParams(m);
        pb.setVisibility(View.VISIBLE);
        asyncTask.execute();
    }

    //this override the implemented method from asyncTask
    @Override
    public void processFinish(String output){
        et.setText(output);
        pb.setVisibility(View.GONE);
    }
}
