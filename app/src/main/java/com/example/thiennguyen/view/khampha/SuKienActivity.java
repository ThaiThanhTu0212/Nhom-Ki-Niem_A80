package com.example.thiennguyen.view.khampha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;

import java.util.ArrayList;
import java.util.List;

public class SuKienActivity extends AppCompatActivity {

    RecyclerView rvSuKien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khampha_activity_sukien);

        rvSuKien = findViewById(R.id.rvSuKien);

        List<SuKienItem> list = new ArrayList<>();
        list.add(new SuKienItem(
                R.drawable.khampha_sukien1,
                "Trẻ em vùng cao",
                "Giải đấu Pickleball Ước Mơ Xanh",
                "30/11/2025 - 30/11/2025",
                "234 người quan tâm"
        ));

        list.add(new SuKienItem(
                R.drawable.khampha_sukien2,
                "Hoàn cảnh khó khăn",
                "Giải Bóng đá thiện nguyện",
                "05/01/2026",
                "221 người quan tâm"
        ));

        rvSuKien.setLayoutManager(new LinearLayoutManager(this));
        rvSuKien.setAdapter(new SuKienAdapter(list));
    }
}
