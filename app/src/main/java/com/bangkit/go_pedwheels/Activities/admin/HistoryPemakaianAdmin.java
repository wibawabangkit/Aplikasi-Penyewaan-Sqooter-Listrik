package com.bangkit.go_pedwheels.Activities.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bangkit.go_pedwheels.Adapter.HistoryAdapter;
import com.bangkit.go_pedwheels.Adapter.HistoryPemakaianAdapter;
import com.bangkit.go_pedwheels.Database.DatabaseHelper;
import com.bangkit.go_pedwheels.Model.HistoryModel;
import com.bangkit.go_pedwheels.Model.Mhistory_pemakaian;
import com.bangkit.go_pedwheels.R;
import com.bangkit.go_pedwheels.Session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryPemakaianAdmin extends AppCompatActivity {
    //Variabel
    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    SessionManager session;
    String ID_Sewa = "", kerusakan,tanggalpakai, riwayatpakai, hasil;
    String email;
    TextView tvNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_pemakaian_admin);

        //Conection Database
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        //variabel target
        tvNotFound = findViewById(R.id.noHistoryPakai);

        //conection session
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);

        //Parameter
        refreshList();
        setupToolbar();
    }
    //Deklarasi Parameter setupToolbar
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbHistoryPakai);
        toolbar.setTitle("Riwayat Pemakaian");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // back to home pada toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Deklarasi Parameter refreshList
    public void refreshList() {
        final ArrayList<Mhistory_pemakaian> kesimpulan = new ArrayList<>();
        cursor = db.rawQuery( "SELECT * FROM TB_SEWA  WHERE ID_Sewa = ID_Sewa",null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            ID_Sewa = cursor.getString(0);
            tanggalpakai = cursor.getString(3);
            kerusakan = cursor.getString(5);
            hasil = cursor.getString(7);
            riwayatpakai = "Berdasarkan pemakaian otoped wheels terdapat kerusakan "+ kerusakan +".";
            kesimpulan.add(new Mhistory_pemakaian(ID_Sewa, tanggalpakai, riwayatpakai, hasil, R.drawable.profile));
        }

        //Configuration List pemakaian
        ListView listBook = findViewById(R.id.list_riwayat_pakai);
        HistoryPemakaianAdapter arrayAdapter = new HistoryPemakaianAdapter(this, kesimpulan);
        listBook.setAdapter(arrayAdapter);
        listBook.setSelected(true);

        //pilihan list
        listBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String selection = kesimpulan.get(i).getIdsewa();
                final CharSequence[] dialogitem = {"Lihat Data", "Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryPemakaianAdmin.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        switch (item) {
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), //melihat detil database berdasarkan ID Penyewaan
                                        DetilPemakaian.class);
                                i.putExtra("ID_Sewa", selection);
                                startActivity(i);
                                break;
                            case 1:
                                db.execSQL("DELETE FROM TB_SEWA WHERE ID_Sewa = " + selection + ""); //menghapus list berdasarkan ID Sewa
                                ID_Sewa = "";
                                refreshList();
                                break;

                        }
                    }});
                builder.create().show();
            }
        });

        //Jika Kosong atau belum ada pemesanan menampilkan session "BELUM ADA PENYEWAAN"
        if (ID_Sewa.equals("")) {
            tvNotFound.setVisibility(View.VISIBLE);
            listBook.setVisibility(View.GONE);
        } else {
            tvNotFound.setVisibility(View.GONE); //Jika sudah ada penyewaan, maka menampilkan isi parameter refreshList
            listBook.setVisibility(View.VISIBLE);

        }
    }
}