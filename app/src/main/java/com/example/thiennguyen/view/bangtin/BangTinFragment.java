package com.example.thiennguyen.view.bangtin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.thiennguyen.R;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class BangTinFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsPostAdapter adapter;
    private List<NewsPost> postList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bang_tin, container, false);

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dữ liệu mẫu
        postList = new ArrayList<>();
        postList.add(new NewsPost(
                "CLB Tình Nguyện Yêu Hoá Vang",
                "Đã thêm 10 ảnh mới • khoảng 1 tiếng trước",
                "Đồng hành cùng bà con về vùng cao Bắc Trà My, Nơi chịu thiệt hại nặng nề do sạt lở, bão số 12 vừa qua... Xem thêm",
                R.drawable.img_house_landslide,
                R.drawable.img_mudslide,
                R.drawable.img_rescue
        ));

        postList.add(new NewsPost(
                "Trái Tim Cho Đi",
                "Đã thêm 2 ảnh mới • 2 tiếng trước",
                "TK 0794 GD: +200,000VND 07/11/25 17:33  ND: UH331D9C1BKQAU TRAN DINH THUY 1985",
                R.drawable.img_donation,
                R.drawable.img_donation,
                R.drawable.img_donation
        ));

        // Adapter
        adapter = new NewsPostAdapter(postList);
        recyclerView.setAdapter(adapter);

        // TabLayout (tùy chọn xử lý chuyển tab)
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                // Xử lý lọc dữ liệu theo tab (nếu cần)
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }
}