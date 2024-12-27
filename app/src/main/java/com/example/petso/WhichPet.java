package com.example.petso;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class WhichPet extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private ImageView petImageView;
    private TextView resultTextView;
    private Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_which_pet);

        // Initialize views
        petImageView = findViewById(R.id.petdetecter);
        resultTextView = findViewById(R.id.textViewResult);

        Button captureImageButton = findViewById(R.id.buttonCaptureImage);
        Button loadFromGalleryButton = findViewById(R.id.buttonLoadImage2);
        Button detectPetButton = findViewById(R.id.buttonDetectPet);

        // Set button listeners
        captureImageButton.setOnClickListener(v -> captureImage());
        loadFromGalleryButton.setOnClickListener(v -> loadFromGallery());
        detectPetButton.setOnClickListener(v -> detectPet());
    }

    private void captureImage() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void loadFromGallery() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_LOAD_IMAGE);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_LOAD_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                selectedImageBitmap = (Bitmap) extras.get("data");
                petImageView.setImageBitmap(selectedImageBitmap);

            } else if (requestCode == REQUEST_LOAD_IMAGE && data != null) {
                Uri imageUri = data.getData();
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    petImageView.setImageBitmap(selectedImageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void detectPet() {
        if (selectedImageBitmap != null) {
            // Placeholder for detection logic
            String detectedPet = "Dog"; // Replace this with actual detection logic
            resultTextView.setText("Detection Result: " + detectedPet);
        } else {
            resultTextView.setText("Please select or capture an image first.");
        }
    }
}
