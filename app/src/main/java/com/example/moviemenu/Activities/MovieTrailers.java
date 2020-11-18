package com.example.moviemenu.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviemenu.R;
import com.example.moviemenu.Utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MovieTrailers extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailers);
        meWebViewContent();

        BottomNavigationView bottomNavigationView = findViewById(R.id.myNavBar);
        final Menu menu = bottomNavigationView.getMenu();
        final MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.movie_search:
                        Toast.makeText(MovieTrailers.this, "Go to home activity to search movies", Toast.LENGTH_LONG).show();
                        break;

                    default:
                        Intent moveBackToHomeScreen = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(moveBackToHomeScreen);
                }
            }
        });
    }

    private void meWebViewContent() {
        webView = findViewById(R.id.myWenView);
        webView.loadUrl(Constants.MOVIE_TRAILER_URL);

        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(Constants.MOVIE_TRAILER_URL);
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
}