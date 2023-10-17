package com.example.ead_assignment_mobile;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class BookingRequest implements Parcelable {
    @SerializedName("referenceId")
    private String referenceId;

    @SerializedName("travellerId")
    private String travellerId;

    @SerializedName("trainId")
    private String trainId;

    @SerializedName("status")
    private boolean status;

    @SerializedName("bookingDate")
    private String bookingDate;

    @SerializedName("reservationDate")
    private String reservationDate;

    @SerializedName("trainName")
    private String trainName;

    @SerializedName("trainFrom")
    private String trainFrom;

    @SerializedName("trainTo")
    private String trainTo;

    @SerializedName("departureTime")
    private String departureTime;

    @SerializedName("arrivalTime")
    private String arrivalTime;

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    @SerializedName("reservationTime")
    private String reservationTime;

    public BookingRequest(String referenceId, String travellerId, String trainId, boolean status, String bookingDate, String reservationDate,String trainName,String trainFrom,String trainTo,String departureTime,String arrivalTime,String reservationTime) {
        this.referenceId = referenceId;
        this.travellerId = travellerId;
        this.trainId = trainId;
        this.status = status;
        this.bookingDate = bookingDate;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.trainName = trainName;
        this.trainFrom = trainFrom;
        this.trainTo = trainTo;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
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

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainFrom() {
        return trainFrom;
    }

    public void setTrainFrom(String trainFrom) {
        this.trainFrom = trainFrom;
    }

    public String getTrainTo() {
        return trainTo;
    }

    public void setTrainTo(String trainTo) {
        this.trainTo = trainTo;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(String travellerId) {
        this.travellerId = travellerId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    protected BookingRequest(Parcel in) {
        referenceId = in.readString();
        travellerId = in.readString();
        trainId = in.readString();
        status = in.readByte() != 0;
        bookingDate = in.readString();
        reservationDate = in.readString();
        reservationTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(referenceId);
        dest.writeString(travellerId);
        dest.writeString(trainId);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(bookingDate);
        dest.writeString(reservationDate);
        dest.writeString(reservationTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingRequest> CREATOR = new Creator<BookingRequest>() {
        @Override
        public BookingRequest createFromParcel(Parcel in) {
            return new BookingRequest(in);
        }

        @Override
        public BookingRequest[] newArray(int size) {
            return new BookingRequest[size];
        }
    };
}
