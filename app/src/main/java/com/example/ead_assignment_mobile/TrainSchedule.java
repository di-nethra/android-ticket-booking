package com.example.ead_assignment_mobile;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainSchedule implements Parcelable {
    private String trainNumber;
    private String trainName;
    private String departureTime;
    private String arrivalTime;

    private String departureStation;

    private String arrivalStation;

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public TrainSchedule() {
        // Empty constructor required by Parcelable
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trainNumber);
        dest.writeString(trainName);
        dest.writeString(departureTime);
        dest.writeString(arrivalTime);
        dest.writeString(departureStation);
        dest.writeString(arrivalStation);
    }

    public static final Parcelable.Creator<TrainSchedule> CREATOR = new Parcelable.Creator<TrainSchedule>() {
        public TrainSchedule createFromParcel(Parcel in) {
            return new TrainSchedule(in);
        }

        public TrainSchedule[] newArray(int size) {
            return new TrainSchedule[size];
        }
    };

    private TrainSchedule(Parcel in) {
        trainNumber = in.readString();
        trainName = in.readString();
        departureTime = in.readString();
        arrivalTime = in.readString();
        departureStation = in.readString();
        arrivalStation = in.readString();
    }
}
