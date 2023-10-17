package com.example.ead_assignment_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView cardMakeReservation = findViewById(R.id.card_make_reservation);
        CardView cardUpcomingBookings = findViewById(R.id.card_upcoming_bookings);
        CardView cardPastBookings = findViewById(R.id.card_past_bookings);
        CardView cardViewProfile = findViewById(R.id.card_view_profile);
    }

    public void onCardClick(View view) {
        switch (view.getId()) {
            case R.id.card_make_reservation:
                Intent createIntentReservation = new Intent(this, ReservationFormActivity.class);
                startActivity(createIntentReservation);
                break;
            case R.id.card_upcoming_bookings:
                Intent createIntentUpBookings = new Intent(this, UpcomingBookingsActivity.class);
                startActivity(createIntentUpBookings);
                break;
            case R.id.card_past_bookings:
                Intent createIntentPastBookings = new Intent(this, PastBookingsActivity.class);
                startActivity(createIntentPastBookings);
                break;
            case R.id.card_view_profile:
                Intent createIntentProfile = new Intent(this, UserProfileActivity.class);
                startActivity(createIntentProfile);
                break;
        }
    }
}
