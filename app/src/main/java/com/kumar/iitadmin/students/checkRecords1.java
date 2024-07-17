package com.kumar.iitadmin.students;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kumar.iitadmin.R;

public class checkRecords1 extends AppCompatActivity {

    private ImageView backButton, searchButton;
    private Button allRecordsButton;
    private EditText searchID;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_records1);

        db = FirebaseFirestore.getInstance();

        backButton = findViewById(R.id.backButtonRecords);
        searchButton = findViewById(R.id.searchButton);
        allRecordsButton = findViewById(R.id.allButton);
        searchID = findViewById(R.id.checkStudent);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(checkRecords1.this, studentsMain.class));
            finish();
        });

        searchButton.setOnClickListener(v -> {
            String ID = searchID.getText().toString();

            if (TextUtils.isEmpty(ID)){
                Toast.makeText(checkRecords1.this, "Enter ID!",
                        Toast.LENGTH_SHORT).show();
            } else {
                db.collection("students")
                        .whereEqualTo("StudentID", ID)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                // Student exists, proceed to next activity
                                Intent intent = new Intent(checkRecords1.this, checkRecords.class);
                                intent.putExtra("studentID", ID);
                                startActivity(intent);
                            } else {
                                // Student doesn't exist
                                Toast.makeText(checkRecords1.this, "Student doesn't exist!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            // Error handling for database query failure
                            Toast.makeText(checkRecords1.this, "Error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        });
            }
        });


        allRecordsButton.setOnClickListener(v -> {
            allRecordsButton.setBackgroundResource(R.drawable.blakish);
            startActivity(new Intent(checkRecords1.this, seeAllRecordsStudent.class));
            finish();
        });
    }
}