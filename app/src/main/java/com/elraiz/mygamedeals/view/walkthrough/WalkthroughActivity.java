package com.elraiz.mygamedeals.view.walkthrough;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.elraiz.mygamedeals.R;
import com.elraiz.mygamedeals.adapter.WalkthroughViewPager;
import com.elraiz.mygamedeals.view.home.HomeActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class WalkthroughActivity extends AppCompatActivity {

    private ViewPager screenPager;
    WalkthroughViewPager walkthroughViewPager;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0;
    TextView btnSkip;
    Animation btnAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set up fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_walkthrough);

        //deklarasi variable
        tabIndicator = findViewById(R.id.tab_indicator);
        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.txt_skip);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottom_animation);


        //list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("AHH THATS HOT","See the hottest deals available righ now",R.drawable.icon_sample));
        mList.add(new ScreenItem("SEARCH FOR THE PRICE","Search for the price for your desired games",R.drawable.icon_sample));
        mList.add(new ScreenItem("ITS FREE REAL ESTATE","See what freebie offer you can get",R.drawable.icon_sample));

        //setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        walkthroughViewPager = new WalkthroughViewPager(this,mList);
        screenPager.setAdapter(walkthroughViewPager);

        //setup indicator
        tabIndicator.setupWithViewPager(screenPager);

        //text skip listener
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        //button next listener on click
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size() && btnNext.getText() != "Lets Go!"){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                else if (btnNext.getText()=="Lets Go!"){
                    Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
                if (position == mList.size()-1 && btnNext.getText()!="Lets Go!"){
                    loadLastScreen();
                }
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==mList.size()-1 && btnNext.getText()!="Lets Go!"){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void loadLastScreen() {
        btnNext.setAnimation(btnAnim);
        btnNext.setText("Lets Go!");
        tabIndicator.setVisibility(View.INVISIBLE);
        btnSkip.setVisibility(View.INVISIBLE);
    }

}
