package com.bangkit.go_pedwheels.Activities.user;

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

import com.bangkit.go_pedwheels.Model.HistoryModel;
import com.bangkit.go_pedwheels.R;
import com.bangkit.go_pedwheels.Adapter.HistoryAdapter;
import com.bangkit.go_pedwheels.Database.DatabaseHelper;
import com.bangkit.go_pedwheels.Session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {

        protected Cursor cursor;
        DatabaseHelper dbHelper;
        SQLiteDatabase db;
        SessionManager session;
        String ID_Sewa = "", Waktu, tanggal, riwayat, total;
        String email;
        TextView tvNotFound;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_history);
            dbHelper = new DatabaseHelper(this);
            db = dbHelper.getReadableDatabase();
            tvNotFound = findViewById(R.id.noHistory);
            session = new SessionManager(getApplicationContext());
            HashMap<String, String> user = session.getUserDetails();
            email = user.get(SessionManager.KEY_EMAIL);
                refreshList();
            setupToolbar();
        }

        private void setupToolbar() {
            Toolbar toolbar = findViewById(R.id.tbHistory);
            toolbar.setTitle("Riwayat Sewa");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        public void refreshList() {
            final ArrayList<HistoryModel> hasil = new ArrayList<>();
            cursor = db.rawQuery("SELECT * FROM TB_SEWA, TB_HARGA WHERE  TB_SEWA.ID_Sewa = TB_HARGA.ID_Sewa", null);
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                ID_Sewa = !cursor.getString(0).isEmpty() ? cursor.getString(0) : "";
                Waktu = cursor.getString(4);
                tanggal = cursor.getString(3);
                total =cursor.getString(12);
                riwayat = "Berhasil melakukan penyewaan dengan waktu sewa " + Waktu + " Menit pada tanggal " + tanggal + ". ";
                hasil.add(new HistoryModel(ID_Sewa, tanggal, riwayat, total, R.drawable.profile));
            }

            //Configuration List Penyewaan
            ListView listBook = findViewById(R.id.list_booking);
            HistoryAdapter arrayAdapter = new HistoryAdapter(this, hasil);
            listBook.setAdapter(arrayAdapter);
            listBook.setSelected(true);

            //pilihan list
            listBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final String selection = hasil.get(i).getIdsewa();
                    final CharSequence[] dialogitem = {"Lihat Data"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                    builder.setTitle("Pilihan");
                    builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            switch (item) {
                                case 0 :
                                    Intent i = new Intent(getApplicationContext(), //melihat detil database berdasarkan ID Penyewaan
                                            DetilSewaActivity.class);
                                    i.putExtra("ID_Sewa", selection);
                                    startActivity(i);
                                    break;

                            }
                        }});
                    builder.create().show();
                }
            });


            if (ID_Sewa.equals("")) {
                tvNotFound.setVisibility(View.VISIBLE);
                listBook.setVisibility(View.GONE);
            } else {
                tvNotFound.setVisibility(View.GONE);
                listBook.setVisibility(View.VISIBLE);
                }
        }
}