package com.example.ead_assignment_mobile;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateReservation {

    public void CreateReservationRequest(BookingRequest bookingRequest) {
        String apiUrl = "http://192.168.1.31:5227/api/ticketbookings";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            Gson gson = new Gson();
            String jsonInputString = gson.toJson(bookingRequest);

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(jsonInputString);
                wr.flush();
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                String responseBody = response.toString();
                Log.d("HTTP Post Response", responseBody);
            } else {

                Log.e("HTTP Post Request", "Response code: " + responseCode);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

