package com.kumar.iitadmin;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
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
import com.kumar.iitadmin.students.studentsMain;
import com.kumar.iitadmin.teacher.teachersMain;

import org.w3c.dom.Text;

public class Home extends AppCompatActivity {

    private Button studentButton, teacherButton;
    private ImageView logoutButton;
    private TextView userDetails, aboutApp;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get the OnBackPressedDispatcher
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();

        // Add a callback to the OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button press event here
                finishAffinity(); // Call finish() to close the current activity
            }
        });

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userDetails = findViewById(R.id.userDetails);
        logoutButton = findViewById(R.id.logoutButton);
        studentButton = findViewById(R.id.studentButton);
        teacherButton = findViewById(R.id.teacherButton);
        aboutApp = findViewById(R.id.aboutApp);

        if (user != null){
            String authEmail = user.getEmail();

            db.collection("user")
                    .whereEqualTo("Email", authEmail)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String getName = document.get("Name").toString();
                                    userDetails.setText("Welcome,\n" + getName);
                                }
                            } else {
                                userDetails.setText("Failed");
                            }
                        }
                    });
        }

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Home.this, Login.class);
            startActivity(intent);
        });

        studentButton.setOnClickListener(v -> {
            studentButton.setBackgroundResource(R.drawable.blakish2);
            startActivity(new Intent(Home.this, studentsMain.class));
            finish();
        });

        teacherButton.setOnClickListener(v -> {
            teacherButton.setBackgroundResource(R.drawable.blakish2);
            startActivity(new Intent(Home.this, teachersMain.class));
            finish();
        });

        aboutApp.setOnClickListener(v -> {
            aboutApp.setTextColor(getColor(R.color.green));
            startActivity(new Intent(Home.this, aboutAPP.class));
            finish();
        });
    }
}