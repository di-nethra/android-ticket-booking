package com.example.ead_assignment_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditBookingActivity extends AppCompatActivity {

    private EditText reservationDateEditText;
    private EditText reservationTimeEditText;
    private CheckBox statusCheckBox;
    private BookingRequest booking;
    private ApiService apiService;
    private static final String BASE_URL = "http://192.168.1.31:5227/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_booking);
        statusCheckBox = findViewById(R.id.statusCheckBox);
        statusCheckBox.setChecked(false);

        reservationDateEditText = findViewById(R.id.reservationDateEditText);
        reservationTimeEditText = findViewById(R.id.reservationTimeEditText);
        booking = getIntent().getParcelableExtra("booking");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        if (booking != null) {
            reservationDateEditText.setText(booking.getReservationDate());
            reservationTimeEditText.setText(booking.getReservationTime());
            statusCheckBox.setChecked(booking.isStatus());
        }

        Button updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedReservationDate = reservationDateEditText.getText().toString();
                String updatedReservationTime = reservationTimeEditText.getText().toString(); // Get reservation time
                boolean updatedStatus = statusCheckBox.isChecked();

                if (booking != null) {
                    // Update the booking object with the new data
                    booking.setReservationDate(updatedReservationDate);
                    booking.setReservationTime(updatedReservationTime);
                    booking.setStatus(updatedStatus);

                    updateBooking(booking);
                } else {
                    Toast.makeText(EditBookingActivity.this, "Booking data is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        statusCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = statusCheckBox.isChecked();
                reservationDateEditText.setEnabled(!isChecked);
                reservationTimeEditText.setEnabled(!isChecked);
            }
        });
    }

    private void updateBooking(BookingRequest updatedBooking) {
        String reservationId = updatedBooking.getReferenceId();

        Call<Void> call = apiService.updateBooking(reservationId, updatedBooking);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditBookingActivity.this, "Booking updated successfully", Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updatedBooking", updatedBooking);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    String errorResponse = null;
                    try {
                        errorResponse = response.errorBody().string();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String firstLineOfError = errorResponse.split("\n")[0];
                    Log.e("HTTP Post Request", "Response code: " + firstLineOfError);
                    Toast.makeText(EditBookingActivity.this,firstLineOfError, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(EditBookingActivity.this, "Error updating booking", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(EditBookingActivity.this, "Network request failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
