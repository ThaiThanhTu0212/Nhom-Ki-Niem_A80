package com.example.thiennguyen.view.TrangChu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
import com.example.thiennguyen.view.data.DTO.Response.ThamGiaChienDichResponse;
import com.example.thiennguyen.view.data.api.ChienDichApi;
import com.example.thiennguyen.view.data.api.ThamGiaChienDichApi;
import com.example.thiennguyen.view.data.sharepreference.DataLocalManager;
import com.example.thiennguyen.view.login.LoginActivity;
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

public class ChiTietChienDichHomeActivity extends AppCompatActivity {
    ImageView imageViewhinhAnhctcd, img_back_tohome;
    TextView tvdm_ctcd, tvtencdctcd_home, tvsoTienMucTieu_ctcd_home, tvsongayconlai_ctcd_home, tvsoTienHienTai_ctcd_home, tvmoTa_ctcd_home;
    List<ChienDich> chienDichListHome;
    List<DanhMuc> danhMuclistHome;
    List<NguoiDung> nguoiDungListHome;
    Button btnDkyThamGia;
    ChienDichApi chienDichApi = ApiClient.getRetrofit().create(ChienDichApi.class);
    ThamGiaChienDichApi thamGiaChienDichApi = ApiClient.getRetrofit().create(ThamGiaChienDichApi.class);

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
        btnDkyThamGia.setOnClickListener(v -> {
            dangKyChienDich();
        });
    }

    private void dangKyChienDich() {
        String rawToken = DataLocalManager.getToken();

        // 🔴 Token null hoặc rỗng → chuyển sang Login
        if (rawToken == null || rawToken.trim().isEmpty()) {
            Intent intent1 = new Intent(ChiTietChienDichHomeActivity.this, LoginActivity.class);
            startActivity(intent1);
        }

        String token = "Bearer " + rawToken;

        Intent intent = getIntent();
        int idChienDich = intent.getIntExtra("ID_CHIEN_DICH",-1);
        if (idChienDich != -1){
            Call<ApiResponse<ThamGiaChienDichResponse>> callThamGiaCD = thamGiaChienDichApi.createThamGiaChienDich(idChienDich,token);
            callThamGiaCD.enqueue(new Callback<ApiResponse<ThamGiaChienDichResponse>>() {
                @Override
                public void onResponse(Call<ApiResponse<ThamGiaChienDichResponse>> call, Response<ApiResponse<ThamGiaChienDichResponse>> response) {
                    if(response.isSuccessful() && response.body()!= null){
                        Toast.makeText(ChiTietChienDichHomeActivity.this, "Bạn đã đăng ký tham gia thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ChiTietChienDichHomeActivity.this, "Bạn đã đăng ký rồi!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<ThamGiaChienDichResponse>> call, Throwable t) {
                    Toast.makeText(ChiTietChienDichHomeActivity.this, "Lỗi mạng!", Toast.LENGTH_SHORT).show();
                }
            });
        }
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

    private void initListData() {
        chienDichListHome = new ArrayList<>();
        danhMuclistHome = new ArrayList<>();
        nguoiDungListHome = new ArrayList<>();

        // Dữ liệu mẫu
        NguoiDung nd1 = new NguoiDung(1, "Quỹ Trái Tim Hảo Tâm", "quytraitim@example.com", "0123456789", "123456");
        NguoiDung nd2 = new NguoiDung(2, "Hội Chữ Thập Xanh", "hoichuthapxanh@example.com", "0987654321", "123456");
        nguoiDungListHome.add(nd1);
        nguoiDungListHome.add(nd2);


        DanhMuc dm1 = new DanhMuc(1, "Giáo dục");
        DanhMuc dm2 = new DanhMuc(2, "Y tế");
        danhMuclistHome.add(dm1);
        danhMuclistHome.add(dm2);

        chienDichListHome.add(new ChienDich(1, "Sách cho em", "https://picsum.photos/400/200", new BigDecimal("50000000"), new BigDecimal("25000000"), new Date(), new Date(), "Quyên góp sách giáo khoa...", "Giấy phép ABC", "Đối tượng XYZ", "Khu vực 1", nd1, dm1));
        chienDichListHome.add(new ChienDich(2, "Áo ấm mùa đông", "https://picsum.photos/401/200", new BigDecimal("80000000"), new BigDecimal("65000000"), new Date(), new Date(), "Tặng áo ấm cho các em nhỏ...", "Giấy phép DEF", "Đối tượng PQR", "Khu vực 2", nd2, dm1));
        chienDichListHome.add(new ChienDich(3, "Hỗ trợ viện phí", "https://picsum.photos/402/200", new BigDecimal("100000000"), new BigDecimal("15000000"), new Date(), new Date(), "Giúp đỡ các bệnh nhi...", "Giấy phép GHI", "Đối tượng TUV", "Khu vực 3", nd1, dm2));
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
        btnDkyThamGia = findViewById(R.id.btnDkyThamGia);
    }
}