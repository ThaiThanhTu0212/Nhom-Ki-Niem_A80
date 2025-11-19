package com.example.thiennguyen.view.bangtin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.thiennguyen.R;

public class ThongBaoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongbao);

        // Đặt tiêu đề
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Thông báo");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}