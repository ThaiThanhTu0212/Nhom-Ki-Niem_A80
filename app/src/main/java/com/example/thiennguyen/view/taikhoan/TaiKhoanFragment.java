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
 * ===================================================================================
 * LỚP: TaiKhoanFragment
 * ===================================================================================
 * MỤC ĐÍCH:
 * - Quản lý giao diện và các chức năng liên quan đến tài khoản người dùng.
 * - Hiển thị thông tin cá nhân khi người dùng đã đăng nhập.
 * - Cung cấp các tùy chọn đăng nhập, đăng ký khi người dùng chưa đăng nhập.
 * - Xử lý các hành động như đăng xuất, chỉnh sửa hồ sơ.
 * ===================================================================================
 */
public class TaiKhoanFragment extends Fragment {

    // ---------------------------------------------------------------------------------
    // KHAI BÁO BIẾN THÀNH VIÊN (MEMBER VARIABLES)
    // ---------------------------------------------------------------------------------

    // Các thành phần hình ảnh
    private ImageView ivBanner;           // Hình ảnh banner của người dùng
    private ImageView ivAvatar;           // Hình ảnh đại diện (avatar) của người dùng
    private ImageView ivCameraAvatar;     // Icon camera để thay đổi avatar
    private ImageView ivCameraBanner;     // Icon camera để thay đổi banner
    private ImageView ivGmail;            // Icon Gmail

    // Các thành phần văn bản
    private TextView tvUserName;        // Tên hiển thị của người dùng
    private TextView tvUserId;          // ID hoặc email của người dùng
    private TextView tvFollowers;       // Số người theo dõi
    private TextView tvPosts;           // Số bài đăng
    private TextView tvOr;              // Chữ "hoặc" giữa nút đăng nhập và đăng ký

    // Các thành phần thống kê
    private TextView tvDonationDays;    // Số ngày quyên góp
    private TextView tvCampaignsJoined; // Số chiến dịch đã tham gia
    private TextView tvSupportCount;    // Số lượt hỗ trợ
    private TextView tvBtnLogOut;       // Nút đăng xuất (dưới dạng TextView)

    // Các nút bấm
    private Button btnEditProfile;      // Nút chỉnh sửa hồ sơ
    private Button btnLoginTaiKhoan;    // Nút điều hướng đến trang đăng nhập
    private Button btnDangKy2;          // Nút điều hướng đến trang đăng ký

    // Thành phần TabLayout
    private TabLayout tabLayout;          // Thanh tab để hiển thị các mục con

    // Các layout container để quản lý hiển thị
    private View llStats;              // Layout chứa các thống kê
    private View llStatsRow;           // Layout hàng thống kê
    private View llFollowing;         // Layout chứa thông tin theo dõi
    private View cvActivity;            // CardView cho hoạt động gần đây

    // View chính của Fragment
    private View view;

    // Model chứa dữ liệu người dùng hiện tại
    private NguoiDung currentUser;

    // Khởi tạo API service cho xác thực
    AuthenticationApi authenticationApi = ApiClient.getRetrofit().create(AuthenticationApi.class);

    // Khởi tạo API service cho các chức năng người dùng
    NguoiDungApi nguoiDungApi = ApiClient.getRetrofit().create(NguoiDungApi.class);


    /**
     * ===================================================================================
     * PHƯƠNG THỨC: onCreateView
     * ===================================================================================
     * MỤC ĐÍCH:
     * - Được gọi để tạo và trả về hệ thống view của Fragment.
     * - Đây là nơi layout của fragment được "inflate" (đọc và chuyển thành đối tượng View).
     * ===================================================================================
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        // Log để theo dõi vòng đời của Fragment (tùy chọn)
        // Log.d("TaiKhoanFragment", "onCreateView called");

        // Inflate layout XML vào một đối tượng View
        view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);

        // Gọi phương thức để ánh xạ các view từ layout
        initViews();

        // Thiết lập giao diện dựa trên trạng thái đăng nhập của người dùng
        setupUserData();

        // Gán sự kiện cho chức năng đăng xuất
        Logout();

        // Trả về view gốc cho hệ thống Android hiển thị
        return view;
    }

    /**
     * ===================================================================================
     * PHƯƠNG THỨC: Logout
     * ===================================================================================
     * MỤC ĐÍCH:
     * - Xử lý hành động đăng xuất của người dùng.
     * - Xóa token xác thực, điều hướng người dùng về màn hình chính và kết thúc phiên làm việc.
     * ===================================================================================
     */
    private void Logout() {
        
        // Gán một OnClickListener cho TextView đăng xuất
        tvBtnLogOut.setOnClickListener(v -> {
            
            // Xóa token khỏi SharedPreferences để đăng xuất người dùng
            DataLocalManager.setToken("");

            // Tạo một Intent để mở lại MainActivity
            Intent intent = new Intent(getContext(), MainActivity.class);

            // Bắt đầu MainActivity
            startActivity(intent);

            // Hiển thị một thông báo ngắn để xác nhận đã đăng xuất
            Toast.makeText(getContext(), "Đã đăng xuất!", Toast.LENGTH_SHORT).show();

            // Đóng Activity hiện tại để ngăn người dùng quay lại màn hình trước đó bằng nút back
            requireActivity().finish();
        });
    }

