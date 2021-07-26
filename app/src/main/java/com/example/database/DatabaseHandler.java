package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HabitApp";

    //table names
    private static final String TABLE_HABIT = "habitTable";
    private static final String TABLE_USER = "userTable";


    //userTable Columns
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String USER_GUID = "UserGuid";

    //habitSettingsTable columns
    private static final String COL0 = "HabitGuid";
    private static final String COL1 = "UserGuid";
    private static final String COL2 = "HabitName";
    private static final String COL3 = "Frequency";
    private static final String COL4 = "StartDate";
    private static final String COL5 = "EndDate";
    private static final String COL6 = "Reminders";
    private static final String COL7 = "ReminderTime";
    public static final String SUCCESS = "SuccessRate";
    public static final String STREAK = "Streak";
    public static final String COMPLETED = "DaysCompleted";



    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userTable = "CREATE TABLE " + TABLE_USER +  " (" +
                FIRST_NAME + " TEXT, " +
                LAST_NAME + " TEXT, " +
                USER_GUID + " TEXT );";

        String habitTable = "CREATE TABLE " + TABLE_HABIT +  " (" +
                COL0 + " TEXT, " +
                COL1 + " TEXT, " +
                COL2 + " TEXT, " +
                COL3 + " TEXT, " +
                COL4 + " TEXT, " +
                COL5 + " TEXT, " +
                COL6 + " BOOLEAN," +
                COL7 + " TEXT," +
                STREAK + " INTEGER," +
                COMPLETED + " INTEGER," +
                SUCCESS + " INTEGER );";


        db.execSQL(habitTable);
        db.execSQL(userTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABIT);
        onCreate(db);
    }

    public boolean addHabitData(String item, String frequency, boolean reminders, String startDate, String endDate, String reminderTime, String userGUID, String habitGUID){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL0, habitGUID);
        contentValues.put(COL1, userGUID);
        contentValues.put(COL2, item);
        contentValues.put(COL3, frequency);
        contentValues.put(COL4, startDate);
        contentValues.put(COL5, endDate);
        contentValues.put(COL6, reminders);
        contentValues.put(COL7, reminderTime);

        Log.d(TABLE_HABIT, "addData: Adding " + item + "to " + TABLE_HABIT);

        long result = db.insert(TABLE_HABIT, null, contentValues);

        //if inserted incorrectly returns -1

        if(result == -1){
            return false;
        } else {
            return true;
        }


    }

    public boolean addUserData(String first, String last, String userGUID){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, first);
        contentValues.put(LAST_NAME, last);
        contentValues.put(USER_GUID, userGUID);

        Log.d(DATABASE_NAME, "addData: Adding " + first + last + "to " + TABLE_USER);

        long result = db.insert(TABLE_USER, null, contentValues);

        //if inserted incorrectly returns -1

        if(result == -1){
            return false;
        } else {
            return true;
        }


    }
    public Cursor getData(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + tableName;
        Cursor data = db.rawQuery(query, null);

        return data;

    }

    public Cursor getUserGUID(String tableName, String userGUID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + tableName + " WHERE UserGuid = ?";



        Cursor data = db.rawQuery(query, new String[] {userGUID} );

        return data;

    }

    public Cursor getHabitGUID(String tableName, String habitGUID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + tableName + " WHERE HabitGuid = ?";



        Cursor data = db.rawQuery(query, new String[] {habitGUID} );

        return data;
    }






}
