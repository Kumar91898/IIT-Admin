package com.kumar.iitadmin.students;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.kumar.iitadmin.R;

import java.util.HashMap;

public class updateRecords extends AppCompatActivity {
    private EditText idField, nameField, emailField, contactField, courseField;
    private ImageView backButton;
    private Button updateButton;
    private FirebaseFirestore db;
    private String getID, getName, getEmail, getContact, getCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_records);

        db = FirebaseFirestore.getInstance();

        idField = findViewById(R.id.idFieldUpdate);
        idField.setInputType(InputType.TYPE_NULL);
        nameField = findViewById(R.id.nameFieldUpdate);
        emailField = findViewById(R.id.emailFieldUpdate);
        contactField = findViewById(R.id.contactFieldUpdate);
        courseField = findViewById(R.id.courseFieldUpdate);
        backButton = findViewById(R.id.backButtonUpdate);
        updateButton = findViewById(R.id.updateButtonUpdate);

        getID = getIntent().getStringExtra("id");
        getName = getIntent().getStringExtra("name");
        getEmail = getIntent().getStringExtra("email");
        getContact = getIntent().getStringExtra("contact");
        getCourse = getIntent().getStringExtra("course");

        idField.setText(getID);
        nameField.setText(getName);
        emailField.setText(getEmail);
        contactField.setText(getContact);
        courseField.setText(getCourse);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(updateRecords.this, studentsMain.class));
        });

        updateButton.setOnClickListener(v -> {
            updateButton.setBackgroundResource(R.drawable.blakish2);
            handleUpdateButton();
        });
    }

    private void handleUpdateButton() {
        String stuID = idField.getText().toString();
        String stuName = nameField.getText().toString();
        String stuEmail = emailField.getText().toString();
        String stuContact = contactField.getText().toString();
        String stuCourse = courseField.getText().toString();

        HashMap<String, Object> newRecord = new HashMap<>();
        newRecord.put("Name", stuName);
        newRecord.put("Email", stuEmail);
        newRecord.put("Contact", stuContact);
        newRecord.put("Course", stuCourse);

        db.collection("students")
                .whereEqualTo("StudentID", stuID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("students")
                                    .document(documentID)
                                    .update(newRecord)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(updateRecords.this, "Updated Successfully!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(updateRecords.this, "Failed!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(updateRecords.this, "Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}