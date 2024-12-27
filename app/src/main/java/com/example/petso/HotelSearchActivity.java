package com.example.petso;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotelSearchActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;
    private List<Hotel> hotelList;
    private EditText searchBar;
    private Button searchButton;
    private HashMap<Hotel, Marker> hotelMarkerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_search);

        // Initialize Google Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize location provider
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.hotel_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize hotel list and adapter
        hotelList = new ArrayList<>();
        hotelAdapter = new HotelAdapter(hotelList, hotel -> {
            // Move the map camera to the selected hotel's location
            Marker marker = hotelMarkerMap.get(hotel);
            if (marker != null) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
                marker.showInfoWindow(); // Show marker info window
            }
        });
        recyclerView.setAdapter(hotelAdapter);

        // Initialize search bar and button
        searchBar = findViewById(R.id.hotel_search_bar);
        searchButton = findViewById(R.id.btn_search_hotels);

        // Search button functionality
        searchButton.setOnClickListener(v -> {
            String query = searchBar.getText().toString().trim();
            if (!query.isEmpty()) {
                searchHotels(query);  // Perform the search
            }
        });

        // Initialize hotelMarkerMap
        hotelMarkerMap = new HashMap<>();

        // Request location permissions if necessary
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            getCurrentLocation();  // If permission is already granted, get the location
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get current location if permissions are granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));

                // Search for nearby hotels
                searchNearbyHotels(currentLatLng);
            }
        });
    }

    private void searchNearbyHotels(LatLng currentLatLng) {
        String apiKey = "YOUR_API_KEY_HERE";  // Replace with your Google Places API key
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?location=" + currentLatLng.latitude + "," + currentLatLng.longitude +
                "&radius=2000" +
                "&type=lodging" +
                "&key=" + apiKey;

        // Use an HTTP library like Retrofit or OkHttp to make the API request
        new FetchHotelsTask().execute(url);
    }

    // Mock class to handle fetching data from API
    private class FetchHotelsTask extends AsyncTask<String, Void, List<Hotel>> {
        @Override
        protected List<Hotel> doInBackground(String... urls) {
            // Make the HTTP request and parse the JSON response
            return HotelDataParser.parseHotels(urls[0]); // Implement HotelDataParser to parse the API response
        }

        @Override
        protected void onPostExecute(List<Hotel> hotels) {
            hotelList.clear();  // Clear previous results
            hotelList.addAll(hotels);  // Add new hotels to the list
            hotelAdapter.notifyDataSetChanged();  // Notify adapter for data change

            // Display markers on the map for each hotel
            for (Hotel hotel : hotels) {
                LatLng hotelLatLng = new LatLng(hotel.getLatitude(), hotel.getLongitude());
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(hotelLatLng)
                        .title(hotel.getName())
                        .snippet(hotel.getAddress()));
                hotelMarkerMap.put(hotel, marker);  // Store the marker for future use
            }
        }
    }

    private void searchHotels(String query) {
        hotelList.clear();
        hotelList.add(new Hotel("Petso Hotel", "Chandgaon", "Available", 22.356851, 91.783182));
        hotelList.add(new Hotel("Happy Tails Resort", "Medical road", "Available", 22.366851, 91.793182));
        hotelList.add(new Hotel("Furry Friends Inn", "Mehedibagh", "Unavailable", 22.376851, 91.803182));

        hotelAdapter.notifyDataSetChanged();
        // Add markers for hotels
        for (Hotel hotel : hotelList) {
            LatLng hotelLatLng = new LatLng(hotel.getLatitude(), hotel.getLongitude());
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(hotelLatLng)
                    .title(hotel.getName())
                    .snippet(hotel.getAddress()));
            hotelMarkerMap.put(hotel, marker);  // Store the marker for future use
        }

        // Center the map to the first hotel
        if (!hotelList.isEmpty()) {
            LatLng firstHotel = new LatLng(hotelList.get(0).getLatitude(), hotelList.get(0).getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstHotel, 15));
        }
    }
}
