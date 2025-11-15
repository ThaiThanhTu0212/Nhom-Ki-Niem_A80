package com.example.thiennguyen.view.model;

import java.util.Date;

public class BaiViet {
    private int idBaiViet;
    private int idNguoiDang;
    private Integer idChienDich; // có thể null
    private String noiDung;
    private String hinhAnh;
    private Date ngayDang;

    public BaiViet() {
    }

    public BaiViet(int idBaiViet, int idNguoiDang, Integer idChienDich, String noiDung, String hinhAnh, Date ngayDang) {
        this.idBaiViet = idBaiViet;
        this.idNguoiDang = idNguoiDang;
        this.idChienDich = idChienDich;
        this.noiDung = noiDung;
        this.hinhAnh = hinhAnh;
        this.ngayDang = ngayDang;
    }

    public int getIdBaiViet() {
        return idBaiViet;
    }

    public void setIdBaiViet(int idBaiViet) {
        this.idBaiViet = idBaiViet;
    }

    public int getIdNguoiDang() {
        return idNguoiDang;
    }

    public void setIdNguoiDang(int idNguoiDang) {
        this.idNguoiDang = idNguoiDang;
    }

    public Integer getIdChienDich() {
        return idChienDich;
    }

    public void setIdChienDich(Integer idChienDich) {
        this.idChienDich = idChienDich;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public Date getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(Date ngayDang) {
        this.ngayDang = ngayDang;
    }
}
