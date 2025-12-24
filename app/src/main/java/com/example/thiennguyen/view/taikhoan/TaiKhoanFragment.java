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
import com.example.thiennguyen.view.data.DTO.Response.NguoiDungResponse;
import com.example.thiennguyen.view.data.api.AuthenticationApi;
import com.example.thiennguyen.view.data.api.NguoiDungApi;
import com.example.thiennguyen.view.data.sharepreference.DataLocalManager;
import com.example.thiennguyen.view.model.NguoiDung;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * TaiKhoanFragment để quản lý giao diện và chức năng liên quan đến tài khoản người dùng.
 *
 * Bao gồm các chức năng như hiển thị thông tin người dùng, đăng nhập, đăng ký,
 * đăng xuất và chỉnh sửa hồ sơ.
 */
public class TaiKhoanFragment extends Fragment {

    // =============================================================================================
    //
    // Các biến thành viên để lưu trữ các View trong layout.
    //
    // =============================================================================================

    // Các View liên quan đến hình ảnh
    private ImageView ivBanner, ivAvatar, ivCameraAvatar, ivCameraBanner, ivGmail;

    // Các View liên quan đến văn bản
    private TextView tvUserName, tvUserId, tvFollowers, tvPosts, tvOr;

    // Các View liên quan đến thống kê
    private TextView tvDonationDays, tvCampaignsJoined, tvSupportCount, tvBtnLogOut;

    // Các nút bấm
    private Button btnEditProfile, btnLoginTaiKhoan, btnDangKy2;

    // TabLayout để hiển thị các tab
    private TabLayout tabLayout;

    // Các View layout để ẩn/hiện
    private View llStats, llStatsRow, llFollowing, cvActivity;

    // View gốc của Fragment
    private View view;

    // Đối tượng người dùng hiện tại
    private NguoiDung currentUser;

    // API client để thực hiện các yêu cầu mạng
    AuthenticationApi authenticationApi = ApiClient.getRetrofit().create(AuthenticationApi.class);

    // API client cho các chức năng liên quan đến người dùng
    NguoiDungApi nguoiDungApi = ApiClient.getRetrofit().create(NguoiDungApi.class);

    /**
     * Phương thức này được gọi khi Fragment được tạo.
     *
     * @param inflater           Đối tượng LayoutInflater để inflate layout cho fragment.
     * @param container          ViewGroup cha mà layout của fragment sẽ được gắn vào.
     * @param savedInstanceState Nếu không phải null, fragment này đang được tái tạo từ trạng thái đã lưu trước đó.
     * @return Trả về View gốc cho giao diện của fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho fragment này
        view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);

        // Khởi tạo các view
        initViews();

        // Thiết lập dữ liệu người dùng
        setupUserData();

        // Cài đặt chức năng đăng xuất
        Logout();

        // Trả về view đã được tạo
        return view;
    }

    /**
     * Xử lý sự kiện đăng xuất của người dùng.
     *
     * Xóa token, điều hướng về màn hình chính và hiển thị thông báo.
     */
    private void Logout() {
        // Gán sự kiện click cho nút đăng xuất
        tvBtnLogOut.setOnClickListener(v -> {
            // Xóa token đã lưu
            DataLocalManager.setToken("");

            // Tạo intent để quay về MainActivity
            Intent intent = new Intent(getContext(), MainActivity.class);

            // Bắt đầu activity mới
            startActivity(intent);

            // Hiển thị thông báo đăng xuất thành công
            Toast.makeText(getContext(), "Đã đăng xuất!", Toast.LENGTH_SHORT).show();

            // Kết thúc activity hiện tại
            requireActivity().finish();
        });
    }

