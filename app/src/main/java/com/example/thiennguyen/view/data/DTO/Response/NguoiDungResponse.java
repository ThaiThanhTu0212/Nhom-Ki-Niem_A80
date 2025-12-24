package com.example.thiennguyen.view.data.DTO.Response;

public class NguoiDungResponse {
    private int idNd;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String vaiTro;     // nguoi_ung_ho, nguoi_van_dong, admin
    private String trangThai;  // mặc định: "hoat_dong"
    private String avatar;

    public NguoiDungResponse() {
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

    @Override
    public String toString() {
        return "NguoiDung{" +
                "idNd=" + idNd +
                ", hoTen='" + hoTen + '\'' +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", vaiTro='" + vaiTro + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
