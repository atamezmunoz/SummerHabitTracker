package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HabitStats extends AppCompatActivity {

    DatabaseHandler mDatabaseHandler;

    Button markAsCompletedBtn;

    TextView habitNameView;

    TextView successRateView;
    TextView successRateDataView;
    TextView streakView;
    TextView streakDataView;
    TextView daysCompletedView;
    TextView daysCompletedDataView;

    String habitName;
    String userGUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_stats);

        mDatabaseHandler = new DatabaseHandler(this);

        markAsCompletedBtn = (Button) findViewById(R.id.markAsCompletedBtn);

        habitNameView = (TextView) findViewById(R.id.habitNameView);

        successRateView = findViewById(R.id.successRateView);
        successRateDataView = findViewById(R.id.successRateDataView);
        streakView = findViewById(R.id.streakView);
        streakDataView = findViewById(R.id.streakDataView);
        daysCompletedView = findViewById(R.id.daysCompletedView);
        daysCompletedDataView = findViewById(R.id.daysCompletedDataView);

        successRateView.setText("Success Rate");
        streakView.setText("Best Streak");
        daysCompletedView.setText("Days Completed");


        markAsCompletedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAsCompleted();
            }
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_orange_dark)));
    }

    public void markAsCompleted(){
        Intent habitNameIntent = getIntent();
        habitName = habitNameIntent.getStringExtra("habitName");
        System.out.println("Habit name"+ habitName);
        Intent userGUIDIntent = getIntent();
        userGUID = userGUIDIntent.getStringExtra("userGuid");
        System.out.println(userGUID);
        mDatabaseHandler.updateCompleted(userGUID, habitName);
        Toast.makeText(HabitStats.this, "Habit Completed", Toast.LENGTH_LONG).show();


    }

}