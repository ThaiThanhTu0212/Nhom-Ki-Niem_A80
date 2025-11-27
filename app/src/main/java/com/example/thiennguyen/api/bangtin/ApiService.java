package com.example.thiennguyen.api.bangtin;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("api/baiviet")
    Call<List<BaiViet>> getBaiViets();

    @POST("api/baiviet")
    Call<Void> createBaiViet(@Body CreatePostRequest request);
}