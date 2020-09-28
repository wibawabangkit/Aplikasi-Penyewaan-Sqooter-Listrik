package com.bangkit.go_pedwheels.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bangkit.go_pedwheels.R;
import com.bangkit.go_pedwheels.Adapter.BaseActivity;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(c, LoginActivity.class));
                finish();
            }
        },5000L);
    }
}
