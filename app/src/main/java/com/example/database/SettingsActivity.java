package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.settings);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        return true;
                    case R.id.inProgress:
                        startActivity(new Intent(getApplicationContext(), InProgressHabits.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_blue_light)));

        sharedPreferences = getApplicationContext().getSharedPreferences("UserDB", MODE_PRIVATE);
        if (sharedPreferences != null) {

            String savedUserName = sharedPreferences.getString("Username", "");
            String savedUuid = sharedPreferences.getString("uuid", "");

            ((TextView) findViewById(R.id.full_name)).setText(savedUserName);
            String[] names = savedUserName.split(" ");
            ((TextView) findViewById(R.id.full_name_preview)).setText(savedUserName);
            ((TextView) findViewById(R.id.profile_first_name)).setText(names[0]);
            ((TextView) findViewById(R.id.profile_last_name)).setText(names[1]);
        }


    }
}