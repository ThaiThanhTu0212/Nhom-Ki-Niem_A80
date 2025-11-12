package com.example.thiennguyen.view.khampha;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thiennguyen.R;
import java.util.ArrayList;
import java.util.List;

public class KhamPhaFragment extends Fragment {

    private ViewPager2 viewPagerBanner;
    private RecyclerView rvChienDich, rvSuKien, rvHoanCanh;
    private LinearLayout btnHoanCanh, btnDongHanh, btnSuKien, btnBanDo;
    private ImageView ivSearch, ivBell;
    private Handler handler = new Handler();
    private int currentBanner = 0;
    private List<Integer> banners = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kham_pha, container, false);

        viewPagerBanner = view.findViewById(R.id.viewPagerBanner);
        rvChienDich = view.findViewById(R.id.rvChienDich);
        rvSuKien = view.findViewById(R.id.rvSuKien);
        rvHoanCanh = view.findViewById(R.id.rvHoanCanh);
        btnHoanCanh = view.findViewById(R.id.btnHoanCanh);
        btnDongHanh = view.findViewById(R.id.btnDongHanh);
        btnSuKien = view.findViewById(R.id.btnSuKien);
        btnBanDo = view.findViewById(R.id.btnBanDo);
        ivSearch = view.findViewById(R.id.ivSearch);
        ivBell = view.findViewById(R.id.ivBell);

        setupBanner();
        setupRecyclerViews();
        setupEvents();

        return view;
    }

    private void setupBanner() {
        banners.add(R.drawable.banner1);
        banners.add(R.drawable.banner2);
        banners.add(R.drawable.banner3);

        BannerAdapter adapter = new BannerAdapter(banners);
        viewPagerBanner.setAdapter(adapter);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentBanner = (currentBanner + 1) % banners.size();
                viewPagerBanner.setCurrentItem(currentBanner, true);
                handler.postDelayed(this, 3000);
            }
        }, 3000);
    }

    private void setupRecyclerViews() {
        rvChienDich.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvSuKien.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvHoanCanh.setLayoutManager(new LinearLayoutManager(getContext()));

        rvChienDich.setAdapter(new DemoAdapter("Chiến dịch"));
        rvSuKien.setAdapter(new DemoAdapter("Sự kiện"));
        rvHoanCanh.setAdapter(new DemoAdapter("Hoàn cảnh"));
    }

    private void setupEvents() {
        ivSearch.setOnClickListener(v -> Toast.makeText(getContext(), "Tìm kiếm", Toast.LENGTH_SHORT).show());
        ivBell.setOnClickListener(v -> Toast.makeText(getContext(), "Thông báo", Toast.LENGTH_SHORT).show());

        btnHoanCanh.setOnClickListener(v ->
                startActivity(new Intent(getContext(), HoanCanhActivity.class)));

        btnDongHanh.setOnClickListener(v ->
                startActivity(new Intent(getContext(), DongHanhActivity.class)));

        btnSuKien.setOnClickListener(v ->
                startActivity(new Intent(getContext(), SuKienActivity.class)));

        btnBanDo.setOnClickListener(v ->
                startActivity(new Intent(getContext(), BanDoActivity.class)));
    }
}
