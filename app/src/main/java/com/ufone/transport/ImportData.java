package com.ufone.transport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ImportData extends Activity {
	String string ="";
	
	public void getData(Context context,final String string){

			try {
				File myFile = new File("/sdcard/HealthHistory.txt");
				myFile.createNewFile();
				FileOutputStream fOut = new FileOutputStream(myFile);
				OutputStreamWriter myOutWriter = 
										new OutputStreamWriter(fOut);
				myOutWriter.append(string);
				myOutWriter.close();
				fOut.close();
				//Toast.makeText(getBaseContext(),
					//	"Done writing SD 'mysdfile.txt'",
					//	Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				//Toast.makeText(getBaseContext(), e.getMessage(),
						//Toast.LENGTH_SHORT).show();
			}
		}


}
