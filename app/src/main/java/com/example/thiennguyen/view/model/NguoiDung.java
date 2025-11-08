package com.example.thiennguyen.view.model;

public class NguoiDung {
    private int idNd;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String matKhau;
    private String vaiTro;     // nguoi_ung_ho, nguoi_van_dong, admin
    private String trangThai;  // mặc định: "hoat_dong"
    private String avatar;

    public NguoiDung() {
    }

    public NguoiDung(int idNd, String hoTen, String email, String soDienThoai, String matKhau, String vaiTro, String trangThai, String avatar) {
        this.idNd = idNd;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIdNd() {
        return idNd;
    }

    public void setIdNd(int idNd) {
        this.idNd = idNd;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
