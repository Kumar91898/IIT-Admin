package com.kumar.iitadmin.teacher;

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
import com.kumar.iitadmin.students.studentsMain;
import com.kumar.iitadmin.students.updateRecords;

import java.util.HashMap;

public class editRecord extends AppCompatActivity {

    private EditText idField, nameField, emailField, contactField, subjectField;
    private ImageView backButton;
    private Button updateButton;
    private FirebaseFirestore db;
    private String getID, getName, getEmail, getContact, getSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        db = FirebaseFirestore.getInstance();

        idField = findViewById(R.id.idFieldEdit);
        idField.setInputType(InputType.TYPE_NULL);
        nameField = findViewById(R.id.nameFieldEdit);
        emailField = findViewById(R.id.emailFieldEdit);
        contactField = findViewById(R.id.contactFieldEdit);
        subjectField = findViewById(R.id.courseFieldEdit);
        backButton = findViewById(R.id.backButtonEdit);
        updateButton = findViewById(R.id.updateButtonEdit);

        getID = getIntent().getStringExtra("id");
        getName = getIntent().getStringExtra("name");
        getEmail = getIntent().getStringExtra("email");
        getContact = getIntent().getStringExtra("contact");
        getSubject = getIntent().getStringExtra("subject");

        idField.setText(getID);
        nameField.setText(getName);
        emailField.setText(getEmail);
        contactField.setText(getContact);
        subjectField.setText(getSubject);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(editRecord.this, teachersMain.class));
        });

        updateButton.setOnClickListener(v -> {
            updateButton.setBackgroundResource(R.drawable.blakish2);
            handleUpdateButton();
        });
    }

    private void handleUpdateButton() {
        String teachID = idField.getText().toString();
        String teachName = nameField.getText().toString();
        String teachEmail = emailField.getText().toString();
        String teachContact = contactField.getText().toString();
        String teachSubject = subjectField.getText().toString();

        HashMap<String, Object> newRecord = new HashMap<>();
        newRecord.put("Name", teachName);
        newRecord.put("Email", teachEmail);
        newRecord.put("Contact", teachContact);
        newRecord.put("Subject", teachSubject);

        db.collection("teachers")
                .whereEqualTo("TeacherID", teachID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("teachers")
                                    .document(documentID)
                                    .update(newRecord)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(editRecord.this, "Updated Successfully!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(editRecord.this, "Failed!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(editRecord.this, "Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}