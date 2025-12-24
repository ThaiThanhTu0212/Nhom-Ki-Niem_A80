package com.example.thiennguyen.api.bangtin;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    // --- BÀI VIẾT ---
    @GET("/api/baiviet")
    Call<List<BaiViet>> getBaiViets();

    @POST("/api/baiviet/{id}/like")
    Call<LikeCommentResponse> likePost(@Path("id") int postId);

    @Multipart
    @POST("/api/baiviet")
    Call<CreatePostResponse> createBaiViet(
            @Part("IdNguoiDang") RequestBody idNguoiDang,
            @Part("NoiDung") RequestBody noiDung,
            @Part("IsCallingForDonation") RequestBody isCallingForDonation,
            @Part MultipartBody.Part file
    );

    // --- BÌNH LUẬN ---
    @GET("/api/baiviet/{id}/comments")
    Call<List<BinhLuan>> getCommentsForPost(@Path("id") int postId);

    @POST("/api/baiviet/{id}/comments")
    Call<BinhLuan> addCommentToPost(@Path("id") int postId, @Body CommentRequest commentRequest);


    // --- ỦNG HỘ ---
    @POST("/api/donations")
    Call<DonationResponse> postDonation(@Body DonationRequest donationRequest);

}
