package com.example.ead_assignment_mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead_assignment_mobile.BookingRequest;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private List<BookingRequest> bookings;

    public BookingAdapter() {
        this.bookings = new ArrayList<>();
    }

    public void setData(List<BookingRequest> bookings) {
        this.bookings = bookings;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingRequest booking = bookings.get(position);
        holder.referenceIdTextView.setText(booking.getReferenceId());
        holder.bookingDateTextView.setText(booking.getBookingDate());
        holder.reservationDateTextView.setText(booking.getReservationDate());
        holder.trainIdTextView.setText(booking.getTrainId());
        holder.toIdTextView.setText(booking.getTrainTo());
        holder.fromIdTextView.setText(booking.getTrainFrom());
        // Handle edit button click
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onEditClick(adapterPosition);
                }
            }
        });


        // Handle delete button click
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onDeleteClick(adapterPosition);
                }
            }
        });
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

        TextView toIdTextView;
        TextView fromIdTextView;
        Button editButton;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            referenceIdTextView = itemView.findViewById(R.id.referenceIdTextView);
            bookingDateTextView = itemView.findViewById(R.id.bookingDateTextView);
            reservationDateTextView = itemView.findViewById(R.id.reservationDateTextView);
            trainIdTextView = itemView.findViewById(R.id.trainTextView);
            toIdTextView=itemView.findViewById(R.id.toIdTextView);
            fromIdTextView=itemView.findViewById(R.id.fromIdTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
