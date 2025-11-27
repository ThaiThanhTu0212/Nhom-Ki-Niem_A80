package com.example.thiennguyen.api.bangtin;

import androidx.annotation.Nullable;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @GET("api/baiviet")
    Call<List<BaiViet>> getBaiViets();

    @Multipart
    @POST("api/baiviet")
    Call<Void> createBaiViet(
            @Part("idNguoiDang") RequestBody idNguoiDang,
            @Part("noiDung") RequestBody noiDung,
            @Part @Nullable MultipartBody.Part file
    );
}
