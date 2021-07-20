package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.home);

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
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.inProgress:
                        startActivity(new Intent(getApplicationContext(), InProgressHabits.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        sharedPreferences = getApplicationContext().getSharedPreferences("UserDB", MODE_PRIVATE);
        if (sharedPreferences != null) {

            String savedUserName = sharedPreferences.getString("Username", "");
            String savedUuid = sharedPreferences.getString("uuid", "");

            ((TextView) findViewById(R.id.welcome)).setText("Hello " + savedUserName + "!");

        }

    }

    public void add(View v) {
        Intent redirectToAdd = new Intent(this, MainActivity.class);
        startActivity(redirectToAdd);
    }
}