package com.example.thiennguyen.view.data.api;

import com.example.thiennguyen.view.data.DTO.ApiResponse;
import com.example.thiennguyen.view.data.DTO.Response.AuthenticationResponse;
import com.example.thiennguyen.view.data.DTO.request.AuthenticationRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi {
    @POST("auth/token")
    Call<ApiResponse<AuthenticationResponse>> login(@Body AuthenticationRequest request);
}
