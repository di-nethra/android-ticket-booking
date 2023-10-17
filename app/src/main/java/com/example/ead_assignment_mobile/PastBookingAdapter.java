package com.example.ead_assignment_mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PastBookingAdapter extends RecyclerView.Adapter<PastBookingAdapter.ViewHolder> {

    private List<BookingRequest> bookings;

    public PastBookingAdapter() {
        this.bookings = new ArrayList<>();
    }

    public void setData(List<BookingRequest> bookings) {
        this.bookings = bookings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingRequest booking = bookings.get(position);
        holder.referenceIdTextView.setText(booking.getReferenceId());
        holder.bookingDateTextView.setText(booking.getBookingDate());
        holder.reservationDateTextView.setText(booking.getReservationDate());
        holder.trainIdTextView.setText(booking.getTrainId());

        // Remove the edit and delete button code
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView referenceIdTextView;
        TextView bookingDateTextView;
        TextView reservationDateTextView;
        TextView trainIdTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            referenceIdTextView = itemView.findViewById(R.id.referenceIdTextView);
            bookingDateTextView = itemView.findViewById(R.id.bookingDateTextView);
            reservationDateTextView = itemView.findViewById(R.id.reservationDateTextView);
            trainIdTextView = itemView.findViewById(R.id.trainTextView);
        }
    }
}
