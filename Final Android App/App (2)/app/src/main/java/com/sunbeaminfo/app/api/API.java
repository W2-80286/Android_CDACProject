package com.sunbeaminfo.app.api;

import com.google.gson.JsonObject;
import com.sunbeaminfo.app.Entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    public static final String BASE_URL = "http://192.168.1.124:4001";

    @GET("/event/")
    Call<JsonObject> getAllEvents();
    @GET("/venue/")
    Call<JsonObject> getAllVenues();
    @POST("/order/details")
    Call<JsonObject> bookEvent(@Body JsonObject order);
    @GET("/order/")
    Call<JsonObject> getAllOrder();
    @GET("/user/")
    public Call<JsonObject> getAllUsers();
    @GET("/user/{id}")
    public Call<JsonObject> getUser(@Path("id") int id);
    public Call<JsonObject> getUser(@Path("token") String token);
    @POST("/user/signin")
    public Call<JsonObject> loginUser(@Body JsonObject user);

    @POST("/user/signup")
    public Call<JsonObject> registerUser(@Body User user);

    @GET("/order/bookingDetails/user/{userId}")
    Call<JsonObject> getBookingDetailsForUser(@Path("userId") int userId);

}