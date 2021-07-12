package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringJoiner;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    DatabaseHandler mDatabaseHandler;


    EditText startDateEdit;
    EditText endDateEdit;
    EditText reminderTimeEdit;
    EditText nameOfHabitEdit;
    int timeHour, timeMinutes;
    CheckBox sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    Button addHabitButton;
    ToggleButton tgbtn;


    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startDateEdit = findViewById(R.id.startDateEdit);
        endDateEdit = findViewById(R.id.endDateEdit);
        reminderTimeEdit = findViewById(R.id.reminderTimeEdit);
        nameOfHabitEdit = findViewById(R.id.nameOfHabitEdit);
        addHabitButton = findViewById(R.id.button);
        sunday = findViewById(R.id.sunday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        tgbtn = findViewById(R.id.tgbtn1);
        mDatabaseHandler = new DatabaseHandler(this);



        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        startDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                month = month + 1;
                                String date = day + "/" + month + "/" + year;
                                startDateEdit.setText(date);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        endDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                month = month + 1;
                                String date = day + "/" + month + "/" + year;
                                endDateEdit.setText(date);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        reminderTimeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ": " + minute;
                        reminderTimeEdit.setText(time);
                    }
                }, 12, 0, false);
                timePickerDialog.updateTime(timeHour, timeMinutes);
                timePickerDialog.show();
            }
        });

        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userGUID = UUID.randomUUID().toString();
                String habitName = nameOfHabitEdit.getText().toString();
                String frequencyString = String.join(",", getFrequency());
                boolean reminders = tgbtn.isChecked();
                String startDate = startDateEdit.getText().toString();
                String endDate = endDateEdit.getText().toString();
                String reminderTime = reminderTimeEdit.getText().toString();
                addData(habitName, frequencyString, reminders, startDate, endDate, reminderTime, userGUID);


            }
        });

    }

    private void addData(String habitName, String frequency, boolean reminders, String startDate, String endDate, String reminderTime, String userGUID){
        boolean insertData = mDatabaseHandler.addData(habitName, frequency, reminders, startDate, endDate, reminderTime, userGUID);
        if(insertData){
            toastMessage("Data Successfully Inserted");
        } else {
            toastMessage("Something went wrong...");
        }
    }

    private ArrayList<String> getFrequency(){
        ArrayList<String> frequency = new ArrayList<>();
        if(sunday.isChecked()){
            frequency.add("Sunday");
        }
        if(monday.isChecked()){
            frequency.add("Monday");
        }
        if(tuesday.isChecked()){
            frequency.add("Tuesday");
        }
        if(wednesday.isChecked()){
            frequency.add("Wednesday");
        }
        if(thursday.isChecked()){
            frequency.add("Thursday");
        }
        if(friday.isChecked()){
            frequency.add("Friday");
        }
        if(saturday.isChecked()){
            frequency.add("Saturday");
        }
        return frequency;
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}