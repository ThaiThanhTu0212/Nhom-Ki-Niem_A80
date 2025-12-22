package com.example.thiennguyen.view.taikhoan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.NguoiDung;
import com.google.android.material.tabs.TabLayout;

// Import thư viện load ảnh (nếu có dùng Glide)
// import com.bumptech.glide.Glide;

public class TaiKhoanFragment extends Fragment {

    // Views
    private ImageView ivBanner, ivAvatar, ivCameraAvatar, ivCameraBanner;
    private TextView tvUserName, tvUserId, tvFollowers, tvPosts;
    private TextView tvDonationDays, tvCampaignsJoined, tvSupportCount;
    private Button btnEditProfile;
    private TabLayout tabLayout;

    // Layout chứa nội dung thay đổi khi bấm Tab (Cần thêm ID này vào XML nếu chưa có)
    private TextView tvEmptyState;

    private View view;
    private NguoiDung currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);

        initViews();
        // Giả lập lấy dữ liệu người dùng
        currentUser = new NguoiDung();
        setupUserData();
        setupListeners();

        return view;
    }

    private void initViews() {
        ivBanner = view.findViewById(R.id.ivBanner);
        // Lưu ý: ID trong XML của bạn là cvAvatar (CardView) bọc ImageView,
        // hãy chắc chắn bạn ánh xạ đúng ImageView bên trong hoặc sửa lại ID.
        // Ở đây tôi giả định bạn có id ivAvatar bên trong.
        ivAvatar = view.findViewById(R.id.ivAvatar);

        ivCameraAvatar = view.findViewById(R.id.ivCameraAvatar); // Cần thêm ID này vào XML
        ivCameraBanner = view.findViewById(R.id.ivCameraBanner); // Cần thêm ID này vào XML

        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserId = view.findViewById(R.id.tvUserId);
        tvFollowers = view.findViewById(R.id.tvFollowers);
        tvPosts = view.findViewById(R.id.tvPosts);

        // Các textview thống kê
        tvDonationDays = view.findViewById(R.id.tvDonationDays); // Sửa ID trong XML cho khớp
        tvCampaignsJoined = view.findViewById(R.id.tvCampaignsJoined);
        tvSupportCount = view.findViewById(R.id.tvSupportCount);

        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        tabLayout = view.findViewById(R.id.tabLayout);

        // View hiển thị "Không có nội dung"
        // Bạn nên đặt ID cho TextView "Không có nội dung hiển thị" trong XML là @+id/tvEmptyState
        tvEmptyState = view.findViewById(R.id.tvEmptyState);
    }

    private void setupUserData() {
        if (currentUser == null) return;

        tvUserName.setText(currentUser.getTenHienThi());
        tvUserId.setText(currentUser.getUserId());
        tvFollowers.setText(currentUser.getSoNguoiTheoDoi());
        tvPosts.setText(currentUser.getSoBaiViet());

        // Hiển thị các chỉ số
        tvDonationDays.setText(currentUser.getSoTienQuyenGop());
        tvCampaignsJoined.setText(currentUser.getSoChienDichThamGia());
        tvSupportCount.setText(currentUser.getSoLuotHoTro());

        // Load ảnh (Ví dụ dùng resource tĩnh, sau này dùng Glide load URL)
        // ivAvatar.setImageResource(R.drawable.hoi_chuthapdo_avatar);
    }

    private void setupListeners() {
        // 1. Nút Chỉnh sửa hồ sơ
        btnEditProfile.setOnClickListener(v -> {
            // Chuyển sang Activity chỉnh sửa (Cần tạo file ChinhSuaHoSoActivity)
            Intent intent = new Intent(getActivity(), ChinhSuaHoSoActivity.class);
            startActivity(intent);
        });

        // 2. Click vào Camera để đổi ảnh
        View.OnClickListener changeImageListener = v -> {
            Toast.makeText(getContext(), "Chức năng đổi ảnh đang phát triển", Toast.LENGTH_SHORT).show();
            // Sau này code mở thư viện ảnh tại đây
        };

        if(ivCameraAvatar != null) ivCameraAvatar.setOnClickListener(changeImageListener);
        if(ivCameraBanner != null) ivCameraBanner.setOnClickListener(changeImageListener);

        // 3. Xử lý Tab Layout (Hoạt động / Thành tựu / Chiến dịch)
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                updateTabContent(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    // Hàm cập nhật nội dung bên dưới khi bấm Tab
    private void updateTabContent(int position) {
        switch (position) {
            case 0: // Hoạt động
                tvEmptyState.setText("Chưa có hoạt động nào gần đây");
                // TODO: Ẩn/Hiện RecyclerView tương ứng với Hoạt động
                break;
            case 1: // Thành tựu
                tvEmptyState.setText("Chưa có thành tựu nào");
                break;
            case 2: // Chiến dịch
                tvEmptyState.setText("Bạn chưa tham gia chiến dịch nào");
                break;
        }
    }
}