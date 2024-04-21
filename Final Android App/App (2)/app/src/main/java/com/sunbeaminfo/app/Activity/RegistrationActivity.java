package com.sunbeaminfo.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sunbeaminfo.app.Entity.User;
import com.sunbeaminfo.app.R;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sunbeaminfo.app.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class RegistrationActivity extends AppCompatActivity {

    EditText editFirstName,editLastName,editEmail,editPassword,editPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editPhone = findViewById(R.id.editPhone);
    }

    public void register(View view) {
        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();

        // Validate user input
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a User object with the registration details
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);

        // Call the API to register the user
        RetrofitClient.getInstance().getApi().registerUser(user).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    // Registration successful
                    Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    finish(); // Close the registration activity
                } else {
                    // Registration failed
                    Toast.makeText(RegistrationActivity.this, "Failed to register. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Registration failed due to network error
                Toast.makeText(RegistrationActivity.this, "Network error. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancel(View view){
        finish();
    }
}