package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void homepage(View v) {
        TextView tView = findViewById(R.id.UserName);
        String userName = tView.getText().toString();
        System.out.println(userName);
//        Log.d("info", userName);
//        ((TextView)findViewById(R.id.output)).setText(userName);

        //launch a new activity
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("WELCOME", userName);
        startActivity(i);
    }
}