package com.example.thiennguyen.view.taikhoan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.NguoiDung;
import com.google.android.material.tabs.TabLayout;

public class TaiKhoanFragment extends Fragment {

    // --- Views ---
    private ImageView ivBanner, ivAvatar, ivCameraAvatar, ivCameraBanner, ivSettings;
    private TextView tvUserName, tvUserId, tvFollowers, tvPosts;
    private TextView tvDonationDays, tvCampaignsJoined, tvSupportCount, tvEmptyState;
    private TextView tvOrganizations, tvIndividuals;
    private Button btnEditProfile;
    private TabLayout tabLayout;

    private View view;
    private NguoiDung currentUser;

    // --- Launchers (Xử lý kết quả trả về) ---

    // 1. Nhận kết quả sau khi chỉnh sửa hồ sơ
    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    NguoiDung updatedUser = (NguoiDung) result.getData().getSerializableExtra("updated_user");
                    if (updatedUser != null) {
                        currentUser = updatedUser;
                        updateUI(); // Cập nhật lại giao diện ngay lập tức
                        Toast.makeText(getContext(), "Cập nhật hồ sơ thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);

        initViews();
        setupUserData();
        setupListeners();

        return view;
    }

    private void initViews() {
        // Images
        ivBanner = view.findViewById(R.id.ivBanner);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        ivCameraAvatar = view.findViewById(R.id.ivCameraAvatar);
        ivCameraBanner = view.findViewById(R.id.ivCameraBanner);

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
    }

    private void setupUserData() {
        // Tạo user mẫu
        currentUser = new NguoiDung(1, "ThanhPhat2604", "phat@example.com", "0123456789", "@phat123");
        // Bạn có thể thêm URL ảnh mẫu nếu muốn
        // currentUser.setAvatarUrl("https://example.com/avatar.jpg");
        // currentUser.setBannerUrl("https://example.com/banner.jpg");

        updateUI();
    }
     private void updateUI() {
        if (currentUser == null) return;

        tvUserName.setText(currentUser.getHoTen());
        tvUserId.setText(currentUser.getUserIdTag());
        tvFollowers.setText(currentUser.getDisplayFollowers());
        tvPosts.setText(currentUser.getDisplayPosts());
        tvDonationDays.setText(currentUser.getTongTienUngHo());
        tvCampaignsJoined.setText(currentUser.getDisplayCampaigns());
        tvSupportCount.setText(currentUser.getDisplaySupport());

        // Sử dụng Glide để tải ảnh (nếu có URL)
        // Ví dụ, bạn có thể thêm ảnh mặc định
        Glide.with(this)
                .load(currentUser.getAvatarUrl())
                .placeholder(R.drawable.sontungavatataikhoan) // Ảnh mặc định
                .circleCrop()
                .into(ivAvatar);

        Glide.with(this)
                .load(currentUser.getBannerUrl())
                .placeholder(R.drawable.sontungavatataikhoan) // Ảnh mặc định
                .into(ivBanner);
    }
    private void setupListeners() {
        // Edit profile button
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChinhSuaHoSoActivity.class);
            intent.putExtra("current_user", currentUser);
            editProfileLauncher.launch(intent);
        });

        // Camera icons
        ivCameraAvatar.setOnClickListener(v -> {
            // TODO: Change avatar
        });

        ivCameraBanner.setOnClickListener(v -> {
            // TODO: Change banner
        });

        // Tab selection
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // TODO: Handle tab change
                int position = tab.getPosition();
                switch (position) {
                    case 0: // Hoạt động
                        break;
                    case 1: // Thành tựu
                        break;
                    case 2: // Chiến dịch đóng hành
                        break;
                        
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}