package com.example.ThienNguyen.repository;

import com.example.ThienNguyen.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung,Integer> {
    boolean existsByHoTen(String hoTen);
    boolean existsByEmail(String email);

    Optional<NguoiDung> findByEmail(String email);

}
