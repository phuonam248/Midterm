package com.example.midterm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF6700"));
        actionBar.setBackgroundDrawable(colorDrawable);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (url != null) {
            Toast.makeText(this, "Successfully", Toast.LENGTH_LONG).show();
            WebView newsWebView = findViewById(R.id.newsWebView);
            if (Build.VERSION.SDK_INT >= 21) {
                newsWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);;
            }
            newsWebView.loadUrl(url);
        }
        else
            Toast.makeText(this, "BADDDDDDDDDDDDD", Toast.LENGTH_LONG).show();
    }
}