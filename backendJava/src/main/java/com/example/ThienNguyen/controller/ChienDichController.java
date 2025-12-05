package com.example.ThienNguyen.controller;

import com.example.ThienNguyen.dto.request.ApiResponse;
import com.example.ThienNguyen.dto.request.ChienDichRequest;
import com.example.ThienNguyen.dto.request.ChienDichUpdateRequest;
import com.example.ThienNguyen.dto.response.ChienDichResponse;
import com.example.ThienNguyen.service.ChienDichService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaign")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChienDichController {
    ChienDichService chienDichService;
    @PostMapping
    public ApiResponse<ChienDichResponse> creatChienDich(@RequestBody ChienDichRequest request,
                                                         @RequestHeader("Authorization") String authentication){
        String token = authentication.startsWith("Bearer")
                ?authentication.substring(7)
                :authentication;
        return ApiResponse.<ChienDichResponse>builder()
                .result(chienDichService.createChienDich(request,token))
                .build();
    }
    @GetMapping
    public ApiResponse<List<ChienDichResponse>> getAllChienDich(){
        return ApiResponse.<List<ChienDichResponse>>builder()
                .result(chienDichService.getAllChienDich())
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<ChienDichResponse> getChienDichById(@PathVariable("id") Integer id){
        return ApiResponse.<ChienDichResponse>builder()
                .result(chienDichService.getChienDichById(id))
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<ChienDichResponse> updateChienDich(@PathVariable("id") Integer id, @RequestBody ChienDichUpdateRequest request){
        return ApiResponse.<ChienDichResponse>builder()
                .result(chienDichService.updateChienDich(request,id))
                .build();
    }

    @GetMapping("/category/{id}")
    public ApiResponse<List<ChienDichResponse>> getAllChienDichByIdDanhMuc(@PathVariable("id") Integer id){
        return ApiResponse.<List<ChienDichResponse>>builder()
                .result(chienDichService.getChienDichByDanhMuc(id))
                .build();
    }

}
