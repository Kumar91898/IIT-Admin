package com.kumar.iitadmin.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kumar.iitadmin.R;
import com.kumar.iitadmin.students.checkRecords;
import com.kumar.iitadmin.students.checkRecords1;
import com.kumar.iitadmin.students.studentsMain;

public class checkSpecificTeacher extends AppCompatActivity {

    private ImageView backButton, searchButton;
    private Button allRecordsButton;
    private EditText searchID;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_specific_teacher);

        db = FirebaseFirestore.getInstance();

        backButton = findViewById(R.id.backButtonRecordsTeacher);
        searchButton = findViewById(R.id.searchButtonTeacher);
        allRecordsButton = findViewById(R.id.allButtonTeacher);
        searchID = findViewById(R.id.checkTeacher);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(checkSpecificTeacher.this, teachersMain.class));
            finish();
        });

        searchButton.setOnClickListener(v -> {
            String ID = searchID.getText().toString();

            if (TextUtils.isEmpty(ID)){
                Toast.makeText(checkSpecificTeacher.this, "Enter ID!",
                        Toast.LENGTH_SHORT).show();
            } else {
                db.collection("teachers")
                        .whereEqualTo("TeacherID", ID)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                // Student exists, proceed to next activity
                                Intent intent = new Intent(checkSpecificTeacher.this, checkSpecificRecord.class);
                                intent.putExtra("teacherID", ID);
                                startActivity(intent);
                            } else {
                                // Student doesn't exist
                                Toast.makeText(checkSpecificTeacher.this, "Teacher doesn't exist!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            // Error handling for database query failure
                            Toast.makeText(checkSpecificTeacher.this, "Error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}