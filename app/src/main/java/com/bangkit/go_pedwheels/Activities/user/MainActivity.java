package com.bangkit.go_pedwheels.Activities.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangkit.go_pedwheels.Activities.admin.HistoryAdmin;
import com.bangkit.go_pedwheels.R;
import com.bangkit.go_pedwheels.Activities.ProsedurActivity;
import com.bangkit.go_pedwheels.Adapter.AlertDialogManager;
import com.bangkit.go_pedwheels.Session.SessionManager;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    Button btnLogout;
    String hariIni;
    TextView today, namaAPP;
    ImageView telp1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        today = findViewById(R.id.tvDate);


        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        btnLogout = findViewById(R.id.out);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                session.logoutUser();
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });
        namaAPP = findViewById(R.id.textcall);
        namaAPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telp = new Intent(MainActivity.this,TelpActivity.class);
                startActivity(telp);
                finish();
            }
        });
        telp1 = findViewById(R.id.call);
        telp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imagetelp1 = new Intent(MainActivity.this, TelpActivity.class);
                startActivity(imagetelp1);
                finish();
            }
        });


        Date dateNow = Calendar.getInstance().getTime();
        hariIni = (String) DateFormat.format("EEEE", dateNow);
        if (hariIni.equalsIgnoreCase("sunday")) {
            hariIni = "Minggu";
        } else if (hariIni.equalsIgnoreCase("monday")) {
            hariIni = "Senin";
        } else if (hariIni.equalsIgnoreCase("tuesday")) {
            hariIni = "Selasa";
        } else if (hariIni.equalsIgnoreCase("wednesday")) {
            hariIni = "Rabu";
        } else if (hariIni.equalsIgnoreCase("thursday")) {
            hariIni = "Kamis";
        } else if (hariIni.equalsIgnoreCase("friday")) {
            hariIni = "Jumat";
        } else if (hariIni.equalsIgnoreCase("saturday")) {
            hariIni = "Sabtu";
        }

        getToday();
    }

    public void profileMenu(View v) {
        Intent profil = new Intent(this, ProfilActivity.class);
        startActivity(profil);
    }

    public void historyMenu(View v) {
        Intent History = new Intent(this, HistoryActivity.class);
        startActivity(History);
    }

    public void MenuSewa(View v) {
        Intent sewa = new Intent(this, SewaActivity.class);
        startActivity(sewa);
    }

    public void Persyaratan(View v) {
        Intent persyaratan = new Intent(this, ProsedurActivity.class);
        startActivity(persyaratan);
    }

    private void getToday() {
        Date date = Calendar.getInstance().getTime();
        String DATE = (String) DateFormat.format("d", date);
        String monthNumber = (String) DateFormat.format("M", date);
        String year = (String) DateFormat.format("yyyy", date);

        int bulan = Integer.parseInt(monthNumber);
        String nama = null;
        if (bulan == 1) {
            nama = "Januari";
        } else if (bulan == 2) {
            nama = "Februari";
        } else if (bulan == 3) {
            nama = "Maret";
        } else if (bulan == 4) {
            nama = "April";
        } else if (bulan == 5) {
            nama = "Mei";
        } else if (bulan == 6) {
            nama = "Juni";
        } else if (bulan == 7) {
            nama = "Juli";
        } else if (bulan == 8) {
            nama = "Agustus";
        } else if (bulan == 9) {
            nama = "September";
        } else if (bulan == 10) {
            nama = "Oktober";
        } else if (bulan == 11) {
            nama = "November";
        } else if (bulan == 12) {
            nama = "Desember";
        }
        String formatFix = hariIni + ", " + DATE + " " + nama + " " + year;
        today.setText(formatFix);
    }
    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Anda yakin ingin keluar ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        session.destroyCurrentUser(); // ini buat clear user yg lg login
                        session.logoutUser();
                        finish();
                    }
                })
                .setNegativeButton("Tidak", null)
                .create();
        dialog.show();
    }
}