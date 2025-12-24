package com.example.thiennguyen.view.khampha;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ChiTietDongHanh3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khampha_activity_chitiet_donghanh3);

        // --- Setup Toolbar --- 
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        // --- Find Views by ID --- 
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        ImageView imgCampaignHeader = findViewById(R.id.imgCampaignHeader);
        TextView txtCampaignTitleDetail = findViewById(R.id.txtCampaignTitleDetail);
        TextView txtSponsorDetail = findViewById(R.id.txtSponsorDetail);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView txtAmountRaised = findViewById(R.id.txtAmountRaised);
        TextView txtTargetAmount = findViewById(R.id.txtTargetAmount);
        TextView txtDescription = findViewById(R.id.txtDescription);
        ExtendedFloatingActionButton fabDonate = findViewById(R.id.fab_donate);

        // --- Populate Views with Correct Data for "Gia đình nghèo" --- 
        String title = "Chương trình hỗ trợ gia đình nghèo";

        collapsingToolbar.setTitle(title);
        txtCampaignTitleDetail.setText(title);

        // Use a suitable image for the poor families support campaign
        Glide.with(this).load(R.drawable.h1).into(imgCampaignHeader);

        txtSponsorDetail.setText("Tổ chức bởi: MB - Khối CNTT");

        // Set progress bar values
        long target = 1000000000L; // 1 tỷ
        int progress = 981152110;
        progressBar.setMax((int) (target / 1000));
        progressBar.setProgress(progress / 1000);

        txtAmountRaised.setText("981.152.110 đ");
        txtTargetAmount.setText(String.format("mục tiêu %,d đ", target).replace(',', '.'));

        txtDescription.setText("Chương trình tập trung hỗ trợ các gia đình có hoàn cảnh đặc biệt khó khăn, giúp họ cải thiện điều kiện sống, tiếp cận giáo dục và y tế. Mỗi đóng góp đều góp phần xây dựng một tương lai tươi sáng hơn cho các gia đình này.");

        // --- Set OnClickListener for FAB --- 
        fabDonate.setOnClickListener(v -> {
            Toast.makeText(ChiTietDongHanh3Activity.this, "Chuyển đến màn hình đồng hành...", Toast.LENGTH_SHORT).show();
        });
    }
}
