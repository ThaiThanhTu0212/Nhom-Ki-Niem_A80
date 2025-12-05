package com.example.ThienNguyen.controller;

import com.example.ThienNguyen.dto.request.ApiResponse;
import com.example.ThienNguyen.dto.request.DanhMucRequest;
import com.example.ThienNguyen.dto.response.DanhMucResponse;
import com.example.ThienNguyen.service.DanhMucService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DanhMucController {
    DanhMucService danhMucService;
    @PostMapping
    public ApiResponse<DanhMucResponse> createDanhMuc(@RequestBody DanhMucRequest request){
        return ApiResponse.<DanhMucResponse>builder()
                .result(danhMucService.createDanhMuc(request))
                .build();
    }
    @GetMapping
    public ApiResponse<List<DanhMucResponse>> getAllDanhMuc(){
        return ApiResponse.<List<DanhMucResponse>>builder()
                .result(danhMucService.getAllDanhMuc())
                .build();
    }
}
