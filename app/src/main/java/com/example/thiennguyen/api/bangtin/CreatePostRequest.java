package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

// Lớp này dùng để tạo body cho request JSON khi đăng bài không có ảnh
public class CreatePostRequest {

    @SerializedName("idNguoiDang")
    private int idNguoiDang;

    @SerializedName("noiDung")
    private String noiDung;

    public CreatePostRequest(int idNguoiDang, String noiDung) {
        this.idNguoiDang = idNguoiDang;
        this.noiDung = noiDung;
    }

    // Getters and setters (không bắt buộc với Gson nhưng là good practice)
    public int getIdNguoiDang() {
        return idNguoiDang;
    }

    public void setIdNguoiDang(int idNguoiDang) {
        this.idNguoiDang = idNguoiDang;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
