package com.kumar.iitadmin.students;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kumar.iitadmin.R;

import java.util.HashMap;

public class attendanceHome extends AppCompatActivity {
    private EditText studentID;
    private ImageView backButton;
    private Button presentButton, absentButton, checkButton;
    private FirebaseFirestore db;
    private long present, absent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_home);

        db = FirebaseFirestore.getInstance();

        studentID = findViewById(R.id.attendanceID);
        backButton = findViewById(R.id.backAttendance);
        presentButton = findViewById(R.id.presentButton);
        absentButton = findViewById(R.id.absentButton);
        checkButton = findViewById(R.id.checkAttendanceButton);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(attendanceHome.this, studentsMain.class));
            finish();
        });

        checkButton.setOnClickListener(v -> {
            checkButton.setBackgroundResource(R.drawable.blakish);
            checkButtonHandler();
        });

        presentButton.setOnClickListener(v -> {
            String stuID = studentID.getText().toString();
            studentID.setText("");

            db.collection("attendance")
                    .whereEqualTo("ID", stuID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                if (!task.getResult().isEmpty()){
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        present = (long) document.get("Present");
                                        updatePresent(stuID);
                                    }
                                } else {
                                    Toast.makeText(attendanceHome.this, "Student ID doesn't exist!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(attendanceHome.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        absentButton.setOnClickListener(v -> {
            String stuID = studentID.getText().toString();
            studentID.setText("");

            db.collection("attendance")
                    .whereEqualTo("ID", stuID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                if (!task.getResult().isEmpty()){
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        absent = (long) document.get("Absent");
                                        updateAbsent(stuID);
                                    }
                                } else {
                                    Toast.makeText(attendanceHome.this, "Student ID doesn't exist!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(attendanceHome.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

    }

    private void checkButtonHandler() {
        String stuID = studentID.getText().toString();

        db.collection("attendance")
                .whereEqualTo("ID", stuID)
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

                                    Intent intent = new Intent(attendanceHome.this, checkSingleAttendance.class);
                                    intent.putExtra("id", stuID);
                                    intent.putExtra("name", name);
                                    intent.putExtra("present", present);
                                    intent.putExtra("absent", absent);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(attendanceHome.this, "Student ID does not exist", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(attendanceHome.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void updatePresent(String stuID) {
        present++;

        HashMap<String, Object> newAttendance = new HashMap<>();
        newAttendance.put("Present", present);

        db.collection("attendance")
                .whereEqualTo("ID", stuID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("attendance")
                                    .document(documentID)
                                    .update(newAttendance)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(attendanceHome.this, "Present! \uD83D\uDE0A ",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(attendanceHome.this, "Failed!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(attendanceHome.this, "Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateAbsent(String stuID) {
        absent++;

        HashMap<String, Object> newAttendance = new HashMap<>();
        newAttendance.put("Absent", absent);

        db.collection("attendance")
                .whereEqualTo("ID", stuID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("attendance")
                                    .document(documentID)
                                    .update(newAttendance)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(attendanceHome.this, "Absent! \uD83D\uDE14",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(attendanceHome.this, "Failed!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(attendanceHome.this, "Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}