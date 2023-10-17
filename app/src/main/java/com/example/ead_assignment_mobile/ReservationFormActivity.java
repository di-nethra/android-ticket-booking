package com.example.ead_assignment_mobile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservationFormActivity extends Activity implements TrainScheduleAdapter.OnItemClickListener {
    private EditText editTextReferenceId;

    private EditText editTextReservationDate;
    private EditText editTextReservationTime;
    private EditText editTextFrom;
    private EditText editTextTo;
    private Button buttonGetSchedules;
    private RecyclerView recyclerViewTrainSchedules;
    private TrainScheduleAdapter scheduleAdapter;

    private static final String BASE_URL = "http://192.168.1.31:5227/";
    private Retrofit retrofit;
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);

        editTextReferenceId = findViewById(R.id.editTextReferenceId);
        editTextReservationDate = findViewById(R.id.editTextReservationDate);
        editTextReservationTime = findViewById(R.id.editTextReservationTime);
        editTextFrom = findViewById(R.id.editTextFrom);
        editTextTo = findViewById(R.id.editTextTo);
        buttonGetSchedules = findViewById(R.id.buttonGetSchedules);
        recyclerViewTrainSchedules = findViewById(R.id.recyclerViewTrainSchedules);

        recyclerViewTrainSchedules.setLayoutManager(new LinearLayoutManager(this));
        scheduleAdapter = new TrainScheduleAdapter(new ArrayList<>(), this);
        recyclerViewTrainSchedules.setAdapter(scheduleAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        buttonGetSchedules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromLocation = editTextFrom.getText().toString();
                String toLocation = editTextTo.getText().toString();
                fetchTrainSchedules(fromLocation, toLocation);
            }
        });

        // Set click listeners for date and time pickers
        editTextReservationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        editTextReservationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }

    private void fetchTrainSchedules(String fromLocation, String toLocation) {
        Call<List<TrainSchedule>> call = apiService.getTrainSchedules(fromLocation, toLocation);
        call.enqueue(new Callback<List<TrainSchedule>>() {
            @Override
            public void onResponse(Call<List<TrainSchedule>> call, Response<List<TrainSchedule>> response) {
                System.out.println("Response Body: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    System.out.println(response);
                    List<TrainSchedule> trainSchedules = response.body();
                    scheduleAdapter.setTrainSchedules(trainSchedules);
                }
            }

            @Override
            public void onFailure(Call<List<TrainSchedule>> call, Throwable t) {
                System.out.println("Error in getting train schedules");
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                editTextReservationDate.setText(selectedDate);
            }
        });
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String selectedTime = hourOfDay + ":" + minute;
                        editTextReservationTime.setText(selectedTime);
                    }
                },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    @Override
    public void onItemClick(TrainSchedule schedule) {
        scheduleAdapter.setSelectedTrainSchedule(schedule);

        Intent intent = new Intent(this, ReservationSummaryActivity.class);
        intent.putExtra("selectedTrainSchedule", schedule);
        intent.putExtra("referenceId", editTextReferenceId.getText().toString());
        intent.putExtra("travellerId", LoginActivity.nic);
        intent.putExtra("reservationDate", editTextReservationDate.getText().toString());
        intent.putExtra("reservationTime", editTextReservationTime.getText().toString());
        intent.putExtra("fromLocation", editTextFrom.getText().toString());
        intent.putExtra("toLocation", editTextTo.getText().toString());
        startActivity(intent);
    }

    public void openSummary(View view) {
        Intent intent = new Intent(this, ReservationSummaryActivity.class);
        intent.putExtra("referenceId", editTextReferenceId.getText().toString());
        intent.putExtra("travellerId", LoginActivity.nic);
        intent.putExtra("reservationDate", editTextReservationDate.getText().toString());
        intent.putExtra("reservationTime", editTextReservationTime.getText().toString());
        intent.putExtra("fromLocation", editTextFrom.getText().toString());
        intent.putExtra("toLocation", editTextTo.getText().toString());
        startActivity(intent);
    }
}
