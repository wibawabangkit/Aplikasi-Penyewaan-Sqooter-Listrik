package com.bangkit.go_pedwheels.Activities.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bangkit.go_pedwheels.R;
import com.bangkit.go_pedwheels.Activities.ProsedurActivity;
import com.bangkit.go_pedwheels.Adapter.AlertDialogManager;
import com.bangkit.go_pedwheels.Session.SessionManager;
import java.util.Calendar;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;


public class HomeAdmin extends AppCompatActivity {
    //Variabel
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    Button btnLogout;
    String hariIni;
    TextView today;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        //deklarasi variabel today
        today = findViewById(R.id.tvDate);

        //configuration session manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        //deklarasi variabel btnLogout
        btnLogout = findViewById(R.id.out);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog dialog = new AlertDialog.Builder(HomeAdmin.this)
                        .setTitle("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                session.destroyCurrentUser();
                                session.logoutUser();
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });

        //deklarasi variabel hariIni
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

        //parameter
        getToday();
    }

    //go to menu history pada admin
    public void historyMenuadmin(View v) {
        Intent History = new Intent(this, HistoryAdmin.class);
        startActivity(History);
    }

    // go to menu total sewa pada admin
    public void MenuTotalSewa(View v) {
        Intent total = new Intent(this, TotalSewaActivity.class);
        startActivity(total);
    }

    //go to menu pemakaian pada admin
    public void Persyaratan(View v) {
        Intent pemakaian = new Intent(this, HistoryPemakaianAdmin.class);
        startActivity(pemakaian);
    }

    //deklarasi parameter getToday
    private void getToday() {
        Date date = Calendar.getInstance().getTime();
        String DATE = (String) DateFormat.format("d", date);
        String monthNumber = (String) DateFormat.format("M", date);
        String year = (String) DateFormat.format("yyyy", date);

        //deklarasi variabel bulan
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

    //ketika ditekan back pada handphone, maka muncul notif peringatan keluar
    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(HomeAdmin.this)
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