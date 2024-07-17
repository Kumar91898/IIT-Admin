package com.kumar.iitadmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private ImageView loginButton;
    private EditText passField, emailField;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.emailField);
        passField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        login();
    }

    public void login(){
        loginButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email = emailField.getText().toString();
            String password = passField.getText().toString();

            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Login.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Login.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(Login.this, "Logged In!",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, Home.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Login.this, "Email or Password incorrect!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }
}