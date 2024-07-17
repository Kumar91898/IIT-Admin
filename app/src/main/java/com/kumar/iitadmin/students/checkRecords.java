package com.kumar.iitadmin.students;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kumar.iitadmin.R;

public class checkRecords extends AppCompatActivity {
    private ImageView backButton, editButton;
    private TextView id, name, gender, email, contact, course;
    private String ID;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_records);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        ID = getIntent().getStringExtra("studentID");

        id = findViewById(R.id.stuID);
        name = findViewById(R.id.stuName);
        gender = findViewById(R.id.stuGender);
        email = findViewById(R.id.stuMail);
        contact = findViewById(R.id.stuContact);
        course = findViewById(R.id.stuCourse);
        backButton = findViewById(R.id.back);
        editButton = findViewById(R.id.editButtonCheck);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(checkRecords.this, studentsMain.class));
            finish();
        });

        if (user != null){
            db.collection("students")
                    .whereEqualTo("StudentID", ID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String getName = document.get("Name").toString();
                                    String getGender = document.get("Gender").toString();
                                    String getEmail = document.get("Email").toString();
                                    String getContact = document.get("Contact").toString();
                                    String getCourse = document.get("Course").toString();

                                    editButton.setOnClickListener(v -> {
                                        intent = new Intent(checkRecords.this, updateRecords.class);
                                        intent.putExtra("name", getName);
                                        intent.putExtra("email", getEmail);
                                        intent.putExtra("contact", getContact);
                                        intent.putExtra("course", getCourse);
                                        intent.putExtra("id", ID);
                                        startActivity(intent);
                                    });


                                    id.setText("Student ID: "+ID);
                                    name.setText("Name: " + getName);
                                    gender.setText("Gender: " + getGender);
                                    email.setText("Email: " + getEmail);
                                    contact.setText("Contact: " + getContact);
                                    course.setText("Course: " + getCourse);
                                }
                            } else {
                                Toast.makeText(checkRecords.this, "Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}