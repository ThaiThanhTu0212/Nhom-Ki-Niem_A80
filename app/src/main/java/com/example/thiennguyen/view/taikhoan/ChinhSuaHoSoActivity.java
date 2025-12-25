package com.example.thiennguyen.view.taikhoan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.data.ApiClient;
import com.example.thiennguyen.view.data.DTO.ApiResponse;
import com.example.thiennguyen.view.data.DTO.request.UpdateProfileRequest;
import com.example.thiennguyen.view.data.api.NguoiDungApi;
import com.example.thiennguyen.view.data.sharepreference.DataLocalManager;
import com.example.thiennguyen.view.model.NguoiDung;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChinhSuaHoSoActivity extends AppCompatActivity {

    private EditText edtHoTen, edtPhone, edtEmail;
    private ImageView ivAvatarEdit, btnBack;
    private Button btnSave;
    private NguoiDung user;
    private NguoiDungApi nguoiDungApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_ho_so);

        nguoiDungApi = ApiClient.getRetrofit().create(NguoiDungApi.class);

        // Ánh xạ View
        edtHoTen = findViewById(R.id.edtHoTen);
        edtPhone = findViewById(R.id.edtSoDienThoai);
        edtEmail = findViewById(R.id.edtEmail);
        ivAvatarEdit = findViewById(R.id.ivAvatarEdit);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnLuuThayDoi);

        // Nhận dữ liệu
        user = (NguoiDung) getIntent().getSerializableExtra("user_data");
        if (user != null) {
            edtHoTen.setText(user.getHoTen());
            edtPhone.setText(user.getSoDienThoai());
            edtEmail.setText(user.getEmail());
            if (user.getAvatarUrl() != null) {
                Glide.with(this).load(user.getAvatarUrl()).circleCrop().into(ivAvatarEdit);
            }
        }

        // Sự kiện
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        if (btnSave != null) btnSave.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String name = edtHoTen.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edtHoTen.setError("Vui lòng nhập tên");
            return;
        }

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(name, phone);
        String token = "Bearer " + DataLocalManager.getToken();
        Call<ApiResponse<Void>> call = nguoiDungApi.updateMyInfo(token, updateProfileRequest);

        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ChinhSuaHoSoActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    if (user != null) {
                        user.setHoTen(name);
                        user.setSoDienThoai(phone);
                    }
                    Intent result = new Intent();
                    result.putExtra("updated_user", user);
                    setResult(Activity.RESULT_OK, result);
                    finish();
                } else {
                    Toast.makeText(ChinhSuaHoSoActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Toast.makeText(ChinhSuaHoSoActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
