package com.example.stanislau_bushuk.foodhealth.presentantion.splashPresentatiom;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.stanislau_bushuk.foodhealth.ActivityManager;

import java.util.Timer;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Timer timer = new Timer();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityManager.startMainActivity(getApplicationContext());
                finish();
            }
        },2000);
    }
}