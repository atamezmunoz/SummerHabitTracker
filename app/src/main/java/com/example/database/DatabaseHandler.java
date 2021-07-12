package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHandler";

    private static final String TABLE_NAME = "habitTable";
    private static final String COL0 = "HabitGuid";
    private static final String COL1 = "UserGuid";
    private static final String COL2 = "HabitName";
    private static final String COL3 = "Frequency";
    private static final String COL4 = "StartDate";
    private static final String COL5 = "EndDate";
    private static final String COL6 = "Reminders";
    private static final String COL7 = "ReminderTime";



    public DatabaseHandler(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +  " (" +
                COL0 + " TEXT, " +
                COL1 + " TEXT, " +
                COL2 + " TEXT, " +
                COL3 + " TEXT, " +
                COL4 + " TEXT, " +
                COL5 + " TEXT, " +
                COL6 + " BOOLEAN," +
                COL7 + " TEXT );";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item, String frequency, boolean reminders, String startDate, String endDate, String reminderTime, String userGUID){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, userGUID);
        contentValues.put(COL2, item);
        contentValues.put(COL3, frequency);
        contentValues.put(COL4, startDate);
        contentValues.put(COL5, endDate);
        contentValues.put(COL6, reminders);
        contentValues.put(COL7, reminderTime);

        Log.d(TAG, "addData: Adding " + item + "to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if inserted incorrectly returns -1

        if(result == -1){
            return false;
        } else {
            return true;
        }


    }
}
