package com.example.ThienNguyen.repository;

import com.example.ThienNguyen.entity.ChienDich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChienDichRepository extends JpaRepository<ChienDich,Integer> {
    List<ChienDich> getAllByDanhMuc_IdDm(Integer idDm);
}
