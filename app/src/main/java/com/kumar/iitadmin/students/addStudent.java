package com.kumar.iitadmin.students;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kumar.iitadmin.R;

import java.util.HashMap;
import java.util.Objects;

public class addStudent extends AppCompatActivity {
    final Context context = this;
    private EditText nameField, emailField, studentIDField, contactField;
    private Spinner courseField;
    private ImageView backButton;
    private RadioGroup radioSexGroup;
    private RadioButton radioButton;
    private Button addButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        db = FirebaseFirestore.getInstance();
        nameField = findViewById(R.id.nameField);
        radioSexGroup = findViewById(R.id.stusexGroup);
        emailField = findViewById(R.id.emailField);
        studentIDField = findViewById(R.id.idField);
        contactField = findViewById(R.id.contactField);
        courseField = findViewById(R.id.courseSpinner);
        backButton = findViewById(R.id.backButtonAdd);
        addButton = findViewById(R.id.addButton);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(addStudent.this, studentsMain.class));
            finish();
        });

        addButton.setOnClickListener(v -> {
            addButton.setBackgroundResource(R.drawable.blakish2);
            int selectedID = radioSexGroup.getCheckedRadioButtonId();
            radioButton = findViewById(selectedID);

            String studentName = nameField.getText().toString();
            String studentGender = radioButton.getText().toString();
            String studentEmail = emailField.getText().toString();
            String studentID = studentIDField.getText().toString();
            String studentContact = contactField.getText().toString();
            String studentCourse = courseField.getSelectedItem().toString();

            if (studentCourse.equals("Select Course")){
                Toast.makeText(addStudent.this, "Select any course!", Toast.LENGTH_SHORT).show();
            } else {
                HashMap<String, Object> student = new HashMap<>();
                student.put("Name", studentName);
                student.put("Gender", studentGender);
                student.put("Email", studentEmail);
                student.put("StudentID", studentID);
                student.put("Contact", studentContact);
                student.put("Course", studentCourse);

                HashMap<String, Object> stuAttendance = new HashMap<>();
                stuAttendance.put("ID", studentID);
                stuAttendance.put("Name", studentName);
                stuAttendance.put("Present", 0);
                stuAttendance.put("Absent", 0);


                db.collection("students")
                        .add(student);

                db.collection("attendance")
                        .add(stuAttendance);

                alertDialogue();
            }
        });

    }

    public void alertDialogue(){
        AlertDialog.Builder alertDialogueBuilder = new AlertDialog.Builder(context);

        alertDialogueBuilder.setTitle("Alert!");

        alertDialogueBuilder
                .setMessage("Student Added Successfully!")
                .setCancelable(false)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(addStudent.this, studentsMain.class));
                        finish();
                    }
                })
                .setNegativeButton("Add new record?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(addStudent.this, addStudent.class));
                        finish();
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogueBuilder.create();
        alertDialog.show();
    }
}