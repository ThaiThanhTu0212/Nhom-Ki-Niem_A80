package com.example.thiennguyen.view.TrangChu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.thiennguyen.view.data.DTO.Response.NguoiDungResponse;
import com.example.thiennguyen.view.data.api.NguoiDungApi;
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

public class ProfileHomeActivity extends AppCompatActivity {
    ImageView imageAvatarProfileHome, imageViewBackgroundProfileHme;
    TextView tvNameProfile;
    Button btnFollowProfileHome;

    NguoiDungApi nguoiDungApi = ApiClient.getRetrofit().create(NguoiDungApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUI();
        getProfileHomeAPI();

    }

    private void getProfileHomeAPI() {
        Intent intent =getIntent();
        int idND = intent.getIntExtra("ID_NGUOI_DUNG",-1);

        if (idND != -1) {
            Call<ApiResponse<NguoiDungResponse>> callGetNguoiDungByid = nguoiDungApi.getNguoiDungByIdHome(idND);
            callGetNguoiDungByid.enqueue(new Callback<ApiResponse<NguoiDungResponse>>() {

                @Override
                public void onResponse(Call<ApiResponse<NguoiDungResponse>> call, Response<ApiResponse<NguoiDungResponse>> response) {
                    if (response.isSuccessful()&& response.body()!=null){
                        SetProfileInformHomeUI(response.body().getResult());
                        Log.d("DATA", "NguoiDung: " + response.body().getResult().getHoTen());
                        Log.d("DATA", "Avatar: " + response.body().getResult().getAvatar());
                        Log.e("Number",String.valueOf(idND));
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<NguoiDungResponse>> call, Throwable t) {
                    Toast.makeText(ProfileHomeActivity.this, "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Log.e("ERORR","loix ");
        }
    }

    private void SetProfileInformHomeUI(NguoiDungResponse nd) {
                    tvNameProfile.setText(nd.getHoTen());
                    Glide.with(this)
                            .load(nd.getAvatar())
                            .placeholder(R.drawable.nguoidung)
                            .error(R.drawable.nguoidung)
                            .into(imageAvatarProfileHome);
    }

    private void initUI() {
        tvNameProfile = findViewById(R.id.tvNameProfile);
        imageAvatarProfileHome = findViewById(R.id.imageAvatarProfileHome);
        btnFollowProfileHome = findViewById(R.id.btnFollowProfileHome);
        imageViewBackgroundProfileHme = findViewById(R.id.imageViewBackgroundProfileHme);
    }

}