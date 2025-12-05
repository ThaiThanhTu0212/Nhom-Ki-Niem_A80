package com.example.ThienNguyen.dto.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NguoiDungRequest {
    String hoTen;

    String email;

    String soDienThoai;

    String matKhau;
    String vaiTro;
    String trangThai;
    String avatar;
}
