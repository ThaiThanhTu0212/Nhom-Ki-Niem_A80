package com.example.ThienNguyen.mapper;

import com.example.ThienNguyen.dto.request.NguoiDungRequest;
import com.example.ThienNguyen.dto.request.NguoiDungUpdateRequest;
import com.example.ThienNguyen.dto.response.NguoiDungResponse;
import com.example.ThienNguyen.entity.NguoiDung;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T07:48:00+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class NguoiDungMapperImpl implements NguoiDungMapper {

    @Override
    public NguoiDungResponse toNguoiDungResponse(NguoiDung nguoiDung) {
        if ( nguoiDung == null ) {
            return null;
        }

        NguoiDungResponse.NguoiDungResponseBuilder nguoiDungResponse = NguoiDungResponse.builder();

        nguoiDungResponse.idNd( nguoiDung.getIdNd() );
        nguoiDungResponse.hoTen( nguoiDung.getHoTen() );
        nguoiDungResponse.email( nguoiDung.getEmail() );
        nguoiDungResponse.soDienThoai( nguoiDung.getSoDienThoai() );
        nguoiDungResponse.vaiTro( nguoiDung.getVaiTro() );
        nguoiDungResponse.trangThai( nguoiDung.getTrangThai() );
        nguoiDungResponse.avatar( nguoiDung.getAvatar() );

        return nguoiDungResponse.build();
    }

    @Override
    public NguoiDung toNguoiDung(NguoiDungRequest request) {
        if ( request == null ) {
            return null;
        }

        NguoiDung.NguoiDungBuilder nguoiDung = NguoiDung.builder();

        nguoiDung.hoTen( request.getHoTen() );
        nguoiDung.email( request.getEmail() );
        nguoiDung.soDienThoai( request.getSoDienThoai() );
        nguoiDung.matKhau( request.getMatKhau() );
        nguoiDung.vaiTro( request.getVaiTro() );
        nguoiDung.trangThai( request.getTrangThai() );
        nguoiDung.avatar( request.getAvatar() );

        return nguoiDung.build();
    }

    @Override
    public void updateNguoiDung(NguoiDung nguoiDung, NguoiDungUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        if ( request.getHoTen() != null ) {
            nguoiDung.setHoTen( request.getHoTen() );
        }
        if ( request.getEmail() != null ) {
            nguoiDung.setEmail( request.getEmail() );
        }
        if ( request.getSoDienThoai() != null ) {
            nguoiDung.setSoDienThoai( request.getSoDienThoai() );
        }
        if ( request.getVaiTro() != null ) {
            nguoiDung.setVaiTro( request.getVaiTro() );
        }
        if ( request.getTrangThai() != null ) {
            nguoiDung.setTrangThai( request.getTrangThai() );
        }
        if ( request.getAvatar() != null ) {
            nguoiDung.setAvatar( request.getAvatar() );
        }
    }
}
