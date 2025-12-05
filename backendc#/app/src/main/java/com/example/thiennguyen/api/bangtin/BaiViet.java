package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

public class BaiViet {
    // SỬA LỖI 404: Đổi tên thuộc tính để khớp với JSON từ server
    @SerializedName("idBv")
    public int id;

    @SerializedName("idNguoiDang")
    public int idNguoiDang;

    @SerializedName("noiDung")
    public String noiDung;

    @SerializedName("hinhAnh")
    public String hinhAnh;

    @SerializedName("ngayDang")
    public String ngayDang;
}
