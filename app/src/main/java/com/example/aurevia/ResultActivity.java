package com.example.aurevia;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        // Menampilkan Fragment Modular
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new DetailFragment())
                .commit();
    }
}