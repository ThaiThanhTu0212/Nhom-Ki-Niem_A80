package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

// Lớp này dùng để định nghĩa cấu trúc body khi gửi yêu cầu tạo comment mới
public class CommentRequest {

    @SerializedName("idNguoiBinhLuan")
    private int idNguoiBinhLuan;

    @SerializedName("noiDung")
    private String noiDung;

    public CommentRequest(int idNguoiBinhLuan, String noiDung) {
        this.idNguoiBinhLuan = idNguoiBinhLuan;
        this.noiDung = noiDung;
    }
}
