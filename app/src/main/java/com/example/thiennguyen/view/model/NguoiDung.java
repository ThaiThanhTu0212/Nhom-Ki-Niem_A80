package com.example.thiennguyen.view.model;

import java.io.Serializable;

public class NguoiDung implements Serializable {
    private int idNd;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String userIdTag; // Ví dụ: @phat123

    // Đường dẫn ảnh
    private String avatarUrl;
    private String bannerUrl;

    // Các trường cũ (giữ lại để tránh lỗi code cũ)
    private String matKhau;
    private String vaiTro;
    private String trangThai;

    // Thống kê
    private int soNguoiTheoDoi;
    private int soBaiViet;
    private String tongTienUngHo;
    private int soChienDichThamGia;
    private int soLuotHoTro;

    public NguoiDung() { }

    // --- CONSTRUCTOR 1: MỚI (Dùng cho màn hình Tài khoản) ---
    public NguoiDung(int idNd, String hoTen, String email, String soDienThoai, String userIdTag) {
        this.idNd = idNd;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.userIdTag = userIdTag;

        // Mặc định
        this.soNguoiTheoDoi = 0;
        this.soBaiViet = 0;
        this.tongTienUngHo = "0 đ";
        this.soChienDichThamGia = 0;
        this.soLuotHoTro = 0;
    }

    // --- CONSTRUCTOR 2: CŨ (Fix lỗi ChiTietChienDichHomeActivity) ---
    // Đây là cái Activity đang thiếu
    public NguoiDung(int idNd, String hoTen, String email, String soDienThoai, String matKhau, String vaiTro, String trangThai, String avatar) {
        this.idNd = idNd;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;

        // Map dữ liệu cũ sang mới
        this.avatarUrl = avatar;
        this.userIdTag = "@user" + idNd; // Tạo tạm tag nếu code cũ không có

        // Init chỉ số thống kê
        this.soNguoiTheoDoi = 0;
        this.soBaiViet = 0;
        this.tongTienUngHo = "0 đ";
        this.soChienDichThamGia = 0;
        this.soLuotHoTro = 0;
    }

    // --- Getters & Setters ---
    public int getIdNd() { return idNd; }
    public void setIdNd(int idNd) { this.idNd = idNd; }

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

    // Getter cho các trường cũ
    public String getMatKhau() { return matKhau; }
    public String getVaiTro() { return vaiTro; }
    public String getTrangThai() { return trangThai; }

    // Helpers hiển thị
    public String getDisplayFollowers() { return String.valueOf(soNguoiTheoDoi); }
    public String getDisplayPosts() { return String.valueOf(soBaiViet); }
    public String getDisplayCampaigns() { return String.valueOf(soChienDichThamGia); }
    public String getDisplaySupport() { return String.valueOf(soLuotHoTro); }
    public String getTongTienUngHo() { return tongTienUngHo; }
}