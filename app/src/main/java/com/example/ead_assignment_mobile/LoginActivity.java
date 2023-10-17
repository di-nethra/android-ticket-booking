package com.example.ead_assignment_mobile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity {
    private EditText editTextNIC;
    private EditText editTextPassword;
    private Button buttonLogin;

    public static String nic;
    public static String firstName;
    public static String lastName;
    public static String role;
    public static String accountStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextNIC = findViewById(R.id.editTextNIC);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        TextView textViewSignUp = findViewById(R.id.textViewSignUp);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registrationIntent);
            }
        });
    }

    private void login() {
        System.out.println("inside  login");
        String nicInput = editTextNIC.getText().toString();
        String passwordInput = editTextPassword.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.31:5227/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        LoginRequest loginRequest = new LoginRequest(nicInput, passwordInput);

        Call<User> call = apiService.login(loginRequest);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                System.out.println("Response Body: " + new Gson().toJson(response.body()));

                System.out.println("response "+response.code());
                if (response.isSuccessful()) {
                    System.out.println("inside if suc");
                    User user = response.body();
                    System.out.println("nic "+user.getNic());
                    String jsonResponse = new Gson().toJson(user);
                    System.out.println("API Response"+ jsonResponse);
                    if (user != null) {

                        nic = user.getNic();
                        firstName = user.getFirstName();
                        lastName = user.getLastName();
                        role = user.getRole();
                        accountStatus = user.getAccountStatus();
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent createIntentLogin = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(createIntentLogin);
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login Failed Try Again", Toast.LENGTH_SHORT).show();
                    System.out.println("Unsuccessful response: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
