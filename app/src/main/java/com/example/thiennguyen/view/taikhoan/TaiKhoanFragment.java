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

    // Views
    private ImageView ivBanner, ivAvatar, ivCameraAvatar, ivCameraBanner, ivSettings;
    private TextView tvUserName, tvUserId, tvFollowers, tvPosts;
    private TextView tvDonationDays, tvCampaignsJoined, tvSupportCount, tvEmptyState;
    private TextView tvOrganizations, tvIndividuals;
    private Button btnEditProfile;
    private TabLayout tabLayout;

    private View view;
    private NguoiDung currentUser;

    // --- LAUNCHERS ---

    // 1. Edit Profile Result
    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    NguoiDung updatedUser = (NguoiDung) result.getData().getSerializableExtra("updated_user");
                    if (updatedUser != null) {
                        currentUser = updatedUser;
                        updateUI();
                    }
                }
            }
    );

    // 2. Pick Avatar
    private final ActivityResultLauncher<String> pickAvatarLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    currentUser.setAvatarUrl(uri.toString());
                    loadImage(uri, ivAvatar, true);
                }
            }
    );

    // 3. Pick Banner
    private final ActivityResultLauncher<String> pickBannerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    currentUser.setBannerUrl(uri.toString());
                    loadImage(uri, ivBanner, false);
                }
            }
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        initViews();
        initDummyData();
        updateUI();
        setupListeners();
        return view;
    }

    private void initViews() {
        ivBanner = view.findViewById(R.id.ivBanner);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        ivCameraAvatar = view.findViewById(R.id.ivCameraAvatar);
        ivCameraBanner = view.findViewById(R.id.ivCameraBanner);
        ivSettings = view.findViewById(R.id.ivSettings); // Đã có ID này trong XML mới

        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserId = view.findViewById(R.id.tvUserId);
        tvFollowers = view.findViewById(R.id.tvFollowers);
        tvPosts = view.findViewById(R.id.tvPosts);
        tvDonationDays = view.findViewById(R.id.tvDonationDays);
        tvCampaignsJoined = view.findViewById(R.id.tvCampaignsJoined);
        tvSupportCount = view.findViewById(R.id.tvSupportCount);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);

        tvOrganizations = view.findViewById(R.id.tvOrganizations);
        tvIndividuals = view.findViewById(R.id.tvIndividuals);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        tabLayout = view.findViewById(R.id.tabLayout);
    }

<<<<<<< HEAD
    private void setupUserData() {
        // Tạo user mẫu
        currentUser = new NguoiDung(1, "ThanhPhat2604", "phat@example.com", "0123456789", "@phat123");
        // Bạn có thể thêm URL ảnh mẫu nếu muốn
        // currentUser.setAvatarUrl("https://example.com/avatar.jpg");
        // currentUser.setBannerUrl("https://example.com/banner.jpg");

        updateUI();
=======
    private void initDummyData() {
        if (currentUser == null) {
            currentUser = new NguoiDung(1, "ThanhPhat2604", "phat@gmail.com", "0909123456", "@phat123");
        }
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

        if (currentUser.getAvatarUrl() != null) loadImage(Uri.parse(currentUser.getAvatarUrl()), ivAvatar, true);
        if (currentUser.getBannerUrl() != null) loadImage(Uri.parse(currentUser.getBannerUrl()), ivBanner, false);
    }

    private void loadImage(Object source, ImageView target, boolean isCircle) {
        if (getContext() == null) return;
        var request = Glide.with(this).load(source);
        if (isCircle) request = request.circleCrop();
        else request = request.centerCrop();
        request.into(target);
>>>>>>> main
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
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChinhSuaHoSoActivity.class);
<<<<<<< HEAD
            intent.putExtra("current_user", currentUser);
=======
            intent.putExtra("user_data", currentUser);
>>>>>>> main
            editProfileLauncher.launch(intent);
        });

        ivCameraAvatar.setOnClickListener(v -> pickAvatarLauncher.launch("image/*"));
        ivCameraBanner.setOnClickListener(v -> pickBannerLauncher.launch("image/*"));
        ivSettings.setOnClickListener(this::showSettingsMenu);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTabContent(tab.getPosition());
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Filter Org/Individual (Đổi màu chữ khi click)
        View.OnClickListener filterListener = v -> {
            boolean isOrg = v.getId() == R.id.tvOrganizations;
            int orange = 0xFFFF9800; // Mã màu cam
            int gray = 0xFF666666;   // Mã màu xám

            tvOrganizations.setTextColor(isOrg ? orange : gray);
            tvIndividuals.setTextColor(!isOrg ? orange : gray);
            tvEmptyState.setText(isOrg ? "Chưa theo dõi tổ chức nào" : "Chưa theo dõi cá nhân nào");
        };
        tvOrganizations.setOnClickListener(filterListener);
        tvIndividuals.setOnClickListener(filterListener);
    }

    private void showSettingsMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenu().add("Đăng xuất");
        popup.setOnMenuItemClickListener(item -> {
            if ("Đăng xuất".equals(item.getTitle())) {
                Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        popup.show();
    }

    private void updateTabContent(int position) {
        if (tvEmptyState == null) return;
        switch (position) {
            case 0: tvEmptyState.setText("Không có hoạt động nào gần đây"); break;
            case 1: tvEmptyState.setText("Chưa có thành tựu nào"); break;
            case 2: tvEmptyState.setText("Bạn chưa tham gia chiến dịch nào"); break;
        }
    }
}