package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent i = getIntent();
        String message = i.getStringExtra("WELCOME");
        if (message != null) {
            ((TextView) findViewById(R.id.welcome)).setText("Hello " + message + "!");
        } else {
            ((TextView) findViewById(R.id.welcome)).setText("Null");
        }
    }

    public void add(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}