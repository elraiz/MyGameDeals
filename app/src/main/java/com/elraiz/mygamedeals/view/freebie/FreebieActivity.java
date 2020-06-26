package com.elraiz.mygamedeals.view.freebie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.elraiz.mygamedeals.R;
import com.elraiz.mygamedeals.view.home.HomeActivity;
import com.elraiz.mygamedeals.view.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FreebieActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freebie);

        //inisialisasi variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        webView = (WebView) findViewById(R.id.freebie_web);
        WebSettings webSettings = webView.getSettings();

        webSettings.getLoadsImagesAutomatically();
        webSettings.setJavaScriptEnabled(true);

        //load tampilan web freebie
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.indiegamebundles.com/category/free/");

        //menu freebie terpilih
        bottomNavigationView.setSelectedItemId(R.id.freebie);

        //navigasi pindah aktivitas
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext()
                                , SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.freebie:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
        else {
        super.onBackPressed();
        }
    }
}
