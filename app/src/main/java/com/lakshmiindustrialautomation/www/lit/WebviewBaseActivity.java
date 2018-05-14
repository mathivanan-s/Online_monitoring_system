package com.lakshmiindustrialautomation.www.lit;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebviewBaseActivity extends AppCompatActivity {
    private WebView webView;
    ProgressBar progressBar;
    Boolean is_loaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_webview_base);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeView.setProgressViewOffset(false, 0,160);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                String base_url = getIntent().getStringExtra("web_url");

                if (Utilities.isStaticIpAvailable(WebviewBaseActivity.this) && !Utilities.isInternetAvailable(WebviewBaseActivity.this)){
                    is_loaded = false;
                    Toast.makeText(WebviewBaseActivity.this, "No internet available", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    is_loaded = true;
                    webView = (WebView) findViewById(R.id.base_web_view);
                    webView.setVisibility(View.GONE);
                    webView.setWebViewClient(new newWebClient());
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setBuiltInZoomControls(true);
                    webView.setVerticalScrollBarEnabled(false);
                    webView.setHorizontalScrollBarEnabled(false);
                    webView.loadUrl(base_url);
                }
                swipeView.setRefreshing(false);
            }
        });
        String base_url = getIntent().getStringExtra("web_url");

        if (Utilities.isStaticIpAvailable(WebviewBaseActivity.this) && !Utilities.isInternetAvailable(WebviewBaseActivity.this)){
            is_loaded = false;
            Toast.makeText(this, "No internet available", Toast.LENGTH_SHORT).show();
        } else {
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);

            is_loaded = true;
            webView = (WebView) findViewById(R.id.base_web_view);
            webView.setVisibility(View.GONE);
            webView.setWebViewClient(new newWebClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setVerticalScrollBarEnabled(false);
            webView.setHorizontalScrollBarEnabled(false);
            webView.loadUrl(base_url);
        }
    }

    private class newWebClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCod,String description, String failingUrl) {
            webView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(WebviewBaseActivity.this, "Web page reception failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (is_loaded) {
            if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
