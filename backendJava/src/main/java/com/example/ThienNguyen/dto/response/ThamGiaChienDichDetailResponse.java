package com.example.ThienNguyen.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThamGiaChienDichDetailResponse {
    private Integer idThamGia;
    private Integer idChienDich;
    private String tenChienDich;
    private Integer idNguoiDung;
    private String vaiTro;
    private String trangThai;
    private LocalDateTime ngayDangKy;
}

