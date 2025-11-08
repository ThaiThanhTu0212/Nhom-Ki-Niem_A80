package com.example.thiennguyen.view.TrangChu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.ChienDich;
import com.example.thiennguyen.view.model.DanhMuc;
import com.example.thiennguyen.view.model.NguoiDung;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {

    View view;
    RecyclerView chiendichHomeRecycleid, danhmucHomeRecyleid;
    List<ChienDich> chienDichListHome;
    List<DanhMuc> danhMuclistHome;
    List<NguoiDung> nguoiDungListHome;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);
        creatListChienDich();
        initUI();
        initListener();
        return view;


    }

    private void creatListChienDich() {
        NguoiDung nd1 = new NguoiDung(1, "Nguyễn Minh An", "an.nguyen@gmail.com", "0912345678", "123456", "nguoi_ung_ho", "hoat_dong", "/images/an.jpg");
        NguoiDung nd2 = new NguoiDung(2, "Trần Thu Hà", "ha.tran@gmail.com", "0987654321", "123456", "nguoi_van_dong", "hoat_dong", "/images/ha.jpg");
        NguoiDung nd3 = new NguoiDung(3, "Lê Quang Vũ", "vu.le@gmail.com", "0905123456", "123456", "admin", "hoat_dong", "/images/vu.jpg");
        NguoiDung nd4 = new NguoiDung(4, "Phạm Thảo Nhi", "nhi.pham@gmail.com", "0977888999", "123456", "nguoi_ung_ho", "hoat_dong", "/images/nhi.jpg");

        DanhMuc dm1 = new DanhMuc(1, "Y tế");
        DanhMuc dm2 = new DanhMuc(2, "Giáo dục");
        DanhMuc dm3 = new DanhMuc(3, "Cứu trợ thiên tai");
        DanhMuc dm4 = new DanhMuc(4, "Hỗ trợ người vô gia cư");

        // Tạo 4 chiến dịch
        ChienDich cd1 = new ChienDich(
                1,
                "Gây quỹ mổ tim cho trẻ em",
                "Chiến dịch hỗ trợ chi phí phẫu thuật tim cho trẻ em nghèo.",
                new BigDecimal("100000000"),
                new BigDecimal("25000000"),
                new Date(2025 - 1900, 10, 1),   // 1/11/2025
                new Date(2025 - 1900, 11, 1),   // 1/12/2025
                "dang_dien_ra",
                "TP.HCM",
                "Quận 1",
                "123 Nguyễn Huệ",
                "https://static.thiennguyen.app/public/donate-target/photo/2025/10/10/879b7786-cef5-4998-8821-76b057efec01.jpg",
                nd1,
                dm1
        );

        ChienDich cd2 = new ChienDich(
                2,
                "Xây trường học vùng cao",
                "Giúp trẻ em vùng núi có trường học kiên cố.",
                new BigDecimal("200000000"),
                new BigDecimal("80000000"),
                new Date(2025 - 1900, 9, 15),
                new Date(2025 - 1900, 11, 30),
                "dang_dien_ra",
                "Lào Cai",
                "Bát Xát",
                "Thôn A, xã Bản Vược",
                "https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2021/3/12/888480/Pham-Dinh-Quy-2.jpg",
                nd2,
                dm2
        );

        ChienDich cd3 = new ChienDich(
                3,
                "Trồng cây xanh đô thị",
                "Chiến dịch phủ xanh thành phố với 10.000 cây mới.",
                new BigDecimal("50000000"),
                new BigDecimal("12000000"),
                new Date(2025 - 1900, 8, 20),
                new Date(2025 - 1900, 9, 20),
                "hoan_thanh",
                "Hà Nội",
                "Cầu Giấy",
                "456 Xuân Thủy",
                "https://maykhoanmakita.net/cdn/images/tin-tuc/cay-xanh-do-thi-la-gi-3.jpg",
                nd3,
                dm3
        );

        ChienDich cd4 = new ChienDich(
                4,
                "Cứu trợ miền Trung sau bão",
                "Hỗ trợ người dân bị ảnh hưởng bởi thiên tai bão lũ.",
                new BigDecimal("300000000"),
                new BigDecimal("290000000"),
                new Date(2025 - 1900, 7, 10),
                new Date(2025 - 1900, 8, 10),
                "hoan_thanh",
                "Quảng Nam",
                "Tam Kỳ",
                "Thôn Trung An",
                "https://dienbien.edu.vn/uploads/news/2024_09/image-20240901174657-7.jpeg",
                nd4,
                dm4
        );


        chienDichListHome = new ArrayList<>();
        // Thêm vào danh sách
        chienDichListHome.add(cd1);
        chienDichListHome.add(cd2);
        chienDichListHome.add(cd3);

        danhMuclistHome = new ArrayList<>();
        danhMuclistHome.add(dm1);
        danhMuclistHome.add(dm2);
        danhMuclistHome.add(dm3);
        danhMuclistHome.add(dm4);

    }

    private void initListener() {

    }

    private void initUI() {
        //recycleView for chienDich
        chiendichHomeRecycleid = view.findViewById(R.id.chiendichHomeRecycleid);
        ChienDichHomeAdapter chienDichHomeAdapter = new ChienDichHomeAdapter(chienDichListHome);
        chiendichHomeRecycleid.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        chiendichHomeRecycleid.setAdapter(chienDichHomeAdapter);

        //recycleView for danhMuc
        danhmucHomeRecyleid = view.findViewById(R.id.danhmucHomeRecyleid);
        DanhMuchHomeAdapter danhMuchHomeAdapter = new DanhMuchHomeAdapter(danhMuclistHome);
        danhmucHomeRecyleid.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        danhmucHomeRecyleid.setAdapter(danhMuchHomeAdapter);

    }
}