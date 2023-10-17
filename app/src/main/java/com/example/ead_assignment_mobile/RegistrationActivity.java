package com.example.ead_assignment_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextNIC, editTextPassword, editTextFirstName, editTextLastName;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextNIC = findViewById(R.id.editTextNIC);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.31:5227/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Register button click listener
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nicNumber = editTextNIC.getText().toString();
                String password = editTextPassword.getText().toString();
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();

                RegistrationData registrationData = new RegistrationData(new Object(),nicNumber, password, firstName, lastName,"Traveller","active");

                Call<RegistrationResponse> call = apiService.registerUser(registrationData);
                call.enqueue(new Callback<RegistrationResponse>() {
                    @Override
                    public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                        if (response.isSuccessful()) {
                            RegistrationResponse registrationResponse = response.body();
                            Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        } else {
                            // Handle other HTTP status codes if needed
                            String errorMessage = "Registration Failed. Error code: " + response.code();
                            if (response.errorBody() != null) {
                                try {
                                    errorMessage += "\n" + response.errorBody().string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            System.out.println(errorMessage);
                            Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent register = new Intent(RegistrationActivity.this,LoginActivity.class);
                            startActivity(register);
                        }

                    }

                    @Override
                    public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                        // Handle the network or other errors here
                        String errorMessage = "Network Error: " + t.getMessage();
                        System.out.println(errorMessage);
                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
