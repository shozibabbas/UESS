package com.ufone.uess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AskHRActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_hr);

        ((TextView) findViewById(R.id.titlebarTitle)).setText(R.string.askHR);

        GetQueryDetails saq = new GetQueryDetails();
    }

    public void openCreateHR(View v) {
        finish();
        startActivity(new Intent(AskHRActivity.this, AskHRQueryActivity.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void expandView(View v) {

    }

    public void backButton(View v) {
        startActivity(new Intent(AskHRActivity.this, DashboardActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AskHRActivity.this, DashboardActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    // creating sign out box
    public void backPress(View v) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(AskHRActivity.this);
        builder.setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UserAuthentication.unauthenticate();
                        Intent intent = new Intent(AskHRActivity.this, LoginActivity.class);
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

    private class ShowAllQueries implements AsyncResponse {
        ShowAllQueries() {
            AsyncDataFetcher df = new AsyncDataFetcher();
            df.delegate = ShowAllQueries.this;
            df.setURL("http://172.16.105.190/AskHR.asmx");
            df.setRequestPath("Get_All_Comments");
            df.setResponsePath("Get_All_CommentsResult");
            Map<String, String> m = new HashMap<>();
            m.put("ask_id", "3");
            m.put("key", StorageController.readString("Emp_No"));
            df.setRequestParams(m);
            df.execute();
        }

        @Override
        public void processFinish(String output) {
            Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
        }
    }

    private class GetQueryDetails implements AsyncResponse {
        GetQueryDetails() {
            AsyncDataFetcher df = new AsyncDataFetcher();
            df.delegate = GetQueryDetails.this;
            df.setURL("http://172.16.105.190/AskHR.asmx");
            df.setRequestPath("Get_Employee_Queries_Details");
            df.setResponsePath("Get_Employee_Queries_DetailsResult");
            Map<String, String> m = new HashMap<>();
            m.put("emp_No", "3939");
            m.put("key", StorageController.readString("Emp_No"));
            df.setRequestParams(m);
            df.execute();
            /*try {
                AsyncJsonFetcher jf = new AsyncJsonFetcher();
                jf.delegate = GetQueryDetails.this;
                jf.setRequestPath("http://192.168.15.110/uess/Get_Employee_Queries_Details.json");
                jf.execute();
            }
            catch(Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }*/
        }

        @SuppressWarnings("ResourceType")
        @Override
        public void processFinish(String output) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ask_hr_ll);
            try {
                JSONArray ja = new JSONArray(output);

                ((ProgressBar) findViewById(R.id.progressBarMain)).setVisibility(View.GONE);

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jObject = ja.getJSONObject(i);
                    Iterator<?> keys = jObject.keys();

                    CardView cardView = new CardView(AskHRActivity.this);
                    {
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        int m8 = getResources().getDimensionPixelSize(R.dimen.space_8);
                        int m3 = getResources().getDimensionPixelSize(R.dimen.space_3);
                        lp.setMargins(m8, m3, m8, m3);
                        lp.gravity = Gravity.CENTER;
                        cardView.setLayoutParams(lp);
                    }
                    cardView.setCardBackgroundColor(ContextCompat.getColor(AskHRActivity.this, android.R.color.white));
                    cardView.setRadius(5);
                    linearLayout.addView(cardView);

                    RelativeLayout relativeLayout = new RelativeLayout(AskHRActivity.this);
                    {
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        relativeLayout.setLayoutParams(lp);
                        int m8 = getResources().getDimensionPixelSize(R.dimen.space_8);
                        relativeLayout.setPadding(m8, m8, m8, m8);
                    }
                    cardView.addView(relativeLayout);

                    ImageView imageView = new ImageView(AskHRActivity.this);
                    {
                        imageView.setId(2601);
                        int imageViewWidth = getResources().getDimensionPixelSize(R.dimen.ask_hr_status_image_width);
                        int imageViewHeight = getResources().getDimensionPixelSize(R.dimen.ask_hr_status_image_height);
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(imageViewWidth, imageViewHeight);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        imageView.setLayoutParams(lp);
                    }
                    relativeLayout.addView(imageView);

                    TextView textViewStatusText = new TextView(AskHRActivity.this);
                    {
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        lp.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                        int m8 = getResources().getDimensionPixelSize(R.dimen.space_8);
                        lp.setMargins(m8, m8, 0, 0);
                        textViewStatusText.setLayoutParams(lp);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        textViewStatusText.setText(jObject.get("status_text").toString());
                        textViewStatusText.setTextSize(10);
                        relativeLayout.addView(textViewStatusText, lp);
                    }

                    {
                        switch (jObject.get("status").toString()) {
                            case "0":
                                imageView.setBackgroundColor(ContextCompat.getColor(AskHRActivity.this, R.color.HRred));
                                textViewStatusText.setTextColor(ContextCompat.getColor(AskHRActivity.this, R.color.HRred));
                                break;
                            case "1":
                                imageView.setBackgroundColor(ContextCompat.getColor(AskHRActivity.this, R.color.HRyellow));
                                textViewStatusText.setTextColor(ContextCompat.getColor(AskHRActivity.this, R.color.HRyellow));
                                break;
                            case "2":
                                imageView.setBackgroundColor(ContextCompat.getColor(AskHRActivity.this, R.color.HRgreen));
                                textViewStatusText.setTextColor(ContextCompat.getColor(AskHRActivity.this, R.color.HRgreen));
                                break;
                        }
                    }

                    TextView textViewCreatedDate = new TextView(AskHRActivity.this);
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

                    TextView textViewRequestType = new TextView(AskHRActivity.this);
                    {
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        lp.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                        lp.addRule(RelativeLayout.LEFT_OF, textViewCreatedDate.getId());
                        int m8 = getResources().getDimensionPixelSize(R.dimen.space_8);
                        lp.setMargins(m8, 0, m8, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            textViewRequestType.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Medium);
                        } else
                            textViewRequestType.setTextAppearance(AskHRActivity.this, android.R.style.TextAppearance_DeviceDefault_Medium);
                        textViewRequestType.setTextColor(ContextCompat.getColor(AskHRActivity.this, android.R.color.black));
                        textViewRequestType.setLayoutParams(lp);
                        textViewRequestType.setText(jObject.get("request_type").toString());
                        textViewRequestType.setMaxLines(2);
                        textViewRequestType.setVerticalScrollBarEnabled(true);
                        textViewRequestType.setVerticalFadingEdgeEnabled(true);
                        textViewRequestType.setFadingEdgeLength(50);
                        textViewRequestType.setMovementMethod(new ScrollingMovementMethod());
                        relativeLayout.addView(textViewRequestType, lp);
                    }

                    Button buttonViewComments = new Button(AskHRActivity.this);
                    {
                        int buttonHeight = getResources().getDimensionPixelSize(R.dimen.ask_hr_button_height);
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, buttonHeight);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        lp.addRule(RelativeLayout.BELOW, textViewCreatedDate.getId());
                        int m8 = getResources().getDimensionPixelSize(R.dimen.space_8);
                        int m16 = getResources().getDimensionPixelSize(R.dimen.space_16);
                        lp.setMargins(0, m16, 0, 0);
                        buttonViewComments.setPadding(m8, 0, m8, 0);
                        buttonViewComments.setText(R.string.ask_hr_button);
                        buttonViewComments.setTextColor(ContextCompat.getColor(AskHRActivity.this, android.R.color.white));
                        buttonViewComments.setBackgroundResource(R.drawable.orange_button);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            buttonViewComments.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Small);
                        } else
                            buttonViewComments.setTextAppearance(AskHRActivity.this, android.R.style.TextAppearance_DeviceDefault_Small);
                        buttonViewComments.setAllCaps(false);
                        buttonViewComments.setTextColor(ContextCompat.getColor(AskHRActivity.this, android.R.color.white));
                        Object obj = new Object();
                        buttonViewComments.setOnClickListener(new CommentButtonClick(Integer.parseInt(jObject.get("ask_id").toString())));
                        relativeLayout.addView(buttonViewComments, lp);
                    }
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class CommentButtonClick implements View.OnClickListener {

        private int askId;

        CommentButtonClick(int ask_id) {
            this.askId = ask_id;
        }

        @Override
        public void onClick(View v) {
            AlertDialog alert = new CustomCommentBox(AskHRActivity.this);
            alert.show();
        }

        private class CustomCommentBox extends AlertDialog implements AsyncResponse {

            private LinearLayout linearLayout;
            private Button closeBtn;

            protected CustomCommentBox(Context context) {
                super(context);
                AsyncDataFetcher df = new AsyncDataFetcher();
                df.delegate = CustomCommentBox.this;
                df.setURL("http://172.16.105.190/AskHR.asmx");
                df.setRequestPath("Get_All_Comments");
                df.setResponsePath("Get_All_CommentsResult");
                Map<String, String> m = new HashMap<>();
                m.put("emp_No", "3939");
                m.put("ask_id", String.valueOf(askId));
                m.put("key", StorageController.readString("Emp_No"));
                df.setRequestParams(m);
                df.execute();
                try {
                    /*AsyncJsonFetcher jf = new AsyncJsonFetcher();
                    jf.delegate = CustomCommentBox.this;
                    jf.setRequestPath("http://192.168.15.110/uess/Get_All_Comments.json");
                    jf.execute();*/
                    ScrollView scrollView = new ScrollView(AskHRActivity.this);
                    linearLayout = new LinearLayout(AskHRActivity.this);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    scrollView.addView(linearLayout);
                    setView(scrollView);
                    closeBtn = new Button(AskHRActivity.this);
                    closeBtn.setBackgroundResource(R.drawable.orange_button);
                    closeBtn.setTextColor(ContextCompat.getColor(AskHRActivity.this, android.R.color.white));
                    closeBtn.setText("Close");
                    closeBtn.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            dismiss();
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @SuppressWarnings("ResourceType")
            @Override
            public void processFinish(String output) {
                try {
                    JSONArray ja = new JSONArray(output);

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jObject = ja.getJSONObject(i);
                        Iterator<?> keys = jObject.keys();

                        CardView cardView = new CardView(AskHRActivity.this);
                        {
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            int m8 = getResources().getDimensionPixelSize(R.dimen.space_8);
                            int m3 = getResources().getDimensionPixelSize(R.dimen.space_3);
                            lp.setMargins(m8, m3, m8, m3);
                            lp.gravity = Gravity.CENTER;
                            cardView.setLayoutParams(lp);
                        }
                        cardView.setCardBackgroundColor(ContextCompat.getColor(AskHRActivity.this, android.R.color.white));
                        cardView.setRadius(5);
                        linearLayout.addView(cardView);

                        RelativeLayout relativeLayout = new RelativeLayout(AskHRActivity.this);
                        {
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            relativeLayout.setLayoutParams(lp);
                            int m8 = getResources().getDimensionPixelSize(R.dimen.space_8);
                            relativeLayout.setPadding(m8, m8, m8, m8);
                        }
                        cardView.addView(relativeLayout);

                        ImageView imageView = new ImageView(AskHRActivity.this);
                        {
                            imageView.setId(2611);
                            int imageViewWidth = getResources().getDimensionPixelSize(R.dimen.ask_hr_status_image_width);
                            int imageViewHeight = getResources().getDimensionPixelSize(R.dimen.ask_hr_status_image_height);
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(imageViewWidth, imageViewHeight);
                            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                            imageView.setLayoutParams(lp);
                        }
                        relativeLayout.addView(imageView);

                        TextView textViewStatusText = new TextView(AskHRActivity.this);
                        {
                            textViewStatusText.setId(2615);
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            lp.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                            int m8 = getResources().getDimensionPixelSize(R.dimen.space_8);
                            lp.setMargins(m8, m8, 0, 0);
                            textViewStatusText.setLayoutParams(lp);
                            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                            textViewStatusText.setText(jObject.get("comment_type").toString());
                            textViewStatusText.setTextSize(10);
                            relativeLayout.addView(textViewStatusText, lp);
                        }

                        TextView textViewCommentText = new TextView(AskHRActivity.this);
                        {
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            lp.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                            lp.addRule(RelativeLayout.BELOW, textViewStatusText.getId());
                            int m8 = getResources().getDimensionPixelSize(R.dimen.space_8);
                            lp.setMargins(m8, m8, 0, 0);
                            textViewCommentText.setLayoutParams(lp);
                            textViewCommentText.setText(jObject.get("comment_text").toString());

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                textViewCommentText.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Small);
                            } else
                                textViewCommentText.setTextAppearance(AskHRActivity.this, android.R.style.TextAppearance_DeviceDefault_Small);
                            textViewCommentText.setTextColor(ContextCompat.getColor(AskHRActivity.this, android.R.color.black));
                            relativeLayout.addView(textViewCommentText, lp);
                        }

                        {
                            switch (jObject.get("comment_type").toString()) {
                                case "Pending for HR":
                                case "Employee":
                                    imageView.setBackgroundColor(ContextCompat.getColor(AskHRActivity.this, R.color.HRred));
                                    textViewStatusText.setTextColor(ContextCompat.getColor(AskHRActivity.this, R.color.HRred));
                                    break;
                                case "HR Update":
                                    imageView.setBackgroundColor(ContextCompat.getColor(AskHRActivity.this, R.color.HRyellow));
                                    textViewStatusText.setTextColor(ContextCompat.getColor(AskHRActivity.this, R.color.HRyellow));
                                    break;
                                case "HR Closed":
                                    imageView.setBackgroundColor(ContextCompat.getColor(AskHRActivity.this, R.color.HRgreen));
                                    textViewStatusText.setTextColor(ContextCompat.getColor(AskHRActivity.this, R.color.HRgreen));
                                    break;
                            }
                        }

                        TextView textViewCreatedDate = new TextView(AskHRActivity.this);
                        {
                            textViewCreatedDate.setId(2612);
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            textViewCreatedDate.setTextSize(12);
                            textViewCreatedDate.setLayoutParams(lp);
                            textViewCreatedDate.setText(jObject.get("created_date2").toString());
                            relativeLayout.addView(textViewCreatedDate, lp);
                        }
                    }
                    linearLayout.addView(closeBtn);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
