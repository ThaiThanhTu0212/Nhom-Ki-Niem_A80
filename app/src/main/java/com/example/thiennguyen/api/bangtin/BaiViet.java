package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

public class BaiViet {

    @SerializedName("id")
    public int id;

    @SerializedName("idNguoiDang")
    public Integer idNguoiDang;

    @SerializedName("idChienDich")
    public Integer idChienDich;

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

    // Các trường được thêm vào để khớp với API
    @SerializedName("showDonationBar")
    public boolean showDonationBar;

    @SerializedName("donationProgress")
    public int donationProgress;

    @SerializedName("donatorsCount")
    public int donatorsCount;

    @SerializedName("daysLeft")
    public int daysLeft;
}
