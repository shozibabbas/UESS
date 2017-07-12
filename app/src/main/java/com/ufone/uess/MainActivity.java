package com.ufone.uess;

import org.ksoap2.serialization.SoapObject;
import com.ufone.transport.Connection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					DataFetcher df = new DataFetcher();
					df.setRequestPath("GetEmployessJSON");
					df.setResponsePath("GetEmployessJSONResult");
					df.execute();
					String r = df.getJSONArray().toString();
					TextView tv = (TextView) findViewById(R.id.tv1);
					tv.setText(r);
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/**
 	Following code is responsible for fetching data from the API
  	DataFetcher df = new DataFetcher();
 	df.setRequestPath("UserAuthenticated");
 	df.setResponsePath("UserAuthenticatedResult");
 	Map<String, String> m = new HashMap<String, String>();
 	m.put("username", "sayyed.shozib");
 	m.put("pwd", "6242746942naq-V");
 	df.setRequestParams(m);
 	df.execute();
 	String r = df.getResponse();
 	TextView tv = (TextView) findViewById(R.id.tv1);
 	tv.setText(r);
 **/
