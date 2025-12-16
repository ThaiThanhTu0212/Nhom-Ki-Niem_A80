package com.example.thiennguyen.view.khampha;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;

import java.util.List;

public class SuKienActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khampha_activity_sukien);

        // Toolbar với mũi tên quay lại
        Toolbar toolbar = findViewById(R.id.toolbarSuKien);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView rvSuKien = findViewById(R.id.rvSuKien);

        // Sử dụng dữ liệu demo dùng chung
        List<SuKienItem> list = SuKienItem.createSampleList();

        rvSuKien.setLayoutManager(new LinearLayoutManager(this));
        rvSuKien.setAdapter(new SuKienAdapter(list));
    }
}
