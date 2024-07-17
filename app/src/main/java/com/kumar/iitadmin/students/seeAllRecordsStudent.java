package com.kumar.iitadmin.students;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.kumar.iitadmin.R;

import java.util.ArrayList;

public class seeAllRecordsStudent extends AppCompatActivity {

    private ImageView back;
    RecyclerView recyclerView;
    ArrayList<studentHelper> studentHelpers;
    com.kumar.iitadmin.students.studentAdapter studentAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_records_student);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data.....");
        progressDialog.show();

        back = findViewById(R.id.allRecordsBack);
        recyclerView = findViewById(R.id.allRecords);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        studentHelpers = new ArrayList<studentHelper>();
        studentAdapter = new studentAdapter(seeAllRecordsStudent.this, studentHelpers);

        recyclerView.setAdapter(studentAdapter);

        back.setOnClickListener(v -> {
            startActivity(new Intent(seeAllRecordsStudent.this, studentsMain.class));
            finish();
        });

        eventChanger();
    }

    private void eventChanger() {
        db.collection("students").orderBy("Name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){

                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }

                            Toast.makeText(seeAllRecordsStudent.this, "Failed to get data", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                studentHelpers.add(dc.getDocument().toObject(studentHelper.class));
                            }

                            studentAdapter.notifyDataSetChanged();

                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }
}