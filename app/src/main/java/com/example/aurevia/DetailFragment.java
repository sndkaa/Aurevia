package com.example.aurevia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {
    public DetailFragment() { super(R.layout.fragment_detail); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView tv = view.findViewById(R.id.tvDisplay);
        // Mengambil data dari SharedPreferences
        SharedPreferences p = getActivity().getSharedPreferences("AUREVIA_DATA", Context.MODE_PRIVATE);

        String hasil = "--- Ringkasan Event Aurevia ---\n\n" +
                "Nama Pemesan: " + p.getString("nama_pemesan", "") + "\n" +
                "Nama Acara: " + p.getString("nama", "") + "\n" +
                "Tanggal Pelaksanaan: " + p.getString("tgl", "") + "\n" +
                "Kategori: " + p.getString("kat", "") + "\n" +
                "Tipe Venue: " + p.getString("ven", "") + "\n" +
                "Status Asuransi: " + p.getString("ins", "");
        tv.setText(hasil);

        // Implicit Intent 1: Membuka Browser
        view.findViewById(R.id.btnWeb).setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://budiluhur.ac.id")));
        });

        // Implicit Intent 2: Fitur Share
        view.findViewById(R.id.btnShare).setOnClickListener(v -> {
            Intent s = new Intent(Intent.ACTION_SEND);
            s.setType("text/plain");
            s.putExtra(Intent.EXTRA_TEXT, hasil);
            startActivity(Intent.createChooser(s, "Kirim Detail Via"));
        });
    }
}