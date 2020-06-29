package com.bangkit.go_pedwheels.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.bangkit.go_pedwheels.R;
import com.bangkit.go_pedwheels.database.DatabaseHelper;
import com.bangkit.go_pedwheels.session.SessionManager;

import java.util.Calendar;
import java.util.HashMap;
/** By Wibawa Bangkit on Tahun 2020
 *  Penyewaan Otoped  Wheels Berdasarkan Metode TOPSIS
 */
public class SewaActivity extends AppCompatActivity {
    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Spinner spinUsia, spinBerat, spinWaktu, spinJarak;
    SessionManager session;
    String email;
    int ID_Sewa;
    public String sUsia, sBerat, sWaktu, sJarak, sTanggal;
    int jml;
    int harga, hargaTotal;
    private EditText etTanggal;
    private DatePickerDialog dpTanggal;
    Calendar newCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa);
        dbHelper = new DatabaseHelper(SewaActivity.this);
        db = dbHelper.getReadableDatabase();


        final String[] Usia = {"0","Anak", "Dewasa"};
        final String[] Berat = {"0","40-50", "50-55", "55-60", "60-65", "65-70"};
        final String[] Waktu = {"0","1", "2", "3", "4", "5"};
        final String[] Jarak = {"0","1", "2", "3", "4", "5"};

        spinUsia = findViewById(R.id.umur);
        spinBerat = findViewById(R.id.berat);
        spinWaktu = findViewById(R.id.lamasewa);
        spinJarak = findViewById(R.id.jarak);

        ArrayAdapter<CharSequence> adapterUmur = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, Usia);
        adapterUmur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinUsia.setAdapter(adapterUmur);

        ArrayAdapter<CharSequence> adapterBerat = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, Berat);
        adapterBerat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBerat.setAdapter(adapterBerat);

        ArrayAdapter<CharSequence> adapterWaktu = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, Waktu);
        adapterWaktu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinWaktu.setAdapter(adapterWaktu);

        ArrayAdapter<CharSequence> adapterJarak = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, Jarak);
        adapterJarak.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinJarak.setAdapter(adapterJarak);

        spinUsia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sUsia = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinBerat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sBerat = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinWaktu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sWaktu = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinJarak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sJarak = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnSewa = findViewById(R.id.btnsewa);
        etTanggal = findViewById(R.id.tanggal_sewa);
        etTanggal.setInputType(InputType.TYPE_NULL);
        etTanggal.requestFocus();
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);
        setDateTimeField();

        btnSewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perhitunganHarga();
                if (sUsia != null && sBerat != null && sWaktu != null && sJarak != null && sTanggal != null) {
                    if ((sUsia.equalsIgnoreCase("0"))
                            || (sBerat.equalsIgnoreCase("0"))
                            || (sWaktu.equalsIgnoreCase("0"))
                            || (sJarak.equalsIgnoreCase("0"))
                            ) {
                        Toast.makeText(SewaActivity.this, "Isi Dengan Benar !", Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog dialog = new AlertDialog.Builder(SewaActivity.this)
                                .setTitle("Ingin Sewa dan lihat advice otoped diprosedur penyewaan?")
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            db.execSQL("INSERT INTO tb_sewa (Usia, Berat, Tanggal, Waktu, Jarak) VALUES ('" +
                                                    sUsia + "','" +
                                                    sBerat + "','" +
                                                    sTanggal + "','" +
                                                    sWaktu + "','" +
                                                    sJarak + "');");
                                            cursor = db.rawQuery("SELECT ID_Sewa FROM tb_sewa ORDER BY ID_Sewa DESC", null);
                                            cursor.moveToLast();
                                            if (cursor.getCount() > 0) {
                                                cursor.moveToPosition(0);
                                                ID_Sewa = cursor.getInt(0);
                                            }
                                            db.execSQL("INSERT INTO tb_harga (username, ID_Sewa, harga, harga_total) VALUES ('" +
                                                    email + "','" +
                                                    ID_Sewa + "','" +
                                                    harga + "','" +
                                                    hargaTotal + "');");
                                            Toast.makeText(SewaActivity.this, "Sewa berhasil", Toast.LENGTH_LONG).show();
                                            finish();
                                        } catch (Exception e) {
                                            Toast.makeText(SewaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .setNegativeButton("Tidak", null)
                                .create();
                        dialog.show();
                    }
                }else {
                    Toast.makeText(SewaActivity.this, "Mohon lengkapi data pemesanan!", Toast.LENGTH_LONG).show();
                }
            }
        });

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbsewa);
        toolbar.setTitle("Form Sewa Otoped Wheels");
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

    public void perhitunganHarga() {
        if (sJarak.equalsIgnoreCase("1") && sBerat.equalsIgnoreCase("40-50")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("1") && sBerat.equalsIgnoreCase("50-55")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("1") && sBerat.equalsIgnoreCase("55-60")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("1") && sBerat.equalsIgnoreCase("60-65")) {
            harga = 20000;
        } else if (sJarak.equalsIgnoreCase("1") && sBerat.equalsIgnoreCase("65-70")) {
            harga = 20000;
        } else if (sJarak.equalsIgnoreCase("2") && sBerat.equalsIgnoreCase("40-50")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("2") && sBerat.equalsIgnoreCase("50-55")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("2") && sBerat.equalsIgnoreCase("55-60")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("2") && sBerat.equalsIgnoreCase("60-65")) {
            harga = 20000;
        } else if (sJarak.equalsIgnoreCase("2") && sBerat.equalsIgnoreCase("65-70")) {
            harga = 20000;
        }else if (sJarak.equalsIgnoreCase("3") && sBerat.equalsIgnoreCase("40-50")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("3") && sBerat.equalsIgnoreCase("50-55")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("3") && sBerat.equalsIgnoreCase("55-60")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("3") && sBerat.equalsIgnoreCase("60-65")) {
            harga = 20000;
        } else if (sJarak.equalsIgnoreCase("3") && sBerat.equalsIgnoreCase("65-70")) {
            harga = 20000;
        }else if (sJarak.equalsIgnoreCase("4") && sBerat.equalsIgnoreCase("40-50")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("4") && sBerat.equalsIgnoreCase("50-55")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("4") && sBerat.equalsIgnoreCase("55-60")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("4") && sBerat.equalsIgnoreCase("60-65")) {
            harga = 20000;
        } else if (sJarak.equalsIgnoreCase("4") && sBerat.equalsIgnoreCase("65-70")) {
            harga = 20000;
        }else if (sJarak.equalsIgnoreCase("5") && sBerat.equalsIgnoreCase("40-50")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("5") && sBerat.equalsIgnoreCase("50-55")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("5") && sBerat.equalsIgnoreCase("55-60")) {
            harga = 15000;
        } else if (sJarak.equalsIgnoreCase("5") && sBerat.equalsIgnoreCase("60-65")) {
            harga = 20000;
        } else if (sJarak.equalsIgnoreCase("5") && sBerat.equalsIgnoreCase("65-70")) {
            harga = 20000;
        }


        jml = Integer.parseInt(sWaktu);

        hargaTotal = jml * harga;
          }

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