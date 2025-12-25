package com.example.ThienNguyen.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateThamGiaChienDichRequest {
    private Integer idThamGia;
    private String trangThai;
}
