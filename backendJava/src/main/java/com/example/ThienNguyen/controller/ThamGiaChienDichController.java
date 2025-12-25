package com.example.ThienNguyen.controller;

import java.util.List;

import com.example.ThienNguyen.dto.request.UpdateThamGiaChienDichRequest;
import org.springframework.web.bind.annotation.*;

import com.example.ThienNguyen.dto.request.ApiResponse;
import com.example.ThienNguyen.dto.response.ThamGiaChienDichDetailResponse;
import com.example.ThienNguyen.dto.response.ThamGiaChienDichResponse;
import com.example.ThienNguyen.service.ThamGiaChienDichService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/thamgia")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThamGiaChienDichController {
    ThamGiaChienDichService thamGiaChienDichService;
    @PostMapping("/{id}")
    public ApiResponse<ThamGiaChienDichResponse> createthamGiaChienDich(@PathVariable("id") Integer idChienDich,
                                                                        @RequestHeader("Authorization") String authentication){
        String token = authentication.startsWith("Bearer")
                ?authentication.substring(7)
                :authentication;
        return ApiResponse.<ThamGiaChienDichResponse>builder()
                .result(thamGiaChienDichService.createThamGiaChienDich(idChienDich, token))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteThamGiaChienDich(@PathVariable("id") Integer idThamGia){
        thamGiaChienDichService.deleteThamGiaChienDich(idThamGia);
        return ApiResponse.<Void>builder()
                .message("Delete successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ThamGiaChienDichResponse>> getAllThamGiaChienDich(){
        return ApiResponse.<List<ThamGiaChienDichResponse>>builder()
                .result(thamGiaChienDichService.getAllThamGiaChienDich())
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<List<ThamGiaChienDichDetailResponse>> getAllThamGiaChienDichByToken(
            @RequestHeader("Authorization") String authentication){
        String token = authentication.startsWith("Bearer")
                ? authentication.substring(7)
                : authentication;
        return ApiResponse.<List<ThamGiaChienDichDetailResponse>>builder()
                .result(thamGiaChienDichService.getAllThamGiaChienDichByToken(token))
                .build();
    }
    @PutMapping()
    public ApiResponse<ThamGiaChienDichResponse> updateThamGiaChienDich(@RequestBody UpdateThamGiaChienDichRequest request,
                                                                        @RequestHeader("Authorization") String authentication ){
        String token = authentication.startsWith("Bearer")
                ? authentication.substring(7)
                : authentication;
        return ApiResponse.<ThamGiaChienDichResponse>builder()
                .result(thamGiaChienDichService.updateThamgiaChienDich(request,token))
                .build();
    }
}
