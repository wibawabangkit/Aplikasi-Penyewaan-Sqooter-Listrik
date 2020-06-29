package com.bangkit.go_pedwheels.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bangkit.go_pedwheels.R;
import com.bangkit.go_pedwheels.adapter.AlertDialogManager;
import com.bangkit.go_pedwheels.session.SessionManager;

/** By Wibawa Bangkit on Tahun 2020
 *  Penyewaan Otoped  Wheels Berdasarkan Metode TOPSIS
 */
public class MainActivity extends AppCompatActivity {

    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    Button btnLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                                finish();
                                session.logoutUser();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });
    }

    public void profileMenu(View v) {
        Intent profil = new Intent(this,ProfilActivity.class);
        startActivity(profil);
    }

    public void historyMenu(View v) {
        Intent History = new Intent(this,HistoryActivity.class);
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
}