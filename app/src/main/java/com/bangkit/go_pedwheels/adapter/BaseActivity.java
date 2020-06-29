package com.bangkit.go_pedwheels.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;

import androidx.appcompat.app.AppCompatActivity;
/** By Wibawa Bangkit on Tahun 2020
 *  Penyewaan Otoped  Wheels Berdasarkan Metode TOPSIS
 */
public class BaseActivity extends AppCompatActivity {
    protected Context c;
    public AlphaAnimation btnAnimasi = new AlphaAnimation(1F, 0.5F);
    //protected AQuery aq;
    //protected SessionManager sesi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = this;
        //aq = new AQuery(c);
        //sesi = new SessionManager(c);
    }
}