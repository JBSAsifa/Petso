package com.example.petso;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Hospital extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btncall, btnEmail;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hospital);
        btncall = findViewById(R.id.btn4);
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Hospital.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Hospital.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
                else {
                    Intent iDial = new Intent(Intent.ACTION_CALL);
                    iDial.setData(Uri.parse("tel:01712382840"));
                    startActivity(iDial);

                }
            }
        });
        btnEmail = findViewById(R.id.btn5);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iEmail = new Intent(Intent.ACTION_SEND);
                iEmail.setType("message/rfc822");
                iEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"imferdusee@cvasu.ac.bd.com","joinabbintesirajasifa@gmail.com"});
                iEmail.putExtra(Intent.EXTRA_SUBJECT,"For Appointment");
                iEmail.putExtra(Intent.EXTRA_TEXT,"Assalamualaikum i want to make an Appointment");
                startActivity(Intent.createChooser(iEmail,"Send by"));
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hospital), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}