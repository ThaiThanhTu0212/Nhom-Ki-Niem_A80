package com.example.thiennguyen.api.bangtin;

public class CreatePostRequest {
    public int idNguoiDang;
    public String noiDung;
    public boolean isCallingForDonation; // THÊM TRƯỜNG MỚI

    // Constructor cũ cho trường hợp không có ảnh
    public CreatePostRequest(int idNguoiDang, String noiDung, boolean isCallingForDonation) {
        this.idNguoiDang = idNguoiDang;
        this.noiDung = noiDung;
        this.isCallingForDonation = isCallingForDonation;
    }
}
