package com.kumar.iitadmin.teacher;

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
import com.kumar.iitadmin.students.attendanceHome;
import com.kumar.iitadmin.students.checkSingleAttendance;

public class checkSingleTeacherAttendance extends AppCompatActivity {

    private TextView id, name, present, absent, total, percentage;
    private ImageView backButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_single_teacher_attendance);

        db = FirebaseFirestore.getInstance();

        id = findViewById(R.id.checkAttendanceIDTeacher);
        name = findViewById(R.id.checkAttendanceNameTeacher);
        present = findViewById(R.id.checkAttendancePresentTeacher);
        absent = findViewById(R.id.checkAttendanceAbsentTeacher);
        total = findViewById(R.id.checkAttendanceTotalTeacher);
        percentage = findViewById(R.id.checkAttendancePercentTeacher);
        backButton = findViewById(R.id.checkAttendanceBackTeacher);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(checkSingleTeacherAttendance.this, teacherAttendance.class));
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

        id.setText("Teacher's ID: "+getID);
        name.setText("Name: "+getName);
        present.setText("Present: "+getPresent);
        absent.setText("Absent: "+getAbsent);
        total.setText("Total classes: "+getTotal);
        percentage.setText("Percentage: "+formattedPercent+"%");
    }
}