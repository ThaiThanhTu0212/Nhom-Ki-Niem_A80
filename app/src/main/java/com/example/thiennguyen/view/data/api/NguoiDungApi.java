package com.example.thiennguyen.view.data.api;

import com.example.thiennguyen.view.data.DTO.ApiResponse;
import com.example.thiennguyen.view.data.DTO.Response.NguoiDungResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface NguoiDungApi {
    @GET("users/{id}")
    Call<ApiResponse<NguoiDungResponse>> getNguoiDungByIdHome(@Path("id") Integer id);

    @GET("users/myinfo")
    Call<ApiResponse<NguoiDungResponse>> getMyInfo(@Header("Authorization") String token);
}
