package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

public class CommentRequest {

    @SerializedName("IdNguoiBinhLuan")
    private int idNguoiBinhLuan;

    @SerializedName("NoiDung")
    private String noiDung;

    public CommentRequest(int idNguoiBinhLuan, String noiDung) {
        this.idNguoiBinhLuan = idNguoiBinhLuan;
        this.noiDung = noiDung;
    }
}