    /**
     * ===================================================================================
     * PHƯƠNG THỨC: LoadMyInform
     * ===================================================================================
     * MỤC ĐÍCH:
     * - Tải thông tin chi tiết của người dùng đang đăng nhập từ máy chủ.
     * - Sử dụng Retrofit để thực hiện yêu cầu API và cập nhật giao diện người dùng.
     * ===================================================================================
     */
    public void LoadMyInform() {
        
        // Lấy token của người dùng từ DataLocalManager
        String tokenValue = DataLocalManager.getToken();

        // Kiểm tra xem token có hợp lệ không. Nếu không, yêu cầu đăng nhập lại.
        if (tokenValue == null || tokenValue.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
            return; // Thoát khỏi phương thức nếu không có token
        }
        
        // Chuẩn bị token cho tiêu đề Authorization của yêu cầu API
        String token = "Bearer " + tokenValue;

        // Tạo một cuộc gọi API để lấy thông tin cá nhân
        Call<ApiResponse<NguoiDungResponse>> callGetMyInform = nguoiDungApi.getMyInfo(token);

        // Gửi yêu cầu API một cách bất đồng bộ
        callGetMyInform.enqueue(new Callback<ApiResponse<NguoiDungResponse>>() {
            
            /**
             * Phương thức được gọi khi có phản hồi từ máy chủ.
             */
            @Override
            public void onResponse(Call<ApiResponse<NguoiDungResponse>> call, Response<ApiResponse<NguoiDungResponse>> response) {
                
                // Kiểm tra nếu yêu cầu thành công (mã 2xx) và có nội dung trả về
                if (response.isSuccessful() && response.body() != null && response.body().getResult() != null) {
                    
                    // Lấy đối tượng kết quả từ phản hồi
                    NguoiDungResponse nguoiDungResponse = response.body().getResult();

                    // Tạo một đối tượng NguoiDung mới từ dữ liệu phản hồi
                    currentUser = new NguoiDung(
                        nguoiDungResponse.getIdNd(), 
                        nguoiDungResponse.getHoTen(), 
                        nguoiDungResponse.getEmail(), 
                        nguoiDungResponse.getSoDienThoai(), 
                        nguoiDungResponse.getEmail() // Tạm thời dùng email làm userIdTag
                    );
                    
                    // Thiết lập URL ảnh đại diện cho đối tượng người dùng
                    currentUser.setAvatarUrl(nguoiDungResponse.getAvatar());

                    // Cập nhật tên người dùng trên giao diện
                    tvUserName.setText(nguoiDungResponse.getHoTen() != null ? nguoiDungResponse.getHoTen() : "");

                    // Sử dụng Glide để tải và hiển thị ảnh đại diện
                    Glide.with(view.getContext())
                            .load(nguoiDungResponse.getAvatar())           // Nguồn ảnh
                            .error(R.drawable.tai_khoan)                   // Ảnh mặc định khi lỗi
                            .placeholder(R.drawable.tai_khoan)            // Ảnh tạm thời khi đang tải
                            .into(ivAvatar);                              // ImageView đích

                    // Sử dụng Glide để tải và hiển thị ảnh bìa
                    Glide.with(view.getContext())
                            .load(nguoiDungResponse.getAvatar())           // Tạm thời dùng avatar làm banner
                            .error(R.drawable.tai_khoan)                   // Ảnh mặc định khi lỗi
                            .placeholder(R.drawable.tai_khoan)            // Ảnh tạm thời khi đang tải
                            .into(ivBanner);                              // ImageView đích

                    // Cập nhật email người dùng trên giao diện
                    tvUserId.setText(nguoiDungResponse.getEmail());

                } else {
                    // Xử lý trường hợp phản hồi không thành công
                    if (response.code() == 401) {
                        // Mã 401: Unauthorized - Phiên đăng nhập hết hạn
                        Toast.makeText(getContext(), "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Các lỗi khác từ máy chủ
                        Toast.makeText(getContext(), "Không thể tải thông tin người dùng!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            /**
             * Phương thức được gọi khi yêu cầu API thất bại (ví dụ: lỗi mạng).
             */
            @Override
            public void onFailure(Call<ApiResponse<NguoiDungResponse>> call, Throwable t) {
                // Hiển thị thông báo lỗi kết nối
                Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * ===================================================================================
     * PHƯƠNG THỨC: setupUserData
     * ===================================================================================
     * MỤC ĐÍCH:
     * - Kiểm tra trạng thái đăng nhập và cấu hình giao diện cho phù hợp.
     * - Nếu đã đăng nhập: hiển thị thông tin người dùng và các chức năng liên quan.
     * - Nếu chưa đăng nhập: hiển thị các nút đăng nhập/đăng ký.
     * ===================================================================================
     */
    private void setupUserData() {
        
        // Lấy token từ local storage để kiểm tra
        String token = DataLocalManager.getToken();

        // Nếu token không tồn tại hoặc rỗng -> người dùng chưa đăng nhập
        if (token == null || token.isEmpty()) {
            
            // --- CHƯA ĐĂNG NHẬP ---

            // Cài đặt văn bản mặc định
            tvUserName.setText("Mời bạn đăng nhập");

            // Ẩn các thành phần không cần thiết
            tvUserId.setVisibility(View.GONE);

            // Thiết lập sự kiện click cho nút Đăng nhập
            btnLoginTaiKhoan.setOnClickListener(v -> {
                startActivity(new Intent(getContext(), LoginActivity.class));
            });

            // Thiết lập sự kiện click cho nút Đăng ký
            btnDangKy2.setOnClickListener(v -> {
                startActivity(new Intent(getContext(), RegistrationActivity.class));
            });

            // Ẩn các icon không dành cho người dùng chưa đăng nhập
            ivCameraAvatar.setVisibility(View.GONE);
            ivCameraBanner.setVisibility(View.GONE);

            // Ẩn các layout và view không cần thiết
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
            
            // --- ĐÃ ĐĂNG NHẬP ---

            // Hiển thị các thành phần dành cho người dùng đã đăng nhập
            tvUserId.setVisibility(View.VISIBLE);

            // Cập nhật văn bản cho nút
            btnEditProfile.setText("Chỉnh sửa hồ sơ");

            // Thiết lập sự kiện click cho nút Chỉnh sửa hồ sơ
            btnEditProfile.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), ChinhSuaHoSoActivity.class);
                intent.putExtra("user_data", currentUser);
                startActivity(intent);
            });

            // Hiển thị các icon camera
            ivCameraAvatar.setVisibility(View.VISIBLE);
            ivCameraBanner.setVisibility(View.VISIBLE);

            // Hiển thị lại các layout đã bị ẩn
            llStats.setVisibility(View.VISIBLE);
            ivGmail.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            cvActivity.setVisibility(View.VISIBLE);
            llStatsRow.setVisibility(View.VISIBLE);
            llFollowing.setVisibility(View.VISIBLE);

            // Ẩn các nút không cần thiết khi đã đăng nhập
            btnLoginTaiKhoan.setVisibility(View.GONE);
            btnDangKy2.setVisibility(View.GONE);
            tvOr.setVisibility(View.GONE);


            // Bắt đầu quá trình tải thông tin người dùng từ máy chủ
            LoadMyInform();
        }
    }

    /**
     * ===================================================================================
     * PHƯƠNG THỨC: initViews
     * ===================================================================================
     * MỤC ĐÍCH:
     * - Ánh xạ các biến thành viên với các View tương ứng trong layout XML.
     * - Tập trung tất cả các lệnh `findViewById` vào một nơi để dễ quản lý.
     * ===================================================================================
     */
    private void initViews() {
        
        // --- Ánh xạ ImageView ---
        ivBanner = view.findViewById(R.id.ivBanner);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        ivCameraAvatar = view.findViewById(R.id.ivCameraAvatar);
        ivCameraBanner = view.findViewById(R.id.ivCameraBanner);
        ivGmail = view.findViewById(R.id.ivGmail);

        // --- Ánh xạ TextView cho thông tin người dùng ---
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserId = view.findViewById(R.id.tvUserId);
        tvFollowers = view.findViewById(R.id.tvFollowers);
        tvPosts = view.findViewById(R.id.tvPosts);

        // --- Ánh xạ TextView cho các thống kê ---
        tvDonationDays = view.findViewById(R.id.tvDonationDays);
        tvCampaignsJoined = view.findViewById(R.id.tvCampaignsJoined);
        tvSupportCount = view.findViewById(R.id.tvSupportCount);

        // --- Ánh xạ Button ---
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        // --- Ánh xạ TabLayout ---
        tabLayout = view.findViewById(R.id.tabLayout);

        // --- Ánh xạ các Layout container ---
        llStats = view.findViewById(R.id.llStats);
        llStatsRow = view.findViewById(R.id.llStatsRow);
        llFollowing = view.findViewById(R.id.llFollowing);
        cvActivity = view.findViewById(R.id.cvActivity);

        // --- Ánh xạ các thành phần còn lại ---
        tvBtnLogOut = view.findViewById(R.id.tvBtnLogOut);
        btnLoginTaiKhoan = view.findViewById(R.id.btnLoginTaiKhoan);
        btnDangKy2 = view.findViewById(R.id.btnDangKy2);
        tvOr = view.findViewById(R.id.tvOr);
    }
}
