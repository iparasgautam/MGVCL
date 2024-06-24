package com.example.mgvcl;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;

public class SelectActivity extends AppCompatActivity {

    TextView tvSelectedDate;
    Button fetchActivity;

    // Dropdowns
    Spinner spinnerCircle, spinnerDistrict, spinnerTaluka, spinnerTimeOfDay;
    String[] circle = {"Anand", "Baroda(city)", "Baroda(O&M)", "Godhra", "Nadiad"};
    String[] timeOfDay = {"Morning", "Afternoon", "Night"};
    HashMap<String, String[]> districtsMap;
    HashMap<String, String[]> talukasMap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        fetchActivity = findViewById(R.id.fetchActivity);
        // Calendar
        tvSelectedDate = findViewById(R.id.tv_selected_date);

        // Dropdowns
        spinnerCircle = findViewById(R.id.spinner_circle);
        spinnerDistrict = findViewById(R.id.spinner_districts);
        spinnerTaluka = findViewById(R.id.spinner_taluka);
        spinnerTimeOfDay = findViewById(R.id.spinner_time_of_day);

        // Setup Spinners
        setupSpinners();

        // Date Picker Dialog
        tvSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Initialize formActivity Button
        fetchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, FetchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupSpinners() {
        districtsMap = new HashMap<>();
        talukasMap = new HashMap<>();

        districtsMap.put("Anand", new String[]{"Anand", "Kheda"});
        districtsMap.put("Baroda(city)", new String[]{"Vadodara"});
        districtsMap.put("Baroda(O&M)", new String[]{"Chhota Udepur", "Vadodara"});
        districtsMap.put("Godhra", new String[]{"Dahod", "Mahisagar", "Panch Mahals", "Vadodara"});
        districtsMap.put("Nadiad", new String[]{"Ahmadabad", "Anand", "Kheda", "Mahisagar"});

        // Initialize Talukas map
        talukasMap.put("Anand", new String[]{"Anand", "Anklav", "Borsad", "Khambhat", "Petlad", "Sojitra", "Tarapur", "Umreth"});
        talukasMap.put("Kheda", new String[]{"Galteshwar", "Kapadvanj", "Kathlal", "Kheda", "Mahudha", "Matar", "Mehmedabd", "Nadiad", "Thasra", "Vaso"});
        talukasMap.put("Vadodara", new String[]{"Dabhoi", "Desar", "Karjan", "Padra", "Savli", "Sinor", "Vadodara", "Vaghodia"});
        talukasMap.put("Chhota Udepur", new String[]{"Bodeli", "Chhota Udaipur", "Jetpur Pavi", "Kavant", "Nasvadi", "Sankheda"});
        talukasMap.put("Dahod", new String[]{"Devgadbaria", "Dhanpur", "Dahod", "Fatepura", "Garbada", "Jhalod", "Limkheda", "Sanjeli", "Shingvad"});
        talukasMap.put("Mahisagar", new String[]{"Balasinor", "Kadana", "Khanpur", "Lunawada", "Santrampur", "Virpur"});
        talukasMap.put("Panch Mahals", new String[]{"Ghoghamba", "Godhra", "Halol", "Jambughoda", "Kalol", "Morwa (Hadaf)", "Shehera"});
        talukasMap.put("Ahmadabad", new String[]{"Bavla", "Daskroi", "Detroj-Rampura", "Dhandhuka", "Dholera", "Dholka", "Mandal", "Sanand", "Viramgam"});

        // Setup Circle Spinner
        ArrayAdapter<String> circleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, circle);
        circleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCircle.setAdapter(circleAdapter);
        spinnerCircle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCircle = parent.getItemAtPosition(position).toString();
                String[] districts = districtsMap.get(selectedCircle);
                setupDistrictSpinner(districts);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Setup Time of Day Spinner
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeOfDay);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeOfDay.setAdapter(timeAdapter);
    }

    private void setupDistrictSpinner(String[] districts) {
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districts);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(districtAdapter);
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDistrict = parent.getItemAtPosition(position).toString();
                String[] talukas = talukasMap.get(selectedDistrict);
                setupTalukaSpinner(talukas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupTalukaSpinner(String[] talukas) {
        ArrayAdapter<String> talukaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, talukas);
        talukaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaluka.setAdapter(talukaAdapter);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    selectedMonth = selectedMonth + 1;
                    String date = selectedDay + "-" + selectedMonth + "-" + selectedYear;
                    tvSelectedDate.setText(date);
                },
                year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }
}
