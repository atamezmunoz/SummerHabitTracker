package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class InProgressHabits extends AppCompatActivity {

    DatabaseHandler mDatabaseHandler;
    ListView habitListView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_progress_habitss);

        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set InProgress Selected
        bottomNavigationView.setSelectedItemId(R.id.inProgress);

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
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.inProgress:
                        return true;
                }
                return false;
            }
        });



        mDatabaseHandler = new DatabaseHandler(this);
        habitListView = findViewById(R.id.habitList);

        populateListView();

    }

    private void populateListView() {

        // Retrieve data from shared preferences
        String savedUserName = "";
        String userGUID = "";
        sharedPreferences = getApplicationContext().getSharedPreferences("UserDB", MODE_PRIVATE);
        if (sharedPreferences != null) {
            savedUserName = sharedPreferences.getString("Username", "");
            userGUID = sharedPreferences.getString("uuid", "");
        }
//        Intent userGUIDIntent = getIntent();
//        String userGUID = userGUIDIntent.getStringExtra("userGUID");



        Cursor data = mDatabaseHandler.getUserGUID("habitTable", userGUID );
        ArrayList<String> habitList = new ArrayList<>();
        while (data.moveToNext()){
            String habit = data.getString(2);
            habitList.add(habit);
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, habitList);
        habitListView.setAdapter(adapter);
    }

}