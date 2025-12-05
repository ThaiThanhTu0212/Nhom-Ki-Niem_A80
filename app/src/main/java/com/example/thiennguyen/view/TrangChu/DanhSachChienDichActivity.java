package com.example.thiennguyen.view.TrangChu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class DanhSachChienDichActivity extends AppCompatActivity {
    RecyclerView listcd_homeRecycleid, loaiCd_ctcd_home;
    List<DanhMuc> danhMuclistHome = new ArrayList<>();
    ImageView image_back_tolist;
    ListChienDichHomeAdapter listChienDichHomeAdapter;
    DanhMuchHomeAdapter danhMuchHomeAdapter;
    SearchView searchBarHomeId;
    List<ChienDichResponse> chienDichResponseList = new ArrayList<>();
    ChienDichApi chienDichApi = ApiClient.getRetrofit().create(ChienDichApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_sach_chien_dich);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUI();
        callApi();
        initListener();
    }

    private void callApi() {
        // Kiểm tra xem có Intent với ID_DANH_MUC không
        int idDanhMuc = getIntent().getIntExtra("ID_DANH_MUC", -1);
        
        if (idDanhMuc != -1) {
            // Nếu có ID danh mục từ Intent, lấy danh sách chiến dịch theo danh mục
            getListChienDichByIdDm(idDanhMuc);
        } else {
            // Nếu không có, lấy tất cả chiến dịch
            Call<ApiResponse<List<ChienDichResponse>>> callGetChienDich = chienDichApi.getAllChienDichResponse();
            callGetChienDich.enqueue(new Callback<ApiResponse<List<ChienDichResponse>>>() {
                @Override
                public void onResponse(Call<ApiResponse<List<ChienDichResponse>>> call, Response<ApiResponse<List<ChienDichResponse>>> response) {
                    if (response.isSuccessful() && response.body()!= null){
                        chienDichResponseList.clear();
                        chienDichResponseList.addAll(response.body().getResult());
                        listChienDichHomeAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<List<ChienDichResponse>>> call, Throwable t) {

                }
            });
        }
        
        //getAllCategory
        Call<ApiResponse<List<DanhMuc>>> callGetAllDanhMuc = chienDichApi.getAllDanhMuc();
        callGetAllDanhMuc.enqueue(new Callback<ApiResponse<List<DanhMuc>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<DanhMuc>>> call, Response<ApiResponse<List<DanhMuc>>> response) {
                if (response.isSuccessful() && response.body()!= null){
                    danhMuclistHome.clear();
                    danhMuclistHome.addAll(response.body().getResult());
                    danhMuchHomeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<DanhMuc>>> call, Throwable t) {

            }
        });
    }

    private void initListener() {
        image_back_tolist.setOnClickListener(v -> {
            finish();
        });
    }

    public void getListChienDichByIdDm(Integer idDm){
        if(idDm == null) {
            //lấy danh sách từ danh mục ở trang chủ
            idDm = getIntent().getIntExtra("ID_DANH_MUC", -1);
        }
        if (idDm != null && idDm != -1){
            Call<ApiResponse<List<ChienDichResponse>>> callGetListChienDichByIdDm = chienDichApi.getAllChienDichByIdDanhMuc(idDm);
            callGetListChienDichByIdDm.enqueue(new Callback<ApiResponse<List<ChienDichResponse>>>() {
                @Override
                public void onResponse(Call<ApiResponse<List<ChienDichResponse>>> call, Response<ApiResponse<List<ChienDichResponse>>> response) {
                    if (response.isSuccessful()&& response.body()!= null){
                        chienDichResponseList.clear();
                        chienDichResponseList.addAll(response.body().getResult());
                        listChienDichHomeAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<List<ChienDichResponse>>> call, Throwable t) {

                }
            });

        }
    }

    private void initUI() {
        image_back_tolist = findViewById(R.id.image_back_tolist);



        //danh sach chien dich
        listcd_homeRecycleid = findViewById(R.id.listcd_homeRecycleid);
        listChienDichHomeAdapter = new ListChienDichHomeAdapter(chienDichResponseList);
        listcd_homeRecycleid.setLayoutManager(new LinearLayoutManager(this));
        listChienDichHomeAdapter.setListener(chienDich -> {
            Intent intent = new Intent(DanhSachChienDichActivity.this,ChiTietChienDichHomeActivity.class);
            intent.putExtra("ID_CHIEN_DICH",chienDich.getIdCd());
            startActivity(intent);
        });
        listcd_homeRecycleid.setAdapter(listChienDichHomeAdapter);

        //danh sach loai chien dich
        loaiCd_ctcd_home = findViewById(R.id.loaiCd_ctcd_home);
        danhMuchHomeAdapter = new DanhMuchHomeAdapter(danhMuclistHome);
        loaiCd_ctcd_home.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        danhMuchHomeAdapter.setListener(danhMuc -> {

            getListChienDichByIdDm(danhMuc.getIdDm());

        });

        loaiCd_ctcd_home.setAdapter(danhMuchHomeAdapter);



        searchBarHomeId = findViewById(R.id.searchBarHomeId);
        searchBarHomeId.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });



    }

}