package com.bangkit.go_pedwheels.Activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bangkit.go_pedwheels.Database.DatabaseHelper;

import static com.bangkit.go_pedwheels.Database.DatabaseHelper.COL_HARGA_TOTAL;
import static com.bangkit.go_pedwheels.R.*;


public class TotalSewaActivity extends AppCompatActivity {
    //variabel
    protected Cursor cursor;
    DatabaseHelper dbHelper;
    Button back;
    String total_pemasukan;
    int kosong = 0;
    SQLiteDatabase db;
    TextView  total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_total_sewa);
        //deklarasi variabel back
        back = (Button) findViewById(id.kembalihome);
        total = (TextView) findViewById(id.totalmasuk);
        //config database
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        //query menampilkan jumlah total pemasukan
        cursor = db.rawQuery("SELECT SUM ("+ COL_HARGA_TOTAL +")  AS TOTAL FROM  TB_HARGA ", null);
        if (cursor == null) {
            total.setText(kosong);
        } else {
            cursor.moveToPosition(0);
            total_pemasukan =  cursor.getString(0);
            total.setText(total_pemasukan);
        }
        //back to home
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeAdmin.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });


    }
}