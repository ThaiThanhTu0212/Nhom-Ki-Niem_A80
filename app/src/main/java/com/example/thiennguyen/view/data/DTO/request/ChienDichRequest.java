package com.example.thiennguyen.view.data.DTO.request;

import java.math.BigDecimal;
import java.util.Date;

public class ChienDichRequest {
    String tenCd;
    String moTa;

    String hinhAnh;

    BigDecimal soTienMucTieu;

    BigDecimal soTienHienTai;

    String ngayBatDau;

    String ngayKetThuc;

    String trangThai;

    String tinhThanh;

    String quanHuyen;

    String diaChi;

    Integer idDanhMuc;

    public ChienDichRequest() {
    }

    public ChienDichRequest(String tenCd, String moTa, String hinhAnh, BigDecimal soTienMucTieu, BigDecimal soTienHienTai, String ngayBatDau, String ngayKetThuc, String trangThai, String tinhThanh, String quanHuyen, String diaChi, Integer idDanhMuc) {
        this.tenCd = tenCd;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.soTienMucTieu = soTienMucTieu;
        this.soTienHienTai = soTienHienTai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
        this.tinhThanh = tinhThanh;
        this.quanHuyen = quanHuyen;
        this.diaChi = diaChi;
        this.idDanhMuc = idDanhMuc;
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

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
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

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
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

    public Integer getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(Integer idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }
}
