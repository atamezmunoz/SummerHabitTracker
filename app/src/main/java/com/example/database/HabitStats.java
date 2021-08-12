package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

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

        successRateView = findViewById(R.id.success_dsc);
        successRateDataView = findViewById(R.id.success_data);
        streakView = findViewById(R.id.streak_dsc);
        streakDataView = findViewById(R.id.streak_data);
        daysCompletedView = findViewById(R.id.days_desc);
        daysCompletedDataView = findViewById(R.id.days_label);

        Intent habitNameIntent = getIntent();
        habitName = habitNameIntent.getStringExtra("habitName");
        Intent userGUIDIntent = getIntent();
        userGUID = userGUIDIntent.getStringExtra("userGuid");

        habitNameView.setText(habitName);
        setDaysCompletedDataView();
        setSuccessRateDataView();
        setStreakDataView();

        markAsCompletedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    markAsCompleted();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_orange_dark)));
    }

    public void markAsCompleted() throws ParseException {
        mDatabaseHandler.updateCompleted(userGUID, habitName);
        mDatabaseHandler.updateSuccessRate(userGUID, habitName);
        mDatabaseHandler.updateStreak(userGUID, habitName);
        Toast.makeText(HabitStats.this, "Habit Completed", Toast.LENGTH_LONG).show();
        finish();
        startActivity(getIntent());


    }

    public void setDaysCompletedDataView(){
        daysCompletedDataView.setText(String.valueOf(mDatabaseHandler.getCompletedDays(userGUID, habitName)));
    }

    public void setSuccessRateDataView(){
        successRateDataView.setText(String.valueOf(mDatabaseHandler.getSuccessRate(userGUID, habitName)));
    }

    public void setStreakDataView(){
        streakDataView.setText(String.valueOf(mDatabaseHandler.getStreak(userGUID, habitName)));
    }



}