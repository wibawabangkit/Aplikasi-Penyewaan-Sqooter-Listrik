package com.bangkit.go_pedwheels.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bangkit.go_pedwheels.R;
import com.bangkit.go_pedwheels.adapter.BaseActivity;
/** By Wibawa Bangkit on Tahun 2020
 *  Penyewaan Otoped  Wheels Berdasarkan Metode TOPSIS
 */
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
