package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

public class MainMenuAuth extends AppCompatActivity {

    private TextView emailUserAuth;
    private TextView nameUserAuth;

    private String nameUser = "";
    private String emailUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_auth);

        emailUserAuth = findViewById(R.id.textViewEmailUser);
        nameUserAuth = findViewById(R.id.textViewNameUser);
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String token = prefs.getString("user_token", "default");


        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url("http://192.168.1.43:8000/user/profile/")
                .header("Authorization","Bearer " + token)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(@NonNull okhttp3.Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainMenuAuth.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainMenuAuth.this, body, Toast.LENGTH_LONG).show();
                        Log.d("=======AUTH=======", body);
                        try {
                            // 1. Парсим строку в JSON объект
                            JSONObject jsonObject = new JSONObject(body);

                            // 2. Достаем значение по ключу "token"
                            try {
                                nameUser = jsonObject.getString("name");
                                emailUser = jsonObject.getString("email");
                            }catch (Exception e){

                            }

                            emailUserAuth.setText(emailUser);
                            nameUserAuth.setText(nameUser);



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
            }
        });

    }
}