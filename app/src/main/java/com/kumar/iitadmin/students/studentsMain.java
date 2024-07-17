package com.kumar.iitadmin.students;

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
import com.kumar.iitadmin.teacher.teachersMain;

public class studentsMain extends AppCompatActivity {

    private ImageView backButton;
    private Button add, update, attendance, remove, teacherManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_main);

        backButton = findViewById(R.id.backButton);
        add = findViewById(R.id.addStudent);
        update = findViewById(R.id.updateStudent);
        attendance = findViewById(R.id.attendanceStudent);
        remove = findViewById(R.id.deleteStudent);
        teacherManagement = findViewById(R.id.tManagement);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(studentsMain.this, Home.class));
            finish();
        });

        teacherManagement.setOnClickListener(v -> {
            teacherManagement.setBackgroundResource(R.color.black);
            startActivity(new Intent(studentsMain.this, teachersMain.class));
            finish();
        });

        add.setOnClickListener(v -> {
            add.setBackgroundResource(R.drawable.blakish2);
            startActivity(new Intent(studentsMain.this, addStudent.class));
            finish();
        });

        update.setOnClickListener(v -> {
            update.setBackgroundResource(R.drawable.blakish2);
            startActivity(new Intent(studentsMain.this, checkRecords1.class));
            finish();
        });

        attendance.setOnClickListener(v -> {
            attendance.setBackgroundResource(R.drawable.blakish2);
            startActivity(new Intent(studentsMain.this, attendanceHome.class));
            finish();
        });

        remove.setOnClickListener(v -> {
            remove.setBackgroundResource(R.drawable.blakish2);
            startActivity(new Intent(studentsMain.this, deleteStudent.class));
            finish();
        });
    }
}