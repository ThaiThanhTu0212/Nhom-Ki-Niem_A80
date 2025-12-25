package com.example.thiennguyen.api.bangtin;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/api/baiviet")
    Call<List<BaiViet>> getBaiViets();

    @Multipart
    @POST("/api/baiviet")
    Call<CreatePostResponse> createBaiViet(
            @PartMap Map<String, RequestBody> params,
            @Part MultipartBody.Part file
    );

    @POST("/api/baiviet/{id}/like")
    Call<LikeCommentResponse> likePost(@Path("id") int postId);

    @GET("/api/baiviet/{id}/comments")
    Call<List<CommentDto>> getComments(@Path("id") int postId);

    @POST("/api/baiviet/{id}/comments")
    Call<LikeCommentResponse> postComment(@Path("id") int postId, @Body CommentRequest commentRequest);

    @DELETE("/api/baiviet/{id}")
    Call<DeletePostResponse> deletePost(@Path("id") int postId);

    // THÊM PHƯƠNG THỨC NÀY VÀO
    @POST("/api/donations")
    Call<DonationResponse> postDonation(@Body DonationRequest donationRequest);
}
