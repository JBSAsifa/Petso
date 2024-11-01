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

public class Vet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btncall1, btncall2, btncall3;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vet);

        btncall1= findViewById(R.id.btn1);
        btncall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Vet.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Vet.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
                else {
                    Intent iDial = new Intent(Intent.ACTION_CALL);
                    iDial.setData(Uri.parse("tel:02334466161"));
                    startActivity(iDial);

                }
            }
        });
        btncall2= findViewById(R.id.btn2);
        btncall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Vet.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Vet.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
                else {
                    Intent iDial = new Intent(Intent.ACTION_CALL);
                    iDial.setData(Uri.parse("tel:02334466270"));
                    startActivity(iDial);

                }
            }
        });
        btncall3= findViewById(R.id.btn3);
        btncall3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Vet.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Vet.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
                else {
                    Intent iDial = new Intent(Intent.ACTION_CALL);
                    iDial.setData(Uri.parse("tel:01712382840"));
                    startActivity(iDial);

                }
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vet), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}