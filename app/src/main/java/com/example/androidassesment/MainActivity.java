package com.example.androidassesment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;


public class MainActivity extends AppCompatActivity {

    private TextView titleGeneratedTv, descriptionsTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initializing TextViews
        titleGeneratedTv = findViewById(R.id.titleGeneratedTv);
        descriptionsTv = findViewById(R.id.descriptionsTv);

        // Fetching API Data
        fetchDataFromApi();

    }

    private void fetchDataFromApi() {
        //  Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.jsonkeeper.com/")  // Use the correct base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //  instance of the API service
        ApiService apiService = retrofit.create(ApiService.class);

        //  Make the API call
        Call<ApiResponse> call = apiService.getApiResponse();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Extract content as a string
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.getChoices() != null && !apiResponse.getChoices().isEmpty()) {
                        ApiResponse.Choice choice = apiResponse.getChoices().get(0);
                        String contentString = choice.getMessage().getContent();

                        try {
                            // Parse contentString as JSON manually
                            JSONObject contentJson = new JSONObject(contentString);

                            // Get title and description
                            String title = contentJson.getJSONArray("titles").getString(0);
                            String description = contentJson.getString("description");

                            // Update the TextViews
                            titleGeneratedTv.setText(title);
                            descriptionsTv.setText(description);

                            Log.d("Retrofit", "Title: " + title);
                            Log.d("Retrofit", "Description: " + description);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Retrofit", "Parsing error: " + e.getMessage());
                        }
                    }
                } else {
                    Log.e("Retrofit", "Response was not successful");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("Retrofit", "Error: " + t.getMessage());
            }
        });
    }
}
