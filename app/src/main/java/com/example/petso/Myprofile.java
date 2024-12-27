package com.example.petso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Myprofile extends AppCompatActivity {
    TextView apptext;
    Button Btnout;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_myprofile); // Call this before initializing views

        // Initialize Views
        apptext = findViewById(R.id.text3);
        Btnout = findViewById(R.id.out);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            // If user is not logged in, redirect to LoginActivity
            Intent intent = new Intent(Myprofile.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If user is logged in, display their email
            apptext.setText("Welcome, " + user.getEmail());
        }

        // Logout Button Click Listener
        Btnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out the user
                FirebaseAuth.getInstance().signOut();
                // Redirect to LoginActivity
                Intent intent = new Intent(Myprofile.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Handle Edge-to-Edge Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
