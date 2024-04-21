package com.sunbeaminfo.app.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sunbeaminfo.app.Entity.BookingDetail;
import com.sunbeaminfo.app.R;
import com.sunbeaminfo.app.adapter.BookingDetailsAdapter;
import com.sunbeaminfo.app.api.API;
import com.sunbeaminfo.app.api.RetrofitClient;
import com.sunbeaminfo.app.utility.EventConstants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookingDetailsAdapter adapter;
    private List<BookingDetail> bookingDetailsList;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBookingDetails);
        bookingDetailsList = new ArrayList<>();
        adapter = new BookingDetailsAdapter(bookingDetailsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Retrieve user ID from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(EventConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt(EventConstants.USER_ID, 0); // Default value 0 if not found

        if (userId != 0) {
            // Make Retrofit call to fetch booking details for the user
            API api = RetrofitClient.getInstance().getApi();
            Call<JsonObject> call = api.getBookingDetailsForUser(userId);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject jsonObject = response.body();
                        if (jsonObject != null && jsonObject.has("data")) {
                            JsonArray bookingDetailsArray = jsonObject.getAsJsonArray("data");
                            for (JsonElement element : bookingDetailsArray) {
                                JsonObject bookingDetailObject = element.getAsJsonObject();
                                BookingDetail bookingDetail = new BookingDetail();
                                bookingDetail.setId(bookingDetailObject.get("id").getAsInt());
                                bookingDetail.setEventName(bookingDetailObject.get("event_name").getAsString());
                                bookingDetail.setVenueName(bookingDetailObject.get("venue_name").getAsString());
                                bookingDetail.setAttendees(bookingDetailObject.get("attendees").getAsInt());
                                bookingDetail.setTotalPrice(bookingDetailObject.get("total_price").getAsDouble());
                                bookingDetail.setBookingDate(bookingDetailObject.get("booking_date").getAsString());
                                bookingDetail.setBookingTimestamp(bookingDetailObject.get("booking_timestamp").getAsString());
                                bookingDetail.setPaymentStatus(bookingDetailObject.get("payment_status").getAsString());
                                bookingDetailsList.add(bookingDetail);
                            }
                            // Notify adapter that data set has changed
                            adapter.notifyDataSetChanged();
                        } else {
                            // No booking details found for this user
                        }
                    } else {
                        // Handle unsuccessful response
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    // Handle failures
                }
            });
        } else {
            // User ID not found in SharedPreferences
        }

        return view;
    }
}
