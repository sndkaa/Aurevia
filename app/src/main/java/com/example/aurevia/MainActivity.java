package com.example.aurevia;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etCustomerName, etEmail, etPhone, etEventName;
    Button btnDate, btnSubmit;
    TextView tvDate;
    Spinner spCat;
    RadioGroup rgV;
    CheckBox cbIns;
    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCustomerName = findViewById(R.id.etCustomerName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etEventName = findViewById(R.id.etEventName);
        btnDate = findViewById(R.id.btnPickDate);
        tvDate = findViewById(R.id.tvDate);
        spCat = findViewById(R.id.spCategory);
        rgV = findViewById(R.id.rgVenue);
        cbIns = findViewById(R.id.cbInsurance);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Spinner Data (Widget Spinner)
        String[] list = {"Konser Musik", "Seminar IT", "Wedding Planner", "Pameran Seni"};
        spCat.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list));

        // DatePicker
        btnDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, y, m, d) -> {
                selectedDate = d + "/" + (m + 1) + "/" + y;
                tvDate.setText("Tanggal Acara: " + selectedDate);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnSubmit.setOnClickListener(v -> {
            String customerName = etCustomerName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String eventName = etEventName.getText().toString().trim();
            int rgId = rgV.getCheckedRadioButtonId();

            // Regex: Nama Pemesan hanya huruf, Nama Event boleh ada angka
            String regexNama = "^[a-zA-Z\\s]+$";
            String regexEvent = "^[a-zA-Z0-9\\s]+$";

            // Validasi Nama Pemesan
            if (customerName.isEmpty()) {
                etCustomerName.setError("Nama pemesan tidak boleh kosong!");
                return;
            } else if (!customerName.matches(regexNama)) {
                etCustomerName.setError("Nama hanya boleh berisi huruf dan spasi!");
                return;
            }

            // Validasi Email
            if (email.isEmpty()) {
                etEmail.setError("Email tidak boleh kosong!");
                return;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Format email tidak valid!");
                return;
            }

            // Validasi Nomor Telepon
            if (phone.isEmpty()) {
                etPhone.setError(getString(R.string.err_phone_empty));
                return;
            } else if (!Patterns.PHONE.matcher(phone).matches()) {
                etPhone.setError(getString(R.string.err_phone_invalid));
                return;
            }

            // Validasi Nama Event
            if (eventName.isEmpty()) {
                etEventName.setError("Nama event tidak boleh kosong!");
                return;
            } else if (!eventName.matches(regexEvent)) {
                etEventName.setError("Nama event hanya boleh berisi huruf, angka, dan spasi!");
                return;
            }

            if (selectedDate.isEmpty() || rgId == -1) {
                Toast.makeText(this, "Mohon lengkapi Tanggal dan Tipe Venue!", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton rb = findViewById(rgId);
            // Data Persistence (SharedPreferences)
            SharedPreferences.Editor edit = getSharedPreferences("AUREVIA_DATA", MODE_PRIVATE).edit();
            edit.putString("nama_pemesan", customerName);
            edit.putString("email", email);
            edit.putString("telpon", phone);
            edit.putString("nama", eventName);
            edit.putString("tgl", selectedDate);
            edit.putString("kat", spCat.getSelectedItem().toString());
            edit.putString("ven", rb.getText().toString());
            edit.putString("ins", cbIns.isChecked() ? "Terproteksi Asuransi" : "Tanpa Asuransi");
            edit.apply();

            // Explicit Intent: Berpindah ke ResultActivity
            startActivity(new Intent(MainActivity.this, ResultActivity.class));
        });
    }
}