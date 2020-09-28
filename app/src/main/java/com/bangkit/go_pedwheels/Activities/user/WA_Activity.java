package com.bangkit.go_pedwheels.Activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bangkit.go_pedwheels.R;




public class WA_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_a_);

            String contact = "+62 85697853892"; //nomer yang akan di whatsapp

            String url = "https://api.whatsapp.com/send?phone=" + contact + "&text=Dear Admin,\n" + //isi whatsapp

                    "    \n";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url)); startActivity(i);
        }
    }
