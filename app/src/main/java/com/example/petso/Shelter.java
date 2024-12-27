package com.example.petso;

public class Shelter {
    private String name;
    private String address;
    private String availability;
    private double latitude;
    private double longitude;

    public Shelter(String name, String address, String availability, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.availability = availability;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getAvailability() {
        return availability;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
