package com.kumar.iitadmin.teacher;

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
import com.kumar.iitadmin.students.addStudent;
import com.kumar.iitadmin.students.studentsMain;

import java.util.HashMap;

public class addTeacher extends AppCompatActivity {

    final Context context = this;
    private EditText nameField, emailField, studentIDField, contactField;
    private Spinner subjectField;
    private ImageView backButton;
    private RadioGroup radioSexGroup;
    private RadioButton radioButton;
    private Button addButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        db = FirebaseFirestore.getInstance();
        nameField = findViewById(R.id.nameFieldTeacher);
        radioSexGroup = findViewById(R.id.teachersexGroup);
        emailField = findViewById(R.id.emailFieldTeacher);
        studentIDField = findViewById(R.id.idFieldTeacher);
        contactField = findViewById(R.id.contactFieldTeacher);
        subjectField = findViewById(R.id.subjectSpinner);
        backButton = findViewById(R.id.teacherBack);
        addButton = findViewById(R.id.addButtonTeacher);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(addTeacher.this, teachersMain.class));
            finish();
        });

        addButton.setOnClickListener(v -> {
            addButton.setBackgroundResource(R.drawable.blakish2);
            int selectedID = radioSexGroup.getCheckedRadioButtonId();
            radioButton = findViewById(selectedID);

            String teacherName = nameField.getText().toString();
            String teacherGender = radioButton.getText().toString();
            String teacherEmail = emailField.getText().toString();
            String teacherID = studentIDField.getText().toString();
            String teacherContact = contactField.getText().toString();
            String teacherCourse = subjectField.getSelectedItem().toString();

            if (teacherCourse.equals("Select Subject")){
                Toast.makeText(addTeacher.this, "Select any subject", Toast.LENGTH_SHORT).show();
            } else {
                HashMap<String, Object> teacher = new HashMap<>();
                teacher.put("Name", teacherName);
                teacher.put("Gender", teacherGender);
                teacher.put("Email", teacherEmail);
                teacher.put("TeacherID", teacherID);
                teacher.put("Contact", teacherContact);
                teacher.put("Subject", teacherCourse);

                HashMap<String , Object> teacherAttendance = new HashMap<>();
                teacherAttendance.put("ID", teacherID);
                teacherAttendance.put("Name", teacherName);
                teacherAttendance.put("Present", 0);
                teacherAttendance.put("Absent", 0);

                db.collection("teachers")
                        .add(teacher);

                db.collection("Teacher_attendance")
                        .add(teacherAttendance);

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
                        startActivity(new Intent(addTeacher.this, teachersMain.class));
                        finish();
                    }
                })
                .setNegativeButton("Add new record?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(addTeacher.this, addTeacher.class));
                        finish();
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogueBuilder.create();
        alertDialog.show();
    }
}