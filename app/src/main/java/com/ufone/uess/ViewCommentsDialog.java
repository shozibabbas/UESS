package com.ufone.uess;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by sayyed.shozib on 8/9/2017.
 */

public class ViewCommentsDialog extends Dialog implements
        android.view.View.OnClickListener {

    private Activity activity;
    private Button closeButton;
    private int askId;

    public ViewCommentsDialog(Activity a, int b) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.askId = b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ask_hr_comments);
        closeButton = (Button) findViewById(R.id.btn_no);
        closeButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    private class GetQueryDetails implements AsyncResponse {
        GetQueryDetails() {
            /*AsyncDataFetcher df = new AsyncDataFetcher();
            df.delegate = GetQueryDetails.this;
            df.setURL("http://172.16.105.190/AskHR.asmx");
            df.setRequestPath("Get_Employee_Queries_Details");
            df.setResponsePath("Get_Employee_Queries_DetailsResult");
            Map<String, String> m = new HashMap<>();
            m.put("emp_No", "3939");
            m.put("key", StorageController.readString("Emp_No"));
            df.setRequestParams(m);
            df.execute();*/
            try {
                AsyncJsonFetcher jf = new AsyncJsonFetcher();
                jf.delegate = ViewCommentsDialog.GetQueryDetails.this;
                jf.setRequestPath("http://192.168.15.110/uess/Get_All_Comments.json");
                jf.execute();
            } catch (Exception e) {
                Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

        @SuppressWarnings("ResourceType")
        @Override
        public void processFinish(String output) {
            Toast.makeText(activity, output, Toast.LENGTH_LONG).show();
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ask_hr_comments_Container);
            linearLayout.setVisibility(View.GONE);
            /*try {
                JSONArray ja = new JSONArray(output);

                for(int i = 0; i < ja.length(); i++) {
                    JSONObject jObject = ja.getJSONObject(i);
                    Iterator<?> keys = jObject.keys();

                    CardView cardView = new CardView(activity);
                    {
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        int m8 = activity.getResources().getDimensionPixelSize(R.dimen.space_8);
                        int m3 = activity.getResources().getDimensionPixelSize(R.dimen.space_3);
                        lp.setMargins(m8, m3, m8, m3);
                        lp.gravity = Gravity.CENTER;
                        cardView.setLayoutParams(lp);
                    }
                    cardView.setCardBackgroundColor(ContextCompat.getColor(activity, android.R.color.white));
                    cardView.setRadius(5);
                    linearLayout.addView(cardView);

                    RelativeLayout relativeLayout = new RelativeLayout(activity);
                    {
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        relativeLayout.setLayoutParams(lp);
                        int m8 = activity.getResources().getDimensionPixelSize(R.dimen.space_8);
                        relativeLayout.setPadding(m8, m8, m8, m8);
                    }
                    cardView.addView(relativeLayout);

                    ImageView imageView = new ImageView(activity);
                    {
                        imageView.setId(2601);
                        int imageViewWidth = activity.getResources().getDimensionPixelSize(R.dimen.ask_hr_status_image_width);
                        int imageViewHeight = activity.getResources().getDimensionPixelSize(R.dimen.ask_hr_status_image_height);
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(imageViewWidth, imageViewHeight);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        imageView.setLayoutParams(lp);
                    }
                    relativeLayout.addView(imageView);

                    TextView textViewStatusText = new TextView(activity);
                    {
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        lp.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                        int m8 = activity.getResources().getDimensionPixelSize(R.dimen.space_8);
                        lp.setMargins(m8, m8 ,0 ,0);
                        textViewStatusText.setLayoutParams(lp);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        textViewStatusText.setText(jObject.get("status_text").toString());
                        textViewStatusText.setTextSize(10);
                        relativeLayout.addView(textViewStatusText, lp);
                    }

                    {
                        switch(jObject.get("status").toString()) {
                            case "0":
                                imageView.setBackgroundColor(ContextCompat.getColor(activity, R.color.HRred));
                                textViewStatusText.setTextColor(ContextCompat.getColor(activity, R.color.HRred));
                                break;
                            case "1":
                                imageView.setBackgroundColor(ContextCompat.getColor(activity, R.color.HRyellow));
                                textViewStatusText.setTextColor(ContextCompat.getColor(activity, R.color.HRyellow));
                                break;
                            case "2":
                                imageView.setBackgroundColor(ContextCompat.getColor(activity, R.color.HRgreen));
                                textViewStatusText.setTextColor(ContextCompat.getColor(activity, R.color.HRgreen));
                                break;
                        }
                    }

                    TextView textViewCreatedDate = new TextView(activity);
                    {
                        textViewCreatedDate.setId(2602);
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        textViewCreatedDate.setTextSize(12);
                        textViewCreatedDate.setLayoutParams(lp);
                        textViewCreatedDate.setText(jObject.get("created_date2").toString());
                        relativeLayout.addView(textViewCreatedDate, lp);
                    }

                    TextView textViewRequestType = new TextView(activity);
                    {
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        lp.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                        lp.addRule(RelativeLayout.LEFT_OF, textViewCreatedDate.getId());
                        int m8 = activity.getResources().getDimensionPixelSize(R.dimen.space_8);
                        lp.setMargins(m8, 0 ,m8 ,0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            textViewRequestType.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Medium);
                        }
                        else
                            textViewRequestType.setTextAppearance(activity, android.R.style.TextAppearance_DeviceDefault_Medium);
                        textViewRequestType.setTextColor(ContextCompat.getColor(activity, android.R.color.black));
                        textViewRequestType.setLayoutParams(lp);
                        textViewRequestType.setText(jObject.get("request_type").toString());
                        textViewRequestType.setMaxLines(2);
                        textViewRequestType.setVerticalScrollBarEnabled(true);
                        textViewRequestType.setVerticalFadingEdgeEnabled(true);
                        textViewRequestType.setFadingEdgeLength(50);
                        textViewRequestType.setMovementMethod(new ScrollingMovementMethod());
                        relativeLayout.addView(textViewRequestType, lp);
                    }
                }
            }
            catch(Exception e) {
                Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
            }*/
        }
    }
}