package com.example.ThienNguyen.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "[NguoiDung]")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nd")
    Integer idNd;

    @Column(name = "ho_ten", length = 100,unique = true)
    String hoTen;

    @Column(name = "email", length = 100, unique = true)
    String email;

    @Column(name = "so_dien_thoai", length = 20)
    String soDienThoai;

    @Column(name = "mat_khau", length = 100)
    String matKhau;

    @Column(name = "vai_tro", length = 20)
    String vaiTro;

    @Column(name = "trang_thai", length = 20)
    String trangThai;
    String avatar;

    @OneToMany(mappedBy = "nguoiToChuc",cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // tránh vòng lặp khi in ra
    private List<ChienDich> chienDiches;

}
