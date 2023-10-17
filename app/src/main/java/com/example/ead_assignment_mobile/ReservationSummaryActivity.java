package com.example.ead_assignment_mobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservationSummaryActivity extends Activity {

    private TextView textViewReferenceId;
    private TextView textViewTravellerId;
    private TextView textViewTrainId;
    private TextView textViewReservationDate;

    private TextView  textViewToLocation;

    private TextView  textViewFromLocation;


    private TextView textViewReservationTime;

    private TextView textViewArrivalTime;
    private Button confirmButton;
    private static final String BASE_URL = "http://192.168.1.31:5227/";
    private Retrofit retrofit;

    private ApiService apiService;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_summary);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        textViewReferenceId = findViewById(R.id.textViewReferenceId);
        textViewTravellerId = findViewById(R.id.textViewTravellerId);
        textViewTrainId = findViewById(R.id.textViewTrainId);
        textViewReservationDate = findViewById(R.id.textViewReservationDate);
        textViewFromLocation = findViewById(R.id.fromLocation);
        textViewToLocation = findViewById(R.id.toLocation);
        confirmButton = findViewById(R.id.confirmButton);
        textViewReservationTime = findViewById(R.id.textViewReservationTime);
        textViewArrivalTime = findViewById(R.id.arrivalTime);

        Intent intent = getIntent();
        String referenceId = intent.getStringExtra("referenceId");
        String travellerId = intent.getStringExtra("travellerId");
        TrainSchedule selectedTrainSchedule = intent.getParcelableExtra("selectedTrainSchedule");

//        TrainSchedule selectedTrainSchedule = (TrainSchedule) intent.getSerializableExtra("selectedTrainSchedule");
        String reservationDate = intent.getStringExtra("reservationDate");
        String fromLocation = intent.getStringExtra("fromLocation");
        String toLocation = intent.getStringExtra("toLocation");
        String reservationTime = intent.getStringExtra("reservationTime");



        String trainName = selectedTrainSchedule.getTrainName();
        String departureTime = selectedTrainSchedule.getDepartureTime();
        String arrivalTime = selectedTrainSchedule.getArrivalTime();

        textViewReferenceId.setText("Reservation ID: " + referenceId);
        textViewTravellerId.setText("Traveller ID: " + travellerId);
        textViewReservationDate.setText("Reservation Date: " + reservationDate);
        textViewTrainId.setText("Train Name: " + trainName);
        textViewFromLocation.setText("From:" + fromLocation);
        textViewToLocation.setText("To:" + toLocation);
        textViewReservationTime.setText("Reservation Time:" + reservationTime);

//        textViewDepartureTime.setText("Departure Time" + departureTime);
//        textViewArrivalTime.setText("Arrival Time" + arrivalTime);



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Date currentDateAndTime = calendar.getTime();

//                String referenceId = editTextReferenceId.getText().toString();
//                String travellerId = editTextTravellerId.getText().toString();
//                String trainId = editTextTrainId.getText().toString();
//                String reservationDate = editTextReservationDate.getText().toString();

                // Create a BookingRequest object
                BookingRequest bookingRequest = new BookingRequest(referenceId, travellerId, "T101", true, "2023-10-06T16:05:15.871+00:00",reservationDate,trainName,fromLocation,toLocation,departureTime,arrivalTime,reservationTime);

                // Get the Retrofit ApiService instance
                ApiService apiService = retrofit.create(ApiService.class);

                // Make the POST request using Retrofit
                Call<BookingRequest> call = apiService.createReservation(bookingRequest);

                call.enqueue(new Callback<BookingRequest>() {
                    @Override
                    public void onResponse(Call<BookingRequest> call, Response<BookingRequest> response) {
                        if (response.isSuccessful()) {
                            // Handle a successful response
                            BookingRequest responseBody = response.body();
                            System.out.println(responseBody);
                            // Process the response as needed
                        } else {
                            // Handle an error response
                            String errorResponse = null;
                            try {
                                errorResponse = response.errorBody().string();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            String firstLineOfError = errorResponse.split("\n")[0];
                            Log.e("HTTP Post Request", "Response code: " + firstLineOfError);
                            Toast.makeText(ReservationSummaryActivity.this,firstLineOfError, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingRequest> call, Throwable t) {
                        // Handle network errors or other failures
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}
