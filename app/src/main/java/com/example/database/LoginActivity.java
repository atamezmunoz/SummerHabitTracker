package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    Button createAccountButton;
    Button existingAccountButton;

    DatabaseHandler mDatabaseHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabaseHandler = new DatabaseHandler(this);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        createAccountButton = findViewById(R.id.createAccountButton);
        existingAccountButton = findViewById(R.id.existingAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        existingAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccounts();
            }
        });

    }

    public void createUser(){
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        String userGUID = UUID.randomUUID().toString();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("userGUID", userGUID);
        startActivity(intent);
        addUserData(first, last, userGUID);

        User newUser = new User(first, last, userGUID);
        SharedPreferences sharedPreferences =  getApplicationContext().getSharedPreferences("UserDB", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear().apply();
        // Store the user info
        editor.putString("Username", newUser.getName());
        editor.putString("uuid", userGUID);

        // Commits the changes and add them to the file
        editor.apply();


    }

    public void showAccounts(){
        Intent accountSelection = new Intent(LoginActivity.this, AccountSelection.class);
        startActivity(accountSelection);

    }

    public void addUserData(String first, String last, String userGUID){
        boolean insertData = mDatabaseHandler.addUserData(first, last, userGUID);
        if(insertData){
            toastMessage("Data Successfully Inserted");
        } else {
            toastMessage("Something went wrong...");
        }
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT);
    }

}

