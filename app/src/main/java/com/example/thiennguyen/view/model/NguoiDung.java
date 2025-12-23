package com.example.thiennguyen.view.model;

<<<<<<< HEAD
import java.io.Serializable;

public class NguoiDung implements Serializable {
    // Các trường cơ bản
=======
import java.io.Serializable; // Thêm Serializable để truyền giữa các Activity

public class NguoiDung implements Serializable {
>>>>>>> ThanhPhat_chinhsuahoso
    private int idNd;
    private String hoTen;
    private String email;
    private String soDienThoai;
<<<<<<< HEAD
    private String userIdTag; // @phat123

    // Dữ liệu ảnh (Lưu dưới dạng String URI hoặc URL)
    private String avatarUrl;
    private String bannerUrl;

    // Dữ liệu thống kê
    private int soNguoiTheoDoi;
    private int soBaiViet;
    private String tongTienUngHo;
    private int soChienDichThamGia;
    private int soLuotHoTro;
=======
    private String matKhau; // Trong thực tế không nên truyền mật khẩu qua UI
    private String vaiTro;
    private String trangThai;
    private String avatar;
>>>>>>> ThanhPhat_chinhsuahoso

    // Thêm các trường thống kê cho UI
    private String userIdTag; // @phat123
    private int soNguoiTheoDoi;
    private int soBaiViet;
    private String tongTienUngHo; // Dùng String để dễ format (ví dụ: "1.500.000 đ")
    private int soChienDichThamGia;
    private int soLuotHoTro;

    public NguoiDung() {
        // Constructor rỗng cần thiết cho một số thao tác
    }

    // Constructor đầy đủ
<<<<<<< HEAD
    public NguoiDung(int idNd, String hoTen, String email, String soDienThoai, String userIdTag, String avatarUrl) {
=======
    public NguoiDung(int idNd, String hoTen, String email, String soDienThoai,
                     String userIdTag, int soNguoiTheoDoi, int soBaiViet,
                     String tongTienUngHo, int soChienDichThamGia, int soLuotHoTro) {
>>>>>>> ThanhPhat_chinhsuahoso
        this.idNd = idNd;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.userIdTag = userIdTag;
<<<<<<< HEAD
        this.avatarUrl = avatarUrl;

        // Dữ liệu giả định ban đầu
        this.soNguoiTheoDoi = 0;
        this.soBaiViet = 0;
        this.tongTienUngHo = "0 đ";
        this.soChienDichThamGia = 0;
        this.soLuotHoTro = 0;
    }

    // --- Getters & Setters ---
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getUserIdTag() { return userIdTag; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getBannerUrl() { return bannerUrl; }
    public void setBannerUrl(String bannerUrl) { this.bannerUrl = bannerUrl; }

    // Helpers trả về String cho TextView
    public String getDisplayFollowers() { return String.valueOf(soNguoiTheoDoi); }
    public String getDisplayPosts() { return String.valueOf(soBaiViet); }
    public String getDisplayCampaigns() { return String.valueOf(soChienDichThamGia); }
    public String getDisplaySupport() { return String.valueOf(soLuotHoTro); }
    public String getTongTienUngHo() { return tongTienUngHo; }
=======
        this.soNguoiTheoDoi = soNguoiTheoDoi;
        this.soBaiViet = soBaiViet;
        this.tongTienUngHo = tongTienUngHo;
        this.soChienDichThamGia = soChienDichThamGia;
        this.soLuotHoTro = soLuotHoTro;
    }

    // --- Getter & Setter ---
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getUserIdTag() { return userIdTag; }

    public String getEmail() { return email; }
    public String getSoDienThoai() { return soDienThoai; }

    public String getSoNguoiTheoDoi() { return String.valueOf(soNguoiTheoDoi); }
    public String getSoBaiViet() { return String.valueOf(soBaiViet); }

    public String getTongTienUngHo() { return tongTienUngHo; }
    public String getSoChienDichThamGia() { return String.valueOf(soChienDichThamGia); }
    public String getSoLuotHoTro() { return String.valueOf(soLuotHoTro); }
>>>>>>> ThanhPhat_chinhsuahoso
}