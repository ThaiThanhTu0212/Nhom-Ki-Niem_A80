package com.example.thiennguyen.view.model;

import java.io.Serializable;

/**
 * Đây là lớp Model đại diện cho một giao dịch trong lịch sử.
 * Backend sau này sẽ trả về một danh sách các đối tượng tương tự như thế này.
 */
public class GiaoDich implements Serializable {
    private String tenChienDich;
    private String ngayGiaoDich;
    private String soTien;
    private boolean isThanhCong;

    public GiaoDich(String tenChienDich, String ngayGiaoDich, String soTien, boolean isThanhCong) {
        this.tenChienDich = tenChienDich;
        this.ngayGiaoDich = ngayGiaoDich;
        this.soTien = soTien;
        this.isThanhCong = isThanhCong;
    }

    public String getTenChienDich() {
        return tenChienDich;
    }

    public String getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public String getSoTien() {
        return soTien;
    }

    public boolean isThanhCong() {
        return isThanhCong;
    }
}
