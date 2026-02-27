package com.example.myapplication;

import static okhttp3.RequestBody.create;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

    private String tokenUserVinogradov = "";
    EditText emailEditText;
    EditText nameEditText;
    EditText lastNameEditText;
    EditText surnameEditText;
    EditText dateEditText;
    EditText genderEditText;
    EditText passwordEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        nameEditText = findViewById(R.id.editTextTextName);
        lastNameEditText = findViewById(R.id.editTextTextLastName);
        surnameEditText = findViewById(R.id.editTextTextSurname);
        dateEditText = findViewById(R.id.editTextDate);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        genderEditText = findViewById(R.id.editTextTextGender);

    }
    public void callPostReg(View v){

            String email = emailEditText.getText().toString().trim();
            String name = nameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String surname = surnameEditText.getText().toString();
            String date = dateEditText.getText().toString();
            String pass = passwordEditText.getText().toString();
            String gender = genderEditText.getText().toString();


            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, "{\n" +
                    "  \"name\": \""+ name +"\",\n" +
                    "  \"last_name\": \""+ lastName +"\",\n" +
                    "  \"surname\": \""+ surname +"\",\n" +
                    "  \"date_of_birth\": \""+ date +"\",\n" +
                    "  \"sex\": \""+ gender +"\",\n" +
                    "  \"email\": \""+ email + "\",\n" +
                    "  \"password\": \""+ pass +"\"\n" +
                    "}");
            Request request = new Request.Builder()
                    .url("http://192.168.1.43:8000/user/reg/")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new okhttp3.Callback() {

                @Override
                public void onFailure(@NonNull okhttp3.Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainMenu.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MainMenu.this, body, Toast.LENGTH_LONG).show();
                            tokenUserVinogradov = body;
                            System.out.println(body);
                        }
                    });
                }
            });

    }
}