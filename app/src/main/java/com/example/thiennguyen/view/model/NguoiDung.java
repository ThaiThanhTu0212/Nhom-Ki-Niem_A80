package com.example.thiennguyen.view.model;

import java.io.Serializable;

public class NguoiDung implements Serializable {
    private int idNd;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String userIdTag; // Ví dụ: @phat123
    private String vaiTro; // vai trò người dùng
    private String trangThai; // trạng thái hoạt động
    private String matKhau;

    // Đường dẫn ảnh (URL hoặc URI)
    private String avatarUrl;
    private String bannerUrl;

    // Thống kê
    private int soNguoiTheoDoi;
    private int soBaiViet;
    private String tongTienUngHo;
    private int soChienDichThamGia;
    private int soLuotHoTro;

    public NguoiDung() { }

    public NguoiDung(int idNd, String hoTen, String email, String soDienThoai, String userIdTag) {
        this.idNd = idNd;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.userIdTag = userIdTag;

        // Dữ liệu mặc định
        this.soNguoiTheoDoi = 0;
        this.soBaiViet = 0;
        this.tongTienUngHo = "0 đ";
        this.soChienDichThamGia = 0;
        this.soLuotHoTro = 0;
    }

    // Constructor mới với 8 tham số
    public NguoiDung(int idNd, String hoTen, String email, String soDienThoai, String matKhau, String vaiTro, String trangThai, String avatarUrl) {
        this.idNd = idNd;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
        this.avatarUrl = avatarUrl;
    }

    public int getIdNd() {
        return idNd;
    }

    // --- Getters & Setters ---
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getUserIdTag() { return userIdTag; }
    public void setUserIdTag(String userIdTag) { this.userIdTag = userIdTag; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getBannerUrl() { return bannerUrl; }
    public void setBannerUrl(String bannerUrl) { this.bannerUrl = bannerUrl; }

    // Helpers hiển thị
    public String getDisplayFollowers() { return String.valueOf(soNguoiTheoDoi); }
    public String getDisplayPosts() { return String.valueOf(soBaiViet); }
    public String getDisplayCampaigns() { return String.valueOf(soChienDichThamGia); }
    public String getDisplaySupport() { return String.valueOf(soLuotHoTro); }
    public String getTongTienUngHo() { return tongTienUngHo; }
}
