package com.example.ThienNguyen.mapper;

import com.example.ThienNguyen.dto.request.ChienDichRequest;
import com.example.ThienNguyen.dto.request.ChienDichUpdateRequest;
import com.example.ThienNguyen.dto.response.ChienDichResponse;
import com.example.ThienNguyen.entity.ChienDich;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChienDichMapper {

    ChienDich toChienDich(ChienDichRequest request);
    @Mapping(source = "nguoiToChuc.idNd",target = "idNguoiToChuc")
    @Mapping(source = "danhMuc.idDm",target = "idDanhMuc")
    ChienDichResponse toChienDichResponse(ChienDich chienDich);

    List<ChienDichResponse> toChienDichResponseList(List<ChienDich> chienDichList);

    @Mapping(target = "idCd",ignore = true)
    @Mapping(target = "nguoiToChuc",ignore = true)
    @Mapping(target = "danhMuc",ignore = true)
    void updateChienDich(@MappingTarget ChienDich chienDich, ChienDichUpdateRequest request);
}

