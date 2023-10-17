package com.example.ead_assignment_mobile;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PastBookingsActivity extends AppCompatActivity {
    private List<BookingRequest> bookings = new ArrayList<>();
    private RecyclerView recyclerView;
    private static final String BASE_URL = "http://192.168.1.31:5227/";
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_bookings);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PastBookingAdapter adapter = new PastBookingAdapter(); // Initialize the adapter here
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        String ReferenceId = LoginActivity.nic;
        Call<List<BookingRequest>> call = apiService.getPastBooking(ReferenceId);
        System.out.println("ref" + ReferenceId);
        call.enqueue(new Callback<List<BookingRequest>>() {
            @Override
            public void onResponse(@NonNull Call<List<BookingRequest>> call, @NonNull Response<List<BookingRequest>> response) {
                System.out.println("res" + response);
                if (response.isSuccessful()) {
                    List<BookingRequest> receivedBookings = response.body();
                    Log.d("DEBUG", "Received " + receivedBookings.size() + " bookings");
                    System.out.println("Received " + receivedBookings.size() + " bookings");

                    adapter.setData(receivedBookings);
                    adapter.notifyDataSetChanged();
                } else {
                    System.out.println("Error response: " + response.code());
                    Log.d("DEBUG", "Error response: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BookingRequest>> call, @NonNull Throwable t) {
                Log.e("NETWORK_ERROR", "Network request failed: " + t.getMessage());
                System.out.println("error in network" + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
