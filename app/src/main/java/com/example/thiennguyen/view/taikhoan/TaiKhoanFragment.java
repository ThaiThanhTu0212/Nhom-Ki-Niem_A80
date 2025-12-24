package com.example.thiennguyen.view.taikhoan;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.MainActivity;
import com.example.thiennguyen.view.data.ApiClient;
import com.example.thiennguyen.view.data.DTO.ApiResponse;
import com.example.thiennguyen.view.data.DTO.Response.AuthenticationResponse;
import com.example.thiennguyen.view.data.DTO.Response.NguoiDungResponse;
import com.example.thiennguyen.view.data.api.AuthenticationApi;
import com.example.thiennguyen.view.data.api.NguoiDungApi;
import com.example.thiennguyen.view.data.api.ThamGiaChienDichApi;
import com.example.thiennguyen.view.data.DTO.Response.ThamGiaChienDichDetailResponse;
import com.example.thiennguyen.view.data.sharepreference.DataLocalManager;
import com.example.thiennguyen.view.login.RegisterActivity;
import com.example.thiennguyen.view.model.NguoiDung;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaiKhoanFragment extends Fragment {

    // Views
    private ImageView ivBanner, ivAvatar, ivCameraAvatar, ivCameraBanner, ivGmail;
    private TextView tvUserName, tvUserId, tvFollowers, tvPosts, tvOr, tvHistory;
    private TextView tvDonationDays, tvCampaignsJoined, tvSupportCount, tvBtnLogOut;
    private Button btnEditProfile,btnLoginTaiKhoan, btnDangKy2;
    private TabLayout tabLayout;
    private View llStats, llStatsRow, llFollowing, cvActivity;
    private RecyclerView recyclerViewThamGia;

    private View view;
    private NguoiDung currentUser;
    private ThamGiaChienDichAdapter thamGiaChienDichAdapter;

    AuthenticationApi authenticationApi = ApiClient.getRetrofit().create(AuthenticationApi.class);
    NguoiDungApi nguoiDungApi = ApiClient.getRetrofit().create(NguoiDungApi.class);
    ThamGiaChienDichApi thamGiaChienDichApi = ApiClient.getRetrofit().create(ThamGiaChienDichApi.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);

        initViews();
        setupRecyclerView();
        setupUserData();
        Logout();

        return view;
    }

    private void Logout() {
        tvBtnLogOut.setOnClickListener(v -> {
            DataLocalManager.setToken("");
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(getContext(), "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
            requireActivity().finish();
        });
    }

    public void LoadMyInform(){
        String tokenValue = DataLocalManager.getToken();
        if (tokenValue == null || tokenValue.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        String token = "Bearer " + tokenValue;
        Call<ApiResponse<NguoiDungResponse>> callGetMyInform = nguoiDungApi.getMyInfo(token);
        callGetMyInform.enqueue(new Callback<ApiResponse<NguoiDungResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<NguoiDungResponse>> call, Response<ApiResponse<NguoiDungResponse>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResult() != null) {
                    NguoiDungResponse nguoiDungResponse = response.body().getResult();
                    // Hiển thị thông tin người dùng
                    tvUserName.setText(nguoiDungResponse.getHoTen() != null ? nguoiDungResponse.getHoTen() : "");
                    Glide.with(view.getContext())
                            .load(nguoiDungResponse.getAvatar())
                            .error(R.drawable.tai_khoan)
                            .placeholder(R.drawable.tai_khoan)
                            .into(ivAvatar);
                    Glide.with(view.getContext())
                            .load(nguoiDungResponse.getAvatar())
                            .error(R.drawable.tai_khoan)
                            .placeholder(R.drawable.tai_khoan)
                            .into(ivBanner);
                    tvUserId.setText(nguoiDungResponse.getEmail());

                } else {
                    // Xử lý khi response không thành công
                    if (response.code() == 401) {
                        Toast.makeText(getContext(), "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Không thể tải thông tin người dùng!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<NguoiDungResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void setupUserData() {
        String token = DataLocalManager.getToken();

        if (token == null || token.isEmpty()) {
            // --- Chưa đăng nhập ---
            tvUserName.setText("Mời bạn đăng nhập");
            tvUserId.setVisibility(View.GONE);

            btnLoginTaiKhoan.setOnClickListener(v -> {
                startActivity(new Intent(getContext(), LoginActivity.class));
            });
            btnDangKy2.setOnClickListener(v -> {
                startActivity(new Intent(getContext(), RegisterActivity.class));
            });

            // Ẩn các icon camera
            ivCameraAvatar.setVisibility(View.GONE);
            ivCameraBanner.setVisibility(View.GONE);

            // Ẩn các phần không cần thiết khi chưa đăng nhập
            llStats.setVisibility(View.GONE);
            ivGmail.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            cvActivity.setVisibility(View.GONE);
            llStatsRow.setVisibility(View.GONE);
            llFollowing.setVisibility(View.GONE);
            ivBanner.setVisibility(View.GONE);
            ivAvatar.setVisibility(View.GONE);
            tvBtnLogOut.setVisibility(View.GONE);
            btnEditProfile.setVisibility(View.GONE);
            tvHistory.setVisibility(View.GONE);

        } else {
            // --- Đã đăng nhập ---
            tvUserId.setVisibility(View.VISIBLE);
            btnEditProfile.setText("Chỉnh sửa hồ sơ");
            btnEditProfile.setOnClickListener(v -> {
                // TODO: Mở activity chỉnh sửa hồ sơ
            });

            // Hiện các icon camera
            ivCameraAvatar.setVisibility(View.VISIBLE);
            ivCameraBanner.setVisibility(View.VISIBLE);

            // Hiện tất cả các phần khi đã đăng nhập
            llStats.setVisibility(View.VISIBLE);
            ivGmail.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            cvActivity.setVisibility(View.VISIBLE);
            llStatsRow.setVisibility(View.VISIBLE);
            llFollowing.setVisibility(View.VISIBLE);
            btnLoginTaiKhoan.setVisibility(View.GONE);
            btnDangKy2.setVisibility(View.GONE);
            tvOr.setVisibility(View.GONE);
            tvHistory.setVisibility(View.VISIBLE);


            // Load user từ API (hoặc dữ liệu local)
            LoadMyInform();
            // Load danh sách tham gia chiến dịch
            loadThamGiaChienDich();
        }
    }

    private void setupRecyclerView() {
        recyclerViewThamGia = view.findViewById(R.id.idReCycleViewDSThamGia);
        if (recyclerViewThamGia == null) {
            android.util.Log.e("TaiKhoanFragment", "RecyclerView not found!");
            return;
        }
        recyclerViewThamGia.setLayoutManager(new LinearLayoutManager(getContext()));
        thamGiaChienDichAdapter = new ThamGiaChienDichAdapter();
        recyclerViewThamGia.setAdapter(thamGiaChienDichAdapter);
        android.util.Log.d("TaiKhoanFragment", "RecyclerView setup completed");
    }

    private void loadThamGiaChienDich() {
        String tokenValue = DataLocalManager.getToken();
        if (tokenValue == null || tokenValue.isEmpty()) {
            android.util.Log.e("TaiKhoanFragment", "Token is null or empty");
            return;
        }

        String token = "Bearer " + tokenValue;
        android.util.Log.d("TaiKhoanFragment", "Calling API with token: " + (tokenValue.length() > 10 ? tokenValue.substring(0, 10) + "..." : tokenValue));
        android.util.Log.d("TaiKhoanFragment", "Base URL: " + com.example.thiennguyen.view.data.ApiClient.URL_BASE);
        
        Call<ApiResponse<List<ThamGiaChienDichDetailResponse>>> call = thamGiaChienDichApi.getThamGiaChienDichByid(token);
        call.enqueue(new Callback<ApiResponse<List<ThamGiaChienDichDetailResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ThamGiaChienDichDetailResponse>>> call, 
                                 Response<ApiResponse<List<ThamGiaChienDichDetailResponse>>> response) {
                android.util.Log.d("TaiKhoanFragment", "Response received. Code: " + response.code());
                android.util.Log.d("TaiKhoanFragment", "Response successful: " + response.isSuccessful());
                
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        android.util.Log.d("TaiKhoanFragment", "Response body not null");
                        if (response.body().getResult() != null) {
                            List<ThamGiaChienDichDetailResponse> list = response.body().getResult();
                            android.util.Log.d("TaiKhoanFragment", "Got " + list.size() + " items");
                            if (list.size() > 0) {
                                android.util.Log.d("TaiKhoanFragment", "First item: " + list.get(0).getTenChienDich());
                            }
                            if (thamGiaChienDichAdapter != null) {
                                thamGiaChienDichAdapter.setThamGiaChienDichList(list);
                                android.util.Log.d("TaiKhoanFragment", "Adapter updated with " + list.size() + " items");
                            } else {
                                android.util.Log.e("TaiKhoanFragment", "Adapter is null!");
                            }
                        } else {
                            android.util.Log.e("TaiKhoanFragment", "Result is null");
                            Toast.makeText(getContext(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        android.util.Log.e("TaiKhoanFragment", "Response body is null");
                        Toast.makeText(getContext(), "Không có dữ liệu trả về!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    android.util.Log.e("TaiKhoanFragment", "Response not successful. Code: " + response.code());
                    String errorBody = "";
                    if (response.errorBody() != null) {
                        try {
                            errorBody = response.errorBody().string();
                            android.util.Log.e("TaiKhoanFragment", "Error body: " + errorBody);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (response.code() == 401) {
                        Toast.makeText(getContext(), "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Lỗi " + response.code() + ": " + errorBody, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<ThamGiaChienDichDetailResponse>>> call, Throwable t) {
                android.util.Log.e("TaiKhoanFragment", "Request failed", t);
                String errorMsg = "Lỗi kết nối";
                if (t != null) {
                    errorMsg += ": " + t.getMessage();
                    android.util.Log.e("TaiKhoanFragment", "Error: " + t.getClass().getName() + " - " + t.getMessage());
                    t.printStackTrace();
                }
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initViews() {
        // Images
        ivBanner = view.findViewById(R.id.ivBanner);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        ivCameraAvatar = view.findViewById(R.id.ivCameraAvatar);
        ivCameraBanner = view.findViewById(R.id.ivCameraBanner);
        ivGmail = view.findViewById(R.id.ivGmail);

        // User Info
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserId = view.findViewById(R.id.tvUserId);
        tvFollowers = view.findViewById(R.id.tvFollowers);
        tvPosts = view.findViewById(R.id.tvPosts);

        // Stats
        tvDonationDays = view.findViewById(R.id.tvDonationDays);
        tvCampaignsJoined = view.findViewById(R.id.tvCampaignsJoined);
        tvSupportCount = view.findViewById(R.id.tvSupportCount);

        // Button
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        // Tabs
        tabLayout = view.findViewById(R.id.tabLayout);

        // Sections to hide/show
        llStats = view.findViewById(R.id.llStats);
        llStatsRow = view.findViewById(R.id.llStatsRow);
        llFollowing = view.findViewById(R.id.llFollowing);
        cvActivity = view.findViewById(R.id.cvActivity);

        tvBtnLogOut = view.findViewById(R.id.tvBtnLogOut);
        btnLoginTaiKhoan = view.findViewById(R.id.btnLoginTaiKhoan);
        btnDangKy2 = view.findViewById(R.id.btnDangKy2);
        tvOr = view.findViewById(R.id.tvOr);
        tvHistory = view.findViewById(R.id.tvHistory);



    }
}
