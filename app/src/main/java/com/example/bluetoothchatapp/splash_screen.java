package com.example.bluetoothchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {

    TextView txtWel,txtConnect;
    //ConstraintLayout constraintLayout;
    Animation txtAnimation,layoutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        hideNavigationBar();

        txtAnimation = AnimationUtils.loadAnimation(splash_screen.this,R.anim.fall_down);
        layoutAnimation = AnimationUtils.loadAnimation(splash_screen.this,R.anim.bottom_to_top);

        txtWel = (TextView)findViewById(R.id.welcome);
        txtConnect = (TextView)findViewById(R.id.connect);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtWel.setVisibility(View.VISIBLE);
                txtConnect.setVisibility(View.VISIBLE);

                txtWel.setAnimation(txtAnimation);
                txtConnect.setAnimation(txtAnimation);
            }
        },1000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splash_screen.this,LoginPage.class);
                startActivity(i);
            }
        },5000);
    }
    private void hideNavigationBar()
    {
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
    }
}