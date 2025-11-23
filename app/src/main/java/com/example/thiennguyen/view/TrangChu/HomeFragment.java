package com.example.thiennguyen.view.TrangChu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thiennguyen.R;
import com.example.thiennguyen.view.data.ApiClient;
import com.example.thiennguyen.view.data.DTO.ApiResponse;
import com.example.thiennguyen.view.data.DTO.Response.ChienDichResponse;
import com.example.thiennguyen.view.data.api.ChienDichApi;
import com.example.thiennguyen.view.model.ChienDich;
import com.example.thiennguyen.view.model.DanhMuc;
import com.example.thiennguyen.view.model.NguoiDung;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    TextView btnXemTatCa_ct_home;

    View view;
    RecyclerView chiendichHomeRecycleid, danhmucHomeRecyleid, nguoiDungHomeRecycleid, ToChuchomeRecycleId;
    List<ChienDich> chienDichListHome;
    List<ChienDichResponse> chienDichResponseList = new ArrayList<>();
    List<DanhMuc> danhMuclistHome;
    List<NguoiDung> nguoiDungListHome;
    ChienDichHomeAdapter chienDichHomeAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);
//        creatListChienDich();
        callApi();
        initUI();
        initListener();
        return view;


    }

    private void callApi() {

        ChienDichApi chienDichApi = ApiClient.getRetrofit().create(ChienDichApi.class);


        Call<ApiResponse<ChienDichResponse>> callChienDich = chienDichApi.getChienDichResponseById(6);
        callChienDich.enqueue(new Callback<ApiResponse<ChienDichResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<ChienDichResponse>> call, Response<ApiResponse<ChienDichResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ChienDichResponse chienDichResponse = response.body().getResult();
                    if (chienDichResponse != null) {
                        Log.d("API_SUCCESS", "Tên chiến dịch: " + chienDichResponse.getTenCd());
                        Log.d("API_SUCCESS", "Mô tả: " + chienDichResponse.getMoTa());
                        Log.d("API_SUCCESS", "Số tiền mục tiêu: " + chienDichResponse.getSoTienMucTieu());
                        Log.d("API_SUCCESS", "Số tiền mục tiêu: " + chienDichResponse.toString());
                        // và các trường khác...
                    } else {
                        Log.e("API_ERROR", "Result is null");
                    }
                } else {
                    Log.e("API_ERROR", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ChienDichResponse>> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage(), t);
            }
        });

        Call<ApiResponse<List<ChienDichResponse>>> callAllChienDichResponse = chienDichApi.getAllChienDichResponse();
        callAllChienDichResponse.enqueue(new Callback<ApiResponse<List<ChienDichResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ChienDichResponse>>> call, Response<ApiResponse<List<ChienDichResponse>>> response) {
                if (response.isSuccessful() && response.body()!= null) {
                    chienDichResponseList.clear();
                    chienDichResponseList.addAll(response.body().getResult());
                    chienDichHomeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ChienDichResponse>>> call, Throwable t) {

            }
        });

    }




    private void initListener() {
        btnXemTatCa_ct_home.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),DanhSachChienDichActivity.class);

            startActivity(intent);
        });
    }

    private void initUI() {
        //recycleView for chienDich
        chiendichHomeRecycleid = view.findViewById(R.id.chiendichHomeRecycleid);
        chienDichHomeAdapter = new ChienDichHomeAdapter(chienDichResponseList);
        chiendichHomeRecycleid.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        chienDichHomeAdapter.setListener(chienDich -> {
            Intent intent = new Intent(getContext(),ChiTietChienDichHomeActivity.class);
            intent.putExtra("ID_CHIEN_DICH", String.valueOf(chienDich.getIdCd()));
            startActivity(intent);
        });

        chiendichHomeRecycleid.setAdapter(chienDichHomeAdapter);

        //recycleView for danhMuc
        danhmucHomeRecyleid = view.findViewById(R.id.danhmucHomeRecyleid);
        DanhMuchHomeAdapter danhMuchHomeAdapter = new DanhMuchHomeAdapter(danhMuclistHome);
        danhmucHomeRecyleid.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        danhMuchHomeAdapter.setListener(danhMuc -> {
            Intent intent = new Intent(getContext(),DanhSachChienDichActivity.class);
            intent.putExtra("ID_DANH_MUC", danhMuc.getIdDm());
            startActivity(intent);
        });

        danhmucHomeRecyleid.setAdapter(danhMuchHomeAdapter);

        //recycleView for nguoi dung
        nguoiDungHomeRecycleid = view.findViewById(R.id.nguoiDungHomeRecycleid);
        NguoiDungHomeAdapter nguoiDungHomeAdapter = new NguoiDungHomeAdapter(nguoiDungListHome);
        nguoiDungHomeRecycleid.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        nguoiDungHomeAdapter.setListener(nguoiDung -> {
            Intent intent = new Intent(getContext(),ProfileHomeActivity.class);
            intent.putExtra("ID_NGUOI_DUNG",nguoiDung.getIdNd());
            startActivity(intent);
        });
        nguoiDungHomeRecycleid.setAdapter(nguoiDungHomeAdapter);

        btnXemTatCa_ct_home = view.findViewById(R.id.btnXemTatCa_ct_home);

        //recycleView người dùng vai trò tổ chức
        ToChuchomeRecycleId = view.findViewById(R.id.ToChuchomeRecycleId);
        ToChuchomeRecycleId.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        ToChucHomeAdapter toChucHomeAdapter = new ToChucHomeAdapter(nguoiDungListHome);
        toChucHomeAdapter.setListener(nguoiDung -> {
            Intent intent = new Intent(getContext(),ProfileHomeActivity.class);
            intent.putExtra("ID_NGUOI_DUNG",nguoiDung.getIdNd());
            startActivity(intent);
        });
        ToChuchomeRecycleId.setAdapter(toChucHomeAdapter);


    }
}