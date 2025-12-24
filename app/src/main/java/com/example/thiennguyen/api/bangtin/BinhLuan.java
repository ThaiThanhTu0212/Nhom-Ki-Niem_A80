package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BinhLuan {

    @SerializedName("id")
    public int id;

    @SerializedName("idBaiViet")
    public int idBaiViet;

    @SerializedName("idNguoiBinhLuan")
    public int idNguoiBinhLuan;

    @SerializedName("noiDung")
    public String noiDung;

    @SerializedName("ngayBinhLuan")
    public Date ngayBinhLuan;
}
