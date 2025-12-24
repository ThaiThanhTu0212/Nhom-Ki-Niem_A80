package com.example.thiennguyen.view.khampha;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.thiennguyen.R;

public class ChiTietSuKien1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khampha_activity_chitiet_sukien1);

        // --- Setup Toolbar --- 
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());

        // --- Find Views --- 
        ImageView actionShare = findViewById(R.id.action_share);
        Button btnJoin = findViewById(R.id.btn_join);

        // In a real app, you would receive event data from an Intent
        // and populate all the TextViews and ImageViews dynamically.

        // --- Set Click Listeners --- 
        actionShare.setOnClickListener(v -> {
            Toast.makeText(this, "Chia sẻ sự kiện...", Toast.LENGTH_SHORT).show();
        });

        btnJoin.setOnClickListener(v -> {
            Toast.makeText(this, "Đăng ký tham gia...", Toast.LENGTH_SHORT).show();
        });
    }
}
