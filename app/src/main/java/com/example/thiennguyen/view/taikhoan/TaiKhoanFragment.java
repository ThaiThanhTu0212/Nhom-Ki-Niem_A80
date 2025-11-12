package com.example.thiennguyen.view.taikhoan;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.NguoiDung;
import com.google.android.material.tabs.TabLayout;

public class TaiKhoanFragment extends Fragment {

    // Views
    private ImageView ivBanner, ivAvatar, ivCameraAvatar, ivCameraBanner;
    private TextView tvUserName, tvUserId, tvFollowers, tvPosts;
    private TextView tvDonationDays, tvCampaignsJoined, tvSupportCount;
    private Button btnEditProfile;
    private TabLayout tabLayout;

    private View view;
    private NguoiDung currentUser;

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
        currentUser = new NguoiDung(
                1,
                "Nguyễn Thành Phát",
                "phat@example.com",
                "0909123456",
                "matkhau123",
                "nguoi_ung_ho",
                "hoat_dong"
        );

        // Hiển thị thông tin
        tvUserName.setText("ThanhPhat2604");
        tvUserId.setText("@phat123");
        tvFollowers.setText("0");
        tvPosts.setText("0");
        tvDonationDays.setText("0 đ");
        tvCampaignsJoined.setText("0");
        tvSupportCount.setText("0");
    }

    private void setupListeners() {
        // Edit profile button
        btnEditProfile.setOnClickListener(v -> {
            // TODO: Navigate to edit profile screen
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