package com.example.petso;

import java.util.Objects;

public class Hotel {

   String name;
   String address;
   String availability;
    double latitude;
    double longitude;
    // Constructor
    public Hotel(String name, String address, String availability, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.availability = availability;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Double.compare(hotel.latitude, latitude) == 0 &&
                Double.compare(hotel.longitude, longitude) == 0 &&
                name.equals(hotel.name) &&
                address.equals(hotel.address);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, address,availability, latitude, longitude);
    }


    // Getters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return availability;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
