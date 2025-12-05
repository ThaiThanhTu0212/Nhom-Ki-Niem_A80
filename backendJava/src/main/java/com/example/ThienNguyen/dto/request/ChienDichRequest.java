package com.example.ThienNguyen.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChienDichRequest {
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

    Integer idDanhMuc;
}
