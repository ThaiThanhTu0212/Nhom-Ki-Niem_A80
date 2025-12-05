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
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dm")
    Integer idDm;

    @Column(name = "ten_dm", length = 100)
    String tenDm;

    @OneToMany(mappedBy = "danhMuc",cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // tránh vòng lặp khi in ra
    private List<ChienDich> chienDiches;
}
