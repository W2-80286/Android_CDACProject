package com.sunbeaminfo.app.Activity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sunbeaminfo.app.Fragments.DetailsFragment;
import com.sunbeaminfo.app.Fragments.EventFragment;
import com.sunbeaminfo.app.R;
import com.sunbeaminfo.app.api.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookEventActivity extends AppCompatActivity {

    private Spinner spinnerEvent, spinnerVenue;
    private EditText editDate, editGuestCount;
    private ImageView imageCal;
    private Button btnBookEvent;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    // Placeholder lists for events and venues (replace with actual data from the database)
    private List<String> eventList = new ArrayList<>();
    private List<String> venueList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_event);

        spinnerEvent = findViewById(R.id.spinnerEvent);
        spinnerVenue = findViewById(R.id.spinnerVenue);
        editDate = findViewById(R.id.editDate);
        editGuestCount = findViewById(R.id.editGuestCount);
//        imageCal = findViewById(R.id.imageCal);
        btnBookEvent = findViewById(R.id.btnBookEvent);

        // Fetch events and venues from the database
        fetchEventsFromDatabase();
        fetchVenuesFromDatabase();

        // Populate spinner with events
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventList);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEvent.setAdapter(eventAdapter);

        // Populate spinner with venues
        ArrayAdapter<String> venueAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, venueList);
        venueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVenue.setAdapter(venueAdapter);

        // Set onClickListener for calendar icon
        imageCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Handle book event button click
        btnBookEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedEvent = spinnerEvent.getSelectedItem().toString();
                String selectedVenue = spinnerVenue.getSelectedItem().toString();
                String date = editDate.getText().toString();
                String guestCount = editGuestCount.getText().toString();

                // Validate input
                if (selectedEvent.isEmpty() || selectedVenue.isEmpty() || date.isEmpty() || guestCount.isEmpty()) {
                    Toast.makeText(BookEventActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Replace current fragment with DetailsFragment
                DetailsFragment detailsFragment = new DetailsFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("event", selectedEvent);
//                bundle.putString("venue", selectedVenue);
//                bundle.putString("date", date);
//                bundle.putString("guestCount", guestCount);
//                detailsFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailsFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        // Initialize dateSetListener
        initDateSetListener();
    }

    // Placeholder method to simulate fetching events from the database
    private void fetchEventsFromDatabase() {
        eventList.clear(); // Clear existing list
        eventList.add("Event 1");
        eventList.add("Event 2");
            };



    // Placeholder method to simulate fetching venues from the database
    private void fetchVenuesFromDatabase() {
        venueList.clear(); // Clear existing list
        venueList.add("Venue 1");
        venueList.add("Venue 2");
        //venuesList.add("Venue 3");
        // Add more venues as needed
    }

    // Method to show date picker dialog
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    // Method to initialize dateSetListener
    private void initDateSetListener() {
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                editDate.setText(date);
            }
        };
    }
}
