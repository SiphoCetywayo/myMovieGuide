package com.example.moviemenu.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.moviemenu.R;
import com.example.moviemenu.Utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MovieTrailers extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailers);

        /*BottomNavigationView bottomNavigationView = findViewById(R.id.myNavBar);
        final Menu menu = bottomNavigationView.getMenu();
        final MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);*/
        meWebViewContent();
    }

    private void meWebViewContent() {
        webView = findViewById(R.id.myWenView);
        webView.loadUrl("https://www.imdb.com/trailers/");

        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("https://www.imdb.com/trailers/");
            }
        }, 500);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                // Ignore SSL certificate errors
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
        return true;
    }

}