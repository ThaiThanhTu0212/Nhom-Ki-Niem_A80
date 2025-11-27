package com.example.thiennguyen.api.bangtin;

public class CreatePostRequest {
    public int idNguoiDang;
    public String noiDung;

    public CreatePostRequest(int idNguoiDang, String noiDung) {
        this.idNguoiDang = idNguoiDang;
        this.noiDung = noiDung;
    }
}