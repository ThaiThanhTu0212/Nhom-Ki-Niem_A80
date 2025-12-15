package com.example.thiennguyen.view.khampha;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;

import java.util.List;

public class HoanCanhActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khampha_activity_hoancanh);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarHoanCanh);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // RecyclerView
        RecyclerView rv = findViewById(R.id.rvHoanCanhFull);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Lấy dữ liệu mẫu dùng chung
        List<HoanCanhItem> list = HoanCanhItem.createSampleList();
        HoanCanhAdapter adapter = new HoanCanhAdapter(list);
        rv.setAdapter(adapter);

        // Empty view
        if (list == null || list.isEmpty()) {
            rv.setVisibility(View.GONE);
            findViewById(R.id.emptyViewHoanCanh)
                    .setVisibility(View.VISIBLE);
        }
    }
}
