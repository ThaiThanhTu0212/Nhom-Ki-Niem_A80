package com.example.ThienNguyen.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NguoiDungUpdateRequest {
    String hoTen;

    String email;

    String soDienThoai;

    String matKhau;
    String vaiTro;
    String trangThai;
    String avatar;

}
