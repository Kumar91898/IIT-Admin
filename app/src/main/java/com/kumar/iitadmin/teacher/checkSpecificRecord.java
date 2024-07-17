package com.kumar.iitadmin.teacher;

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
import com.kumar.iitadmin.students.checkRecords;
import com.kumar.iitadmin.students.studentsMain;
import com.kumar.iitadmin.students.updateRecords;

public class checkSpecificRecord extends AppCompatActivity {

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
        setContentView(R.layout.activity_check_specific_record);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        ID = getIntent().getStringExtra("teacherID");

        id = findViewById(R.id.teachID);
        name = findViewById(R.id.teachName);
        gender = findViewById(R.id.teachGender);
        email = findViewById(R.id.teachMail);
        contact = findViewById(R.id.teachContact);
        course = findViewById(R.id.teachSubject);
        backButton = findViewById(R.id.backSpecificTeacher);
        editButton = findViewById(R.id.editButtonTeacher);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(checkSpecificRecord.this, teachersMain.class));
            finish();
        });

        if (user != null) {
            db.collection("teachers")
                    .whereEqualTo("TeacherID", ID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String getName = document.get("Name").toString();
                                    String getGender = document.get("Gender").toString();
                                    String getEmail = document.get("Email").toString();
                                    String getContact = document.get("Contact").toString();
                                    String getCourse = document.get("Subject").toString();

                                    editButton.setOnClickListener(v -> {
                                        intent = new Intent(checkSpecificRecord.this, editRecord.class);
                                        intent.putExtra("name", getName);
                                        intent.putExtra("email", getEmail);
                                        intent.putExtra("contact", getContact);
                                        intent.putExtra("subject", getCourse);
                                        intent.putExtra("id", ID);
                                        startActivity(intent);
                                    });


                                    id.setText("Teacher ID: " + ID);
                                    name.setText("Name: " + getName);
                                    gender.setText("Gender: " + getGender);
                                    email.setText("Email: " + getEmail);
                                    contact.setText("Contact: " + getContact);
                                    course.setText("Subject: " + getCourse);
                                }
                            } else {
                                Toast.makeText(checkSpecificRecord.this, "Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}