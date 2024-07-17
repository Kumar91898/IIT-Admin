package com.kumar.iitadmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class aboutAPP extends AppCompatActivity {

    private ImageView backButton, instagramButton, whatsappButton, gitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        backButton = findViewById(R.id.backAbout);
        instagramButton = findViewById(R.id.instagram);
        whatsappButton = findViewById(R.id.whatsapp);
        gitButton = findViewById(R.id.github);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(aboutAPP.this, Home.class));
            finish();
        });

        instagramButton.setOnClickListener(v -> {
            // Define the WhatsApp contact URL
            String instagramURL = "https://www.instagram.com/iam_kumar.91898/";

            try {
                // Create an Intent to open the URL in a web browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramURL));

                // Set the package name of the web browser explicitly to avoid issues
                intent.setPackage("com.android.chrome"); // Change this to the package name of the web browser you want to use

                // Open the link in the web browser
                startActivity(intent);
            } catch (Exception e) {
                // Display a message or handle the exception
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        whatsappButton.setOnClickListener(v -> {
            // Define the WhatsApp contact URL
            String whatsappURL = "https://wa.me/+923463962248";

            try {
                // Create an Intent to open the URL in a web browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(whatsappURL));

                // Set the package name of the web browser explicitly to avoid issues
                intent.setPackage("com.android.chrome"); // Change this to the package name of the web browser you want to use

                // Open the link in the web browser
                startActivity(intent);
            } catch (Exception e) {
                // Display a message or handle the exception
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        gitButton.setOnClickListener(v -> {
            // Define the WhatsApp contact URL
            String url = "https://github.com/Kumar91898";

            try {
                // Create an Intent to open the URL in a web browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Set the package name of the web browser explicitly to avoid issues
                intent.setPackage("com.android.chrome"); // Change this to the package name of the web browser you want to use

                // Open the link in the web browser
                startActivity(intent);
            } catch (Exception e) {
                // Display a message or handle the exception
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}