package com.sunbeaminfo.app.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sunbeaminfo.app.Entity.Event;
import com.sunbeaminfo.app.R;
import com.sunbeaminfo.app.adapter.EventAdapter;
import com.sunbeaminfo.app.api.RetrofitClient;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    RecyclerView recyclerView;
    EventAdapter eventAdapter;
    List<Event> eventList;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(getActivity(), eventList);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Call the API to fetch events
        getAllEvents();

        return view;
    }

    private void getAllEvents() {
        RetrofitClient.getInstance().getApi().getAllEvents().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject.has("status") && jsonObject.get("status").getAsString().equals("success")) {
                        JsonArray dataArray = jsonObject.getAsJsonArray("data");
                        eventList.clear();
                        for (JsonElement element : dataArray) {
                            JsonObject dataObject = element.getAsJsonObject();
                            Event event = new Event();
                            event.setName(dataObject.get("name").getAsString());
                            event.setImage(dataObject.get("image").getAsString());
                            event.setDetails(dataObject.get("details").getAsString());
                            event.setId(dataObject.get("id").getAsInt());
                            event.setCreatedTimestamp(dataObject.get("createdTimestamp").getAsString());
                            // Add more fields as needed

                            eventList.add(event);
                        }
                        eventAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch products", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong while fetching products", Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Failed to fetch products: " + t);
            }
        });
    }





}
