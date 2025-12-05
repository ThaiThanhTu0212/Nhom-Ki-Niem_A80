package com.example.ThienNguyen.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChienDichUpdateRequest {
    String tenCd;
    String moTa;

    String hinhAnh;

    BigDecimal soTienMucTieu;

    BigDecimal soTienHienTai;

    LocalDate ngayBatDau;

    LocalDate ngayKetThuc;

    String trangThai;

    String tinhThanh;

    String quanHuyen;

    String diaChi;

    Integer idDanhMuc;
}
