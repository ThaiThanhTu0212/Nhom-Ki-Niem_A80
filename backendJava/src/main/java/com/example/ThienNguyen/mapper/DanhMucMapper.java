package com.example.ThienNguyen.mapper;

import com.example.ThienNguyen.dto.request.DanhMucRequest;
import com.example.ThienNguyen.dto.response.DanhMucResponse;
import com.example.ThienNguyen.entity.DanhMuc;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DanhMucMapper  {
    DanhMuc toDanhMuc(DanhMucRequest request);
    DanhMucResponse toDanhMucResponse(DanhMuc danhMuc);
}
