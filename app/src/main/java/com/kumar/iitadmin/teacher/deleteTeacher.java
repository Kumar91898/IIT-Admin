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
import com.google.firebase.firestore.QuerySnapshot;
import com.kumar.iitadmin.R;
import com.kumar.iitadmin.students.deleteStudent;
import com.kumar.iitadmin.students.studentsMain;

public class deleteTeacher extends AppCompatActivity {

    private FirebaseFirestore db;
    private ImageView back;
    private EditText ID;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_teacher);

        db = FirebaseFirestore.getInstance();
        back = findViewById(R.id.backButtonDeleteTeacher);
        ID = findViewById(R.id.deleteTeacherID);
        delete = findViewById(R.id.delTeacherButton);

        back.setOnClickListener(v -> {
            startActivity(new Intent(deleteTeacher.this, studentsMain.class));
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

        db.collection("teachers")
                .whereEqualTo("TeacherID", getID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("teachers")
                                    .document(documentID)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(deleteTeacher.this, "Deleting....!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(deleteTeacher.this, "Deleting Failed!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(deleteTeacher.this, "Searching....!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        db.collection("Teacher_attendance")
                .whereEqualTo("ID", getID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            db.collection("Teacher_attendance")
                                    .document(documentID)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(deleteTeacher.this, "Deleted Successfully!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(deleteTeacher.this, "Deleting Failed!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(deleteTeacher.this, "Teacher doesn't exits!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}