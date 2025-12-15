package com.example.thiennguyen.view.khampha;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;

import java.util.List;

public class DongHanhActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khampha_activity_donghanh);

        // Toolbar với mũi tên quay lại
        Toolbar toolbar = findViewById(R.id.toolbarDongHanh);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView rvCampaigns = findViewById(R.id.rvCampaigns);

        // Sử dụng dữ liệu demo dùng chung
        List<DongHanhItem> list = DongHanhItem.createSampleList();

        rvCampaigns.setLayoutManager(new LinearLayoutManager(this));
        rvCampaigns.setAdapter(new DongHanhAdapter(list));
    }
}
