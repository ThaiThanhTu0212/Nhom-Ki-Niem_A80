package com.example.ThienNguyen.mapper;

import com.example.ThienNguyen.dto.request.ThamGiaChienDichRequest;
import com.example.ThienNguyen.dto.response.ThamGiaChienDichDetailResponse;
import com.example.ThienNguyen.dto.response.ThamGiaChienDichResponse;
import com.example.ThienNguyen.entity.ThamGiaChienDich;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ThamGiaChienDichMapper {
    ThamGiaChienDich toThamGiaChienDich(ThamGiaChienDichRequest request);
    @Mapping(source = "chienDich.idCd", target = "idChienDich")
    @Mapping(source = "nguoiDung.idNd", target = "idNguoiDung")
    @Mapping(source = "idTg", target = "idThamGia")
    ThamGiaChienDichResponse toThamGiaChienDichResponse(ThamGiaChienDich request);
    
    @Mapping(source = "chienDich.idCd", target = "idChienDich")
    @Mapping(source = "chienDich.tenCd", target = "tenChienDich")
    @Mapping(source = "nguoiDung.idNd", target = "idNguoiDung")
    @Mapping(source = "idTg", target = "idThamGia")
    ThamGiaChienDichDetailResponse toThamGiaChienDichDetailResponse(ThamGiaChienDich request);
}
