package com.kumar.iitadmin.students;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kumar.iitadmin.R;

public class checkSingleAttendance extends AppCompatActivity {
    private TextView id, name, present, absent, total, percentage;
    private ImageView backButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_single_attendance);

        db = FirebaseFirestore.getInstance();

        id = findViewById(R.id.checkAttendanceID);
        name = findViewById(R.id.checkAttendanceName);
        present = findViewById(R.id.checkAttendancePresent);
        absent = findViewById(R.id.checkAttendanceAbsent);
        total = findViewById(R.id.checkAttendanceTotal);
        percentage = findViewById(R.id.checkAttendancePercent);
        backButton = findViewById(R.id.checkAttendanceBack);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(checkSingleAttendance.this, attendanceHome.class));
            finish();
        });

        String getID = getIntent().getStringExtra("id");
        String getName = getIntent().getStringExtra("name");
        String getPresent = getIntent().getStringExtra("present");
        String getAbsent = getIntent().getStringExtra("absent");
        assert getPresent != null;
        assert getAbsent != null;
        int getTotal = Integer.parseInt(getPresent)+Integer.parseInt(getAbsent);
        float getPercent = (float) Integer.parseInt(getPresent) /getTotal*100;

        String formattedPercent = String.format("%.2f", getPercent);

        id.setText("Student ID: "+getID);
        name.setText("Name: "+getName);
        present.setText("Present: "+getPresent);
        absent.setText("Absent: "+getAbsent);
        total.setText("Total classes: "+getTotal);
        percentage.setText("Percentage: "+formattedPercent+"%");
    }
}