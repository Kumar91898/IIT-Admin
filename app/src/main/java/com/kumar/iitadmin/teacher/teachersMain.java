package com.kumar.iitadmin.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kumar.iitadmin.Home;
import com.kumar.iitadmin.R;
import com.kumar.iitadmin.students.studentsMain;

public class teachersMain extends AppCompatActivity {

    private ImageView backButton;
    private Button add, update, attendance, remove, studentManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_main);

        backButton = findViewById(R.id.backingButton);
        add = findViewById(R.id.addTeacher);
        update = findViewById(R.id.checkTeacher);
        attendance = findViewById(R.id.attendanceTeacher);
        remove = findViewById(R.id.deleteTeacher);
        studentManagement = findViewById(R.id.sManagement);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(teachersMain.this, Home.class));
            finish();
        });

        studentManagement.setOnClickListener(v -> {
            studentManagement.setBackgroundResource(R.color.black);
            startActivity(new Intent(teachersMain.this, studentsMain.class));
            finish();
        });

        add.setOnClickListener(v -> {
            add.setBackgroundResource(R.drawable.blakish2);
            startActivity(new Intent(teachersMain.this, addTeacher.class));
            finish();
        });

        update.setOnClickListener(v -> {
            update.setBackgroundResource(R.drawable.blakish2);
            startActivity(new Intent(teachersMain.this, checkSpecificTeacher.class));
            finish();
        });

        attendance.setOnClickListener(v -> {
            attendance.setBackgroundResource(R.drawable.blakish2);
            startActivity(new Intent(teachersMain.this, teacherAttendance.class));
            finish();
        });

        remove.setOnClickListener(v -> {
            remove.setBackgroundResource(R.drawable.blakish2);
            startActivity(new Intent(teachersMain.this, deleteTeacher.class));
            finish();
        });
    }
}