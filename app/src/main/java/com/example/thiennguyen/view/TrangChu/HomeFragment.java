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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thiennguyen.R;
import com.example.thiennguyen.view.data.ApiClient;
import com.example.thiennguyen.view.data.DTO.ApiResponse;
import com.example.thiennguyen.view.data.DTO.Response.ChienDichResponse;
import com.example.thiennguyen.view.data.DTO.Response.NguoiDungResponse;
import com.example.thiennguyen.view.data.api.ChienDichApi;
import com.example.thiennguyen.view.model.ChienDich;
import com.example.thiennguyen.view.model.DanhMuc;
import com.example.thiennguyen.view.model.NguoiDung;
import com.example.thiennguyen.view.taikhoan.LoginActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    TextView btnXemTatCa_ct_home;
    Button btnGotoLogin;

    View view;
    RecyclerView chiendichHomeRecycleid, danhmucHomeRecyleid, nguoiDungHomeRecycleid, ToChuchomeRecycleId;

    List<ChienDichResponse> chienDichResponseList = new ArrayList<>();
    List<DanhMuc> danhMuclistHome = new ArrayList<>();
    List<NguoiDungResponse> nguoiDungResponseList = new ArrayList<>();

    ChienDichHomeAdapter chienDichHomeAdapter;
    DanhMuchHomeAdapter danhMuchHomeAdapter;
    NguoiDungHomeAdapter nguoiDungHomeAdapter;
    ToChucHomeAdapter toChucHomeAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);
        initUI();
        callApi();

        initListener();
        return view;


    }

    private void callApi() {

        ChienDichApi chienDichApi = ApiClient.getRetrofit().create(ChienDichApi.class);



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

        Call<ApiResponse<List<DanhMuc>>> callDanhMuc = chienDichApi.getAllDanhMuc();
        callDanhMuc.enqueue(new Callback<ApiResponse<List<DanhMuc>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<DanhMuc>>> call, Response<ApiResponse<List<DanhMuc>>> response) {
                if(response.isSuccessful() && response.body()!= null){
                    danhMuclistHome.clear();
                    danhMuclistHome.addAll(response.body().getResult());
                    danhMuchHomeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<DanhMuc>>> call, Throwable t) {

            }
        });

        Call<ApiResponse<List<NguoiDungResponse>>> callNguoiDungList = chienDichApi.getAllNguoiDung();
        callNguoiDungList.enqueue(new Callback<ApiResponse<List<NguoiDungResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<NguoiDungResponse>>> call, Response<ApiResponse<List<NguoiDungResponse>>> response) {
                if (response.isSuccessful() && response.body()!= null){
                    nguoiDungResponseList.clear();
                    nguoiDungResponseList.addAll(response.body().getResult());
                    nguoiDungHomeAdapter.notifyDataSetChanged();
                    toChucHomeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<NguoiDungResponse>>> call, Throwable t) {

            }
        });

    }


    private void initListener() {
        btnXemTatCa_ct_home.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),DanhSachChienDichActivity.class);

            startActivity(intent);
        });
        btnGotoLogin.setOnClickListener(v -> {
            Intent intentlogin = new Intent(getContext(), LoginActivity.class);
            startActivity(intentlogin);
        });
    }

    private void initUI() {
        //recycleView for chienDich
        chiendichHomeRecycleid = view.findViewById(R.id.chiendichHomeRecycleid);
        chienDichHomeAdapter = new ChienDichHomeAdapter(chienDichResponseList);
        chiendichHomeRecycleid.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        chienDichHomeAdapter.setListener(chienDich -> {
            Intent intent = new Intent(getContext(),ChiTietChienDichHomeActivity.class);
            intent.putExtra("ID_CHIEN_DICH", chienDich.getIdCd());
            startActivity(intent);
        });

        chiendichHomeRecycleid.setAdapter(chienDichHomeAdapter);

        //recycleView for danhMuc
        danhmucHomeRecyleid = view.findViewById(R.id.danhmucHomeRecyleid);
        danhMuchHomeAdapter = new DanhMuchHomeAdapter(danhMuclistHome);
        danhmucHomeRecyleid.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        danhMuchHomeAdapter.setListener(danhMuc -> {
            Intent intent = new Intent(getContext(),DanhSachChienDichActivity.class);
            intent.putExtra("ID_DANH_MUC", danhMuc.getIdDm());
            startActivity(intent);
        });

        danhmucHomeRecyleid.setAdapter(danhMuchHomeAdapter);

        //recycleView for nguoi dung
        nguoiDungHomeRecycleid = view.findViewById(R.id.nguoiDungHomeRecycleid);
        nguoiDungHomeAdapter = new NguoiDungHomeAdapter(nguoiDungResponseList);
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

        toChucHomeAdapter = new ToChucHomeAdapter(nguoiDungResponseList);
        toChucHomeAdapter.setListener(nguoiDung -> {
            Intent intent = new Intent(getContext(),ProfileHomeActivity.class);
            intent.putExtra("ID_NGUOI_DUNG",nguoiDung.getIdNd());
            startActivity(intent);
        });
        ToChuchomeRecycleId.setAdapter(toChucHomeAdapter);

        btnGotoLogin = view.findViewById(R.id.btnGotoLogin);
    }
}