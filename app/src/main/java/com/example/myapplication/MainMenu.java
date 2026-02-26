package com.example.myapplication;

import static okhttp3.RequestBody.create;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url("http://localhost:8000/user/profile")
                .get()
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(@NonNull okhttp3.Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainMenu.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainMenu.this, body, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("http://localhost:8000/user/profile")
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Toast.makeText(MainMenu.this, "Hoti shtoto", Toast.LENGTH_LONG).show();
//                try (ResponseBody responseBody = response.body()) {
//
//                    if (!response.isSuccessful()) {
//                        throw new IOException("Запрос к серверу не был успешен: " +
//                                response.code() + " " + response.message());
//                    }
//
//                    // пример получения всех заголовков ответа
//                    Headers responseHeaders = response.headers();
//                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
//                        // вывод заголовков
//                        Toast.makeText(MainMenu.this, "URAAAA", Toast.LENGTH_LONG).show();
//                    }
//                    // вывод тела ответа
//                    Toast.makeText(MainMenu.this, "Hoti shtoto", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

    }
}