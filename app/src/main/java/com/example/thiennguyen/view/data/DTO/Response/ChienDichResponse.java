package com.example.thiennguyen.view.data.DTO.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class ChienDichResponse {
    Integer idCd;

    String tenCd;
    String moTa;

    String hinhAnh;

    BigDecimal soTienMucTieu;

    BigDecimal soTienHienTai;

    Date ngayBatDau;

    Date ngayKetThuc;

    String trangThai;

    String tinhThanh;

    String quanHuyen;

    String diaChi;

    Integer idNguoiToChuc;

    Integer idDanhMuc;

    public Integer getIdCd() {
        return idCd;
    }

    public String getTenCd() {
        return tenCd;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public BigDecimal getSoTienMucTieu() {
        return soTienMucTieu;
    }

    public BigDecimal getSoTienHienTai() {
        return soTienHienTai;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getTinhThanh() {
        return tinhThanh;
    }

    public String getQuanHuyen() {
        return quanHuyen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public Integer getIdNguoiToChuc() {
        return idNguoiToChuc;
    }

    public Integer getIdDanhMuc() {
        return idDanhMuc;
    }

    @Override
    public String toString() {
        return "ChienDichResponse{" +
                "idCd=" + idCd +
                ", tenCd='" + tenCd + '\'' +
                ", moTa='" + moTa + '\'' +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", soTienMucTieu=" + soTienMucTieu +
                ", soTienHienTai=" + soTienHienTai +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", trangThai='" + trangThai + '\'' +
                ", tinhThanh='" + tinhThanh + '\'' +
                ", quanHuyen='" + quanHuyen + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", idNguoiToChuc=" + idNguoiToChuc +
                ", idDanhMuc=" + idDanhMuc +
                '}';
    }
}
