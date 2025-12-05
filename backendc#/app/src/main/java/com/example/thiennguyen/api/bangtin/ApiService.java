package com.example.thiennguyen.api.bangtin;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    // SỬA LỖI 404: Quay lại đường dẫn "baiviet" (số ít) đã hoạt động
    @GET("api/baiviet")
    Call<List<BaiViet>> getBaiViets();

    @POST("api/baiviet")
    Call<Void> createBaiViet(@Body CreatePostRequest request);

    @Multipart
    @POST("api/baiviet")
    Call<Void> createBaiViet(
            @Part("idNguoiDang") RequestBody idNguoiDang,
            @Part("noiDung") RequestBody noiDung,
            @Part MultipartBody.Part file
    );

    @DELETE("api/baiviet/{id}")
    Call<Void> deleteBaiViet(@Path("id") int postId);
}
