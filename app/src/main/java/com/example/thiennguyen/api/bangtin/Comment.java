package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

public class Comment {

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
