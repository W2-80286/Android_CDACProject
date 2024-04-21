    package com.sunbeaminfo.app.Fragments;

    import android.content.Context;
    import android.content.SharedPreferences;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.google.gson.JsonArray;
    import com.google.gson.JsonObject;
    import com.sunbeaminfo.app.R;
    import com.sunbeaminfo.app.api.RetrofitClient;
    import com.sunbeaminfo.app.utility.EventConstants;


    import java.io.Console;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;


    public class ProfileFragment extends Fragment {

        TextView textFirstName,textLastName,textEmail,textMobile;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_profile, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            textFirstName = view.findViewById(R.id.textFirstName);
            textLastName = view.findViewById(R.id.textLastName);
            textEmail = view.findViewById(R.id.textEmail);
            textMobile = view.findViewById(R.id.textMobile);
            getProfile();
        }

        private void getProfile() {
    //        Log.e("callprofile", "getProfile");
    //        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences(EventConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    //        String userToken = sharedPreferences.getString(EventConstants.USER_TOKEN, null);
    //        if (userToken != null) {
    //            Log.e("usertoken in profile",userToken );
    //        } else {
    //            Log.e("usertoken in profile","not found" );
    //            // Value does not exist or was not found
    //            // Handle this case accordingly
    //        }

            int userid= getContext().getSharedPreferences(EventConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
                    .getInt(EventConstants.USER_ID,0);
            Log.e("userid", ""+userid);
            RetrofitClient.getInstance().getApi().getUser(userid).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.body().get("status").getAsString().equals("success")){
                        JsonObject jsonObject  = response.body().getAsJsonObject("data");
    //                    JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                        textFirstName.setText(jsonObject.get("firstName").getAsString());
                        textLastName.setText(jsonObject.get("lastName").getAsString());
                        textEmail.setText(jsonObject.get("email").getAsString());
                        textMobile.setText(jsonObject.get("phone").getAsString());
                        Log.e("profilee", textFirstName+""+textEmail+""+textMobile);

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(getContext(), "something went wrong while getting the user", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }