    package com.sunbeaminfo.app.Fragments;

    import android.os.Bundle;

    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Toast;

    import com.google.gson.JsonArray;
    import com.google.gson.JsonElement;
    import com.google.gson.JsonObject;
    import com.sunbeaminfo.app.Entity.Venue;
    import com.sunbeaminfo.app.R;

    import com.sunbeaminfo.app.adapter.VenueAdapter;
    import com.sunbeaminfo.app.api.RetrofitClient;

    import java.util.ArrayList;
    import java.util.List;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;


    public class VenueFragment extends Fragment {

        RecyclerView recyclerView;
        VenueAdapter venueAdapter;
        List<Venue> venueList;

        public VenueFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_event, container, false);

            recyclerView = view.findViewById(R.id.recyclerView);
            venueList = new ArrayList<>();
            venueAdapter = new VenueAdapter(getActivity(), venueList);
            recyclerView.setAdapter(venueAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            // Call the API to fetch events
            getAllVenue();

            return view;
        }

        private void getAllVenue() {
            RetrofitClient.getInstance().getApi().getAllVenues().enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.has("status") && jsonObject.get("status").getAsString().equals("success")) {
                            JsonArray dataArray = jsonObject.getAsJsonArray("data");
                            venueList.clear();
                            for (JsonElement element : dataArray) {
                                JsonObject dataObject = element.getAsJsonObject();
                                Venue venue = new Venue();
                                 venue.setId(dataObject.get("id").getAsInt());
                                venue.setName(dataObject.get("name").getAsString());
                                venue.setCapacity(dataObject.get("capacity").getAsString());
                                venue.setPrice(dataObject.get("price").getAsString());
                                venue.setLocation(dataObject.get("location").getAsString());
                                venue.setImage(dataObject.get("image").getAsString());
                                // Set other fields as needed
                                  Log.e("venue" , venue.toString());
                                venueList.add(venue);
                            }
                            // Assuming venueList and eventAdapter are class variables
                            venueAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Failed to fetch venues", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch venues", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(getContext(), "Something went wrong while fetching venues", Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Failed to fetch venues: " + t.getMessage());
                }
            });
        }






    }