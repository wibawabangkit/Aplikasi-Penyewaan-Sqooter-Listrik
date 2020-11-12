package com.bangkit.go_pedwheels.Activities.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bangkit.go_pedwheels.Activities.user.HistoryActivity;
import com.bangkit.go_pedwheels.Activities.user.SewaActivity;
import com.bangkit.go_pedwheels.Database.DatabaseHelper;
import com.bangkit.go_pedwheels.R;
import com.bangkit.go_pedwheels.Session.SessionManager;

import java.util.Calendar;
import java.util.HashMap;

public class InsertPemakaian extends AppCompatActivity {
    //variabel
    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Spinner rusak,Hasil;
    SessionManager session;
    String email;
    int ID_Sewa;
    public String sKerusakan, sTanggal,sHasil;
    EditText etkehilangan;
    private EditText etTanggal;
    private DatePickerDialog dpTanggal;
    Calendar newCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pemakaian);
        //cpnfig database
        dbHelper = new DatabaseHelper(InsertPemakaian.this);
        db = dbHelper.getReadableDatabase();
        //array
        final String[] Rusak = {"Tidak Ada","Stang Patah / Belok", "Ban Belok / Speleng", "Stang Tak Bisa Muter / Tak Bisa Belok"};
        final String[] hasil = {"Default","BAIK", "KURANG BAIK"};
        //spinner sewa
        rusak = findViewById(R.id.Spinkerusakan);
        Hasil = findViewById(R.id.SpinHasil);
        //deklarasi spiner
        ArrayAdapter<CharSequence> adapterRusak = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, Rusak);
        adapterRusak.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rusak.setAdapter(adapterRusak);

        ArrayAdapter<CharSequence> adapterhasil = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, hasil);
        adapterhasil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Hasil.setAdapter(adapterhasil);
        //Pemanggilan spinner
        rusak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sKerusakan = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Hasil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sHasil = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //deklarasi variabel btnSewa, etTanggal
        Button btnSubmit = findViewById(R.id.btnInsert);
        etTanggal = findViewById(R.id.tanggal_sewa);
        etTanggal.setInputType(InputType.TYPE_NULL);
        etTanggal.requestFocus();

        //config database
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);

        //parameter
        setDateTimeField();

    //isi variabel  btnSewa
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //jika memilih "0" maka akan muncul notifikasi "Isi Dengan Benar !"
                if ( sTanggal != null && sHasil != null ) {
                    if ((sTanggal.equalsIgnoreCase("Default"))
                            || (sHasil.equalsIgnoreCase("Default"))
                    ) {
                        Toast.makeText(InsertPemakaian.this, "Isi Dengan Benar !", Toast.LENGTH_LONG).show();
                    } else {
                    //Jika selesai mengisi form, maka akan tampil warning  "Ingin Sewa dan lihat advice otoped diprosedur penyewaan?"
                        AlertDialog dialog = new AlertDialog.Builder(InsertPemakaian.this)
                                .setTitle("Data yang diinput sudah benar?")
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            db.execSQL( "UPDATE TB_SEWA SET  rusak  = '"+sKerusakan+"', hilang = '"+ etkehilangan +"',hasil = '"+ sHasil +"' WHERE ID_Sewa = '"+
                                                        getIntent().getStringExtra("ID_Sewa") + "'");
                                            cursor = db.rawQuery("SELECT ID_Sewa FROM TB_SEWA ORDER BY ID_Sewa DESC", null);
                                            cursor.moveToLast();
                                            if (cursor.getCount() > 0) {
                                                cursor.moveToPosition(0);
                                                ID_Sewa = cursor.getInt(0);
                                            }
                                            Toast.makeText(InsertPemakaian.this, "Data Disimpan", Toast.LENGTH_LONG).show();
                                            Intent CEK_HistoryPakai = new Intent(InsertPemakaian.this, HistoryPemakaianAdmin.class);
                                            startActivity(CEK_HistoryPakai);
                                            finish();
                                        } catch (Exception e) {
                                            Toast.makeText(InsertPemakaian.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .setNegativeButton("Tidak", null)
                                .create();
                        dialog.show();
                    }
                }else {
                    Toast.makeText(InsertPemakaian.this, "Mohon lengkapi data!", Toast.LENGTH_LONG).show();
                }
            }
        });
        //parameter  setupToolbar
        setupToolbar();
    }

    //deklarasi parameter setupToolbar
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.formPakai);
        toolbar.setTitle("Form History Pemakaian");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //back home pada toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //set tanggal
    private void setDateTimeField() {
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpTanggal.show();
            }
        });

        dpTanggal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei",
                        "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
                sTanggal = dayOfMonth + " " + bulan[monthOfYear] + " " + year;
                etTanggal.setText(sTanggal);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
