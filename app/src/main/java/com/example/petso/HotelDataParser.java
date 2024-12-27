package com.example.petso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HotelDataParser {
    public static List<Hotel> parseHotels(String jsonResponse) {
        List<Hotel> hotels = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject hotel = results.getJSONObject(i);
                String name = hotel.getString("name");
                String address = hotel.getString("vicinity");
                String availability = hotel.getString("availability");
                JSONObject location = hotel.getJSONObject("geometry").getJSONObject("location");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");

                hotels.add(new Hotel(name, address,availability, lat, lng));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hotels;
    }
}

