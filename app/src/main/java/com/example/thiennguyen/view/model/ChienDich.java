package com.example.thiennguyen.view.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ChienDich implements Serializable {
    private int idCd;
    private String tenCd;
    private String moTa;
    private BigDecimal soTienMucTieu;
    private BigDecimal soTienHienTai;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String trangThai;
    private String tinhThanh;
    private String quanHuyen;
    private String diaChi;
    private String hinhAnh;
    private NguoiDung nguoiToChuc; // khóa ngoại id_nguoi_to_chuc
    private DanhMuc danhMuc;       // khóa ngoại id_danh_muc

    public ChienDich(int idCd, String tenCd, String moTa, BigDecimal soTienMucTieu, BigDecimal soTienHienTai, Date ngayBatDau, Date ngayKetThuc, String trangThai, String tinhThanh, String quanHuyen, String diaChi, NguoiDung nguoiToChuc, DanhMuc danhMuc) {
        this.idCd = idCd;
        this.tenCd = tenCd;
        this.moTa = moTa;
        this.soTienMucTieu = soTienMucTieu;
        this.soTienHienTai = soTienHienTai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
        this.tinhThanh = tinhThanh;
        this.quanHuyen = quanHuyen;
        this.diaChi = diaChi;
        this.nguoiToChuc = nguoiToChuc;
        this.danhMuc = danhMuc;
    }

    public ChienDich(int idCd, String tenCd, String moTa, BigDecimal soTienMucTieu, BigDecimal soTienHienTai, Date ngayBatDau, Date ngayKetThuc, String trangThai, String tinhThanh, String quanHuyen, String diaChi, String hinhAnh, NguoiDung nguoiToChuc, DanhMuc danhMuc) {
        this.idCd = idCd;
        this.tenCd = tenCd;
        this.moTa = moTa;
        this.soTienMucTieu = soTienMucTieu;
        this.soTienHienTai = soTienHienTai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
        this.tinhThanh = tinhThanh;
        this.quanHuyen = quanHuyen;
        this.diaChi = diaChi;
        this.hinhAnh = hinhAnh;
        this.nguoiToChuc = nguoiToChuc;
        this.danhMuc = danhMuc;
    }

    public int getIdCd() {
        return idCd;
    }

    public void setIdCd(int idCd) {
        this.idCd = idCd;
    }

    public String getTenCd() {
        return tenCd;
    }

    public void setTenCd(String tenCd) {
        this.tenCd = tenCd;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public BigDecimal getSoTienMucTieu() {
        return soTienMucTieu;
    }

    public void setSoTienMucTieu(BigDecimal soTienMucTieu) {
        this.soTienMucTieu = soTienMucTieu;
    }

    public BigDecimal getSoTienHienTai() {
        return soTienHienTai;
    }

    public void setSoTienHienTai(BigDecimal soTienHienTai) {
        this.soTienHienTai = soTienHienTai;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTinhThanh() {
        return tinhThanh;
    }

    public void setTinhThanh(String tinhThanh) {
        this.tinhThanh = tinhThanh;
    }

    public String getQuanHuyen() {
        return quanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public NguoiDung getNguoiToChuc() {
        return nguoiToChuc;
    }

    public void setNguoiToChuc(NguoiDung nguoiToChuc) {
        this.nguoiToChuc = nguoiToChuc;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }
}