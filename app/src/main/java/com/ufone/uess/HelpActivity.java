package com.ufone.uess;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        //PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        //pdfView.fromUri(Uri.parse("http://172.16.105.190/SAPESS-HowTo.pdf")).load();

        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=https://uportal.ufone.com/sites/hrportal/SRMng/Documents/SAPESS-HowTo.pdf");

    }
}
