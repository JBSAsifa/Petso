package com.example.petso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    ImageView iconprofile, icongram, iconshop, iconadopsell, iconstay, iconcare, iconwpet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        icongram = findViewById(R.id.Pgram);
        iconshop = findViewById(R.id.shop);
        iconadopsell = findViewById(R.id.AdopSell);
        iconstay = findViewById(R.id.stay);
        iconcare = findViewById(R.id.care);
        iconwpet = findViewById(R.id.whichp);
        iconprofile = findViewById(R.id.Profile);



        iconprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start SecondActivity
                Intent intent = new Intent(MainActivity.this, Myprofile.class);
                startActivity(intent);
            }
        });

        icongram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start SecondActivity
                Intent intent = new Intent(MainActivity.this, petsogram.class);
                startActivity(intent);
            }
        });
        iconshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start SecondActivity
                Intent intent = new Intent(MainActivity.this, Shoppiee.class);
                startActivity(intent);
            }
        });
        iconshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start SecondActivity
                Intent intent = new Intent(MainActivity.this, Shoppiee.class);
                startActivity(intent);
            }
        });
        iconadopsell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start SecondActivity
                Intent intent = new Intent(MainActivity.this, Petgo.class);
                startActivity(intent);
            }
        });



        iconstay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start SecondActivity
                Intent intent = new Intent(MainActivity.this, PetStay.class);
                startActivity(intent);
            }
        });

        iconcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start SecondActivity
                Intent intent = new Intent(MainActivity.this, PetCare.class);
                startActivity(intent);
            }
        });
        iconwpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start SecondActivity
                Intent intent = new Intent(MainActivity.this, WhichPet.class);
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}