package com.example.ead_assignment_mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Button deactivateButton = findViewById(R.id.deactivateButton);
        deactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deactivateAccount();
            }
        });
        loadUserProfile();
    }

    private void loadUserProfile() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.31:5227/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        String userNic = LoginActivity.nic;
        System.out.println(userNic);
        Call<UserProfile> call = apiService.getUserProfile(userNic);

        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    UserProfile userProfile = response.body();

                    TextView nicTextView = findViewById(R.id.nicTextView);
                    nicTextView.setText(userProfile.getNic());

                    TextView firstNameTextView = findViewById(R.id.firstNameTextView);
                    firstNameTextView.setText(userProfile.getFirstName());

                    TextView lastNameTextView = findViewById(R.id.lastNameTextView);
                    lastNameTextView.setText(userProfile.getLastName());

                    TextView roleTextView = findViewById(R.id.roleTextView);
                    roleTextView.setText(userProfile.getRole());

                    TextView accountStatusTextView = findViewById(R.id.accountStatusTextView);
                    accountStatusTextView.setText(userProfile.getAccountStatus());
                } else {
                    // Handle unsuccessful response, e.g., show an error message
                    Toast.makeText(UserProfileActivity.this, "Failed to load user profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deactivateAccount() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.31:5227/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        String userNic = LoginActivity.nic;
        Call<Void> call = apiService.deactivateAccount(userNic);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserProfileActivity.this, "Account Deactivated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserProfileActivity.this, "Failed to deactivate account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
