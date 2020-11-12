package com.bangkit.go_pedwheels.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangkit.go_pedwheels.Model.Mhistory_pemakaian;
import com.bangkit.go_pedwheels.R;

import java.util.ArrayList;

public class HistoryPemakaianAdapter extends ArrayAdapter<Mhistory_pemakaian> {

    public HistoryPemakaianAdapter(Activity context, ArrayList<Mhistory_pemakaian> notification) {
        super(context, 0, notification);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_history_pakai, parent, false);
        }

        Mhistory_pemakaian current = getItem(position);

        TextView idSewa = listItemView.findViewById(R.id.id_booking);
        idSewa.setText("ID : " + current.getIdsewa());

        TextView tanggal = listItemView.findViewById(R.id.tanggalpakai);
        tanggal.setText(current.getTanggalpakai());

        TextView riwayatpakai = listItemView.findViewById(R.id.riwayat_pakai);
        riwayatpakai.setText(current.getRiwayatpakai());

        TextView tvHasil = listItemView.findViewById(R.id.tv_Hasil);
        tvHasil.setText("Hasil Pemakaian :");

        TextView Hasil = listItemView.findViewById(R.id.hasil);
        Hasil.setText(current.getHasil());



        ImageView imageIcon = listItemView.findViewById(R.id.image);

        if (current.hasImage()) {
            imageIcon.setImageResource(current.getImageResourceId());
            imageIcon.setVisibility(View.VISIBLE);
        } else {
            imageIcon.setVisibility(View.GONE);
        }

        return listItemView;
    }
}