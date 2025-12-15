package com.example.thiennguyen.view.khampha;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;
import com.example.thiennguyen.databinding.FragmentKhamPhaBinding;

import java.util.ArrayList;
import java.util.List;

public class KhamPhaFragment extends Fragment {

    private FragmentKhamPhaBinding binding;
    private final Handler handler = new Handler();
    private int currentBanner = 0;
    private List<BannerItem> banners = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentKhamPhaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setupBanner();
        setupRecyclerViews();
        setupCampaignTop1();
        setupHoanCanh();
        setupEvents();
        setupSuKien();


        return view;
    }

    private void setupBanner() {
        banners.add(new BannerItem(
                "CHUNG TAY CỨU TRỢ KHẨN CẤP ĐỒNG BÀO BỊ ẢNH HƯỞNG BỞI LŨ LỤT",
                "Trên nền tảng VNeID năm 2025",
                0xFFD32F2F, 0xFFF57C00,
                R.drawable.khampha_ic_flood));

        banners.add(new BannerItem(
                "Thử thách chạy bộ #HiGreen",
                "Vì Trường Sa xanh",
                0xFF00BFA5, 0xFF42A5F5,
                R.drawable.khampha_ic_leaf));

        banners.add(new BannerItem(
                "FUN FIT FEST – KHỞI ĐỘNG MÙA MỚI",
                "Cùng Standard Chartered đồng hành",
                0xFF81D4FA, 0xFF0288D1,
                R.drawable.khampha_ic_runner));

        BannerAdapter adapter = new BannerAdapter(banners);
        binding.viewPagerBanner.setAdapter(adapter);

        binding.viewPagerBanner.setPageTransformer((page, position) -> {
            page.setAlpha(0.5f + (1 - Math.abs(position)));
            page.setScaleY(0.9f + (1 - Math.abs(position)) * 0.1f);
        });

        // Tự động cuộn
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (binding.viewPagerBanner.getAdapter() != null && !banners.isEmpty()) {
                    currentBanner = (currentBanner + 1) % banners.size();
                    binding.viewPagerBanner.setCurrentItem(currentBanner, true);
                    handler.postDelayed(this, 3000);
                }
            }
        }, 3000);
    }

    private void setupRecyclerViews() {
        binding.rvSuKienNoiBat.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        binding.rvHoanCanh.setLayoutManager(
                new LinearLayoutManager(getContext()));

        // demo
        binding.rvSuKienNoiBat.setAdapter(new Adapter("Sự kiện"));
        binding.rvHoanCanh.setAdapter(new Adapter("Hoàn cảnh"));
    }

    private void setupCampaignTop1() {

        List<DongHanhItem> list = new ArrayList<>();

        list.add(new DongHanhItem(
                R.drawable.h1,
                "Chương trình trồng 1 triệu cây xanh cho Trường Sa",
                "5.981.152.110 đ",
                "24/04/2025",
                "MB - Khối CNTT",
                1
        ));

        List<DongHanhItem> top1 = new ArrayList<>();
        top1.add(list.get(0));

        DongHanhAdapter adapter = new DongHanhAdapter(top1);

        binding.rvChienDich.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvChienDich.setAdapter(adapter);
    }

    // --------------------------
    // SỰ KIỆN NHẤN ICON
    // --------------------------
    private void setupEvents() {
        binding.ivSearch.setOnClickListener(v ->
                Toast.makeText(getContext(), "Tìm kiếm", Toast.LENGTH_SHORT).show());

        binding.ivBell.setOnClickListener(v ->
                Toast.makeText(getContext(), "Thông báo", Toast.LENGTH_SHORT).show());

        binding.btnHoanCanh.setOnClickListener(v ->
                startActivity(new Intent(getContext(), HoanCanhActivity.class)));

        binding.btnDongHanh.setOnClickListener(v ->
                startActivity(new Intent(getContext(), DongHanhActivity.class)));

        binding.btnSuKien.setOnClickListener(v ->
                startActivity(new Intent(getContext(), SuKienActivity.class)));

        binding.btnBanDo.setOnClickListener(v ->
                startActivity(new Intent(getContext(), BanDoActivity.class)));

        binding.btnXemTatCa.setOnClickListener(v ->
                startActivity(new Intent(getContext(), DongHanhActivity.class))
        );

        binding.btnXemTatCaSuKien.setOnClickListener(v ->
                startActivity(new Intent(getContext(), SuKienActivity.class)));


    }

    private void setupHoanCanh() {
        // Dữ liệu mẫu dùng chung từ HoanCanhItem
        List<HoanCanhItem> list = HoanCanhItem.createSampleList();

        // chỉ hiển thị 3 item như mẫu
        binding.rvHoanCanh.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)
        );

        binding.rvHoanCanh.setAdapter(new HoanCanhAdapter(list));

        binding.btnXemTatCaHoanCanh.setOnClickListener(v ->
                startActivity(new Intent(getContext(), HoanCanhActivity.class))
        );
    }

    private void setupSuKien() {
        List<SuKienItem> suKienList = new ArrayList<>();

        suKienList.add(new SuKienItem(
                R.drawable.khampha_sukien1,
                "GIẢI ĐẤU PICKLEBALL SMASH FOR SOUNDS",
                "30/11/2025 - 30/11/2025",
                "Trẻ em vùng cao",
                "234 người quan tâm"
        ));

        suKienList.add(new SuKienItem(
                R.drawable.khampha_sukien2,
                "Kỷ niệm 20 năm thành lập PanNature",
                "05/01/2026",
                "Hoàn cảnh khó khăn",
                "20 người quan tâm"
        ));


        // Lấy chỉ sự kiện nổi bật nhất
        List<SuKienItem> suKienNoiBat = new ArrayList<>();
        suKienNoiBat.add(suKienList.get(0));

        // Gắn adapter cho sự kiện nổi bật
        SuKienAdapter adapter = new SuKienAdapter(suKienNoiBat);
        binding.rvSuKienNoiBat.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvSuKienNoiBat.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        binding = null;
    }
}
