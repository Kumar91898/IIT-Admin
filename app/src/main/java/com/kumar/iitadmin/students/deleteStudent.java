package com.kumar.iitadmin.students;

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
import com.google.firebase.firestore.QuerySnapshot;
import com.kumar.iitadmin.R;

public class deleteStudent extends AppCompatActivity {

    private FirebaseFirestore db;
    private ImageView back;
    private EditText ID;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        db = FirebaseFirestore.getInstance();
        back = findViewById(R.id.backButtonDelete);
        ID = findViewById(R.id.deleteStudentID);
        delete = findViewById(R.id.delStudentButton);

        back.setOnClickListener(v -> {
            startActivity(new Intent(deleteStudent.this, studentsMain.class));
            finish();
        });

        delete.setOnClickListener(v -> {
            delete.setBackgroundResource(R.drawable.blakish);
            deleteRecord();
        });
    }

    private void deleteRecord() {
        String getID = ID.getText().toString();
        ID.setText("");

        db.collection("students")
                .whereEqualTo("StudentID", getID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("students")
                                    .document(documentID)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(deleteStudent.this, "Deleted....!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(deleteStudent.this, "Deleting Failed!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(deleteStudent.this, "Searching....!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        db.collection("attendance")
                .whereEqualTo("ID", getID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("attendance")
                                    .document(documentID)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(deleteStudent.this, "Deleted Successfully!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(deleteStudent.this, "Deleting Failed!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(deleteStudent.this, "Student doesn't exists!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}