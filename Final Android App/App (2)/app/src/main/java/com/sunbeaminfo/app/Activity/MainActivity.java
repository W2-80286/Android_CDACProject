package com.sunbeaminfo.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.sunbeaminfo.app.Activity.LoginActivity;
import com.sunbeaminfo.app.Fragments.DetailsFragment;
import com.sunbeaminfo.app.Fragments.EventBookFragment;
import com.sunbeaminfo.app.Fragments.EventFragment;
import com.sunbeaminfo.app.Fragments.ProfileFragment;
import com.sunbeaminfo.app.Fragments.VenueFragment;
import com.sunbeaminfo.app.R;
import com.sunbeaminfo.app.utility.EventConstants;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageButton buttonDrawer;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=findViewById(R.id.drawer_layout);
        buttonDrawer = findViewById(R.id.buttonDrawerToggle);
        navigationView = findViewById(R.id.navigationView);

        buttonDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new EventFragment())
                            .commit();
                } else if (itemId == R.id.nav_event) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new EventFragment())
                            .commit();
                } else if (itemId == R.id.nav_venue) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new VenueFragment())
                            .commit();
                } //else if (itemId == R.id.nav_bookevent || itemId == R.id.nav_details || itemId == R.id.nav_profile) {
                   // Toast.makeText(MainActivity.this, "To be implemented", Toast.LENGTH_SHORT).show();
                else if (itemId == R.id.nav_bookevent) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new EventBookFragment())
                            .commit();
                }
                else if (itemId == R.id.nav_details) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new DetailsFragment())
                            .commit();
                }
                else if (itemId == R.id.nav_profile) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new ProfileFragment())
                            .commit();
                }
                else if (itemId == R.id.nav_logout) {
                    // Perform logout logic here
                    // Clear login status from SharedPreferences
                    getSharedPreferences(EventConstants.SHARED_PREFERENCE_FILE_NAME, MODE_PRIVATE)
                            .edit()
                            .remove(EventConstants.LOGIN_STATUS)
                            .apply();
                    // Redirect to the login activity after logout
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(MainActivity.this, "Logout successful", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
        //Intent intent = new Intent(MainActivity.this,MainActivity.class);
        //startActivity(intent);
        // Attach the EventFragment initially
        Log.e("MainActivity","btnSave Clicked");
//        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
//        startActivity(intent);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new EventFragment())
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
