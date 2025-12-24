package com.example.ThienNguyen.mapper;

import com.example.ThienNguyen.dto.request.ChienDichRequest;
import com.example.ThienNguyen.dto.request.ChienDichUpdateRequest;
import com.example.ThienNguyen.dto.response.ChienDichResponse;
import com.example.ThienNguyen.entity.ChienDich;
import com.example.ThienNguyen.entity.DanhMuc;
import com.example.ThienNguyen.entity.NguoiDung;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T07:48:00+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class ChienDichMapperImpl implements ChienDichMapper {

    @Override
    public ChienDich toChienDich(ChienDichRequest request) {
        if ( request == null ) {
            return null;
        }

        ChienDich.ChienDichBuilder chienDich = ChienDich.builder();

        chienDich.tenCd( request.getTenCd() );
        chienDich.moTa( request.getMoTa() );
        chienDich.hinhAnh( request.getHinhAnh() );
        chienDich.soTienMucTieu( request.getSoTienMucTieu() );
        chienDich.soTienHienTai( request.getSoTienHienTai() );
        chienDich.ngayBatDau( request.getNgayBatDau() );
        chienDich.ngayKetThuc( request.getNgayKetThuc() );
        chienDich.trangThai( request.getTrangThai() );
        chienDich.tinhThanh( request.getTinhThanh() );
        chienDich.quanHuyen( request.getQuanHuyen() );
        chienDich.diaChi( request.getDiaChi() );

        return chienDich.build();
    }

    @Override
    public ChienDichResponse toChienDichResponse(ChienDich chienDich) {
        if ( chienDich == null ) {
            return null;
        }

        ChienDichResponse.ChienDichResponseBuilder chienDichResponse = ChienDichResponse.builder();

        chienDichResponse.idNguoiToChuc( chienDichNguoiToChucIdNd( chienDich ) );
        chienDichResponse.idDanhMuc( chienDichDanhMucIdDm( chienDich ) );
        chienDichResponse.idCd( chienDich.getIdCd() );
        chienDichResponse.tenCd( chienDich.getTenCd() );
        chienDichResponse.moTa( chienDich.getMoTa() );
        chienDichResponse.hinhAnh( chienDich.getHinhAnh() );
        chienDichResponse.soTienMucTieu( chienDich.getSoTienMucTieu() );
        chienDichResponse.soTienHienTai( chienDich.getSoTienHienTai() );
        chienDichResponse.ngayBatDau( chienDich.getNgayBatDau() );
        chienDichResponse.ngayKetThuc( chienDich.getNgayKetThuc() );
        chienDichResponse.trangThai( chienDich.getTrangThai() );
        chienDichResponse.tinhThanh( chienDich.getTinhThanh() );
        chienDichResponse.quanHuyen( chienDich.getQuanHuyen() );
        chienDichResponse.diaChi( chienDich.getDiaChi() );

        return chienDichResponse.build();
    }

    @Override
    public List<ChienDichResponse> toChienDichResponseList(List<ChienDich> chienDichList) {
        if ( chienDichList == null ) {
            return null;
        }

        List<ChienDichResponse> list = new ArrayList<ChienDichResponse>( chienDichList.size() );
        for ( ChienDich chienDich : chienDichList ) {
            list.add( toChienDichResponse( chienDich ) );
        }

        return list;
    }

    @Override
    public void updateChienDich(ChienDich chienDich, ChienDichUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        chienDich.setTenCd( request.getTenCd() );
        chienDich.setMoTa( request.getMoTa() );
        chienDich.setHinhAnh( request.getHinhAnh() );
        chienDich.setSoTienMucTieu( request.getSoTienMucTieu() );
        chienDich.setSoTienHienTai( request.getSoTienHienTai() );
        if ( request.getNgayBatDau() != null ) {
            chienDich.setNgayBatDau( Date.from( request.getNgayBatDau().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        else {
            chienDich.setNgayBatDau( null );
        }
        if ( request.getNgayKetThuc() != null ) {
            chienDich.setNgayKetThuc( Date.from( request.getNgayKetThuc().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        else {
            chienDich.setNgayKetThuc( null );
        }
        chienDich.setTrangThai( request.getTrangThai() );
        chienDich.setTinhThanh( request.getTinhThanh() );
        chienDich.setQuanHuyen( request.getQuanHuyen() );
        chienDich.setDiaChi( request.getDiaChi() );
    }

    private Integer chienDichNguoiToChucIdNd(ChienDich chienDich) {
        if ( chienDich == null ) {
            return null;
        }
        NguoiDung nguoiToChuc = chienDich.getNguoiToChuc();
        if ( nguoiToChuc == null ) {
            return null;
        }
        Integer idNd = nguoiToChuc.getIdNd();
        if ( idNd == null ) {
            return null;
        }
        return idNd;
    }

    private Integer chienDichDanhMucIdDm(ChienDich chienDich) {
        if ( chienDich == null ) {
            return null;
        }
        DanhMuc danhMuc = chienDich.getDanhMuc();
        if ( danhMuc == null ) {
            return null;
        }
        Integer idDm = danhMuc.getIdDm();
        if ( idDm == null ) {
            return null;
        }
        return idDm;
    }
}
