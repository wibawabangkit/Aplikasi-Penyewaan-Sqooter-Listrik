package com.bangkit.go_pedwheels.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bangkit.go_pedwheels.Activities.user.KonfirmasiActivity;
import com.bangkit.go_pedwheels.R;


public class ProsedurActivity extends AppCompatActivity {
Button byr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosedur);

       byr = (Button) findViewById(R.id.konfirmasi);

       byr.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent bayar = new Intent(ProsedurActivity.this, KonfirmasiActivity.class);
               startActivity(bayar);
           }
       });

    }
}
