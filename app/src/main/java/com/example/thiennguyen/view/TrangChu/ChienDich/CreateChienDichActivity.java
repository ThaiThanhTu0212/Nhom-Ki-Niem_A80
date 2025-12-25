package com.example.thiennguyen.view.TrangChu.ChienDich;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thiennguyen.R;
import com.example.thiennguyen.view.data.ApiClient;
import com.example.thiennguyen.view.data.DTO.ApiResponse;
import com.example.thiennguyen.view.data.DTO.Response.ChienDichResponse;
import com.example.thiennguyen.view.data.DTO.request.ChienDichRequest;
import com.example.thiennguyen.view.data.api.ChienDichApi;
import com.example.thiennguyen.view.data.sharepreference.DataLocalManager;
import com.example.thiennguyen.view.model.DanhMuc;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateChienDichActivity extends AppCompatActivity {

    private EditText edtTenChiendichHome, edtMoTa, edtHinhAnh, edtSoTienMucTieu, edtSoTienHienTai, edtTrangThai, edtTinhThanh, edtQuanHuyen, edtDiaChi;
    private Spinner spDanhMuc;
    private EditText edtNgayBatDau;
    private EditText edtNgayKetThuc;
    private Button btnCreateChienDich;
    List<DanhMuc> danhMucList = new ArrayList<>();
    DanhMucCreateAdapter danhMucCreateAdapter;
    private final SimpleDateFormat isoDateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    ChienDichApi chienDichApi = ApiClient.getRetrofit().create(ChienDichApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_chien_dich);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUI();
        getAllDanhMuc();

        initListener();

    }

    private void initListener() {
        btnCreateChienDich.setOnClickListener(v -> {
            ChienDichRequest request = setUpChienDichRequest();
            if (request != null) {
                createChienDichHome(request);
            }
        });
    }

    private void createChienDichHome(ChienDichRequest request) {
        String tokenValue = DataLocalManager.getToken();
        if (tokenValue == null || tokenValue.isEmpty()) {
            Toast.makeText(CreateChienDichActivity.this, "Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
            return;
        }

        String token = "Bearer " + tokenValue;

        Call<ApiResponse<ChienDichResponse>> callCreateChienDich = chienDichApi.createChienDichHome(request,token);
        callCreateChienDich.enqueue(new Callback<ApiResponse<ChienDichResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<ChienDichResponse>> call, Response<ApiResponse<ChienDichResponse>> response) {
                if (response.isSuccessful() && response.body()!= null){
                    Toast.makeText(CreateChienDichActivity.this, "Tạo chiến dịch thành công!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ChienDichResponse>> call, Throwable t) {
                Toast.makeText(CreateChienDichActivity.this, "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public ChienDichRequest setUpChienDichRequest(){

        String tenCd = edtTenChiendichHome.getText().toString().trim();
        String moTa = edtMoTa.getText().toString().trim();
        String hinhAnh = edtHinhAnh.getText().toString().trim();
        String trangThai = edtTrangThai.getText().toString().trim();
        String tinhThanh = edtTinhThanh.getText().toString().trim();
        String quanHuyen = edtQuanHuyen.getText().toString().trim();
        String diaChi = edtDiaChi.getText().toString().trim();

        if (TextUtils.isEmpty(moTa)) {
            edtMoTa.setError("Vui lòng nhập mô tả");
            edtMoTa.requestFocus();
            return null;
        }

        BigDecimal soTienMucTieu = parseBigDecimal(edtSoTienMucTieu, true);
        if (soTienMucTieu == null) {
            return null;
        }

        BigDecimal soTienHienTai = parseBigDecimal(edtSoTienHienTai, false);
        String ngayBatDau = edtNgayBatDau.getText().toString().trim();
        String ngayBatDauAPI = "";
        if (ngayBatDau != null) {
            ngayBatDauAPI = convertToApiDate(ngayBatDau);
        }
        String ngayKetThuc = edtNgayKetThuc.getText().toString().trim();
        String ngayKetThucAPI = "";
        if (ngayKetThuc != null) {
            ngayKetThucAPI = convertToApiDate(ngayKetThuc);
        }

        DanhMuc selectedDanhMuc = (DanhMuc) spDanhMuc.getSelectedItem();
        if (selectedDanhMuc == null) {
            Toast.makeText(this, "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();
            return null;
        }

        ChienDichRequest chienDichRequest = new ChienDichRequest();
        chienDichRequest.setTenCd(tenCd);
        chienDichRequest.setMoTa(moTa);
        chienDichRequest.setHinhAnh(hinhAnh);
        chienDichRequest.setSoTienMucTieu(soTienMucTieu);
        chienDichRequest.setSoTienHienTai(soTienHienTai);
        chienDichRequest.setNgayBatDau(ngayBatDauAPI);
        chienDichRequest.setNgayKetThuc(ngayKetThucAPI);
        chienDichRequest.setTrangThai(trangThai);
        chienDichRequest.setTinhThanh(tinhThanh);
        chienDichRequest.setQuanHuyen(quanHuyen);
        chienDichRequest.setDiaChi(diaChi);
        chienDichRequest.setIdDanhMuc(selectedDanhMuc.getIdDm());
        return chienDichRequest;
    }

    private void getAllDanhMuc() {
        Call<ApiResponse<List<DanhMuc>>> callGetAllDanhMuc = chienDichApi.getAllDanhMuc();
        callGetAllDanhMuc.enqueue(new Callback<ApiResponse<List<DanhMuc>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<DanhMuc>>> call, Response<ApiResponse<List<DanhMuc>>> response) {
                if (response.isSuccessful()&& response.body()!= null){
                    danhMucList.clear();
                    danhMucList.addAll(response.body().getResult());
                    danhMucCreateAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<DanhMuc>>> call, Throwable t) {
                Toast.makeText(CreateChienDichActivity.this, "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initUI() {
        edtTenChiendichHome = findViewById(R.id.edtTenChiendichHome);

        edtMoTa = findViewById(R.id.edtMoTa);
        edtHinhAnh = findViewById(R.id.edtHinhAnh);
        edtSoTienMucTieu = findViewById(R.id.edtSoTienMucTieu);
        edtSoTienHienTai = findViewById(R.id.edtSoTienHienTai);
        edtNgayBatDau = findViewById(R.id.edtNgayBatDau);
        edtNgayKetThuc = findViewById(R.id.edtNgayKetThuc);
        edtTrangThai = findViewById(R.id.edtTrangThai);
        edtTinhThanh = findViewById(R.id.edtTinhThanh);
        edtQuanHuyen = findViewById(R.id.edtQuanHuyen);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        spDanhMuc = findViewById(R.id.spDanhMuc);
        btnCreateChienDich = findViewById(R.id.btnCreateChiendichHome);

        setupDanhMucSpinner();

    }

    private void setupDanhMucSpinner() {
        danhMucCreateAdapter = new DanhMucCreateAdapter(this,danhMucList);
        spDanhMuc.setAdapter(danhMucCreateAdapter);
    }


    private BigDecimal parseBigDecimal(EditText source, boolean required) {
        String value = source.getText().toString().trim();
        if (TextUtils.isEmpty(value)) {
            if (required) {
                source.setError("Trường này bắt buộc");
                source.requestFocus();
            }
            return null;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException ex) {
            source.setError("Giá trị không hợp lệ");
            source.requestFocus();
            return null;
        }
    }
    public String convertToApiDate(String input) {
        try {
            SimpleDateFormat userFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            Date date = userFormat.parse(input);
            return apiFormat.format(date);

        } catch (Exception e) {
            return null; // hoặc trả về ""
        }
    }


}