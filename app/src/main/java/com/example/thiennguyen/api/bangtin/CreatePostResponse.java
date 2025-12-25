package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

public class CreatePostResponse {
    @SerializedName("message")
    public String message;

    @SerializedName("idBaiViet")
    public int idBaiViet;

    // Thêm trường này để nhận ID chiến dịch từ backend
    @SerializedName("idChienDich")
    public Integer idChienDich;
}