    /**
     * Tải thông tin cá nhân của người dùng từ server.
     *
     * Yêu cầu API để lấy thông tin và cập nhật giao diện người dùng.
     */
    public void LoadMyInform() {
        // Lấy token từ bộ nhớ cục bộ
        String tokenValue = DataLocalManager.getToken();

        // Kiểm tra xem token có tồn tại không
        if (tokenValue == null || tokenValue.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
            return; // Dừng thực thi nếu không có token
        }
        
        // Thêm "Bearer " vào trước token để xác thực
        String token = "Bearer " + tokenValue;

        // Gọi API để lấy thông tin người dùng
        Call<ApiResponse<NguoiDungResponse>> callGetMyInform = nguoiDungApi.getMyInfo(token);

        // Thực hiện yêu cầu bất đồng bộ
        callGetMyInform.enqueue(new Callback<ApiResponse<NguoiDungResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<NguoiDungResponse>> call, Response<ApiResponse<NguoiDungResponse>> response) {
                // Kiểm tra xem yêu cầu có thành công và có dữ liệu trả về không
                if (response.isSuccessful() && response.body() != null && response.body().getResult() != null) {
                    // Lấy đối tượng NguoiDungResponse từ kết quả API
                    NguoiDungResponse nguoiDungResponse = response.body().getResult();

                    // Khởi tạo đối tượng currentUser với dữ liệu từ API
                    currentUser = new NguoiDung(nguoiDungResponse.getIdNd(), nguoiDungResponse.getHoTen(), nguoiDungResponse.getEmail(), nguoiDungResponse.getSoDienThoai(), nguoiDungResponse.getEmail()); // Sử dụng email làm userIdTag tạm thời
                    
                    // Cập nhật URL ảnh đại diện cho người dùng
                    currentUser.setAvatarUrl(nguoiDungResponse.getAvatar());

                    // Cập nhật giao diện với thông tin người dùng
                    tvUserName.setText(nguoiDungResponse.getHoTen() != null ? nguoiDungResponse.getHoTen() : "");

                    // Tải và hiển thị avatar bằng Glide
                    Glide.with(view.getContext())
                            .load(nguoiDungResponse.getAvatar())
                            .error(R.drawable.tai_khoan) // Ảnh hiển thị khi có lỗi
                            .placeholder(R.drawable.tai_khoan) // Ảnh hiển thị khi đang tải
                            .into(ivAvatar);

                    // Tải và hiển thị banner bằng Glide
                    Glide.with(view.getContext())
                            .load(nguoiDungResponse.getAvatar())
                            .error(R.drawable.tai_khoan) // Ảnh hiển thị khi có lỗi
                            .placeholder(R.drawable.tai_khoan) // Ảnh hiển thị khi đang tải
                            .into(ivBanner);

                    // Hiển thị email người dùng
                    tvUserId.setText(nguoiDungResponse.getEmail());

                } else {
                    // Xử lý khi response không thành công
                    if (response.code() == 401) { // Lỗi xác thực
                        Toast.makeText(getContext(), "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
                    } else { // Các lỗi khác
                        Toast.makeText(getContext(), "Không thể tải thông tin người dùng!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<NguoiDungResponse>> call, Throwable t) {
                // Xử lý khi có lỗi kết nối mạng
                Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * Thiết lập giao diện người dùng dựa trên trạng thái đăng nhập.
     *
     * Hiển thị các nút đăng nhập/đăng ký nếu chưa đăng nhập, hoặc thông tin
     * người dùng và các chức năng khác nếu đã đăng nhập.
     */
    private void setupUserData() {
        // Lấy token để kiểm tra trạng thái đăng nhập
        String token = DataLocalManager.getToken();

        // Kiểm tra nếu người dùng chưa đăng nhập
        if (token == null || token.isEmpty()) {
            // --- Xử lý trạng thái CHƯA ĐĂNG NHẬP ---

            // Hiển thị lời mời đăng nhập
            tvUserName.setText("Mời bạn đăng nhập");

            // Ẩn ID người dùng
            tvUserId.setVisibility(View.GONE);

            // Gán sự kiện cho nút đăng nhập
            btnLoginTaiKhoan.setOnClickListener(v -> {
                startActivity(new Intent(getContext(), LoginActivity.class));
            });

            // Gán sự kiện cho nút đăng ký
            btnDangKy2.setOnClickListener(v -> {
                startActivity(new Intent(getContext(), RegistrationActivity.class));
            });

            // Ẩn các icon camera không cần thiết
            ivCameraAvatar.setVisibility(View.GONE);
            ivCameraBanner.setVisibility(View.GONE);

            // Ẩn các phần của giao diện không cần thiết khi chưa đăng nhập
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

        } else {
            // --- Xử lý trạng thái ĐÃ ĐĂNG NHẬP ---

            // Hiển thị ID người dùng
            tvUserId.setVisibility(View.VISIBLE);

            // Hiển thị nút chỉnh sửa hồ sơ
            btnEditProfile.setText("Chỉnh sửa hồ sơ");

            // Gán sự kiện cho nút chỉnh sửa hồ sơ
            btnEditProfile.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), ChinhSuaHoSoActivity.class);
                intent.putExtra("user_data", currentUser);
                startActivity(intent);
            });

            // Hiện các icon camera
            ivCameraAvatar.setVisibility(View.VISIBLE);
            ivCameraBanner.setVisibility(View.VISIBLE);

            // Hiện các phần của giao diện dành cho người dùng đã đăng nhập
            llStats.setVisibility(View.VISIBLE);
            ivGmail.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            cvActivity.setVisibility(View.VISIBLE);
            llStatsRow.setVisibility(View.VISIBLE);
            llFollowing.setVisibility(View.VISIBLE);

            // Ẩn các nút đăng nhập và đăng ký
            btnLoginTaiKhoan.setVisibility(View.GONE);
            btnDangKy2.setVisibility(View.GONE);
            tvOr.setVisibility(View.GONE);


            // Tải thông tin người dùng từ API
            LoadMyInform();
        }
    }

    /**
     * Khởi tạo và ánh xạ các View từ layout.
     *
     * Phương thức này tìm và gán các View cho các biến thành viên tương ứng.
     */
    private void initViews() {
        // Ánh xạ các ImageView
        ivBanner = view.findViewById(R.id.ivBanner);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        ivCameraAvatar = view.findViewById(R.id.ivCameraAvatar);
        ivCameraBanner = view.findViewById(R.id.ivCameraBanner);
        ivGmail = view.findViewById(R.id.ivGmail);

        // Ánh xạ các TextView thông tin người dùng
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserId = view.findViewById(R.id.tvUserId);
        tvFollowers = view.findViewById(R.id.tvFollowers);
        tvPosts = view.findViewById(R.id.tvPosts);

        // Ánh xạ các TextView thống kê
        tvDonationDays = view.findViewById(R.id.tvDonationDays);
        tvCampaignsJoined = view.findViewById(R.id.tvCampaignsJoined);
        tvSupportCount = view.findViewById(R.id.tvSupportCount);

        // Ánh xạ các Button
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        // Ánh xạ TabLayout
        tabLayout = view.findViewById(R.id.tabLayout);

        // Ánh xạ các Layout để ẩn/hiện
        llStats = view.findViewById(R.id.llStats);
        llStatsRow = view.findViewById(R.id.llStatsRow);
        llFollowing = view.findViewById(R.id.llFollowing);
        cvActivity = view.findViewById(R.id.cvActivity);

        // Ánh xạ các View còn lại
        tvBtnLogOut = view.findViewById(R.id.tvBtnLogOut);
        btnLoginTaiKhoan = view.findViewById(R.id.btnLoginTaiKhoan);
        btnDangKy2 = view.findViewById(R.id.btnDangKy2);
        tvOr = view.findViewById(R.id.tvOr);



    }
}
