package com.example.thiennguyen.view.ungho;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

public class UngHoFragment extends Fragment implements ChienDichUngHoAdapter.OnChienDichClickListener {
    RecyclerView chiendich_ungHo_recycleID;
    List<ChienDich> chienDichList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ung_ho, container, false);
        initData();
        initUi(view);
        return  view;
    }
    private void initUi(View view) {
        chiendich_ungHo_recycleID = view.findViewById(R.id.chiendich_ungHo_recycleID);
        chiendich_ungHo_recycleID.setLayoutManager(new LinearLayoutManager(getContext()));
        ChienDichUngHoAdapter chiendichUngHoAdapter = new ChienDichUngHoAdapter(chienDichList,this);
        chiendich_ungHo_recycleID.setAdapter(chiendichUngHoAdapter);
    }

    private void initData() {
        // 1️⃣ TẠO NGƯỜI DÙNG
// ----------------------
        NguoiDung nd1 = new NguoiDung(1, "Nguyễn Văn A", "a@gmail.com", "0901111111", "123456", "nguoi_van_dong", "hoat_dong", "avatar1.png");
        NguoiDung nd2 = new NguoiDung(2, "Trần Thị B", "b@gmail.com", "0902222222", "123456", "nguoi_van_dong", "hoat_dong", "avatar2.png");
        NguoiDung nd3 = new NguoiDung(3, "Lê Văn C", "c@gmail.com", "0903333333", "123456", "nguoi_van_dong", "hoat_dong", "avatar3.png");

        List<NguoiDung> nguoiDungList = new ArrayList<>();
        nguoiDungList.add(nd1);
        nguoiDungList.add(nd2);
        nguoiDungList.add(nd3);


// ----------------------
// 2️⃣ TẠO DANH MỤC
// ----------------------
        DanhMuc dm1 = new DanhMuc(1, "Giáo dục");
        DanhMuc dm2 = new DanhMuc(2, "Y tế");
        DanhMuc dm3 = new DanhMuc(3, "Cơ sở hạ tầng");

        List<DanhMuc> danhMucList = new ArrayList<>();
        danhMucList.add(dm1);
        danhMucList.add(dm2);
        danhMucList.add(dm3);


// ----------------------
// 3️⃣ TẠO CHIẾN DỊCH
// ----------------------
        ChienDich cd1 = new ChienDich(
                1,
                "Hỗ trợ học sinh nghèo - Sơn La",
                "Quyên góp sách vở, áo ấm cho học sinh khó khăn.",
                new BigDecimal("10000000"),
                new BigDecimal("2500000"),
                new Date(),
                new Date(System.currentTimeMillis() + 86400000L * 30),
                "đang_kêu_gọi",
                "Sơn La",
                "Mộc Châu",
                "Bản Mường Vạt",
                "https://i.pinimg.com/236x/86/84/99/86849922ac2ddc9223897abce598e786.jpg",
                nd1,
                dm1
        );

        ChienDich cd2 = new ChienDich(
                2,
                "Chung tay cho em - Hà Giang",
                "Ủng hộ quần áo và chăn cho trẻ vùng cao.",
                new BigDecimal("8000000"),
                new BigDecimal("3500000"),
                new Date(),
                new Date(System.currentTimeMillis() + 86400000L * 25),
                "đang_kêu_gọi",
                "Hà Giang",
                "Đồng Văn",
                "Xã Lũng Cú",
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fbanobagi.vn%2Fhinh-anh%2Fhinh-anh-gai-cute%2F&psig=AOvVaw02i2l1LjC5KzQ5XfXEIQLm&ust=1763026530936000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCPDvuOOn7JADFQAAAAAdAAAAABAd",
                nd2,
                dm2
        );

        ChienDich cd3 = new ChienDich(
                3,
                "Xây cầu yêu thương - An Giang",
                "Góp phần xây cầu giúp học sinh đi học thuận tiện mùa mưa.",
                new BigDecimal("20000000"),
                new BigDecimal("12000000"),
                new Date(),
                new Date(System.currentTimeMillis() + 86400000L * 40),
                "đang_kêu_gọi",
                "An Giang",
                "Châu Phú",
                "Ấp Bình Hòa",
                "https://i.pinimg.com/236x/86/84/99/86849922ac2ddc9223897abce598e786.jpg",
                nd3,
                dm3
        );

         chienDichList = new ArrayList<>();
        chienDichList.add(cd1);
        chienDichList.add(cd2);
        chienDichList.add(cd3);
    }


    @Override
    public void onChienDichClick(ChienDich chienDich) {
        ChiTietChienDichFragment chiTietChienDichFragment = new ChiTietChienDichFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("chien_dich", chienDich);
        chiTietChienDichFragment.setArguments(bundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, chiTietChienDichFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
