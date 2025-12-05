package com.example.ThienNguyen.dto.response;

import com.example.ThienNguyen.entity.DanhMuc;
import com.example.ThienNguyen.entity.NguoiDung;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

//    NguoiDung nguoiToChuc;
//
//    DanhMuc danhMuc;
}
