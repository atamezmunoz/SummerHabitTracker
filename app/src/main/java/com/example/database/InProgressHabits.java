package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import android.app.AlarmManager;
import android.app.PendingIntent;
=======
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
>>>>>>> f277f1d8df06d094b3524ae6a7874d80c17562a8
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InProgressHabits extends AppCompatActivity {

    DatabaseHandler mDatabaseHandler;
    ListView habitListView;
    SharedPreferences sharedPreferences;
    ArrayList<Intent> intents;

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
        completeHabit();



        setAlarm();

    }

    private void setAlarm(){

        ArrayList<Habit> dailyHabits = dailyHabit();
        int notificationId = 1;

        for (Habit habit: dailyHabits) {

            //Set notificationId & message
            Intent intent = new Intent(InProgressHabits.this, AlarmReceiver.class);
            intent.putExtra("notificationId", notificationId);
            intent.putExtra("message", "You have a habit to keep: " + habit.getHabitName());
            intents = new ArrayList<>();
            intents.add(intent);
            notificationId++;

            //PendingIntent
            PendingIntent alarmIntent = PendingIntent.getBroadcast(
                    InProgressHabits.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
            );

            // AlarmManager
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


            // Set Alarm
            String[] time = habit.reminderTime.split(":");
            int hour = Integer.parseInt(time[0].trim());
            int minute = Integer.parseInt(time[1].trim());
            System.out.println("hour: " + hour + "minute: " + minute);

            // Create time
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, hour);
            startTime.set(Calendar.MINUTE, minute);
            startTime.set(Calendar.SECOND, 0);
            long alarmSetTime = startTime.getTimeInMillis();

            // Set Alarm
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmSetTime, alarmIntent);


        }
    }

    private String getUserGUID() {
        // Retrieve data from shared preferences
        String savedUserName = "";
        String userGUID = "";
        sharedPreferences = getApplicationContext().getSharedPreferences("UserDB", MODE_PRIVATE);
        if (sharedPreferences != null) {
            savedUserName = sharedPreferences.getString("Username", "");
            userGUID = sharedPreferences.getString("uuid", "");
        }
        return userGUID;
    }

    private void populateListView() {

        String userGUID = getUserGUID();
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
                String selectedItem = (String) parent.getItemAtPosition(position);
                String userGUID = "56017b6c-1fcb-484d-b3f2-a2e0b38c0bfa";
                //sharedPreferences = getApplicationContext().getSharedPreferences("UserDB", MODE_PRIVATE);
                //if (sharedPreferences != null) {
                  //  userGUID = sharedPreferences.getString("uuid", "");
                //}
                mDatabaseHandler.updateCompleted(userGUID, selectedItem);
                Toast.makeText(InProgressHabits.this, "Habit Completed", Toast.LENGTH_SHORT).show();
            }
        });
    }

<<<<<<< HEAD
    private ArrayList<Habit> getHabits(String userGUID){
        //store all habitGUIDs in an arraylist
        Cursor data = mDatabaseHandler.getHabitList("habitTable",userGUID);
        System.out.println(data);
        ArrayList<Habit> habitsList = new ArrayList<>();
        while (data.moveToNext()){
            String habitGUID = data.getString(0);
            String habitName = data.getString(2);
            String frequency = data.getString(3);
            String startDate = data.getString(4);
            String endDate = data.getString(5);
            int num_reminders = Integer.parseInt(data.getString(6));
            boolean reminders = false;
            if (num_reminders == 1) {
                reminders = true;
            }
            String reminderTime = data.getString(7);

            Habit newHabit = new Habit(habitGUID, habitName,frequency, startDate,
                    endDate, reminders,reminderTime);
            habitsList.add(newHabit);
        }
        return  habitsList;
    }

    private ArrayList<Habit> dailyHabit(){

        String uuid = getUserGUID();
        ArrayList<Habit> habits = getHabits(uuid);
//        for (Habit h: habits) {
//            System.out.println(h.toString());
//        }

        Streak streak = new Streak(habits);
        ArrayList<Habit> dailyList = streak.createDailyHabitsList();
        System.out.println("line 142" + dailyList.toString());
        return dailyList;
    }

    public ArrayList<Intent> getIntents(){
        return intents;
    }

    //private void updateCompleted(){
        //Cursor data = mDatabaseHandler.
    //}
=======

>>>>>>> f277f1d8df06d094b3524ae6a7874d80c17562a8

}
