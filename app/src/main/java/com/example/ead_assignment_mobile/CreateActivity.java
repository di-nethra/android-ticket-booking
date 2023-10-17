//package com.example.ead_assignment_mobile;
//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.TimePicker;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Calendar;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class CreateActivity extends AppCompatActivity {
//
//    private EditText editTextReferenceId;
//    private EditText editTextTravellerId;
//    private EditText editTextTrainId;
//    private EditText editTextReservationDate;
//    private Button buttonCreateReservation;
//
//    private Calendar calendar;
//
//    private static final String BASE_URL = "http://192.168.1.31:5227/";
//    private Retrofit retrofit;
//    private ApiService apiService;
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create);
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        apiService = retrofit.create(ApiService.class);
//
//
//        editTextReservationDate = findViewById(R.id.editTextReservationDate);
//        buttonCreateReservation = findViewById(R.id.buttonCreateReservation);
//        editTextReferenceId = findViewById(R.id.editTextReferenceId);
//        editTextTravellerId = findViewById(R.id.editTextTravellerId);
//        editTextTrainId = findViewById(R.id.editTextTrainId);
//
//        calendar = Calendar.getInstance();
//
//        editTextReservationDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDateTimePicker();
//            }
//        });
//
//
//        buttonCreateReservation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Date currentDateAndTime = calendar.getTime();
//
//                String referenceId = editTextReferenceId.getText().toString();
//                String travellerId = editTextTravellerId.getText().toString();
//                String trainId = editTextTrainId.getText().toString();
//                String reservationDate = editTextReservationDate.getText().toString();
//
//                // Create a BookingRequest object
//                BookingRequest bookingRequest = new BookingRequest(referenceId, travellerId, trainId, true, "2023-10-06T16:05:15.871+00:00", "2023-10-06T16:05:15.871+00:00");
//
//                // Get the Retrofit ApiService instance
//                ApiService apiService = retrofit.create(ApiService.class);
//
//                // Make the POST request using Retrofit
//                Call<BookingRequest> call = apiService.createReservation(bookingRequest);
//
//                call.enqueue(new Callback<BookingRequest>() {
//                    @Override
//                    public void onResponse(Call<BookingRequest> call, Response<BookingRequest> response) {
//                        if (response.isSuccessful()) {
//                            // Handle a successful response
//                            BookingRequest responseBody = response.body();
//                            // Process the response as needed
//                        } else {
//                            // Handle an error response
//                            Log.e("HTTP Post Request", "Response code: " + response.code());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<BookingRequest> call, Throwable t) {
//                        // Handle network errors or other failures
//                        t.printStackTrace();
//                    }
//                });
//            }
//        });
//
//    }
//
//    private void showDateTimePicker() {
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                // Handle the date selection
//                calendar.set(Calendar.YEAR, year);
//                calendar.set(Calendar.MONTH, month);
//                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                        calendar.set(Calendar.MINUTE, minute);
//
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        String selectedDateTime = dateFormat.format(calendar.getTime());
//
//
//                        editTextReservationDate.setText(selectedDateTime);
//                    }
//                }, hour, minute, false);
//
//                timePickerDialog.show();
//            }
//        }, year, month, day);
//
//        datePickerDialog.show();
//    }
//}
