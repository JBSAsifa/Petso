package com.example.petso;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ShelterSearchActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private RecyclerView recyclerView;
    private ShelterAdapter shelterAdapter;
    private List<Shelter> shelterList;
    private EditText searchBar;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_search);

        // Initialize Google Map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.shelter_map);
        mapFragment.getMapAsync(this);

        // Initialize location provider
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.shelter_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize shelter list and adapter
        shelterList = new ArrayList<>();
        shelterAdapter = new ShelterAdapter(shelterList);
        recyclerView.setAdapter(shelterAdapter);

        // Initialize search bar and button
        searchBar = findViewById(R.id.shelter_search_bar);
        searchButton = findViewById(R.id.btn_search_shelters);

        // Set click listener for the search button
        searchButton.setOnClickListener(v -> {
            String query = searchBar.getText().toString().trim();
            if (!query.isEmpty()) {
                searchShelters(query);
            }
        });

        // Request location permissions if not already granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Fetch current location
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));

                    // Search nearby shelters using the current location
                    searchNearbyShelters(currentLatLng);
                }
            });
        }
    }

    private void searchNearbyShelters(LatLng currentLatLng) {
        String apiKey = "AIzaSyB6FygTNiZaEulikmlzsWerQ7PMS03VnAc"; // Replace with your actual Google Maps API key
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?location=" + currentLatLng.latitude + "," + currentLatLng.longitude +
                "&radius=2000" +
                "&type=shelter" +
                "&key=" + apiKey;

        // Start an AsyncTask to fetch data
        new FetchSheltersTask().execute(url);
    }

    private class FetchSheltersTask extends AsyncTask<String, Void, List<Shelter>> {
        @Override
        protected List<Shelter> doInBackground(String... urls) {
            String jsonResponse = ApiHelper.fetchData(urls[0]); // Implement ApiHelper to handle HTTP requests
            return ShelterDataParser.parseShelters(jsonResponse);
        }

        @Override
        protected void onPostExecute(List<Shelter> shelters) {
            shelterList.clear();
            shelterList.addAll(shelters);
            shelterAdapter.notifyDataSetChanged();

            // Add markers for each shelter on the map
            for (Shelter shelter : shelters) {
                LatLng shelterLatLng = new LatLng(shelter.getLatitude(), shelter.getLongitude());
                googleMap.addMarker(new MarkerOptions()
                        .position(shelterLatLng)
                        .title(shelter.getName())
                        .snippet(shelter.getAddress()));
            }
        }
    }

    private void searchShelters(String query) {
        shelterList.clear();
        shelterList.add(new Shelter("Safe Haven Shelter", "Chandgaon", "Available", 22.356851, 91.783182));
        shelterList.add(new Shelter("Happy Paws Shelter", "Medical road", "Available", 22.366851, 91.793182));
        shelterList.add(new Shelter("Animal Rescue Hub", "Mehedibagh", "Unavailable", 22.376851, 91.803182));

        shelterAdapter.notifyDataSetChanged();

        // Add markers for shelters on the map
        for (Shelter shelter : shelterList) {
            LatLng shelterLatLng = new LatLng(shelter.getLatitude(), shelter.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(shelterLatLng)
                    .title(shelter.getName())
                    .snippet(shelter.getAddress()));
        }

        // Center map on the first shelter
        if (!shelterList.isEmpty()) {
            LatLng firstShelter = new LatLng(shelterList.get(0).getLatitude(), shelterList.get(0).getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstShelter, 15));
        }
    }
}
