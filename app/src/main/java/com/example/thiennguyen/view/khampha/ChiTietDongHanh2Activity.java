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

public class ChiTietDongHanh2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khampha_activity_chitiet_donghanh2);

        // --- Setup Toolbar --- 
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        // --- Find Views by ID --- 
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        ImageView imgCampaign = findViewById(R.id.imgCampaign);
        TextView txtCampaignTitleDetail = findViewById(R.id.txtCampaignTitleDetail);
        TextView txtSponsorDetail = findViewById(R.id.txtSponsorDetail);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView txtAmountRaised = findViewById(R.id.txtAmountRaised);
        TextView txtTargetAmount = findViewById(R.id.txtTargetAmount);
        TextView txtDescription = findViewById(R.id.txtDescription);
        ExtendedFloatingActionButton fabDonate = findViewById(R.id.fab_donate);

        // --- Get Data from Intent (In the future) --- 
        // String titleFromIntent = getIntent().getStringExtra("TITLE");

        // --- Populate Views with Correct Data for "Cụ già neo đơn" --- 
        String title = "Chương trình hỗ trợ những cụ già neo đơn";

        collapsingToolbar.setTitle(title);
        txtCampaignTitleDetail.setText(title);

        // Use the correct image for the elderly support campaign
        Glide.with(this).load(R.drawable.h2).into(imgCampaign);

        txtSponsorDetail.setText("Tổ chức bởi: MB ĐỒNG SÀI GÒN");
        
        // Set progress bar values
        long target = 1200000000L; // Example target: 1.2 tỷ
        int progress = 1006243000;
        progressBar.setMax((int) (target / 1000)); // scale down for progress bar
        progressBar.setProgress(progress / 1000);

        txtAmountRaised.setText("1.006.243.000 đ");
        txtTargetAmount.setText(String.format("mục tiêu %,d đ", target).replace(',', '.'));

        txtDescription.setText("Chương trình nhằm mang đến sự chăm sóc và hỗ trợ về vật chất cũng như tinh thần cho các cụ già có hoàn cảnh khó khăn, neo đơn, không nơi nương tựa. Mỗi sự đóng góp của bạn là một niềm an ủi lớn lao, giúp các cụ có một cuộc sống tuổi già ấm áp và ý nghĩa hơn.");

        // --- Set OnClickListener for FAB --- 
        fabDonate.setOnClickListener(v -> {
            Toast.makeText(ChiTietDongHanh2Activity.this, "Chuyển đến màn hình đồng hành...", Toast.LENGTH_SHORT).show();
            // Handle donation logic here
        });
    }
}
