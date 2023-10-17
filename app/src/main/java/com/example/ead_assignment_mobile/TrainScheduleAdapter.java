package com.example.ead_assignment_mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TrainScheduleAdapter extends RecyclerView.Adapter<TrainScheduleAdapter.ViewHolder> {
    private List<TrainSchedule> trainSchedules;
    private OnItemClickListener onItemClickListener;
    private TrainSchedule selectedTrainSchedule;

    public interface OnItemClickListener {
        void onItemClick(TrainSchedule schedule);
    }

    public void setTrainSchedules(List<TrainSchedule> trainSchedules) {
        this.trainSchedules = trainSchedules;
        notifyDataSetChanged();
    }

    public TrainScheduleAdapter(List<TrainSchedule> trainSchedules, OnItemClickListener listener) {
        this.trainSchedules = trainSchedules;
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_train_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TrainSchedule schedule = trainSchedules.get(position);
        holder.trainName.setText(schedule.getTrainName());
        holder.departureTime.setText(schedule.getDepartureTime());
        holder.arrivalTime.setText(schedule.getArrivalTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    TrainSchedule selectedSchedule = trainSchedules.get(position);

                    selectedTrainSchedule = selectedSchedule;

                    onItemClickListener.onItemClick(selectedSchedule);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainSchedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView trainName;
        public final TextView departureTime;
        public final TextView arrivalTime;

        public ViewHolder(View view) {
            super(view);
            trainName = view.findViewById(R.id.trainName);
            departureTime = view.findViewById(R.id.departureTime);
            arrivalTime = view.findViewById(R.id.arrivalTime);
        }
    }

    public TrainSchedule getSelectedTrainSchedule() {
        return selectedTrainSchedule;
    }

    public void setSelectedTrainSchedule(TrainSchedule schedule) {
        selectedTrainSchedule = schedule;
    }
}
