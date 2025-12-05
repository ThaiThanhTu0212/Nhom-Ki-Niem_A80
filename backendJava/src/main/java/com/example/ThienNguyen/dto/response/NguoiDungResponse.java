package com.example.ThienNguyen.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NguoiDungResponse {
    Integer idNd;
    String hoTen;

    String email;

    String soDienThoai;

    String vaiTro;
    String trangThai;
    String avatar;
}
