package com.example.thiennguyen.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thiennguyen.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottomNavigationView = findViewById(R.id.bottomNavID);
        viewPager2 = findViewById(R.id.viewPagerID);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_home) {
                viewPager2.setCurrentItem(0);
                return true;
            } else if (id == R.id.menu_ungho) {
                viewPager2.setCurrentItem(1);
                return true;
            } else if (id == R.id.menu_bangtin) {
                viewPager2.setCurrentItem(2);
                return true;
            } else if (id == R.id.menu_khampha) {
                viewPager2.setCurrentItem(3);
                return true;
            }

            return false;
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.menu_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.menu_ungho);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.menu_bangtin);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.menu_khampha);
                        break;
                }
            }
        });

    }
}