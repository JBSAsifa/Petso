package com.example.petso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShelterDataParser {

    public static List<Shelter> parseShelters(String jsonResponse) {
        List<Shelter> shelters = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject shelterObject = results.getJSONObject(i);
                String name = shelterObject.getString("name");
                String address = shelterObject.getString("vicinity");
                double latitude = shelterObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                double longitude = shelterObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                // Availability can be mocked or based on custom logic (not present in API)
                String availability = "Available";

                shelters.add(new Shelter(name, address, availability, latitude, longitude));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return shelters;
    }
}
