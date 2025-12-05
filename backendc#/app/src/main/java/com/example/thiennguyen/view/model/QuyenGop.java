package com.example.thiennguyen.view.model;

import java.math.BigDecimal;
import java.security.Timestamp;

public class QuyenGop {
    private int idQg;
    private int idCd;
    private int idNd;
    private BigDecimal soTien;
    private Timestamp ngayQuyenGop;
    private String loiNhan;

    public QuyenGop() {}

    public QuyenGop(int idQg, int idCd, int idNd, BigDecimal soTien, Timestamp ngayQuyenGop, String loiNhan) {
        this.idQg = idQg;
        this.idCd = idCd;
        this.idNd = idNd;
        this.soTien = soTien;
        this.ngayQuyenGop = ngayQuyenGop;
        this.loiNhan = loiNhan;
    }

    // Getters và Setters
    public int getIdQg() { return idQg; }
    public void setIdQg(int idQg) { this.idQg = idQg; }

    public int getIdCd() { return idCd; }
    public void setIdCd(int idCd) { this.idCd = idCd; }

    public int getIdNd() { return idNd; }
    public void setIdNd(int idNd) { this.idNd = idNd; }

    public BigDecimal getSoTien() { return soTien; }
    public void setSoTien(BigDecimal soTien) { this.soTien = soTien; }

    public Timestamp getNgayQuyenGop() { return ngayQuyenGop; }
    public void setNgayQuyenGop(Timestamp ngayQuyenGop) { this.ngayQuyenGop = ngayQuyenGop; }

    public String getLoiNhan() { return loiNhan; }
    public void setLoiNhan(String loiNhan) { this.loiNhan = loiNhan; }
}
