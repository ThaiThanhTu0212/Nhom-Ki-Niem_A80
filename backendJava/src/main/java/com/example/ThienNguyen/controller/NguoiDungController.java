package com.example.ThienNguyen.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ThienNguyen.dto.request.ApiResponse;
import com.example.ThienNguyen.dto.request.NguoiDungRequest;
import com.example.ThienNguyen.dto.request.NguoiDungUpdateRequest;
import com.example.ThienNguyen.dto.response.NguoiDungResponse;
import com.example.ThienNguyen.service.NguoiDungService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NguoiDungController {
    NguoiDungService nguoiDungService;
    @PostMapping
    public ApiResponse<NguoiDungResponse> createNguoiDung(@RequestBody NguoiDungRequest request){
        return ApiResponse.<NguoiDungResponse>builder()
                .result(nguoiDungService.createNguoiDung(request))
                .build();
    }
    @GetMapping
    public ApiResponse<List<NguoiDungResponse>> getAllNguoiDung(){
        return ApiResponse.<List<NguoiDungResponse>>builder()
                .result(nguoiDungService.getAllNguoiDung())
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<NguoiDungResponse> getNguoiDungById(@PathVariable("id") Integer id){
        return ApiResponse.<NguoiDungResponse>builder()
                .result(nguoiDungService.getNguoiDungById(id))
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<NguoiDungResponse> updateNguoiDung(@PathVariable("id") Integer id,@RequestBody NguoiDungUpdateRequest request){
        return ApiResponse.<NguoiDungResponse>builder()
                .result(nguoiDungService.updateNguoiDung(id,request))
                .build();
    }
    @GetMapping("myinfo")
    public ApiResponse<NguoiDungResponse> getMyInfo(@RequestHeader("Authorization") String authorization){
        // Extract token from "Bearer <token>" format
        String token = authorization.startsWith("Bearer ") 
                ? authorization.substring(7) 
                : authorization;
        return ApiResponse.<NguoiDungResponse>builder()
                .result(nguoiDungService.getMyInfo(token))
                .build();
    }
}
