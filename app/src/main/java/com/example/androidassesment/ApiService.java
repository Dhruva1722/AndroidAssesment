package com.example.androidassesment;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/b/6HBE")
    Call<ApiResponse> getApiResponse();
}
