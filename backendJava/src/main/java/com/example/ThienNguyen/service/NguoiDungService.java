package com.example.ThienNguyen.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ThienNguyen.dto.request.NguoiDungRequest;
import com.example.ThienNguyen.dto.request.NguoiDungUpdateRequest;
import com.example.ThienNguyen.dto.response.NguoiDungResponse;
import com.example.ThienNguyen.entity.NguoiDung;
import com.example.ThienNguyen.exception.AppException;
import com.example.ThienNguyen.exception.ErrorCode;
import com.example.ThienNguyen.mapper.NguoiDungMapper;
import com.example.ThienNguyen.repository.NguoiDungRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NguoiDungService {
    NguoiDungMapper nguoiDungMapper;
    NguoiDungRepository nguoiDungRepository;
    @Lazy
    AuthenticationService authenticationService;
    PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

    public NguoiDungResponse createNguoiDung(NguoiDungRequest request){
        if (nguoiDungRepository.existsByHoTen(request.getHoTen())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if (nguoiDungRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTS);
        }
        NguoiDung nguoiDung = nguoiDungMapper.toNguoiDung(request);
        nguoiDung.setMatKhau(passwordEncoder.encode(request.getMatKhau()));

        nguoiDungRepository.save(nguoiDung);

        return nguoiDungMapper.toNguoiDungResponse(nguoiDung);
    }
    public List<NguoiDungResponse> getAllNguoiDung(){
        return nguoiDungRepository.findAll()
                .stream()
                .map(nguoiDungMapper::toNguoiDungResponse)
                .toList();
    }
    public NguoiDungResponse getNguoiDungById(Integer id){
        NguoiDung nguoiDung =nguoiDungRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return nguoiDungMapper.toNguoiDungResponse(nguoiDung);

    }

    public NguoiDungResponse updateNguoiDung(Integer id, NguoiDungUpdateRequest request){
        NguoiDung nguoiDung = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));


        nguoiDungMapper.updateNguoiDung(nguoiDung,request);
        if(request.getMatKhau() != null && !request.getMatKhau().isBlank()) {
            nguoiDung.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        }

        nguoiDungRepository.save(nguoiDung);
        return nguoiDungMapper.toNguoiDungResponse(nguoiDung);
    }
    /**
     * Lấy thông tin người dùng từ token
     * @param token JWT token từ Authorization header
     * @return NguoiDungResponse thông tin người dùng
     */
    public NguoiDungResponse getMyInfo(String token){
        // Lấy id từ token đã được verify
        Integer id = authenticationService.getUserIdFromToken(token);

        // Tìm người dùng theo id
        NguoiDung nguoiDung = nguoiDungRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return nguoiDungMapper.toNguoiDungResponse(nguoiDung);
    }
}
