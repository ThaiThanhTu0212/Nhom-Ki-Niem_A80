package com.example.thiennguyen.view.model;

import java.io.Serializable; // Thêm Serializable để truyền giữa các Activity

public class NguoiDung implements Serializable {
    private int idNd;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String matKhau; // Trong thực tế không nên truyền mật khẩu qua UI
    private String vaiTro;
    private String trangThai;
    private String avatar;

    // Thêm các trường thống kê cho UI
    private String userIdTag; // @phat123
    private int soNguoiTheoDoi;
    private int soBaiViet;
    private String tongTienUngHo; // Dùng String để dễ format (ví dụ: "1.500.000 đ")
    private int soChienDichThamGia;
    private int soLuotHoTro;

    public NguoiDung() {
    }

    // Constructor đầy đủ
    public NguoiDung(int idNd, String hoTen, String email, String soDienThoai,
                     String userIdTag, int soNguoiTheoDoi, int soBaiViet,
                     String tongTienUngHo, int soChienDichThamGia, int soLuotHoTro) {
        this.idNd = idNd;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.userIdTag = userIdTag;
        this.soNguoiTheoDoi = soNguoiTheoDoi;
        this.soBaiViet = soBaiViet;
        this.tongTienUngHo = tongTienUngHo;
        this.soChienDichThamGia = soChienDichThamGia;
        this.soLuotHoTro = soLuotHoTro;
    }

    // --- Getter & Setter ---
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getUserIdTag() { return userIdTag; }

    public String getEmail() { return email; }
    public String getSoDienThoai() { return soDienThoai; }

    public String getSoNguoiTheoDoi() { return String.valueOf(soNguoiTheoDoi); }
    public String getSoBaiViet() { return String.valueOf(soBaiViet); }

    public String getTongTienUngHo() { return tongTienUngHo; }
    public String getSoChienDichThamGia() { return String.valueOf(soChienDichThamGia); }
    public String getSoLuotHoTro() { return String.valueOf(soLuotHoTro); }
}