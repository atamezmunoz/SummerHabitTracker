package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AccountSelection extends AppCompatActivity {

    private ListView listView;

    DatabaseHandler mDatabaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_selection);


        listView = findViewById(R.id.listView);
        mDatabaseHandler = new DatabaseHandler(this);

        populateListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<String> userGUIDList = getGUIDs();
                Intent sendUserGUID = new Intent(AccountSelection.this, InProgressHabits.class);
                sendUserGUID.putExtra("userGUID", userGUIDList.get(i));
                startActivity(sendUserGUID);



            }
        });
    }

    private void populateListView() {
        //get data, append to list

        Cursor data = mDatabaseHandler.getData("userTable");
        ArrayList<String> accountList = new ArrayList<>();
        while (data.moveToNext()){
            String fullName = data.getString(0) + " " + data.getString(1);
            accountList.add(fullName);
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accountList);
        listView.setAdapter(adapter);

    }

    private ArrayList<String> getGUIDs(){
        //store all userGUIDs in an arraylist
        Cursor data = mDatabaseHandler.getData("userTable");
        ArrayList<String> userGUIDList = new ArrayList<>();
        while (data.moveToNext()){
            String userGUID = data.getString(2);
            userGUIDList.add(userGUID);
        }
        return  userGUIDList;
    }


 }
