package com.example.ThienNguyen.mapper;

import com.example.ThienNguyen.dto.request.NguoiDungRequest;
import com.example.ThienNguyen.dto.request.NguoiDungUpdateRequest;
import com.example.ThienNguyen.dto.response.NguoiDungResponse;
import com.example.ThienNguyen.entity.NguoiDung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NguoiDungMapper {
    NguoiDungResponse toNguoiDungResponse(NguoiDung nguoiDung);
    NguoiDung toNguoiDung(NguoiDungRequest request);
    
    @Mapping(target = "matKhau", ignore = true)
    @Mapping(target = "idNd", ignore = true)
    void updateNguoiDung(@MappingTarget NguoiDung nguoiDung, NguoiDungUpdateRequest request);


}
