package com.example.thiennguyen.view.khampha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.thiennguyen.R;

import java.util.ArrayList;
import java.util.List;

public class DongHanhActivity extends AppCompatActivity {

    RecyclerView rvCampaigns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khampha_activity_donghanh);

        rvCampaigns = findViewById(R.id.rvCampaigns);

        List<DongHanhItem> list = new ArrayList<>();

        list.add(new DongHanhItem(
                R.drawable.h1,
                "Chương trình trồng 1 triệu cây xanh cho Trường Sa",
                "5.981.152.110 đ",
                "24/04/2025",
                "MB - Khối CNTT",
                1   // TOP 1
        ));

        list.add(new DongHanhItem(
                R.drawable.h2,
                "Chương trình trồng 1 triệu cây xanh cho Trường Sa",
                "506.243.000 đ",
                "09/08/2025",
                "MB ĐÔNG SÀI GÒN",
                2   // TOP 2
        ));

        list.add(new DongHanhItem(
                R.drawable.h3,
                "Chương trình trồng 1 triệu cây xanh cho Trường Sa",
                "343.863.336 đ",
                "05/08/2025",
                "MB MỸ ĐÌNH",
                3   // TOP 3
        ));

        rvCampaigns.setLayoutManager(new LinearLayoutManager(this));
        rvCampaigns.setAdapter(new DongHanhAdapter(list));
    }
}
