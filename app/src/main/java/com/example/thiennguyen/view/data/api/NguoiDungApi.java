package com.example.thiennguyen.view.data.api;

import com.example.thiennguyen.view.data.DTO.ApiResponse;
import com.example.thiennguyen.view.data.DTO.Response.NguoiDungResponse;
import com.example.thiennguyen.view.data.DTO.request.NguoiDungRequest;
import com.example.thiennguyen.view.data.DTO.request.UpdateProfileRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NguoiDungApi {
    @GET("users/{id}")
    Call<ApiResponse<NguoiDungResponse>> getNguoiDungByIdHome(@Path("id") Integer id);

    @GET("users/myinfo")
    Call<ApiResponse<NguoiDungResponse>> getMyInfo(@Header("Authorization") String token);
    @POST("users")
    Call<ApiResponse<NguoiDungResponse>> createNguoiDung(@Body NguoiDungRequest request);

    @PUT("users/myinfo")
    Call<ApiResponse<Void>> updateMyInfo(@Header("Authorization") String token, @Body UpdateProfileRequest request);
}
