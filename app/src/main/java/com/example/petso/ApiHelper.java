package com.example.petso;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiHelper {

    public static String fetchData(String urlString) {
        StringBuilder result = new StringBuilder();

        try {
            // Create URL object
            URL url = new URL(urlString);

            // Open HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the input stream and read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null in case of any error
        }

        return result.toString();
    }
}
