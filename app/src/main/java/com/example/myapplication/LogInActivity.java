package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LogInActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userToken = prefs.getString("user_token", "default");
        Toast.makeText(LogInActivity.this, userToken, Toast.LENGTH_LONG).show();
    }
    public void onClickRegistered(View v){
        email = findViewById(R.id.editTextTextEmailAuth);
        pass = findViewById(R.id.editTextTextPasswordAuth);
        String emailAuth = email.getText().toString().trim();
        String passAuth = pass.getText().toString().trim();

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, " {\"email\": \""+ emailAuth +"\",\n" +
                "  \"password\": \""+ passAuth +"\"}");
        Request request = new Request.Builder()
                .url("http://192.168.1.43:8000/user/enter/")
                .post(body)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(@NonNull okhttp3.Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LogInActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                        System.out.println(String.valueOf(e));
                    }
                });
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                assert response.body() != null;
                String body = response.body().string();
                // Inside your background thread or callback method:
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LogInActivity.this, body, Toast.LENGTH_LONG).show();
                        String responseData = body;
                        try {
                            // 1. Парсим строку в JSON объект
                            JSONObject jsonObject = new JSONObject(responseData);

                            // 2. Достаем значение по ключу "token"
                            final String token = jsonObject.getString("token");
                            saveToken(token);
                            Toast.makeText(LogInActivity.this, token, Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Intent intent = new Intent(LogInActivity.this, MainMenuAuth.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void onClick(View v){
        Intent intent = new Intent(LogInActivity.this, MainMenu.class);
        startActivity(intent);
    }
    public void saveToken(String token) {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_token", token);
        // Также можно: putInt, putBoolean, putFloat, putLong
        editor.apply();
    }
}