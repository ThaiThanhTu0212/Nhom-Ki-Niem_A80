package com.example.thiennguyen.view.data.api;

import com.example.thiennguyen.view.data.DTO.ApiResponse;
import com.example.thiennguyen.view.data.DTO.Response.ChienDichResponse;
import com.example.thiennguyen.view.data.DTO.Response.NguoiDungResponse;
import com.example.thiennguyen.view.model.ChienDich;
import com.example.thiennguyen.view.model.DanhMuc;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChienDichApi {
    @GET("campaign")
    Call<ApiResponse<List<ChienDichResponse>>> getAllChienDichResponse();
    @GET("campaign/{id}")
    Call<ApiResponse<ChienDichResponse>> getChienDichResponseById(@Path("id") Integer id);

    @GET("category")
    Call<ApiResponse<List<DanhMuc>>> getAllDanhMuc();

    @GET("users")
    Call<ApiResponse<List<NguoiDungResponse>>> getAllNguoiDung();
}
