package com.example.thiennguyen.view.ungho;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.ChienDich;
import com.example.thiennguyen.view.model.DanhMuc;
import com.example.thiennguyen.view.model.NguoiDung;
import com.example.thiennguyen.view.thongbao.ThongBaoFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class UngHoFragment extends Fragment implements ChienDichUngHoAdapter.OnChienDichClickListener {
    RecyclerView chiendich_ungHo_recycleID;
    ArrayList<ChienDich> chienDichList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ung_ho, container, false);
        initData();
        initUi(view);

        ImageView btnNotifications = view.findViewById(R.id.btnNotifications);
        btnNotifications.setOnClickListener(v -> {
            ThongBaoFragment thongBaoFragment = new ThongBaoFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("chien_dich_list", chienDichList);
            thongBaoFragment.setArguments(bundle);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, thongBaoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    private void initUi(View view) {
        chiendich_ungHo_recycleID = view.findViewById(R.id.chiendich_ungHo_recycleID);
        chiendich_ungHo_recycleID.setLayoutManager(new LinearLayoutManager(getContext()));
        ChienDichUngHoAdapter chiendichUngHoAdapter = new ChienDichUngHoAdapter(chienDichList, this);
        chiendich_ungHo_recycleID.setAdapter(chiendichUngHoAdapter);
    }

    private void initData() {
        NguoiDung nd1 = new NguoiDung(1, "Nguyễn Văn A", "a@gmail.com", "0901111111", "123456", "nguoi_van_dong", "hoat_dong", "avatar1.png");
        NguoiDung nd2 = new NguoiDung(2, "Trần Thị B", "b@gmail.com", "0902222222", "123456", "nguoi_van_dong", "hoat_dong", "avatar2.png");
        NguoiDung nd3 = new NguoiDung(3, "Lê Văn C", "c@gmail.com", "0903333333", "123456", "nguoi_van_dong", "hoat_dong", "avatar3.png");

        DanhMuc dm1 = new DanhMuc(1, "Giáo dục");
        DanhMuc dm2 = new DanhMuc(2, "Y tế");
        DanhMuc dm3 = new DanhMuc(3, "Môi trường");
        DanhMuc dm4 = new DanhMuc(4, "Cộng đồng");

        chienDichList = new ArrayList<>();

        chienDichList.add(new ChienDich(1, "Hỗ trợ học sinh nghèo - Sơn La", "Quyên góp sách vở, áo ấm.", new BigDecimal("10000000"), new BigDecimal("2500000"), new Date(), new Date(), "đang_kêu_gọi", "Sơn La", "Mộc Châu", "", "https://i.pinimg.com/564x/b8/69/27/b8692797a22459434bedb93301077334.jpg", nd1, dm1));
        chienDichList.add(new ChienDich(2, "Chung tay cho em - Hà Giang", "Ủng hộ quần áo và chăn ấm.", new BigDecimal("8000000"), new BigDecimal("3500000"), new Date(), new Date(), "đang_kêu_gọi", "Hà Giang", "Đồng Văn", "", "https://i.pinimg.com/564x/4b/7e/37/4b7e373b8a2e58a23351945a8f579d4b.jpg", nd2, dm4));
        chienDichList.add(new ChienDich(3, "Xây cầu yêu thương - An Giang", "Xây cầu giúp học sinh đi học.", new BigDecimal("20000000"), new BigDecimal("12000000"), new Date(), new Date(), "đang_kêu_gọi", "An Giang", "Châu Phú", "", "https://i.pinimg.com/564x/a2/45/63/a24563a61f3f615f3f4e2f3d61172c1c.jpg", nd3, dm1));
        chienDichList.add(new ChienDich(4, "Nước sạch cho vùng hạn hán", "Cung cấp nước sạch tại Ninh Thuận.", new BigDecimal("50000000"), new BigDecimal("15000000"), new Date(), new Date(), "đang_kêu_gọi", "Ninh Thuận", "Thuận Nam", "", "https://i.pinimg.com/564x/d1/a7/6b/d1a76b0f1d82e2e7a4f89a9f2d15024b.jpg", nd1, dm3));
        chienDichList.add(new ChienDich(5, "Trồng câyhttps://github.com/ThaiThanhTu0212/Nhom-Ki-Niem_A80/pull/7/conflict?name=app%252Fsrc%252Fmain%252Fjava%252Fcom%252Fexample%252Fthiennguyen%252Fview%252Fungho%252FUngHoFragment.java&ancestor_oid=2f3d438a886a2d905f34f48266da0c6e66225b7e&base_oid=2ad8a01dff5c4cf9399fc04e2b1ab256d98d92da&head_oid=e5c5fa91709b5e965fae693dcd699687edb9edea gây rừng - Vì một VN xanh", "Trồng 1 triệu cây xanh tại Quảng Bình.", new BigDecimal("100000000"), new BigDecimal("45000000"), new Date(), new Date(), "đang_kêu_gọi", "Quảng Bình", "Bố Trạch", "", "https://i.pinimg.com/564x/e7/7c/49/e77c49b145e1b1d1f7c3c3a9f73315a2.jpg", nd2, dm3));
        chienDichList.add(new ChienDich(6, "Ánh sáng cho bản làng", "Lắp điện mặt trời cho vùng cao.", new BigDecimal("30000000"), new BigDecimal("11000000"), new Date(), new Date(), "đang_kêu_gọi", "Điện Biên", "Mường Nhé", "", "https://i.pinimg.com/564x/c7/2a/3a/c72a3a0e1b6f1e2e9f0d1a4a4d6b9d6a.jpg", nd1, dm4));
        chienDichList.add(new ChienDich(7, "Bữa ăn có thịt", "Cải thiện bữa ăn cho trẻ em nội trú.", new BigDecimal("15000000"), new BigDecimal("7000000"), new Date(), new Date(), "đang_kêu_gọi", "Lào Cai", "Sa Pa", "", "https://i.pinimg.com/564x/b4/5a/27/b45a271f2b2e5c6e8f0a0d7e2e5c6d5b.jpg", nd3, dm2));
        chienDichList.add(new ChienDich(8, "Sách cho em", "Tặng sách cho thư viện trường học.", new BigDecimal("5000000"), new BigDecimal("4800000"), new Date(), new Date(), "hoàn_thành", "TP. Hồ Chí Minh", "Quận 1", "", "https://i.pinimg.com/564x/8d/5a/3d/8d5a3d4b6e8f0a0d7e2e5c6d5b4a271f.jpg", nd2, dm1));
        chienDichList.add(new ChienDich(9, "Giúp đỡ người già neo đơn", "Tặng quà và chăm sóc sức khỏe.", new BigDecimal("25000000"), new BigDecimal("19000000"), new Date(), new Date(), "đang_kêu_gọi", "Hà Nội", "Hoàn Kiếm", "", "https://i.pinimg.com/564x/f3/0c/3a/f30c3a0e1b6f1e2e9f0d1a4a4d6b9d6a.jpg", nd1, dm2));
        chienDichList.add(new ChienDich(10, "Máy tính cho em", "Trang bị phòng máy tính cho trường học.", new BigDecimal("70000000"), new BigDecimal("33000000"), new Date(), new Date(), "đang_kêu_gọi", "Đà Nẵng", "Liên Chiểu", "", "https://i.pinimg.com/564x/9a/9a/9a/9a9a9a9a9a9a9a9a9a9a9a9a9a9a9a9a.jpg", nd3, dm1));
        chienDichList.add(new ChienDich(11, "Dọn rác bãi biển", "Làm sạch bãi biển tại Quảng Ninh.", new BigDecimal("3000000"), new BigDecimal("3000000"), new Date(), new Date(), "hoàn_thành", "Quảng Ninh", "Hạ Long", "", "https://i.pinimg.com/564x/1e/1e/1e/1e1e1e1e1e1e1e1e1e1e1e1e1e1e1e1e.jpg", nd2, dm3));
        chienDichList.add(new ChienDich(12, "Nhà cho người nghèo", "Xây nhà tình thương cho hộ khó khăn.", new BigDecimal("60000000"), new BigDecimal("21000000"), new Date(), new Date(), "đang_kêu_gọi", "Cà Mau", "Trần Văn Thời", "", "https://i.pinimg.com/564x/3d/3d/3d/3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d.jpg", nd1, dm4));
        chienDichList.add(new ChienDich(13, "Phẫu thuật tim cho em", "Chi phí phẫu thuật cho bệnh nhi.", new BigDecimal("120000000"), new BigDecimal("85000000"), new Date(), new Date(), "đang_kêu_gọi", "Huế", "TP. Huế", "", "https://i.pinimg.com/564x/c3/c3/c3/c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3.jpg", nd3, dm2));
        chienDichList.add(new ChienDich(14, "Xe lăn cho người khuyết tật", "Tặng xe lăn cho người có nhu cầu.", new BigDecimal("40000000"), new BigDecimal("38000000"), new Date(), new Date(), "đang_kêu_gọi", "Bình Dương", "Thủ Dầu Một", "", "https://i.pinimg.com/564x/b2/b2/b2/b2b2b2b2b2b2b2b2b2b2b2b2b2b2b2b2.jpg", nd2, dm2));
        chienDichList.add(new ChienDich(15, "Học bổng cho sinh viên", "Hỗ trợ học phí cho sinh viên giỏi.", new BigDecimal("90000000"), new BigDecimal("50000000"), new Date(), new Date(), "đang_kêu_gọi", "Cần Thơ", "Ninh Kiều", "", "https://i.pinimg.com/564x/a1/a1/a1/a1a1a1a1a1a1a1a1a1a1a1a1a1a1a1a1.jpg", nd1, dm1));
        chienDichList.add(new ChienDich(16, "Giếng sạch cho trường học", "Xây giếng nước tại điểm trường.", new BigDecimal("18000000"), new BigDecimal("18000000"), new Date(), new Date(), "hoàn_thành", "Kon Tum", "Đắk Glei", "", "https://i.pinimg.com/564x/f1/f1/f1/f1f1f1f1f1f1f1f1f1f1f1f1f1f1f1f1.jpg", nd3, dm4));
        chienDichList.add(new ChienDich(17, "Tủ đồ dùng học tập", "Cung cấp đồ dùng học tập miễn phí.", new BigDecimal("7000000"), new BigDecimal("2000000"), new Date(), new Date(), "đang_kêu_gọi", "Gia Lai", "Pleiku", "", "https://i.pinimg.com/564x/e2/e2/e2/e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2.jpg", nd2, dm1));
        chienDichList.add(new ChienDich(18, "Chăn ấm cho mùa đông", "Tặng chăn ấm cho người vô gia cư.", new BigDecimal("12000000"), new BigDecimal("9500000"), new Date(), new Date(), "đang_kêu_gọi", "Hà Nội", "Đống Đa", "", "https://i.pinimg.com/564x/d3/d3/d3/d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3.jpg", nd1, dm4));
        chienDichList.add(new ChienDich(19, "Thư viện container", "Xây dựng thư viện sáng tạo.", new BigDecimal("45000000"), new BigDecimal("15000000"), new Date(), new Date(), "đang_kêu_gọi", "Hải Phòng", "Ngô Quyền", "", "https://i.pinimg.com/564x/c4/c4/c4/c4c4c4c4c4c4c4c4c4c4c4c4c4c4c4c4.jpg", nd3, dm1));
        chienDichList.add(new ChienDich(20, "Bảo vệ động vật hoang dã", "Hỗ trợ trung tâm cứu hộ động vật.", new BigDecimal("35000000"), new BigDecimal("12000000"), new Date(), new Date(), "đang_kêu_gọi", "Đồng Nai", "Vĩnh Cửu", "", "https://i.pinimg.com/564x/b5/b5/b5/b5b5b5b5b5b5b5b5b5b5b5b5b5b5b5b5.jpg", nd2, dm3));
        chienDichList.add(new ChienDich(21, "Áo mới cho em", "Tặng quần áo mới cho trẻ em khó khăn.", new BigDecimal("9000000"), new BigDecimal("8000000"), new Date(), new Date(), "đang_kêu_gọi", "Thanh Hóa", "Quan Hóa", "", "https://i.pinimg.com/564x/a6/a6/a6/a6a6a6a6a6a6a6a6a6a6a6a6a6a6a6a6.jpg", nd1, dm4));
        chienDichList.add(new ChienDich(22, "Đường đến trường", "Sửa chữa đường đi cho học sinh.", new BigDecimal("22000000"), new BigDecimal("5000000"), new Date(), new Date(), "đang_kêu_gọi", "Lạng Sơn", "Bình Gia", "", "https://i.pinimg.com/564x/98/98/98/98989898989898989898989898989898.jpg", nd3, dm4));
        chienDichList.add(new ChienDich(23, "Nồi cháo tình thương", "Nấu và phát cháo miễn phí tại bệnh viện.", new BigDecimal("10000000"), new BigDecimal("10000000"), new Date(), new Date(), "hoàn_thành", "Hà Nội", "Hai Bà Trưng", "", "https://i.pinimg.com/564x/87/87/87/87878787878787878787878787878787.jpg", nd2, dm2));
        chienDichList.add(new ChienDich(24, "Túi thuốc gia đình", "Tặng túi thuốc cơ bản cho hộ nghèo.", new BigDecimal("13000000"), new BigDecimal("6000000"), new Date(), new Date(), "đang_kêu_gọi", "Quảng Nam", "Đại Lộc", "", "https://i.pinimg.com/564x/76/76/76/76767676767676767676767676767676.jpg", nd1, dm2));
        chienDichList.add(new ChienDich(25, "Vui trung thu", "Tổ chức trung thu cho trẻ em thiệt thòi.", new BigDecimal("16000000"), new BigDecimal("11000000"), new Date(), new Date(), "đang_kêu_gọi", "Thái Bình", "TP. Thái Bình", "", "https://i.pinimg.com/564x/65/65/65/65656565656565656565656565656565.jpg", nd3, dm4));
    }

    @Override
    public void onChienDichClick(ChienDich chienDich) {
        int position = chienDichList.indexOf(chienDich);
        ChiTietPagerFragment chiTietPagerFragment = ChiTietPagerFragment.newInstance(chienDichList, position);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, chiTietPagerFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
