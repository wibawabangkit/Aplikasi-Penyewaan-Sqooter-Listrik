package com.bangkit.go_pedwheels.Activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bangkit.go_pedwheels.Activities.ProsedurActivity;
import com.bangkit.go_pedwheels.R;
import com.bangkit.go_pedwheels.Database.DatabaseHelper;


public class DetilSewaActivity extends AppCompatActivity {
    //variabel
    TextView id, berat, tgl, wkt;
    protected Cursor cursor;
    DatabaseHelper dbHelper;
    public String email;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deil_sewa);

        //target variabel
         id = (TextView) findViewById(R.id.value_idsewaa);
         berat = (TextView) findViewById(R.id.value_beratbadan);
         tgl = (TextView) findViewById( R.id.value_tanggalsewa);
         wkt = (TextView) findViewById(R.id.value_waktusewa);
         back = (Button) findViewById(R.id.Prosedurr);

         //connection database
        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //query menampilkan tabel sewa berdasarkan ID Sewa
        cursor = db.rawQuery( "SELECT * FROM TB_SEWA WHERE  ID_Sewa = '"+
               getIntent().getStringExtra("ID_Sewa") + "' ",null);
        cursor.moveToFirst();

        //Yang akan ditampilkan
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            id.setText(!cursor.getString(0).isEmpty() ? cursor.getString(0) : "");
            berat.setText(cursor.getString(2));
            tgl.setText(!cursor.getString(3).isEmpty() ? cursor.getString(3) : "");
            wkt.setText(!cursor.getString(4).isEmpty() ? cursor.getString(4) : "");
        }

        //tombol ke prosedur penyewaan
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProsedurActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });


    }
}
