package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BinhLuan implements Serializable {
    @SerializedName("id")
    public int id;

    @SerializedName("idNguoiBinhLuan")
    public int idNguoiBinhLuan;

    @SerializedName("noiDung")
    public String noiDung;

    @SerializedName("ngayBinhLuan")
    public String ngayBinhLuan;
}
