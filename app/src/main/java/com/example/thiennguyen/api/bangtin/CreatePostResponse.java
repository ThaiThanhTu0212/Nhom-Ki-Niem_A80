package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

// DTO để hứng dữ liệu trả về từ server sau khi tạo bài viết thành công
public class CreatePostResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("hinhAnh")
    public String hinhAnh;

    @SerializedName("message")
    public String message;
}
