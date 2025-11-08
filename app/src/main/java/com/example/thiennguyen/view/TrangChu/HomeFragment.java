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

        // Tạo 4 đối tượng ChienDich
        ChienDich cd1 = new ChienDich(
                1, nd1, dm1, "Chiến dịch 1", "Mô tả chiến dịch 1",
                new BigDecimal("5000000"), new BigDecimal("1000000"),
                new Date(), new Date(System.currentTimeMillis() + 86400000L * 30),
                "Đang diễn ra", "Hà Nội", "Hoàn Kiếm", "123 Đường A"
        );

        ChienDich cd2 = new ChienDich(
                2, nd1, dm1, "Chiến dịch 2", "Mô tả chiến dịch 2",
                new BigDecimal("10000000"), new BigDecimal("2500000"),
                new Date(), new Date(System.currentTimeMillis() + 86400000L * 60),
                "Đang diễn ra", "Hà Nội", "Ba Đình", "456 Đường B"
        );

        ChienDich cd3 = new ChienDich(
                3, nd1, dm1, "Chiến dịch 3", "Mô tả chiến dịch 3",
                new BigDecimal("8000000"), new BigDecimal("3000000"),
                new Date(), new Date(System.currentTimeMillis() + 86400000L * 45),
                "Đang diễn ra", "Hồ Chí Minh", "Quận 1", "789 Đường C"
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