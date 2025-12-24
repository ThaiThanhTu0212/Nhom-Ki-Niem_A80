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

public class ChiTietDongHanh1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.khampha_activity_chitiet_donghanh1);

        // --- Setup Toolbar --- 
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        // --- Find Views by ID --- 
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        ImageView imgCampaignHeader = findViewById(R.id.imgCampaignHeader);
        TextView txtTitle = findViewById(R.id.txtTitle);
        TextView txtSponsor = findViewById(R.id.txtSponsor);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView txtAmount = findViewById(R.id.txtAmount);
        TextView txtTarget = findViewById(R.id.txtTarget);
        TextView txtDescription = findViewById(R.id.txtDescription);
        ExtendedFloatingActionButton fabDonate = findViewById(R.id.fab_donate);

        // --- Get Data from Intent (Example) --- 
        String titleFromIntent = getIntent().getStringExtra("TITLE");
        String amountFromIntent = getIntent().getStringExtra("AMOUNT");

        // --- Populate Views with Data --- 

        // Set toolbar title
        collapsingToolbar.setTitle(titleFromIntent != null ? titleFromIntent : "Chi tiết chương trình");

        // Set campaign title in the body
        txtTitle.setText(titleFromIntent != null ? titleFromIntent : "Chương trình cứu đói vùng thiên tai");

        // Set amount raised
        txtAmount.setText(amountFromIntent != null ? amountFromIntent : "1.943.863.336 đ");

        // Use the correct image for the disaster relief campaign
        Glide.with(this).load(R.drawable.img_house_landslide).into(imgCampaignHeader);

        // Set placeholder/default data for other views
        txtSponsor.setText("Người đồng hành: MB MỸ ĐÌNH");
        txtTarget.setText("/ 2.000.000.000 đ");
        progressBar.setMax(2000000000);
        progressBar.setProgress(1943863336);
        txtDescription.setText("Chương trình được tổ chức nhằm giúp đỡ các hộ gia đình bị ảnh hưởng nặng nề bởi thiên tai, lũ lụt tại các tỉnh miền Trung. Chúng tôi kêu gọi sự chung tay của cộng đồng để cùng nhau san sẻ khó khăn, giúp bà con sớm ổn định lại cuộc sống.");

        // --- Set OnClickListener for FAB --- 
        fabDonate.setOnClickListener(v -> {
            Toast.makeText(ChiTietDongHanh1Activity.this, "Chuyển đến màn hình quyên góp...", Toast.LENGTH_SHORT).show();
            // Here you would start a new Activity for the donation process
        });
    }
}
