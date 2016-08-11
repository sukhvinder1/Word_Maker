package com.example.wordmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by sinsukhv on 8/9/2016.
 */
public class WebSearch extends Activity {

    WebView webview;

    TextView webSerachText;

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websearch);
        webSerachText = (TextView) findViewById(R.id.webSearchText);
        webview=(WebView)findViewById(R.id.webView);
        webview.setWebViewClient(new MyWebViewClient());

        Intent intent = getIntent();
        Log.d("findMe*", "getting all extra"+intent.getExtras().toString());
        String key = intent.getStringExtra("word");
        Log.d("findMe*", "incoming key " + key);

        if(key != null && !key.isEmpty()){
            webSerachText.setText("Meaning of "+key);
            openURL(key);
        }else{
            Log.d("findMe*", "No incoming key");
            webSerachText.setText("Something is not okay !");
        }
    }

    private void openURL(String key) {
        webview.loadUrl("http://m.dictionary.com/d/?q="+key);
        webview.requestFocus();
    }
}