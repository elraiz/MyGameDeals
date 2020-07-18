package com.elraiz.mygamedeals.view.article;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.elraiz.mygamedeals.R;
import com.elraiz.mygamedeals.view.freebie.FreebieActivity;
import com.elraiz.mygamedeals.view.home.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        //inisialisasi variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //menu search terpilih
        bottomNavigationView.setSelectedItemId(R.id.article);

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
                    case R.id.article:
                        return true;
                    case R.id.freebie:
                        startActivity(new Intent(getApplicationContext()
                                , FreebieActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
