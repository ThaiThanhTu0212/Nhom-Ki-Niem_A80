package com.example.thiennguyen.view.ungho;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.ChienDich;
import com.example.thiennguyen.view.model.DanhMuc;
import com.example.thiennguyen.view.model.NguoiDung;
import com.example.thiennguyen.view.thongbao.ThongBaoFragment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class UngHoFragment extends Fragment implements ChienDichUngHoAdapter.OnChienDichClickListener {
    private RecyclerView chiendich_ungHo_recycleID;
    private ArrayList<ChienDich> chienDichList;
    private ChienDichUngHoAdapter chiendichUngHoAdapter;
    private EditText edtSearch;

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

        edtSearch = view.findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void initUi(View view) {
        chiendich_ungHo_recycleID = view.findViewById(R.id.chiendich_ungHo_recycleID);
        chiendich_ungHo_recycleID.setLayoutManager(new LinearLayoutManager(getContext()));
        chiendichUngHoAdapter = new ChienDichUngHoAdapter(chienDichList, this);
        chiendich_ungHo_recycleID.setAdapter(chiendichUngHoAdapter);
    }

    private void filter(String text) {
        ArrayList<ChienDich> filteredList = new ArrayList<>();
        for (ChienDich item : chienDichList) {
            if (item.getTenCd().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        chiendichUngHoAdapter.filterList(filteredList);
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

        chienDichList.add(new ChienDich(1, "Hỗ trợ học sinh nghèo - Sơn La", "Chiến dịch nhằm cung cấp sách vở, đồ dùng học tập và những chiếc áo ấm cho các em học sinh có hoàn cảnh khó khăn tại vùng cao Sơn La, giúp các em có một mùa đông ấm áp và đủ đầy hơn.", new BigDecimal("10000000"), new BigDecimal("2500000"), new Date(), new Date(), "đang_kêu_gọi", "Sơn La", "Mộc Châu", "", "https://i.pinimg.com/564x/b8/69/27/b8692797a22459434bedb93301077334.jpg", nd1, dm1));
        chienDichList.add(new ChienDich(2, "Chung tay cho em - Hà Giang", "Cùng nhau mang đến cho các em nhỏ tại Hà Giang những bộ quần áo mới và những chiếc chăn ấm để chống chọi với mùa đông giá rét. Mỗi một đóng góp của bạn đều là một nguồn động viên to lớn.", new BigDecimal("8000000"), new BigDecimal("3500000"), new Date(), new Date(), "đang_kêu_gọi", "Hà Giang", "Đồng Văn", "", "https://i.pinimg.com/564x/4b/7e/37/4b7e373b8a2e58a23351945a8f579d4b.jpg", nd2, dm4));
        chienDichList.add(new ChienDich(3, "Xây cầu yêu thương - An Giang", "Dự án xây dựng một cây cầu dân sinh mới, giúp cho việc đi lại, đặc biệt là đến trường của các em học sinh tại huyện Châu Phú, An Giang được an toàn và thuận tiện hơn, nhất là trong mùa mưa lũ.", new BigDecimal("20000000"), new BigDecimal("12000000"), new Date(), new Date(), "đang_kêu_gọi", "An Giang", "Châu Phú", "", "https://i.pinimg.com/564x/a2/45/63/a24563a61f3f615f3f4e2f3d61172c1c.jpg", nd3, dm1));
        chienDichList.add(new ChienDich(4, "Nước sạch cho vùng hạn hán", "Chung tay giải quyết vấn đề thiếu nước sạch tại các vùng bị ảnh hưởng bởi hạn hán ở Ninh Thuận bằng cách xây dựng các hệ thống lọc và cung cấp nước, đảm bảo sức khỏe cho cộng đồng.", new BigDecimal("50000000"), new BigDecimal("15000000"), new Date(), new Date(), "đang_kêu_gọi", "Ninh Thuận", "Thuận Nam", "", "https://i.pinimg.com/564x/d1/a7/6b/d1a76b0f1d82e2e7a4f89a9f2d15024b.jpg", nd1, dm3));
        chienDichList.add(new ChienDich(5, "Trồng cây gây rừng - Vì một VN xanh", "Hãy cùng chúng tôi thực hiện mục tiêu trồng 1 triệu cây xanh tại Quảng Bình, góp phần phủ xanh đất trống, đồi trọc, cải thiện môi trường và ứng phó với biến đổi khí hậu.", new BigDecimal("100000000"), new BigDecimal("45000000"), new Date(), new Date(), "đang_kêu_gọi", "Quảng Bình", "Bố Trạch", "", "https://i.pinimg.com/564x/e7/7c/49/e77c49b145e1b1d1f7c3c3a9f73315a2.jpg", nd2, dm3));
        chienDichList.add(new ChienDich(6, "Ánh sáng cho bản làng", "Dự án mang ánh sáng điện mặt trời đến cho các hộ gia đình và trường học ở những bản làng vùng cao, vùng sâu vùng xa của tỉnh Điện Biên, cải thiện điều kiện sống và học tập.", new BigDecimal("30000000"), new BigDecimal("11000000"), new Date(), new Date(), "đang_kêu_gọi", "Điện Biên", "Mường Nhé", "", "https://i.pinimg.com/564x/c7/2a/3a/c72a3a0e1b6f1e2e9f0d1a4a4d6b9d6a.jpg", nd1, dm4));
        chienDichList.add(new ChienDich(7, "Bữa ăn có thịt", "Góp phần cải thiện chất lượng bữa ăn hàng ngày cho các em học sinh nội trú tại các trường vùng cao Sa Pa, đảm bảo các em có đủ dinh dưỡng để phát triển khỏe mạnh.", new BigDecimal("15000000"), new BigDecimal("7000000"), new Date(), new Date(), "đang_kêu_gọi", "Lào Cai", "Sa Pa", "", "https://i.pinimg.com/564x/b4/5a/27/b45a271f2b2e5c6e8f0a0d7e2e5c6d5b.jpg", nd3, dm2));
        chienDichList.add(new ChienDich(8, "Sách cho em", "Chiến dịch quyên góp sách để xây dựng và làm phong phú thêm các tủ sách, thư viện tại các trường học ở TP. Hồ Chí Minh, lan tỏa văn hóa đọc đến với các em nhỏ.", new BigDecimal("5000000"), new BigDecimal("4800000"), new Date(), new Date(), "hoàn_thành", "TP. Hồ Chí Minh", "Quận 1", "", "https://i.pinimg.com/564x/8d/5a/3d/8d5a3d4b6e8f0a0d7e2e5c6d5b4a271f.jpg", nd2, dm1));
        chienDichList.add(new ChienDich(9, "Giúp đỡ người già neo đơn", "Mang đến sự quan tâm, chăm sóc và những món quà ý nghĩa cho những người cao tuổi có hoàn cảnh khó khăn, neo đơn trên địa bàn thành phố Hà Nội.", new BigDecimal("25000000"), new BigDecimal("19000000"), new Date(), new Date(), "đang_kêu_gọi", "Hà Nội", "Hoàn Kiếm", "", "https://i.pinimg.com/564x/f3/0c/3a/f30c3a0e1b6f1e2e9f0d1a4a4d6b9d6a.jpg", nd1, dm2));
        chienDichList.add(new ChienDich(10, "Máy tính cho em", "Trang bị những phòng máy tính hiện đại cho các trường học ở Đà Nẵng, tạo điều kiện cho các em học sinh được tiếp cận với công nghệ thông tin và tri thức số.", new BigDecimal("70000000"), new BigDecimal("33000000"), new Date(), new Date(), "đang_kêu_gọi", "Đà Nẵng", "Liên Chiểu", "", "https://i.pinimg.com/564x/9a/9a/9a/9a9a9a9a9a9a9a9a9a9a9a9a9a9a9a9a.jpg", nd3, dm1));
        chienDichList.add(new ChienDich(11, "Dọn rác bãi biển", "Cùng nhau hành động để làm sạch các bãi biển tại Quảng Ninh, nâng cao ý thức bảo vệ môi trường biển và giữ gìn cảnh quan thiên nhiên tươi đẹp.", new BigDecimal("3000000"), new BigDecimal("3000000"), new Date(), new Date(), "hoàn_thành", "Quảng Ninh", "Hạ Long", "", "https://i.pinimg.com/564x/1e/1e/1e/1e1e1e1e1e1e1e1e1e1e1e1e1e1e1e1e.jpg", nd2, dm3));
        chienDichList.add(new ChienDich(12, "Nhà cho người nghèo", "Chung tay xây dựng những ngôi nhà tình thương, tạo một mái ấm kiên cố và an toàn cho các hộ gia đình có hoàn cảnh đặc biệt khó khăn tại tỉnh Cà Mau.", new BigDecimal("60000000"), new BigDecimal("21000000"), new Date(), new Date(), "đang_kêu_gọi", "Cà Mau", "Trần Văn Thời", "", "https://i.pinimg.com/564x/3d/3d/3d/3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d.jpg", nd1, dm4));
        chienDichList.add(new ChienDich(13, "Phẫu thuật tim cho em", "Gây quỹ hỗ trợ chi phí phẫu thuật cho các bệnh nhi mắc bệnh tim bẩm sinh, mang lại cho các em một trái tim khỏe mạnh và một tương lai tươi sáng hơn.", new BigDecimal("120000000"), new BigDecimal("85000000"), new Date(), new Date(), "đang_kêu_gọi", "Huế", "TP. Huế", "", "https://i.pinimg.com/564x/c3/c3/c3/c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3c3.jpg", nd3, dm2));
        chienDichList.add(new ChienDich(14, "Xe lăn cho người khuyết tật", "Trao tặng những chiếc xe lăn cho những người khuyết tật có hoàn cảnh khó khăn, giúp họ có phương tiện đi lại và hòa nhập tốt hơn với cộng đồng.", new BigDecimal("40000000"), new BigDecimal("38000000"), new Date(), new Date(), "đang_kêu_gọi", "Bình Dương", "Thủ Dầu Một", "", "https://i.pinimg.com/564x/b2/b2/b2/b2b2b2b2b2b2b2b2b2b2b2b2b2b2b2b2.jpg", nd2, dm2));
        chienDichList.add(new ChienDich(15, "Học bổng cho sinh viên", "Trao các suất học bổng giá trị để hỗ trợ chi phí học tập và sinh hoạt cho những sinh viên nghèo vượt khó, có thành tích học tập xuất sắc tại Cần Thơ.", new BigDecimal("90000000"), new BigDecimal("50000000"), new Date(), new Date(), "đang_kêu_gọi", "Cần Thơ", "Ninh Kiều", "", "https://i.pinimg.com/564x/a1/a1/a1/a1a1a1a1a1a1a1a1a1a1a1a1a1a1a1a1.jpg", nd1, dm1));
        chienDichList.add(new ChienDich(16, "Giếng sạch cho trường học", "Dự án xây dựng các giếng nước sạch tại các điểm trường vùng sâu vùng xa ở Kon Tum, đảm bảo nguồn nước sinh hoạt hợp vệ sinh cho các em học sinh và giáo viên.", new BigDecimal("18000000"), new BigDecimal("18000000"), new Date(), new Date(), "hoàn_thành", "Kon Tum", "Đắk Glei", "", "https://i.pinimg.com/564x/f1/f1/f1/f1f1f1f1f1f1f1f1f1f1f1f1f1f1f1f1.jpg", nd3, dm4));
        chienDichList.add(new ChienDich(17, "Tủ đồ dùng học tập", "Thành lập các tủ đồ dùng học tập miễn phí tại các trường học ở Gia Lai, cung cấp đầy đủ sách vở, bút thước cho các em học sinh có hoàn cảnh khó khăn.", new BigDecimal("7000000"), new BigDecimal("2000000"), new Date(), new Date(), "đang_kêu_gọi", "Gia Lai", "Pleiku", "", "https://i.pinimg.com/564x/e2/e2/e2/e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2.jpg", nd2, dm1));
        chienDichList.add(new ChienDich(18, "Chăn ấm cho mùa đông", "Phát tặng những chiếc chăn ấm cho những người vô gia cư tại Hà Nội, giúp họ có một mùa đông bớt lạnh lẽo và thêm phần ấm áp tình người.", new BigDecimal("12000000"), new BigDecimal("9500000"), new Date(), new Date(), "đang_kêu_gọi", "Hà Nội", "Đống Đa", "", "https://i.pinimg.com/564x/d3/d3/d3/d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3d3.jpg", nd1, dm4));
        chienDichList.add(new ChienDich(19, "Thư viện container", "Xây dựng những thư viện container độc đáo và sáng tạo tại Hải Phòng, tạo không gian đọc sách và học tập mới lạ, hấp dẫn cho các bạn trẻ.", new BigDecimal("45000000"), new BigDecimal("15000000"), new Date(), new Date(), "đang_kêu_gọi", "Hải Phòng", "Ngô Quyền", "", "https://i.pinimg.com/564x/c4/c4/c4/c4c4c4c4c4c4c4c4c4c4c4c4c4c4c4c4.jpg", nd3, dm1));
        chienDichList.add(new ChienDich(20, "Bảo vệ động vật hoang dã", "Gây quỹ hỗ trợ cho các trung tâm cứu hộ động vật hoang dã tại Đồng Nai, góp phần chăm sóc, chữa trị và tái thả các loài động vật quý hiếm về với tự nhiên.", new BigDecimal("35000000"), new BigDecimal("12000000"), new Date(), new Date(), "đang_kêu_gọi", "Đồng Nai", "Vĩnh Cửu", "", "https://i.pinimg.com/564x/b5/b5/b5/b5b5b5b5b5b5b5b5b5b5b5b5b5b5b5b5.jpg", nd2, dm3));
        chienDichList.add(new ChienDich(21, "Áo mới cho em", "Mang đến niềm vui và sự ấm áp cho các em nhỏ có hoàn cảnh khó khăn ở vùng cao Thanh Hóa bằng những bộ quần áo mới tinh tươm trong dịp Tết đến xuân về.", new BigDecimal("9000000"), new BigDecimal("8000000"), new Date(), new Date(), "đang_kêu_gọi", "Thanh Hóa", "Quan Hóa", "", "https://i.pinimg.com/564x/a6/a6/a6/a6a6a6a6a6a6a6a6a6a6a6a6a6a6a6a6.jpg", nd1, dm4));
        chienDichList.add(new ChienDich(22, "Đường đến trường", "Chung tay sửa chữa, nâng cấp những con đường đất lầy lội, khó đi tại các bản làng ở Lạng Sơn, giúp các em học sinh có một con đường đến trường an toàn và sạch sẽ hơn.", new BigDecimal("22000000"), new BigDecimal("5000000"), new Date(), new Date(), "đang_kêu_gọi", "Lạng Sơn", "Bình Gia", "", "https://i.pinimg.com/564x/98/98/98/98989898989898989898989898989898.jpg", nd3, dm4));
        chienDichList.add(new ChienDich(23, "Nồi cháo tình thương", "Duy trì và phát triển hoạt động nấu và phát cháo miễn phí hàng tuần tại các bệnh viện lớn ở Hà Nội, mang đến những bữa ăn ấm lòng cho bệnh nhân và người nhà.", new BigDecimal("10000000"), new BigDecimal("10000000"), new Date(), new Date(), "hoàn_thành", "Hà Nội", "Hai Bà Trưng", "", "https://i.pinimg.com/564x/87/87/87/87878787878787878787878787878787.jpg", nd2, dm2));
        chienDichList.add(new ChienDich(24, "Túi thuốc gia đình", "Trang bị những túi thuốc y tế gia đình cơ bản cho các hộ gia đình nghèo ở vùng sâu vùng xa của tỉnh Quảng Nam, giúp họ có thể xử lý các vấn đề sức khỏe thông thường một cách kịp thời.", new BigDecimal("13000000"), new BigDecimal("6000000"), new Date(), new Date(), "đang_kêu_gọi", "Quảng Nam", "Đại Lộc", "", "https://i.pinimg.com/564x/76/76/76/76767676767676767676767676767676.jpg", nd1, dm2));
        chienDichList.add(new ChienDich(25, "Vui trung thu", "Tổ chức một đêm hội trăng rằm thật ý nghĩa với đèn ông sao, bánh kẹo và các trò chơi dân gian cho các em nhỏ có hoàn cảnh thiệt thòi tại thành phố Thái Bình.", new BigDecimal("16000000"), new BigDecimal("11000000"), new Date(), new Date(), "đang_kêu_gọi", "Thái Bình", "TP. Thái Bình", "", "https://i.pinimg.com/564x/65/65/65/65656565656565656565656565656565.jpg", nd3, dm4));
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
