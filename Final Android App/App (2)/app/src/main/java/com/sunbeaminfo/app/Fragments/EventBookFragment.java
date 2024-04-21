package com.sunbeaminfo.app.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sunbeaminfo.app.Entity.Event;
import com.sunbeaminfo.app.Entity.Venue;
import com.sunbeaminfo.app.R;
import com.sunbeaminfo.app.api.API;
import com.sunbeaminfo.app.api.RetrofitClient;
import com.sunbeaminfo.app.utility.EventConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventBookFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private Spinner spinnerEvent;
    private Spinner spinnerVenue;
    private EditText editDate;
    private EditText editGuestCount;
    private Button btnBookEvent;
    private TextView totalPriceTextView;

    private API api;

    private Event selectedEvent;
    private Venue selectedVenue;
    private int attendeePrice = 200;
    int totalPrice = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerEvent = view.findViewById(R.id.spinnerEvent);
        spinnerVenue = view.findViewById(R.id.spinnerVenue);
        editDate = view.findViewById(R.id.editDate);
        editGuestCount = view.findViewById(R.id.editGuestCount);
        btnBookEvent = view.findViewById(R.id.btnBookEvent);
        totalPriceTextView = view.findViewById(R.id.textTotalPriceValue);

        api = RetrofitClient.getInstance().getApi();

        loadEvents();
        loadVenues();

        spinnerEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEvent = (Event) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedEvent = null;
            }
        });

        spinnerVenue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVenue = (Venue) parent.getItemAtPosition(position);
                Log.d("EventBookFragment", "calculateTotalPrice() called from spinnerVenue");
              int a=   calculateTotalPrice(); // Update total price when venue is selected

                totalPriceTextView.setText(String.valueOf(a));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedVenue = null;
            }
        });


        btnBookEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookEvent();
            }
        });

        // Set click listener for the editDate field to show DatePickerDialog
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void loadEvents() {
        Call<JsonObject> call = api.getAllEvents();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("data")) {
                        JsonArray eventData = jsonObject.getAsJsonArray("data");
                        List<Event> events = parseEventJson(eventData);
                        ArrayAdapter<Event> adapter = new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_spinner_item, events);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerEvent.setAdapter(adapter);
                    } else {
                        Toast.makeText(requireContext(), "Failed to load events", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(requireContext(), "Error loading events", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Event> parseEventJson(JsonArray eventData) {
        List<Event> events = new ArrayList<>();
        Gson gson = new Gson();
        for (JsonElement element : eventData) {
            Event event = gson.fromJson(element, Event.class);
            events.add(event);
        }
        return events;
    }

    private void loadVenues() {
        Call<JsonObject> call = api.getAllVenues();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("data")) {
                        JsonArray venueData = jsonObject.getAsJsonArray("data");
                        List<Venue> venues = parseVenueJson(venueData);
                        ArrayAdapter<Venue> adapter = new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_spinner_item, venues);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerVenue.setAdapter(adapter);
                    } else {
                        Toast.makeText(requireContext(), "Failed to load venues", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load venues", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(requireContext(), "Error loading venues", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Venue> parseVenueJson(JsonArray venueData) {
        List<Venue> venues = new ArrayList<>();
        Gson gson = new Gson();
        for (JsonElement element : venueData) {
            Venue venue = gson.fromJson(element, Venue.class);
            venues.add(venue);
        }
        return venues;
    }

    private int calculateTotalPrice() {
        Log.d("EventBookFragment", "calculate tel lga kt");


        if (selectedVenue != null && !editGuestCount.getText().toString().isEmpty()) {
            int attendees = Integer.parseInt(editGuestCount.getText().toString());
            double venuePrice = Double.parseDouble(selectedVenue.getPrice()); // Parse as double
            totalPrice = (int) (venuePrice + (attendees * attendeePrice)); // Convert back to int
            Log.d("EventBookFragment", "ander aa gya "+totalPrice);
        }
        return totalPrice;
    }

    private void showDatePicker() {
        // Get current date to initialize DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog and set its listener
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                // Set the selected date in the editDate EditText field
                editDate.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth);
            }
        }, year, month, dayOfMonth);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Not used in this implementation
    }

    private void bookEvent() {
        if (selectedEvent == null || selectedVenue == null) {
            Toast.makeText(requireContext(), "Please select event and venue", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gather booking details
        String bookingDate = editDate.getText().toString();
        int attendees = Integer.parseInt(editGuestCount.getText().toString());
        int totalPrice = calculateTotalPrice();
        int userid= getContext().getSharedPreferences(EventConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                .getInt(EventConstants.USER_ID,0);
        // Prepare booking request
        JsonObject order = new JsonObject();
        order.addProperty("eventId", selectedEvent.getId());
        order.addProperty("venueId", selectedVenue.getId());
        order.addProperty("attendees", attendees);
        order.addProperty("bookingDate", bookingDate);
        order.addProperty("totalPrice", totalPrice);
        order.addProperty("user_id", userid);

        Log.e("Order" , order.toString());

        RetrofitClient.getInstance().getApi().bookEvent(order).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Booking successful!", Toast.LENGTH_SHORT).show();
                    // Reset form
                    editDate.setText("");
                    editGuestCount.setText("");
                    // You might also want to reset spinners to default selection
                } else {
                    Toast.makeText(requireContext(), "Failed to book event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(requireContext(), "Error booking event", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
