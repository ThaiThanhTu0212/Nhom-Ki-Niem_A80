package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

// Lớp này chỉ dùng để nhận dữ liệu JSON từ API
public class CommentDto {

    @SerializedName("id")
    public int id;

    @SerializedName("idNguoiBinhLuan")
    public int idNguoiBinhLuan;

    @SerializedName("tenNguoiBinhLuan")
    public String tenNguoiBinhLuan;

    @SerializedName("noiDung")
    public String noiDung;

    @SerializedName("ngayBinhLuan")
    public String ngayBinhLuan;
}
