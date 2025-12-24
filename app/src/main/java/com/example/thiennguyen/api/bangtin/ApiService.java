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
    @GET("api/baiviet")
    Call<List<BaiViet>> getBaiViets();

    @POST("api/baiviet")
    Call<CreatePostResponse> createBaiViet(@Body CreatePostRequest request);

    @Multipart
    @POST("api/baiviet")
    Call<CreatePostResponse> createBaiViet(
            @Part("IdNguoiDang") RequestBody idNguoiDang,
            @Part("NoiDung") RequestBody noiDung,
            @Part MultipartBody.Part file
    );

    @DELETE("api/baiviet/{id}")
    Call<Void> deleteBaiViet(@Path("id") int postId);

    @POST("api/baiviet/{id}/like")
    Call<LikeCommentResponse> likePost(@Path("id") int postId);

    // --- TÍNH NĂNG ỦNG HỘ MỚI ---
    @POST("api/donations")
    Call<Void> postDonation(@Body DonationRequest donationRequest);

    // --- TÍNH NĂNG BÌNH LUẬN ---
    @GET("api/baiviet/{id}/comments")
    Call<List<BinhLuan>> getCommentsForPost(@Path("id") int postId);

    @POST("api/baiviet/{id}/comments")
    Call<BinhLuan> addCommentToPost(@Path("id") int postId, @Body CommentRequest commentRequest);
}
