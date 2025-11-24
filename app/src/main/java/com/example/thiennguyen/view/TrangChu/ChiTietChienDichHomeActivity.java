package com.example.thiennguyen.view.TrangChu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.data.ApiClient;
import com.example.thiennguyen.view.data.DTO.ApiResponse;
import com.example.thiennguyen.view.data.DTO.Response.ChienDichResponse;
import com.example.thiennguyen.view.data.api.ChienDichApi;
import com.example.thiennguyen.view.model.ChienDich;
import com.example.thiennguyen.view.model.DanhMuc;
import com.example.thiennguyen.view.model.NguoiDung;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietChienDichHomeActivity extends AppCompatActivity {
    ImageView imageViewhinhAnhctcd, img_back_tohome;
    TextView tvdm_ctcd, tvtencdctcd_home, tvsoTienMucTieu_ctcd_home, tvsongayconlai_ctcd_home, tvsoTienHienTai_ctcd_home, tvmoTa_ctcd_home;
    List<ChienDich> chienDichListHome;
    List<DanhMuc> danhMuclistHome;
    List<NguoiDung> nguoiDungListHome;
    ChienDichApi chienDichApi = ApiClient.getRetrofit().create(ChienDichApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_chien_dich_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getChienDichByIDApi();

        initUI();

        initListener();
    }

    private void getChienDichByIDApi() {
        Intent intent = getIntent();
        int idChienDich = intent.getIntExtra("ID_CHIEN_DICH",-1);
        if (idChienDich != -1){

            Call<ApiResponse<ChienDichResponse>> callGetChienDichById = chienDichApi.getChienDichResponseById(idChienDich);
            callGetChienDichById.enqueue(new Callback<ApiResponse<ChienDichResponse>>() {
                @Override
                public void onResponse(Call<ApiResponse<ChienDichResponse>> call, Response<ApiResponse<ChienDichResponse>> response) {
                    if (response.isSuccessful() && response.body()!= null){
                        hienThiChiTiet(response.body().getResult());
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<ChienDichResponse>> call, Throwable t) {
                    Toast.makeText(ChiTietChienDichHomeActivity.this, "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
        });
        }
    }


    private void initListener() {
        img_back_tohome.setOnClickListener(v -> {
            finish();
        });
    }



    private void hienThiChiTiet(ChienDichResponse cd) {
        tvtencdctcd_home.setText(cd.getTenCd());
//        tvdm_ctcd.setText(cd.getDanhMuc().getTenDm());
        tvmoTa_ctcd_home.setText(cd.getMoTa());
//        tvsongayconlai_ctcd_home.setText(cd.getTenCd());
        tvsoTienHienTai_ctcd_home.setText(cd.getSoTienHienTai().toString() +" đ");
        tvsoTienMucTieu_ctcd_home.setText(cd.getSoTienMucTieu().toString() + " đ");
        Glide.with(this)
                .load(cd.getHinhAnh())
                .placeholder(R.drawable.chiendich_image)
                .error(R.drawable.chiendich_image)
                .into(imageViewhinhAnhctcd);
    }


    private void initUI() {
        imageViewhinhAnhctcd = findViewById(R.id.imageViewhinhAnhctcd);
        tvdm_ctcd = findViewById(R.id.tvdm_ctcd);
        tvtencdctcd_home = findViewById(R.id.tvtencdctcd_home);
        tvsoTienMucTieu_ctcd_home = findViewById(R.id.tvsoTienMucTieu_ctcd_home);
        tvsongayconlai_ctcd_home = findViewById(R.id.tvsongayconlai_ctcd_home);
        tvsoTienHienTai_ctcd_home = findViewById(R.id.tvsoTienHienTai_ctcd_home);
        tvmoTa_ctcd_home = findViewById(R.id.tvmoTa_ctcd_home);
        img_back_tohome = findViewById(R.id.image_back_listcd_list_home);
    }
}