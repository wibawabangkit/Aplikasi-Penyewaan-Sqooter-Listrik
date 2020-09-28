package com.bangkit.go_pedwheels.Activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bangkit.go_pedwheels.Model.HistoryModel;
import com.bangkit.go_pedwheels.R;
import com.bangkit.go_pedwheels.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;

import static com.bangkit.go_pedwheels.Database.DatabaseHelper.COL_HARGA_TOTAL;



public class TotalSewaActivity extends AppCompatActivity {
    //variabel
    protected Cursor cursor;
    DatabaseHelper dbHelper;
    Button back;
    String total_pemasukan;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_sewa);
        //deklarasi variabel back
        back = (Button) findViewById(R.id.kembalihome);

        //config database
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        //query menampilkan jumlah total pemasukan
        cursor = db.rawQuery("SELECT SUM ("+ COL_HARGA_TOTAL +")  AS TOTAL FROM TB_HARGA", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            total_pemasukan =  !cursor.getString(0).isEmpty() ? cursor.getString(0) : "";
        TextView total = findViewById(R.id.totalmasuk);
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