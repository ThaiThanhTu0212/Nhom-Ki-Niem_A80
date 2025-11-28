package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

public class BaiViet {
    // SỬA LỖI: Đồng bộ lại với server, server trả về "id"
    @SerializedName("id")
    public int id;

    @SerializedName("idNguoiDang")
    public int idNguoiDang;

    @SerializedName("noiDung")
    public String noiDung;

    @SerializedName("hinhAnh")
    public String hinhAnh;

    @SerializedName("ngayDang")
    public String ngayDang;

    @SerializedName("soLuotThich")
    public int soLuotThich;

    @SerializedName("soLuotBinhLuan")
    public int soLuotBinhLuan;
}
