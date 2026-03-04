package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LogInActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
       // Toast.makeText(LogInActivity.this,getUsername(), Toast.LENGTH_LONG);
    }
    public String getUsername(Context context) {
        String tokenUser = sharedPref.getString("user_token", "noone");
        return tokenUser;
    }

    public void onClick(View v){
        Intent intent = new Intent(LogInActivity.this, MainMenu.class);
        startActivity(intent);
    }
    public void onClickRegistered(View w){

        Intent intent = new Intent(LogInActivity.this, MainMenuAuth.class);
        startActivity(intent);
    }
}