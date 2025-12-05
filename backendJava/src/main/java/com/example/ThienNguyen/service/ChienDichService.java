package com.example.ThienNguyen.service;

import com.example.ThienNguyen.dto.request.ChienDichRequest;
import com.example.ThienNguyen.dto.request.ChienDichUpdateRequest;
import com.example.ThienNguyen.dto.response.ChienDichResponse;
import com.example.ThienNguyen.entity.ChienDich;
import com.example.ThienNguyen.entity.DanhMuc;
import com.example.ThienNguyen.entity.NguoiDung;
import com.example.ThienNguyen.exception.AppException;
import com.example.ThienNguyen.exception.ErrorCode;
import com.example.ThienNguyen.mapper.ChienDichMapper;
import com.example.ThienNguyen.repository.ChienDichRepository;
import com.example.ThienNguyen.repository.DanhMucRepository;
import com.example.ThienNguyen.repository.NguoiDungRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ChienDichService {
    ChienDichMapper chienDichMapper;
    ChienDichRepository chienDichRepository;
    NguoiDungRepository nguoiDungRepository;
    DanhMucRepository danhMucRepository;
    AuthenticationService authenticationService;

    public ChienDichResponse createChienDich(ChienDichRequest request,String token){
        Integer id =authenticationService.getUserIdFromToken(token);
        NguoiDung nguoiDung = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        DanhMuc danhMuc = danhMucRepository.findById(request.getIdDanhMuc())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST));

        ChienDich chienDich = chienDichMapper.toChienDich(request);
        chienDich.setNguoiToChuc(nguoiDung);
        chienDich.setDanhMuc(danhMuc);

        chienDichRepository.save(chienDich);
        return chienDichMapper.toChienDichResponse(chienDich);
    }
    public List<ChienDichResponse> getAllChienDich(){
       return chienDichRepository.findAll()
                .stream()
                .map(chienDichMapper::toChienDichResponse)
                .toList();
    }
    public ChienDichResponse getChienDichById(Integer id){
        ChienDich chienDich = chienDichRepository.findById(id)
                .orElseThrow(() ->  new AppException(ErrorCode.CAMPAIGN_NOT_EXIST));
        return chienDichMapper.toChienDichResponse(chienDich);
    }
    public ChienDichResponse updateChienDich(ChienDichUpdateRequest request,Integer id){
       ChienDich chienDich = chienDichRepository.findById(id)
               .orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_EXIST));
       DanhMuc danhMuc = danhMucRepository.findById(request.getIdDanhMuc())
               .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST));
        chienDichMapper.updateChienDich(chienDich, request);
        chienDich.setDanhMuc(danhMuc);

        chienDichRepository.save(chienDich);
        return chienDichMapper.toChienDichResponse(chienDich);
    }
    public List<ChienDichResponse> getChienDichByDanhMuc(Integer idDm){
        List<ChienDich> chienDiches= chienDichRepository.getAllByDanhMuc_IdDm(idDm);
        return chienDichMapper.toChienDichResponseList(chienDiches);
    }
}
