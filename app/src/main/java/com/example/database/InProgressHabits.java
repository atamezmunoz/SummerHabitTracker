package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class InProgressHabits extends AppCompatActivity {

    DatabaseHandler mDatabaseHandler;
    ListView habitListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_progress_habitss);


        mDatabaseHandler = new DatabaseHandler(this);
        habitListView = findViewById(R.id.habitList);

        populateListView();

    }

    private void populateListView() {
        Intent userGUIDIntent = getIntent();
        String userGUID = userGUIDIntent.getStringExtra("userGUID");
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