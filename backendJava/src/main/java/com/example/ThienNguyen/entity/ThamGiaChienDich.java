package com.example.ThienNguyen.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "tham_gia_chien_dich",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_cd", "id_nd"})
        }
)
public class ThamGiaChienDich {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tg")
    private Integer idTg;

    // ===== Quan hệ tới Chiến Dịch =====
    @ManyToOne
    @JoinColumn(name = "id_cd", nullable = false)
    private ChienDich chienDich;

    // ===== Quan hệ tới Người Dùng =====
    @ManyToOne
    @JoinColumn(name = "id_nd", nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "vai_tro", length = 50)
    private String vaiTro;

    @Column(name = "trang_thai", length = 20)
    private String trangThai = "cho_duyet";

    @Column(name = "ngay_dang_ky")
    private LocalDateTime ngayDangKy;

    // ===== Tự động set ngày đăng ký =====
    @PrePersist
    protected void onCreate() {
        this.ngayDangKy = LocalDateTime.now();
    }

    // ===== Constructor =====
    public ThamGiaChienDich() {
    }

    // ===== Getter & Setter =====
    public Integer getIdTg() {
        return idTg;
    }

    public void setIdTg(Integer idTg) {
        this.idTg = idTg;
    }

    public ChienDich getChienDich() {
        return chienDich;
    }

    public void setChienDich(ChienDich chienDich) {
        this.chienDich = chienDich;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
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
        return ngayDangKy;
    }

    public void setNgayDangKy(LocalDateTime ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }
}
