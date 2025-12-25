package com.example.thiennguyen.view.data.DTO.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ThamGiaChienDichDetailResponse {
    private Integer idThamGia;
    private Integer idChienDich;
    private String tenChienDich;
    private Integer idNguoiDung;
    private String vaiTro;
    private String trangThai;
    private String ngayDangKy; // Tạm thời dùng String để tránh lỗi parsing

    public Integer getIdThamGia() {
        return idThamGia;
    }

    public void setIdThamGia(Integer idThamGia) {
        this.idThamGia = idThamGia;
    }

    public Integer getIdChienDich() {
        return idChienDich;
    }

    public void setIdChienDich(Integer idChienDich) {
        this.idChienDich = idChienDich;
    }

    public String getTenChienDich() {
        return tenChienDich;
    }

    public void setTenChienDich(String tenChienDich) {
        this.tenChienDich = tenChienDich;
    }

    public Integer getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(Integer idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
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

    public LocalDateTime getNgayDangKy() {
        if (ngayDangKy == null || ngayDangKy.isEmpty()) {
            return null;
        }
        try {
            // Parse format: "2025-12-24T22:39:38.907"
            String dateStr = ngayDangKy;
            if (dateStr.contains(".")) {
                dateStr = dateStr.substring(0, dateStr.lastIndexOf("."));
            }
            return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            return null;
        }
    }

    public void setNgayDangKy(String ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }
    
    // Helper method để lấy String trực tiếp
    public String getNgayDangKyString() {
        return ngayDangKy;
    }
}
