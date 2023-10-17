package com.example.ead_assignment_mobile;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    @POST("api/ticketbookings")
    Call<BookingRequest> createReservation(@Body BookingRequest bookingRequest);
    @GET("api/ticketbookings/{travellerId}/true")
    Call<List<BookingRequest>> getUpcomingBooking(@Path("travellerId") String referenceId);

    @GET("api/ticketbookings/{referenceId}/false")
    Call<List<BookingRequest>> getPastBooking(@Path("referenceId") String referenceId);
    @DELETE("api/ticketbookings/{reservationId}")
    Call<Void> deleteReservationById(@Path("reservationId") String reservationId);
    @PUT("api/ticketbookings/updateReservationDateAndStatus/{reservationId}")
    Call<Void> updateBooking(@Path("reservationId") String reservationId, @Body BookingRequest bookingRequest);

    @GET("api/train/search")
    Call<List<TrainSchedule>> getTrainSchedules(@Query("departureStation") String departureStation, @Query("arrivalStation") String arrivalStation);

    @GET("api/User/get-by-nic/{nic}")
    Call<UserProfile> getUserProfile(@Path("nic") String nic);

    @PUT("api/User/disable-account/{nic}")
    Call<Void> deactivateAccount(@Path("nic") String nic);

    @POST("api/User/login")
    Call<User> login(@Body LoginRequest request);

    @POST("api/User/register")
    Call<RegistrationResponse> registerUser(@Body RegistrationData registrationData);
}
