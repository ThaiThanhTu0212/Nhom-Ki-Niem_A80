package com.example.ThienNguyen.mapper;

import com.example.ThienNguyen.dto.request.DanhMucRequest;
import com.example.ThienNguyen.dto.response.DanhMucResponse;
import com.example.ThienNguyen.entity.DanhMuc;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T07:48:00+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class DanhMucMapperImpl implements DanhMucMapper {

    @Override
    public DanhMuc toDanhMuc(DanhMucRequest request) {
        if ( request == null ) {
            return null;
        }

        DanhMuc.DanhMucBuilder danhMuc = DanhMuc.builder();

        danhMuc.tenDm( request.getTenDm() );

        return danhMuc.build();
    }

    @Override
    public DanhMucResponse toDanhMucResponse(DanhMuc danhMuc) {
        if ( danhMuc == null ) {
            return null;
        }

        DanhMucResponse.DanhMucResponseBuilder danhMucResponse = DanhMucResponse.builder();

        danhMucResponse.idDm( danhMuc.getIdDm() );
        danhMucResponse.tenDm( danhMuc.getTenDm() );

        return danhMucResponse.build();
    }
}
