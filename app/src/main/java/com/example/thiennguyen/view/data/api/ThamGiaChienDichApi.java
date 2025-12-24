package com.example.thiennguyen.view.data.api;

import com.example.thiennguyen.view.data.DTO.ApiResponse;
import com.example.thiennguyen.view.data.DTO.Response.ChienDichResponse;
import com.example.thiennguyen.view.data.DTO.Response.ThamGiaChienDichResponse;
import com.example.thiennguyen.view.data.DTO.request.ChienDichRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ThamGiaChienDichApi {
    @POST("thamgia/{id}")
    Call<ApiResponse<ThamGiaChienDichResponse>> createThamGiaChienDich(@Path("id") Integer id,
                                                             @Header("Authorization") String token);


}
