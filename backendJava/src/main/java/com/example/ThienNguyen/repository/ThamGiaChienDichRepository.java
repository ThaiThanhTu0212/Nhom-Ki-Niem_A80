package com.example.ThienNguyen.repository;

import com.example.ThienNguyen.entity.NguoiDung;
import com.example.ThienNguyen.entity.ThamGiaChienDich;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThamGiaChienDichRepository extends JpaRepository<ThamGiaChienDich,Integer> {
    List<ThamGiaChienDich> findByNguoiDung(NguoiDung nguoiDung);
    
    @Query("SELECT tg FROM ThamGiaChienDich tg JOIN FETCH tg.chienDich WHERE tg.nguoiDung = :nguoiDung")
    List<ThamGiaChienDich> findByNguoiDungWithChienDich(@Param("nguoiDung") NguoiDung nguoiDung);
}
