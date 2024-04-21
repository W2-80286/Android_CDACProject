package com.sunbeaminfo.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sunbeaminfo.app.Entity.User;
import com.sunbeaminfo.app.R;
import com.sunbeaminfo.app.api.RetrofitClient;
import com.sunbeaminfo.app.utility.EventConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    CheckBox checkboxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        checkboxRememberMe = findViewById(R.id.checkboxRememberMe);
        if(getSharedPreferences(EventConstants.SHARED_PREFERENCE_FILE_NAME,MODE_PRIVATE)
                .getBoolean(EventConstants.LOGIN_STATUS,false))
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }

    public void login(View view) {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkboxRememberMe.isChecked()){
            getSharedPreferences(EventConstants.SHARED_PREFERENCE_FILE_NAME,MODE_PRIVATE)
                    .edit()
                    .putBoolean(EventConstants.LOGIN_STATUS,true)
                    .apply();
        }

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("email", email);
        requestBody.addProperty("password", password);

        RetrofitClient.getInstance().getApi().loginUser(requestBody).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject responseBody = response.body();
                    String status = responseBody.get("status").getAsString();
                    if (status.equals("success")) {
                        JsonObject data = responseBody.getAsJsonObject("data");
//                        String token = data.get("token").getAsString();

//                        // Save user token in SharedPreferences
//                        getSharedPreferences(EventConstants.SHARED_PREFERENCE_FILE_NAME, MODE_PRIVATE)
//                                .edit()
//                                .putString(EventConstants.USER_TOKEN, token)
//                                .apply();

                        int id = data.get("userId").getAsInt();
//                        int id = array.get(0).getAsJsonObject().get("user_id").getAsInt();
                        Log.e("eeeeeeee", id+ "gggggggggg");
                        getSharedPreferences(EventConstants.SHARED_PREFERENCE_FILE_NAME,MODE_PRIVATE)
                                .edit()
                                .putInt(EventConstants.USER_ID,id)
                                .apply();


                        // Start MainActivity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // Display specific error message from the server
                        String errorMessage = responseBody.get("message").getAsString();
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response
                    int errorCode = response.code();
                    if (errorCode == 401) {
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Failed to login. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Log error and display error message
                Log.e("LoginActivity", "Error: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Something went wrong at the Login", Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void register(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}
