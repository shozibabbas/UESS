package com.ufone.uess;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Map;

public class EmployeeProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        EditText et = (EditText) findViewById(R.id.editText2);
        DataFetcher df = new DataFetcher();
        df.setRequestPath("GetEmpProfileInfo");
        df.setResponsePath("GetEmpProfileInfoResult");
        Map<String, String> m = new HashMap<>();
        m.put("key", StorageController.readString("Emp_No"));
        m.put("emp_no", "3602");
        df.setRequestParams(m);
        df.execute();
        et.setText(df.getResponse());
    }
}
