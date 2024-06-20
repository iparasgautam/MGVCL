package com.example.mgvcl;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {
    // Calendar
    TextView tvSelectedDate;
    Button btnSubmit;

    // Dropdowns
    Spinner spinnerCircle, spinnerDistrict, spinnerTaluka, spinnerTimeOfDay;
    String[] circle = {"Anand", "Baroda(city)", "Baroda(O&M)", "Godhra", "Nadiad"};
    String[] timeOfDay = {"Morning", "Afternoon", "Night"};
    HashMap<String, String[]> districtsMap;
    HashMap<String, String[]> talukasMap;

    // Data Entry
    EditText inputAffectedV, inputRectifiedV, inputBalanceV,
            inputAffectedF, inputRectifiedF, inputBalanceF,
            inputAffectedT, inputRectifiedT, inputBalanceT,
            inputAffectedP, inputRectifiedP, inputBalanceP,
            inputAffectedH, inputRectifiedH, inputBalanceH,
            inputAffectedL, inputRectifiedL, inputBalanceL,
            inputBalanceHL,
            inputAffectedTC, inputRectifiedTC, inputBalanceTC,
            inputAffectedTP, inputRectifiedTP, inputBalanceTP,
            inputAffectedTO, inputRectifiedTO, inputBalanceTO,
            inputAffectedW, inputRectifiedW, inputBalanceW,
            inputDeptHFD, inputOsHFO, inputDeptHND, inputOsHNO, inputAnimalFatal,
            inputAffectedTN, inputRectifiedTN, inputBalanceTN,
            inputExpenditure;

    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Calendar
        tvSelectedDate = findViewById(R.id.tv_selected_date);

        // Button
        btnSubmit = findViewById(R.id.btn_submit_f);

        // Dropdowns
        spinnerCircle = findViewById(R.id.spinner_circle);
        spinnerDistrict = findViewById(R.id.spinner_districts);
        spinnerTaluka = findViewById(R.id.spinner_taluka);
        spinnerTimeOfDay = findViewById(R.id.spinner_time_of_day);

        // Data Entry
        setupDataEntryFields();

        // Setup Spinners
        setupSpinners();

        // Date Picker Dialog
        tvSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Submit button click listener
        Button btnSubmit = findViewById(R.id.btn_submit_f);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToFirestore();
            }
        });
    }

    private void setupDataEntryFields() {
        // Initialize all EditText fields and set TextWatchers
        inputAffectedV = findViewById(R.id.input_affectedV);
        inputRectifiedV = findViewById(R.id.input_rectifiedV);
        inputBalanceV = findViewById(R.id.input_balanceV);
        inputAffectedV.addTextChangedListener(new GenericTextWatcher(inputAffectedV));
        inputRectifiedV.addTextChangedListener(new GenericTextWatcher(inputRectifiedV));

        inputAffectedF = findViewById(R.id.input_affectedF);
        inputRectifiedF = findViewById(R.id.input_rectifiedF);
        inputBalanceF = findViewById(R.id.input_balanceF);
        inputAffectedF.addTextChangedListener(new GenericTextWatcher(inputAffectedF));
        inputRectifiedF.addTextChangedListener(new GenericTextWatcher(inputRectifiedF));

        inputAffectedT = findViewById(R.id.input_affectedT);
        inputRectifiedT = findViewById(R.id.input_rectifiedT);
        inputBalanceT = findViewById(R.id.input_balanceT);
        inputAffectedT.addTextChangedListener(new GenericTextWatcher(inputAffectedT));
        inputRectifiedT.addTextChangedListener(new GenericTextWatcher(inputRectifiedT));

        inputAffectedP = findViewById(R.id.input_affectedP);
        inputRectifiedP = findViewById(R.id.input_rectifiedP);
        inputBalanceP = findViewById(R.id.input_balanceP);
        inputAffectedP.addTextChangedListener(new GenericTextWatcher(inputAffectedP));
        inputRectifiedP.addTextChangedListener(new GenericTextWatcher(inputRectifiedP));

        inputAffectedH = findViewById(R.id.input_affectedH);
        inputRectifiedH = findViewById(R.id.input_rectifiedH);
        inputBalanceH = findViewById(R.id.input_balanceH);
        inputAffectedH.addTextChangedListener(new GenericTextWatcher(inputAffectedH));
        inputRectifiedH.addTextChangedListener(new GenericTextWatcher(inputRectifiedH));

        inputAffectedL = findViewById(R.id.input_affectedL);
        inputRectifiedL = findViewById(R.id.input_rectifiedL);
        inputBalanceL = findViewById(R.id.input_balanceL);
        inputAffectedL.addTextChangedListener(new GenericTextWatcher(inputAffectedL));
        inputRectifiedL.addTextChangedListener(new GenericTextWatcher(inputRectifiedL));

        inputBalanceHL = findViewById(R.id.input_balanceHL);
        inputAffectedH.addTextChangedListener(new GenericTextWatcher(inputBalanceHL));
        inputAffectedL.addTextChangedListener(new GenericTextWatcher(inputBalanceHL));

        inputAffectedTC = findViewById(R.id.input_affectedTC);
        inputRectifiedTC = findViewById(R.id.input_rectifiedTC);
        inputBalanceTC = findViewById(R.id.input_balanceTC);
        inputAffectedTC.addTextChangedListener(new GenericTextWatcher(inputAffectedTC));
        inputRectifiedTC.addTextChangedListener(new GenericTextWatcher(inputRectifiedTC));

        inputAffectedTP = findViewById(R.id.input_affectedTP);
        inputRectifiedTP = findViewById(R.id.input_rectifiedTP);
        inputBalanceTP = findViewById(R.id.input_balanceTP);
        inputAffectedTP.addTextChangedListener(new GenericTextWatcher(inputAffectedTP));
        inputRectifiedTP.addTextChangedListener(new GenericTextWatcher(inputRectifiedTP));

        inputAffectedTO = findViewById(R.id.input_affectedTO);
        inputRectifiedTO = findViewById(R.id.input_rectifiedTO);
        inputBalanceTO = findViewById(R.id.input_balanceTO);
        inputAffectedTO.addTextChangedListener(new GenericTextWatcher(inputAffectedTO));
        inputRectifiedTO.addTextChangedListener(new GenericTextWatcher(inputRectifiedTO));

        inputAffectedW = findViewById(R.id.input_affectedW);
        inputRectifiedW = findViewById(R.id.input_rectifiedW);
        inputBalanceW = findViewById(R.id.input_balanceW);
        inputAffectedW.addTextChangedListener(new GenericTextWatcher(inputAffectedW));
        inputRectifiedW.addTextChangedListener(new GenericTextWatcher(inputRectifiedW));

        inputDeptHFD = findViewById(R.id.input_deptHFD);
        inputOsHFO = findViewById(R.id.input_osHFO);
        inputDeptHND = findViewById(R.id.input_deptHNO);
        inputOsHNO = findViewById(R.id.input_osHNO);
        inputAnimalFatal = findViewById(R.id.input_animalFatal);
        inputDeptHND.addTextChangedListener(new GenericTextWatcher(inputDeptHND));
        inputOsHNO.addTextChangedListener(new GenericTextWatcher(inputOsHNO));
        inputDeptHFD.addTextChangedListener(new GenericTextWatcher(inputDeptHFD));
        inputOsHFO.addTextChangedListener(new GenericTextWatcher(inputOsHFO));
        inputAnimalFatal.addTextChangedListener(new GenericTextWatcher(inputAnimalFatal));

        inputAffectedTN = findViewById(R.id.input_affectedTN);
        inputRectifiedTN = findViewById(R.id.input_rectifiedTN);
        inputBalanceTN = findViewById(R.id.input_balanceTN);
        inputAffectedTN.addTextChangedListener(new GenericTextWatcher(inputAffectedTN));
        inputRectifiedTN.addTextChangedListener(new GenericTextWatcher(inputRectifiedTN));

        inputExpenditure = findViewById(R.id.input_expenditure);
    }

    private void setupSpinners() {
        districtsMap = new HashMap<>();
        talukasMap = new HashMap<>();

        // Populate Districts and Talukas Map (Sample Data)

//        districtsMap.put("Anand", new String[]{"Anand", "Kheda"});
//        talukasMap.put("Anand", new String[]{"Anand", "Anklav", "Borsad", "Khambhat", "Petlad", "Sojitra", "Tarapur", "Umreth"});
//        districtsMap.put("Baroda(city)", new String[]{"Vadodara"});
//        talukasMap.put("Baroda(city)", new String[]{"Dabhoi", "Desar", "Karjan", "Padra", "Savli", "Sinor"});
//        districtsMap.put("Baroda(city)", new String[]{"Chhota Udepur","Vadodara"});
//        talukasMap.put("Baroda(O&M)", new String[]{"Dabhoi", "Desar", "Karjan", "Padra", "Savli", "Sinor"});
//        talukasMap.put("Godhra", new String[]{"Balasinor", "Kadana", "Khanpur", "Lunawada", "Santrampur", "Virpur"});
//        talukasMap.put("Nadiad", new String[]{"Kapadvanj", "Kathlal", "Kheda", "Mahudha", "Matar", "Mehmedabad", "Nadiad", "Thasra", "Vaso"});

        districtsMap = new HashMap<>();
        districtsMap.put("Anand", new String[]{"Anand", "Kheda"});
        districtsMap.put("Baroda(city)", new String[]{"Vadodara"});
        districtsMap.put("Baroda(O&M)", new String[]{"Chhota Udepur", "Vadodara"});
        districtsMap.put("Godhra", new String[]{"Dahod", "Mahisagar", "Panch Mahals", "Vadodara"});
        districtsMap.put("Nadiad", new String[]{"Ahmadabad", "Anand", "Kheda", "Mahisagar"});

        // Initialize Talukas map
        talukasMap = new HashMap<>();
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

    private class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            calculateValues(view);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Do nothing
        }

        private void calculateValues(View view) {
            if (view == inputAffectedV || view == inputRectifiedV) {
                calculateBalance(inputAffectedV, inputRectifiedV, inputBalanceV);
            } else if (view == inputAffectedF || view == inputRectifiedF) {
                calculateBalance(inputAffectedF, inputRectifiedF, inputBalanceF);
            } else if (view == inputAffectedT || view == inputRectifiedT) {
                calculateBalance(inputAffectedT, inputRectifiedT, inputBalanceT);
            } else if (view == inputAffectedP || view == inputRectifiedP) {
                calculateBalance(inputAffectedP, inputRectifiedP, inputBalanceP);
            } else if (view == inputAffectedH || view == inputRectifiedH) {
                calculateBalance(inputAffectedH, inputRectifiedH, inputBalanceH);
                calculateBalance1(inputAffectedH, inputAffectedL, inputBalanceHL);
                calculateBalance2(inputAffectedH, inputAffectedL, inputAffectedTC, inputExpenditure);
            } else if (view == inputAffectedL || view == inputRectifiedL) {
                calculateBalance(inputAffectedL, inputRectifiedL, inputBalanceL);
                calculateBalance1(inputAffectedH, inputAffectedL, inputBalanceHL);
                calculateBalance2(inputAffectedH, inputAffectedL, inputAffectedTC, inputExpenditure);
            } else if (view == inputAffectedTC || view == inputRectifiedTC) {
                calculateBalance(inputAffectedTC, inputRectifiedTC, inputBalanceTC);
                calculateBalance2(inputAffectedH, inputAffectedL, inputAffectedTC, inputExpenditure);
            } else if (view == inputAffectedTP || view == inputRectifiedTP) {
                calculateBalance(inputAffectedTP, inputRectifiedTP, inputBalanceTP);
            } else if (view == inputAffectedTO || view == inputRectifiedTO) {
                calculateBalance(inputAffectedTO, inputRectifiedTO, inputBalanceTO);
            } else if (view == inputAffectedW || view == inputRectifiedW) {
                calculateBalance(inputAffectedW, inputRectifiedW, inputBalanceW);
            } else if (view == inputDeptHND || view == inputOsHNO) {
                calculateBalance(inputDeptHND, inputOsHNO, inputBalanceHL);
            } else if (view == inputDeptHFD || view == inputOsHFO) {
                calculateBalance(inputDeptHFD, inputOsHFO, inputBalanceHL);
            } else if (view == inputAnimalFatal) {
                // Do nothing
            } else if (view == inputAffectedTN || view == inputRectifiedTN) {
                calculateBalance(inputAffectedTN, inputRectifiedTN, inputBalanceTN);
            }
        }


        private void calculateBalance1(EditText affectedView, EditText rectifiedView, EditText balanceView) {
            String affectedText = affectedView.getText().toString();
            String rectifiedText = rectifiedView.getText().toString();

            int affected = 0;
            int rectified = 0;

            if (!affectedText.isEmpty()) {
                affected = Integer.parseInt(affectedText);
            }

            if (!rectifiedText.isEmpty()) {
                rectified = Integer.parseInt(rectifiedText);
            }

            int balance = affected + rectified;
            balanceView.setText(String.valueOf(balance));
        }

        private void calculateBalance2(EditText affectedView, EditText rectifiedView, EditText balancedView, EditText expenditureView) {
            String affectedText = affectedView.getText().toString();
            String rectifiedText = rectifiedView.getText().toString();
            String balancedText = balancedView.getText().toString();

            int affected = 0;
            int rectified = 0;
            int balanced = 0;

            if (!affectedText.isEmpty()) {
                affected = Integer.parseInt(affectedText);
            }

            if (!rectifiedText.isEmpty()) {
                rectified = Integer.parseInt(rectifiedText);
            }

            if (!balancedText.isEmpty()) {
                balanced = Integer.parseInt(balancedText);
            }

            float expenditure = (float)((affected*255977) + (rectified*211834) + (balanced*155425))/100000;
            expenditureView.setText(String.valueOf(expenditure));
        }

        private void calculateBalance(EditText affectedView, EditText rectifiedView, EditText balanceView) {
            String affectedText = affectedView.getText().toString();
            String rectifiedText = rectifiedView.getText().toString();

            int affected = 0;
            int rectified = 0;

            if (!affectedText.isEmpty()) {
                affected = Integer.parseInt(affectedText);
            }

            if (!rectifiedText.isEmpty()) {
                rectified = Integer.parseInt(rectifiedText);
            }

            int balance = affected - rectified;
            balanceView.setText(String.valueOf(balance));
        }
    }

    private void saveDataToFirestore() {
        String date = tvSelectedDate.getText().toString();
        String timeOfDay = spinnerTimeOfDay.getSelectedItem().toString();
        String circle = spinnerCircle.getSelectedItem().toString();
        String district = spinnerDistrict.getSelectedItem().toString();
        String taluka = spinnerTaluka.getSelectedItem().toString();

        Map<String, Object> dataVillages = new HashMap<>();
        dataVillages.put("affectedV", Integer.parseInt(inputAffectedV.getText().toString()));
        dataVillages.put("rectifiedV", Integer.parseInt(inputRectifiedV.getText().toString()));
        dataVillages.put("balanceV", Integer.parseInt(inputBalanceV.getText().toString()));

        Map<String, Object> dataFeeders = new HashMap<>();
        dataFeeders.put("affectedF", Integer.parseInt(inputAffectedF.getText().toString()));
        dataFeeders.put("rectifiedF", Integer.parseInt(inputRectifiedF.getText().toString()));
        dataFeeders.put("balanceF", Integer.parseInt(inputBalanceF.getText().toString()));

        Map<String, Object> dataTown = new HashMap<>();
        dataTown.put("affectedT", Integer.parseInt(inputAffectedT.getText().toString()));
        dataTown.put("rectifiedT", Integer.parseInt(inputRectifiedT.getText().toString()));
        dataTown.put("balanceT", Integer.parseInt(inputBalanceT.getText().toString()));

        Map<String, Object> dataPoles = new HashMap<>();
        dataPoles.put("affectedP", Integer.parseInt(inputAffectedP.getText().toString()));
        dataPoles.put("rectifiedP", Integer.parseInt(inputRectifiedP.getText().toString()));
        dataPoles.put("balanceP", Integer.parseInt(inputBalanceP.getText().toString()));

        Map<String, Object> dataHTLine = new HashMap<>();
        dataHTLine.put("affectedH", Integer.parseInt(inputAffectedH.getText().toString()));
        dataHTLine.put("rectifiedH", Integer.parseInt(inputRectifiedH.getText().toString()));
        dataHTLine.put("balanceH", Integer.parseInt(inputBalanceH.getText().toString()));

        Map<String, Object> dataLTLine = new HashMap<>();
        dataLTLine.put("affectedL", Integer.parseInt(inputAffectedL.getText().toString()));
        dataLTLine.put("rectifiedL", Integer.parseInt(inputRectifiedL.getText().toString()));
        dataLTLine.put("balanceL", Integer.parseInt(inputBalanceL.getText().toString()));

        Map<String, Object> dataHLandLT = new HashMap<>();
        int a = Integer.parseInt(inputAffectedH.getText().toString());
        int b = Integer.parseInt(inputAffectedL.getText().toString());
        int total = a + b;
        dataHLandLT.put("Damaged Conductor(H.T + L.T)", total);

        Map<String, Object> dataTCDamage = new HashMap<>();
        dataTCDamage.put("affectedTC", Integer.parseInt(inputAffectedTC.getText().toString()));
        dataTCDamage.put("rectifiedTC", Integer.parseInt(inputRectifiedTC.getText().toString()));
        dataTCDamage.put("balanceTC", Integer.parseInt(inputBalanceTC.getText().toString()));

        Map<String, Object> data3PhaseServiceLine = new HashMap<>();
        data3PhaseServiceLine.put("affectedTP", Integer.parseInt(inputAffectedTP.getText().toString()));
        data3PhaseServiceLine.put("rectifiedTP", Integer.parseInt(inputRectifiedTP.getText().toString()));
        data3PhaseServiceLine.put("balanceTP", Integer.parseInt(inputBalanceTP.getText().toString()));

        Map<String, Object> data1PhaseServiceLine = new HashMap<>();
        data1PhaseServiceLine.put("affectedTO", Integer.parseInt(inputAffectedTO.getText().toString()));
        data1PhaseServiceLine.put("rectifiedTO", Integer.parseInt(inputRectifiedTO.getText().toString()));
        data1PhaseServiceLine.put("balanceTO", Integer.parseInt(inputBalanceTO.getText().toString()));

        Map<String, Object> dataWaterWorks = new HashMap<>();
        dataWaterWorks.put("affectedW", Integer.parseInt(inputAffectedW.getText().toString()));
        dataWaterWorks.put("rectifiedW", Integer.parseInt(inputRectifiedW.getText().toString()));
        dataWaterWorks.put("balanceW", Integer.parseInt(inputBalanceW.getText().toString()));

        Map<String, Object> dataAccidentHumanFatal = new HashMap<>();
        dataAccidentHumanFatal.put("DeptHFD", Integer.parseInt(inputDeptHFD.getText().toString()));
        dataAccidentHumanFatal.put("OsHFO", Integer.parseInt(inputOsHFO.getText().toString()));

        Map<String, Object> dataAccidentHumanNonFatal = new HashMap<>();
        dataAccidentHumanNonFatal.put("DeptHND", Integer.parseInt(inputDeptHND.getText().toString()));
        dataAccidentHumanNonFatal.put("OsHNO", Integer.parseInt(inputOsHNO.getText().toString()));

        Map<String, Object> dataAccidentAnimalFatal = new HashMap<>();
        dataAccidentAnimalFatal.put("AnimalFatal", Integer.parseInt(inputAnimalFatal.getText().toString()));

        Map<String, Object> dataTotalConsumers = new HashMap<>();
        dataTotalConsumers.put("affectedTN", Integer.parseInt(inputAffectedTN.getText().toString()));
        dataTotalConsumers.put("rectifiedTN", Integer.parseInt(inputRectifiedTN.getText().toString()));
        dataTotalConsumers.put("balanceTN", Integer.parseInt(inputBalanceTN.getText().toString()));

        Map<String, Object> dataExpenditure = new HashMap<>();
        dataExpenditure.put("Expenditure", Float.parseFloat(inputExpenditure.getText().toString()));

        CollectionReference mgvclRef = db.collection("MGVCL");
        DocumentReference dateTimeRef = mgvclRef.document(date + " " + timeOfDay);
        CollectionReference circleRef = dateTimeRef.collection(circle);
        DocumentReference districtRef = circleRef.document(district);
        CollectionReference talukaRef = districtRef.collection(taluka);

        talukaRef.document("Villages").set(dataVillages);
        talukaRef.document("Feeders").set(dataFeeders);
        talukaRef.document("Town").set(dataTown);
        talukaRef.document("Poles").set(dataPoles);
        talukaRef.document("H.T Line").set(dataHTLine);
        talukaRef.document("L.T Line").set(dataLTLine);
        talukaRef.document("HL + LT").set(dataHLandLT);
        talukaRef.document("TC Damage").set(dataTCDamage);
        talukaRef.document("3Phase Service line").set(data3PhaseServiceLine);
        talukaRef.document("1Phase Service Line").set(data1PhaseServiceLine);
        talukaRef.document("Water Works").set(dataWaterWorks);
        talukaRef.document("Accident Human Fatal").set(dataAccidentHumanFatal);
        talukaRef.document("Accident Human NonFatal").set(dataAccidentHumanNonFatal);
        talukaRef.document("Accident Animal Fatal").set(dataAccidentAnimalFatal);
        talukaRef.document("Total No of Consumers").set(dataTotalConsumers);
        talukaRef.document("Expenditure").set(dataExpenditure)

                .addOnSuccessListener(aVoid -> Toast.makeText(FormActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(FormActivity.this, "Error saving data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}