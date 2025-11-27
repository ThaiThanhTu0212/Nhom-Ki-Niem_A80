package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

public class BaiViet {
    @SerializedName("idBv")
    public int idBv;

    @SerializedName("idNguoiDang")
    public int idNguoiDang;

    @SerializedName("noiDung")
    public String noiDung;

    @SerializedName("hinhAnh")
    public String hinhAnh;

    // QUAN TRỌNG: đổi từ String → Object để Gson không lỗi khi parse DateTime
    @SerializedName("ngayDang")
    public Object ngayDang;  // Dùng Object hoặc String đều được, nhưng Object an toàn hơn
}