package com.example.ThienNguyen.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ChienDich {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cd")
    Integer idCd;

    @Column(name = "ten_cd", length = 200)
    String tenCd;

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    String moTa;

    @Column(name = "hinh_anh", columnDefinition = "NVARCHAR(MAX)")
    String hinhAnh;

    @Column(name = "so_tien_muc_tieu", precision = 18, scale = 2)
    BigDecimal soTienMucTieu;

    @Column(name = "so_tien_hien_tai", precision = 18, scale = 2)
    BigDecimal soTienHienTai;

    @Column(name = "ngay_bat_dau")
    Date ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    Date ngayKetThuc;

    @Column(name = "trang_thai", length = 20)
    String trangThai;

    @Column(name = "tinh_thanh", length = 100)
    String tinhThanh;

    @Column(name = "quan_huyen", length = 100)
    String quanHuyen;

    @Column(name = "dia_chi", length = 200)
    String diaChi;



    // --- FK: nguoi_dung.id_nd ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_to_chuc", referencedColumnName = "id_nd")
    NguoiDung nguoiToChuc;

    // --- FK: danh_muc.id_dm ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_danh_muc", referencedColumnName = "id_dm")
    DanhMuc danhMuc;
}
