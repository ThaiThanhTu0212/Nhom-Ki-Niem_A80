package com.example.thiennguyen.view.taikhoan;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;
import com.example.thiennguyen.api.bangtin.LichSuAdapter;
import com.example.thiennguyen.view.model.GiaoDich;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class LichSuChuyenTienActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLichSu;
    private LichSuAdapter adapter;
    private List<GiaoDich> giaoDichList;
    private TextView tvEmptyHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_chuyen_tien);

        // Gán Views
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        recyclerViewLichSu = findViewById(R.id.recyclerViewLichSu);
        tvEmptyHistory = findViewById(R.id.tvEmptyHistory);

        // Thiết lập Toolbar
        toolbar.setNavigationOnClickListener(v -> finish());

        // Thiết lập RecyclerView
        setupRecyclerView();

        // Tải dữ liệu (hiện tại là dữ liệu mẫu)
        loadLichSuGiaoDich();
    }

    private void setupRecyclerView() {
        giaoDichList = new ArrayList<>();
        adapter = new LichSuAdapter(this, giaoDichList);
        recyclerViewLichSu.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLichSu.setAdapter(adapter);
    }

    private void loadLichSuGiaoDich() {
        // SAU NÀY SẼ THAY BẰNG VIỆC GỌI API TỪ BACKEND
        // Dưới đây là dữ liệu mẫu để hiển thị
        giaoDichList.add(new GiaoDich("Chung tay ủng hộ đồng bào miền Trung", "15:30 25/12/2024", "50,000", true));
        giaoDichList.add(new GiaoDich("Quyên góp cho trẻ em vùng cao", "11:05 24/12/2024", "100,000", true));
        giaoDichList.add(new GiaoDich("Xây trường cho em", "09:20 23/12/2024", "200,000", true));

        // Kiểm tra và hiển thị danh sách hoặc thông báo rỗng
        if (giaoDichList.isEmpty()) {
            recyclerViewLichSu.setVisibility(View.GONE);
            tvEmptyHistory.setVisibility(View.VISIBLE);
        } else {
            recyclerViewLichSu.setVisibility(View.VISIBLE);
            tvEmptyHistory.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }
}
