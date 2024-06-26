package com.example.mgvcl;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FetchActivity extends AppCompatActivity {

    private static final String TAG = "FetchActivity";

    private FirebaseFirestore db;
    private TextView displayDataTextView1, displayDataTextView2, displayDataTextView3;
    private EditText input_affectedV, input_rectifiedV, input_balanceV,
            input_affectedF, input_rectifiedF, input_balanceF,
            input_affectedT, input_rectifiedT, input_balanceT,
            input_affectedP, input_rectifiedP, input_balanceP,
            input_affectedH, input_rectifiedH, input_balanceH,
            input_affectedL, input_rectifiedL, input_balanceL,
            input_affectedTC, input_rectifiedTC, input_balanceTC,
            input_affectedTP, input_rectifiedTP, input_balanceTP,
            input_affectedTO, input_rectifiedTO, input_balanceTO,
            input_affectedW, input_rectifiedW, input_balanceW,
            input_deptHFD, input_osHFO, input_deptHNO, input_osHNO,
            input_animalFatal, input_affectedTN, input_rectifiedTN, input_balanceTN,
            input_expenditure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch);

        db = FirebaseFirestore.getInstance();
        initializeViews();

        // Get the data passed from SelectActivity
        String dateTime = getIntent().getStringExtra("dateTime");
        String circle = getIntent().getStringExtra("circle");
        String district = getIntent().getStringExtra("district");
        String taluka = getIntent().getStringExtra("taluka");

        // Fetch data from Firestore based on the criteria
        fetchDataFromFirestore(dateTime, circle, district, taluka);
    }

    private void initializeViews() {
        displayDataTextView1 = findViewById(R.id.displayDataTextView1);
        displayDataTextView2 = findViewById(R.id.displayDataTextView2);
        displayDataTextView3 = findViewById(R.id.displayDataTextView3);

        input_affectedV = findViewById(R.id.input_affectedV);
        input_rectifiedV = findViewById(R.id.input_rectifiedV);
        input_balanceV = findViewById(R.id.input_balanceV);

        input_affectedF = findViewById(R.id.input_affectedF);
        input_rectifiedF = findViewById(R.id.input_rectifiedF);
        input_balanceF = findViewById(R.id.input_balanceF);

        input_affectedT = findViewById(R.id.input_affectedT);
        input_rectifiedT = findViewById(R.id.input_rectifiedT);
        input_balanceT = findViewById(R.id.input_balanceT);

        input_affectedP = findViewById(R.id.input_affectedP);
        input_rectifiedP = findViewById(R.id.input_rectifiedP);
        input_balanceP = findViewById(R.id.input_balanceP);

        input_affectedH = findViewById(R.id.input_affectedH);
        input_rectifiedH = findViewById(R.id.input_rectifiedH);
        input_balanceH = findViewById(R.id.input_balanceH);

        input_affectedL = findViewById(R.id.input_affectedL);
        input_rectifiedL = findViewById(R.id.input_rectifiedL);
        input_balanceL = findViewById(R.id.input_balanceL);

        input_affectedTC = findViewById(R.id.input_affectedTC);
        input_rectifiedTC = findViewById(R.id.input_rectifiedTC);
        input_balanceTC = findViewById(R.id.input_balanceTC);

        input_affectedTP = findViewById(R.id.input_affectedTP);
        input_rectifiedTP = findViewById(R.id.input_rectifiedTP);
        input_balanceTP = findViewById(R.id.input_balanceTP);

        input_affectedTO = findViewById(R.id.input_affectedTO);
        input_rectifiedTO = findViewById(R.id.input_rectifiedTO);
        input_balanceTO = findViewById(R.id.input_balanceTO);

        input_affectedW = findViewById(R.id.input_affectedW);
        input_rectifiedW = findViewById(R.id.input_rectifiedW);
        input_balanceW = findViewById(R.id.input_balanceW);

        input_deptHFD = findViewById(R.id.input_deptHFD);
        input_osHFO = findViewById(R.id.input_osHFO);
        input_deptHNO = findViewById(R.id.input_deptHNO);
        input_osHNO = findViewById(R.id.input_osHNO);

        input_animalFatal = findViewById(R.id.input_animalFatal);

        input_affectedTN = findViewById(R.id.input_affectedTN);
        input_rectifiedTN = findViewById(R.id.input_rectifiedTN);
        input_balanceTN = findViewById(R.id.input_balanceTN);

        input_expenditure = findViewById(R.id.input_expenditure);
    }

    private void fetchDataFromFirestore(String dateTime, String circle, String district, String taluka) {
        Log.d(TAG, "fetchDataFromFirestore: Start fetching data");

        db.collection("MGVCL")
                .document("24-6-2024 Afternoon")
                .collection("Baroda(city)")
                .document("Vadodara")
                .collection("Karjan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                try {
                                    // Extract data from each document
                                    String village = document.getString("village");
                                    String feeder = document.getString("feeder");
                                    String town = document.getString("town");
                                    long affectedV = document.getLong("AffectedV");
                                    long rectifiedV = document.getLong("RectifiedV");
                                    long balanceV = document.getLong("BalanceV");

                                    long affectedF = document.getLong("AffectedF");
                                    long rectifiedF = document.getLong("RectifiedF");
                                    long balanceF = document.getLong("BalanceF");

                                    long affectedT = document.getLong("AffectedT");
                                    long rectifiedT = document.getLong("RectifiedT");
                                    long balanceT = document.getLong("BalanceT");

                                    long affectedP = document.getLong("AffectedP");
                                    long rectifiedP = document.getLong("RectifiedP");
                                    long balanceP = document.getLong("BalanceP");

                                    long affectedH = document.getLong("AffectedH");
                                    long rectifiedH = document.getLong("RectifiedH");
                                    long balanceH = document.getLong("BalanceH");

                                    long affectedL = document.getLong("AffectedL");
                                    long rectifiedL = document.getLong("RectifiedL");
                                    long balanceL = document.getLong("BalanceL");

                                    long affectedTC = document.getLong("AffectedTC");
                                    long rectifiedTC = document.getLong("RectifiedTC");
                                    long balanceTC = document.getLong("BalanceTC");

                                    long affectedTP = document.getLong("AffectedTP");
                                    long rectifiedTP = document.getLong("RectifiedTP");
                                    long balanceTP = document.getLong("BalanceTP");

                                    long affectedTO = document.getLong("AffectedTO");
                                    long rectifiedTO = document.getLong("RectifiedTO");
                                    long balanceTO = document.getLong("BalanceTO");

                                    long affectedW = document.getLong("AffectedW");
                                    long rectifiedW = document.getLong("RectifiedW");
                                    long balanceW = document.getLong("BalanceW");

                                    long deptHFD = document.getLong("DeptHFD");
                                    long osHFO = document.getLong("OsHFO");
                                    long deptHND = document.getLong("DeptHND");
                                    long osHNO = document.getLong("OsHNO");

                                    long animalFatal = document.getLong("AnimalFatal");

                                    long affectedTN = document.getLong("AffectedTN");
                                    long rectifiedTN = document.getLong("RectifiedTN");
                                    long balanceTN = document.getLong("BalanceTN");

                                    long expenditure = document.getLong("Expenditure");

                                    // Set data to corresponding TextViews and EditTexts
                                    displayDataTextView1.setText(village);
                                    input_affectedV.setText(String.valueOf(affectedV));
                                    input_rectifiedV.setText(String.valueOf(rectifiedV));
                                    input_balanceV.setText(String.valueOf(balanceV));

                                    displayDataTextView2.setText(feeder);
                                    input_affectedF.setText(String.valueOf(affectedF));
                                    input_rectifiedF.setText(String.valueOf(rectifiedF));
                                    input_balanceF.setText(String.valueOf(balanceF));

                                    displayDataTextView3.setText(town);
                                    input_affectedT.setText(String.valueOf(affectedT));
                                    input_rectifiedT.setText(String.valueOf(rectifiedT));
                                    input_balanceT.setText(String.valueOf(balanceT));

                                    // Set additional fields as needed
                                    input_affectedP.setText(String.valueOf(affectedP));
                                    input_rectifiedP.setText(String.valueOf(rectifiedP));
                                    input_balanceP.setText(String.valueOf(balanceP));

                                    input_affectedH.setText(String.valueOf(affectedH));
                                    input_rectifiedH.setText(String.valueOf(rectifiedH));
                                    input_balanceH.setText(String.valueOf(balanceH));

                                    input_affectedL.setText(String.valueOf(affectedL));
                                    input_rectifiedL.setText(String.valueOf(rectifiedL));
                                    input_balanceL.setText(String.valueOf(balanceL));

                                    input_affectedTC.setText(String.valueOf(affectedTC));
                                    input_rectifiedTC.setText(String.valueOf(rectifiedTC));
                                    input_balanceTC.setText(String.valueOf(balanceTC));

                                    input_affectedTP.setText(String.valueOf(affectedTP));
                                    input_rectifiedTP.setText(String.valueOf(rectifiedTP));
                                    input_balanceTP.setText(String.valueOf(balanceTP));

                                    input_affectedTO.setText(String.valueOf(affectedTO));
                                    input_rectifiedTO.setText(String.valueOf(rectifiedTO));
                                    input_balanceTO.setText(String.valueOf(balanceTO));

                                    input_affectedW.setText(String.valueOf(affectedW));
                                    input_rectifiedW.setText(String.valueOf(rectifiedW));
                                    input_balanceW.setText(String.valueOf(balanceW));

                                    input_deptHFD.setText(String.valueOf(deptHFD));
                                    input_osHFO.setText(String.valueOf(osHFO));
                                    input_deptHNO.setText(String.valueOf(deptHND));
                                    input_osHNO.setText(String.valueOf(osHNO));

                                    input_animalFatal.setText(String.valueOf(animalFatal));

                                    input_affectedTN.setText(String.valueOf(affectedTN));
                                    input_rectifiedTN.setText(String.valueOf(rectifiedTN));
                                    input_balanceTN.setText(String.valueOf(balanceTN));

                                    input_expenditure.setText(String.valueOf(expenditure));

                                }catch (Exception e) {
                                    Log.e(TAG, "Error parsing document: " + document.getId(), e);
                                }
                                // You can add logic to handle more documents or UI elements as needed
                            }
                        } else {
                            Log.e(TAG, "Error fetching documents: ", task.getException());
                            displayDataTextView1.setText("Error fetching documents: " + task.getException());
                        }
                    }
                });
    }
}
