package com.example.thiennguyen.view.taikhoan;

<<<<<<< HEAD
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
=======
import android.content.Intent;
>>>>>>> ThanhPhat_chinhsuahoso
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
<<<<<<< HEAD

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
=======
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.bumptech.glide.Glide;



>>>>>>> ThanhPhat_chinhsuahoso
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.NguoiDung;
import com.google.android.material.tabs.TabLayout;

public class TaiKhoanFragment extends Fragment {

    // --- Views ---
    private ImageView ivBanner, ivAvatar, ivCameraAvatar, ivCameraBanner, ivSettings;
    private TextView tvUserName, tvUserId, tvFollowers, tvPosts;
<<<<<<< HEAD
    private TextView tvDonationDays, tvCampaignsJoined, tvSupportCount, tvEmptyState;
    private TextView tvOrganizations, tvIndividuals;
=======
    private TextView tvDonationDays, tvCampaignsJoined, tvSupportCount;
    private TextView tvEmptyState; // View hiển thị trạng thái trống
    private TextView tvOrganizations, tvIndividuals; // Tab con (Tổ chức/Cá nhân)
>>>>>>> ThanhPhat_chinhsuahoso
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
        createDummyData(); // Tạo dữ liệu mẫu
        setupUserData();   // Hiển thị lên màn hình
        setupListeners();  // Xử lý sự kiện click

        return view;
    }

    private void initViews() {
        ivBanner = view.findViewById(R.id.ivBanner);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        ivCameraAvatar = view.findViewById(R.id.ivCameraAvatar);
        ivCameraBanner = view.findViewById(R.id.ivCameraBanner);

        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserId = view.findViewById(R.id.tvUserId);
        tvFollowers = view.findViewById(R.id.tvFollowers);
        tvPosts = view.findViewById(R.id.tvPosts);

        tvDonationDays = view.findViewById(R.id.tvDonationDays);
        tvCampaignsJoined = view.findViewById(R.id.tvCampaignsJoined);
        tvSupportCount = view.findViewById(R.id.tvSupportCount);

        // Đảm bảo bạn đã thêm ID này vào file XML: android:id="@+id/tvEmptyState"
        tvEmptyState = view.findViewById(R.id.tvEmptyState);
        if (tvEmptyState == null) {
            // Fallback nếu chưa thêm ID trong XML để tránh crash
            // Bạn nên thêm ID vào XML nhé
        }

        tvOrganizations = view.findViewById(R.id.tvOrganizations);
        tvIndividuals = view.findViewById(R.id.tvIndividuals);

        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        tabLayout = view.findViewById(R.id.tabLayout);
    }

    private void createDummyData() {
        // Tạo dữ liệu giả lập cho người dùng
        currentUser = new NguoiDung(
                1,
                "ThanhPhat2604",
                "phat@gmail.com",
                "0909123456",
                "@phat123",
                120, // Followers
                5,   // Posts
                "1.500.000 đ", // Tiền ủng hộ
                3,   // Chiến dịch
                15   // Lượt hỗ trợ
        );
    }

    private void setupUserData() {
        if (currentUser == null) return;

        tvUserName.setText(currentUser.getHoTen());
        tvUserId.setText(currentUser.getUserIdTag());
        tvFollowers.setText(currentUser.getSoNguoiTheoDoi());
        tvPosts.setText(currentUser.getSoBaiViet());

        tvDonationDays.setText(currentUser.getTongTienUngHo());
        tvCampaignsJoined.setText(currentUser.getSoChienDichThamGia());
        tvSupportCount.setText(currentUser.getSoLuotHoTro());

        // TODO: Sử dụng Glide hoặc Picasso để load ảnh từ URL nếu có
        // Glide.with(this).load(currentUser.getAvatar()).into(ivAvatar);
    }

    private void setupListeners() {
        // 1. Chuyển sang màn hình chỉnh sửa
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChinhSuaHoSoActivity.class);
            // Truyền dữ liệu người dùng sang màn hình sửa
            intent.putExtra("user_data", currentUser);
            startActivity(intent);
        });

        // 2. Click vào icon Camera (Giả lập)
        View.OnClickListener cameraClick = v ->
                Toast.makeText(getContext(), "Chức năng thay đổi ảnh đang phát triển", Toast.LENGTH_SHORT).show();

        ivCameraAvatar.setOnClickListener(cameraClick);
        ivCameraBanner.setOnClickListener(cameraClick);

        // 3. Xử lý Tab Layout (Hoạt động / Thành tựu / Chiến dịch)
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateContentBasedOnTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // 4. Xử lý Tab con (Tổ chức / Cá nhân)
        tvOrganizations.setOnClickListener(v -> {
            tvOrganizations.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            tvIndividuals.setTextColor(getResources().getColor(android.R.color.darker_gray));
            if(tvEmptyState != null) tvEmptyState.setText("Chưa theo dõi tổ chức nào.");
        });

        tvIndividuals.setOnClickListener(v -> {
            tvIndividuals.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            tvOrganizations.setTextColor(getResources().getColor(android.R.color.darker_gray));
            if(tvEmptyState != null) tvEmptyState.setText("Chưa theo dõi cá nhân nào.");
        });
    }
    private final ActivityResultLauncher<String> pickAvatarLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    // Load ảnh vừa chọn lên ImageView Avatar
                    Glide.with(this)
                            .load(uri)
                            .circleCrop() // Cắt ảnh hình tròn tự động
                            .into(ivAvatar);

                    // TODO: Tại đây bạn sẽ viết code upload ảnh lên Server
                    currentUser.setAvatar(uri.toString()); // Lưu tạm vào model
                }
            }
    );
    private void updateContentBasedOnTab(int position) {
        if (tvEmptyState == null) return;

        switch (position) {
            case 0: // Hoạt động
                tvEmptyState.setText("Không có hoạt động nào gần đây");
                break;
            case 1: // Thành tựu
                tvEmptyState.setText("Chưa có thành tựu nào được ghi nhận");
                break;
            case 2: // Chiến dịch
                tvEmptyState.setText("Bạn chưa đồng hành cùng chiến dịch nào");
                break;
        }
    }
}