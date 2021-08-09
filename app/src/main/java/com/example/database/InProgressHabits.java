package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_orange_dark)));
        populateListView();
        completeHabit();




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

        //delete habit
        habitListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {

                final int item = i;


                new AlertDialog.Builder(InProgressHabits.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to delete this habit")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String habit = (String) habitListView.getItemAtPosition(item);
                                habitList.remove(item);
                                mDatabaseHandler.deleteHabit(habit);
                                ((ArrayAdapter<?>) adapter).notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

    }



    private void completeHabit(){
        habitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get habitName from list, and put it as extra in intent
                String habitName = (String) parent.getItemAtPosition(position);
                //retrieve userGUID from AccountList, and create a new intent to send userGUID to HabitStats
                Intent userGUIDIntent = getIntent();
                String userGUID = userGUIDIntent.getStringExtra("userGUID");
                System.out.println(userGUID);

                Intent sendHabitData = new Intent(InProgressHabits.this, HabitStats.class);
                sendHabitData.putExtra("habitName", habitName);
                sendHabitData.putExtra("userGuid", userGUID);

                startActivity(sendHabitData);

//                String selectedItem = (String) parent.getItemAtPosition(position);
//                Intent userGUIDIntent = getIntent();
//                String userGUID = userGUIDIntent.getStringExtra("userGUID");
//                System.out.println(userGUID);
//                mDatabaseHandler.updateCompleted(userGUID, selectedItem);
//                Toast.makeText(InProgressHabits.this, "Habit Completed", Toast.LENGTH_LONG).show();

            }
        });
    }



}
