package com.bangkit.go_pedwheels.Activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bangkit.go_pedwheels.Activities.ProsedurActivity;
import com.bangkit.go_pedwheels.Activities.user.MainActivity;
import com.bangkit.go_pedwheels.Database.DatabaseHelper;
import com.bangkit.go_pedwheels.R;

public class DetilPemakaian  extends AppCompatActivity {
    //variabel
    TextView id, kerusakan, tgl, hasil, wkt;
    protected Cursor cursor;
    DatabaseHelper dbHelper;
    public String email;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_pemakaian2);

        //target variabel
        id = (TextView) findViewById(R.id.value_idsewaaa);
        kerusakan = (TextView) findViewById(R.id.value_rusakkk);
        tgl = (TextView) findViewById( R.id.value_tanggalpakaiii);
        wkt = (TextView) findViewById(R.id.value_waktupakaiii);
        hasil = (TextView) findViewById(R.id.value_hasilll);
        back = (Button) findViewById(R.id.backbranda);

        //connection database
        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //query menampilkan tabel sewa berdasarkan ID Sewa
        cursor = db.rawQuery( "SELECT * FROM TB_SEWA where ID_Sewa = '"+
                getIntent().getStringExtra("ID_Sewa") + "'",null);
        cursor.moveToFirst();
        //Yang akan ditampilkan
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            id.setText(!cursor.getString(0).isEmpty() ? cursor.getString(0) : "");
            tgl.setText(cursor.getString(3));
            wkt.setText(!cursor.getString(4).isEmpty() ? cursor.getString(4) : "");
            kerusakan.setText( cursor.getString(5));
            hasil.setText(cursor.getString(7));

        }

        //tombol ke prosedur penyewaan
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeAdmin.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });


    }
}
