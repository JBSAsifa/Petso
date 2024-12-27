package com.example.petso;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BookingActivity extends AppCompatActivity {

    private TextView hotelNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        hotelNameText = findViewById(R.id.hotel_name_text);

        // Get hotel name from intent
        String hotelName = getIntent().getStringExtra("hotel_name");
        hotelNameText.setText(hotelName);

        Button confirmButton = findViewById(R.id.btn_confirm_booking);
        confirmButton.setOnClickListener(v -> {
            Toast.makeText(this, "Booking confirmed for " + hotelName, Toast.LENGTH_SHORT).show();
        });
    }
}
