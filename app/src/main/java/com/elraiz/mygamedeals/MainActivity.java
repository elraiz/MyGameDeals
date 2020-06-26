package com.elraiz.mygamedeals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.elraiz.mygamedeals.view.walkthrough.WalkthroughActivity;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;

    //Variables
    Animation topAnim, leftAnim, rightAnim;
    TextView txtappname, txtappslogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Deklarasi Animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        leftAnim = AnimationUtils.loadAnimation(this,R.anim.left_animation);
        rightAnim = AnimationUtils.loadAnimation(this,R.anim.right_animation);

        //Deklarasi Variable Text
        txtappname = findViewById(R.id.appname);
        txtappslogan = findViewById(R.id.appslogan);

        //Set Animation
        txtappname.setAnimation(leftAnim);
        txtappslogan.setAnimation(rightAnim);

        //Transisi Splash Screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent inten = new Intent(MainActivity.this, WalkthroughActivity.class);
                startActivity(inten);
                finish();
            }
        },SPLASH_SCREEN);
    }
}
