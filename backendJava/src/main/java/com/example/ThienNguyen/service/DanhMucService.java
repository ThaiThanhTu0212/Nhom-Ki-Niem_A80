package com.example.ThienNguyen.service;

import com.example.ThienNguyen.dto.request.DanhMucRequest;
import com.example.ThienNguyen.dto.response.DanhMucResponse;
import com.example.ThienNguyen.entity.DanhMuc;
import com.example.ThienNguyen.exception.AppException;
import com.example.ThienNguyen.exception.ErrorCode;
import com.example.ThienNguyen.mapper.DanhMucMapper;
import com.example.ThienNguyen.repository.DanhMucRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DanhMucService {
    DanhMucMapper danhMucMapper;
    DanhMucRepository danhMucRepository;

    public DanhMucResponse createDanhMuc(DanhMucRequest request){
        if (danhMucRepository.existsByTenDm(request.getTenDm())){
            throw new AppException(ErrorCode.CATEGORY_EXIST);
        }
        DanhMuc danhMuc = danhMucMapper.toDanhMuc(request);
        danhMucRepository.save(danhMuc);

        return danhMucMapper.toDanhMucResponse(danhMuc);
    }

    public List<DanhMucResponse> getAllDanhMuc(){
        return danhMucRepository.findAll()
                .stream()
                .map(danhMucMapper::toDanhMucResponse)
                .toList();
    }
}
