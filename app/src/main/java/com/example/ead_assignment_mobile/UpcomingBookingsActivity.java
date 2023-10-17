package com.example.ead_assignment_mobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcomingBookingsActivity extends AppCompatActivity {
    private List<BookingRequest> bookings = new ArrayList<>();
    private RecyclerView recyclerView;
    private static final int EDIT_REQUEST_CODE = 1;

    private BookingAdapter adapter;
    private static final String BASE_URL = "http://192.168.1.31:5227/";
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_bookings);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookingAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BookingAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                BookingRequest bookingToEdit = bookings.get(position);
                Intent editIntent = new Intent(UpcomingBookingsActivity.this, EditBookingActivity.class);
                editIntent.putExtra("booking", bookingToEdit);
                startActivityForResult(editIntent, EDIT_REQUEST_CODE);
            }

            @Override
            public void onDeleteClick(int position) {
                BookingRequest bookingToDelete = bookings.get(position);
                showDeleteConfirmationDialog(bookingToDelete);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        String ReferenceId = LoginActivity.nic;
        Call<List<BookingRequest>> call = apiService.getUpcomingBooking(ReferenceId);
        System.out.println("ref" + ReferenceId);
        call.enqueue(new Callback<List<BookingRequest>>() {
            @Override
            public void onResponse(@NonNull Call<List<BookingRequest>> call, @NonNull Response<List<BookingRequest>> response) {
                System.out.println("res" + response);
                if (response.isSuccessful()) {
                    List<BookingRequest> receivedBookings = response.body();
                    Log.d("DEBUG", "Received " + receivedBookings.size() + " bookings");
                    System.out.println("Received " + receivedBookings.size() + " bookings");

                    // Notify the adapter of the data change
                    bookings = receivedBookings;
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

    private void showDeleteConfirmationDialog(final BookingRequest bookingToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this booking?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteBooking(bookingToDelete);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteBooking(BookingRequest bookingToDelete) {
        String reservationId = bookingToDelete.getReferenceId();
        Call<Void> call = apiService.deleteReservationById(reservationId);
        System.out.println(call);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    int position = bookings.indexOf(bookingToDelete);
                    if (position != -1) {
                        bookings.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                } else {
                    Log.e("DELETE_ERROR", "Error deleting booking: " + response.code());
                    Toast.makeText(UpcomingBookingsActivity.this, "Error deleting booking", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("NETWORK_ERROR", "Network request failed: " + t.getMessage());
                Toast.makeText(UpcomingBookingsActivity.this, "Network request failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            BookingRequest updatedBooking = data.getParcelableExtra("updatedBooking");
            if (updatedBooking != null) {
                int position = bookings.indexOf(updatedBooking);
                if (position != -1) {
                    bookings.set(position, updatedBooking);
                    adapter.notifyItemChanged(position);
                }
            }
        }
    }
}