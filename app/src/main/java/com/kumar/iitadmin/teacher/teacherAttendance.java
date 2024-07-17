package com.kumar.iitadmin.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kumar.iitadmin.R;
import com.kumar.iitadmin.students.attendanceHome;
import com.kumar.iitadmin.students.checkSingleAttendance;
import com.kumar.iitadmin.students.studentsMain;

import java.util.HashMap;

public class teacherAttendance extends AppCompatActivity {

    private EditText teacherID;
    private ImageView backButton;
    private Button presentButton, absentButton, checkButton, checkAllButton;
    private FirebaseFirestore db;
    private String present, absent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance);

        db = FirebaseFirestore.getInstance();

        teacherID = findViewById(R.id.attendanceIDTeacher);
        backButton = findViewById(R.id.backAttendanceTeacher);
        presentButton = findViewById(R.id.presentButtonTeacher);
        absentButton = findViewById(R.id.absentButtonTeacher);
        checkButton = findViewById(R.id.checkAttendanceTeacher);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(teacherAttendance.this, teachersMain.class));
            finish();
        });

        checkButton.setOnClickListener(v -> {
            checkButton.setBackgroundResource(R.drawable.blakish);
            checkButtonHandler();
        });

        presentButton.setOnClickListener(v -> {
            String teachID = teacherID.getText().toString();
            teacherID.setText("");

            db.collection("Teacher_attendance")
                    .whereEqualTo("ID", teachID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                if (!task.getResult().isEmpty()){
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        present = document.get("Present").toString();
                                        updatePresent(teachID);
                                    }
                                } else {
                                    Toast.makeText(teacherAttendance.this, "Teacher's ID doesn't exists!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(teacherAttendance.this, "Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        absentButton.setOnClickListener(v -> {
            String teachID = teacherID.getText().toString();
            teacherID.setText("");

            db.collection("Teacher_attendance")
                    .whereEqualTo("ID", teachID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                if (!task.getResult().isEmpty()){
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        absent = document.get("Absent").toString();
                                        updateAbsent(teachID);
                                    }
                                } else {
                                    Toast.makeText(teacherAttendance.this, "Teacher's ID doesn't exists!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(teacherAttendance.this, "Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }

    private void updatePresent(String teachID) {
        int intPresent = Integer.parseInt(present);
        intPresent++;

        HashMap<String, Object> newAttendance = new HashMap<>();
        newAttendance.put("Present", String.valueOf(intPresent));

        db.collection("Teacher_attendance")
                .whereEqualTo("ID", teachID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("Teacher_attendance")
                                    .document(documentID)
                                    .update(newAttendance)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(teacherAttendance.this, "Present! \uD83D\uDE0A ",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(teacherAttendance.this, "Failed!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(teacherAttendance.this, "Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateAbsent(String teachID) {
        int intAbsent = Integer.parseInt(absent);
        intAbsent++;

        HashMap<String, Object> newAttendance = new HashMap<>();
        newAttendance.put("Absent", String.valueOf(intAbsent));

        db.collection("Teacher_attendance")
                .whereEqualTo("ID", teachID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("Teacher_attendance")
                                    .document(documentID)
                                    .update(newAttendance)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(teacherAttendance.this, "Absent! \uD83D\uDE14",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(teacherAttendance.this, "Failed!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(teacherAttendance.this, "Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkButtonHandler() {
        String teachID = teacherID.getText().toString();

        db.collection("Teacher_attendance")
                .whereEqualTo("ID", teachID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            if (!task.getResult().isEmpty()) { // Check if the query result is not empty
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String present = document.get("Present").toString();
                                    String absent = document.get("Absent").toString();
                                    String name = document.get("Name").toString();

                                    Intent intent = new Intent(teacherAttendance.this, checkSingleTeacherAttendance.class);
                                    intent.putExtra("id", teachID);
                                    intent.putExtra("name", name);
                                    intent.putExtra("present", present);
                                    intent.putExtra("absent", absent);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(teacherAttendance.this, "Teacher's ID does not exist", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(teacherAttendance.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}