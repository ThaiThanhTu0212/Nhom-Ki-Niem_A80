package com.example.ThienNguyen.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ThienNguyen.dto.request.UpdateThamGiaChienDichRequest;
import org.springframework.stereotype.Service;

import com.example.ThienNguyen.dto.response.ThamGiaChienDichDetailResponse;
import com.example.ThienNguyen.dto.response.ThamGiaChienDichResponse;
import com.example.ThienNguyen.entity.ChienDich;
import com.example.ThienNguyen.entity.NguoiDung;
import com.example.ThienNguyen.entity.ThamGiaChienDich;
import com.example.ThienNguyen.exception.AppException;
import com.example.ThienNguyen.exception.ErrorCode;
import com.example.ThienNguyen.mapper.ThamGiaChienDichMapper;
import com.example.ThienNguyen.repository.ChienDichRepository;
import com.example.ThienNguyen.repository.NguoiDungRepository;
import com.example.ThienNguyen.repository.ThamGiaChienDichRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ThamGiaChienDichService {
    ThamGiaChienDichMapper thamGiaChienDichMapper;
    ThamGiaChienDichRepository thamGiaChienDichRepository;
    NguoiDungRepository nguoiDungRepository;
    ChienDichRepository chienDichRepository;
    AuthenticationService authenticationService;

    public ThamGiaChienDichResponse createThamGiaChienDich(Integer idChienDich,String token){
        Integer idND =authenticationService.getUserIdFromToken(token);

        NguoiDung nguoiDung = nguoiDungRepository.findById(idND)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        ChienDich chienDich = chienDichRepository.findById(idChienDich)
                .orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_EXIST));

        ThamGiaChienDich tg = new ThamGiaChienDich();
        tg.setChienDich(chienDich);
        tg.setNguoiDung(nguoiDung);
        tg.setVaiTro("tinh_nguyen_vien");
        tg.setTrangThai("cho_duyet");
        thamGiaChienDichRepository.save(tg);
        return thamGiaChienDichMapper.toThamGiaChienDichResponse(tg);
    }

    public void deleteThamGiaChienDich(Integer idThamGia){
        ThamGiaChienDich thamGiaChienDich = thamGiaChienDichRepository.findById(idThamGia)
                .orElseThrow(() -> new AppException(ErrorCode.THAM_GIA_CHIEN_DICH_NOT_EXIST));
        thamGiaChienDichRepository.delete(thamGiaChienDich);
    }

    public List<ThamGiaChienDichResponse> getAllThamGiaChienDich(){
        List<ThamGiaChienDich> thamGiaChienDichList = thamGiaChienDichRepository.findAll();
        return thamGiaChienDichList.stream()
                .map(thamGiaChienDichMapper::toThamGiaChienDichResponse)
                .collect(Collectors.toList());
    }

    public List<ThamGiaChienDichDetailResponse> getAllThamGiaChienDichByToken(String token){
        Integer idND = authenticationService.getUserIdFromToken(token);
        NguoiDung nguoiDung = nguoiDungRepository.findById(idND)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<ThamGiaChienDich> thamGiaChienDichList = thamGiaChienDichRepository.findByNguoiDungWithChienDich(nguoiDung);
        return thamGiaChienDichList.stream()
                .map(thamGiaChienDichMapper::toThamGiaChienDichDetailResponse)
                .collect(Collectors.toList());
    }
    public ThamGiaChienDichResponse updateThamgiaChienDich(UpdateThamGiaChienDichRequest request, String token){
        Integer idND = authenticationService.getUserIdFromToken(token);
        NguoiDung nguoiDung = nguoiDungRepository.findById(idND)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        ThamGiaChienDich thamGiaChienDich = thamGiaChienDichRepository.findById(request.getIdThamGia())
                .orElseThrow(() -> new AppException(ErrorCode.THAM_GIA_CHIEN_DICH_NOT_EXIST));
        thamGiaChienDich.setTrangThai(request.getTrangThai());
        thamGiaChienDichRepository.save(thamGiaChienDich);
        return thamGiaChienDichMapper.toThamGiaChienDichResponse(thamGiaChienDich);
    }
}
