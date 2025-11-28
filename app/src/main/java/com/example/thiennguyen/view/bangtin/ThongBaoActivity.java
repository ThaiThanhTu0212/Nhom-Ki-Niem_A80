package com.example.thiennguyen.view.bangtin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;

import java.util.ArrayList;
import java.util.List;

public class ThongBaoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ThongBaoAdapter adapter;
    private List<ThongBao> thongBaoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);

        recyclerView = findViewById(R.id.thongbao_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        thongBaoList = new ArrayList<>();
        adapter = new ThongBaoAdapter(this, thongBaoList);
        recyclerView.setAdapter(adapter);

        loadDummyNotifications();
    }

    // THAY ĐỔI: Cập nhật thông báo giả theo chủ đề thiện nguyện
    private void loadDummyNotifications() {
        thongBaoList.clear();

        thongBaoList.add(new ThongBao("Mạnh Thường Quân đã thích bài viết kêu gọi ủng hộ của bạn.", R.drawable.bangtin_avatar_default));
        thongBaoList.add(new ThongBao("Nhà Hảo Tâm đã bình luận về bài viết của bạn: 'Cảm ơn thông tin hữu ích!'", R.drawable.bangtin_avatar_default));
        thongBaoList.add(new ThongBao("Hội Chữ Thập Đỏ đã thích bình luận của bạn.", R.drawable.bangtin_avatar_default));
        thongBaoList.add(new ThongBao("Chiến dịch 'Chung tay vì miền Trung' vừa được cập nhật.", R.drawable.bangtin_avatar_default));

        adapter.notifyDataSetChanged();
    }
}
