package com.example.thiennguyen.view.TrangChu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.ChienDich;
import com.example.thiennguyen.view.model.DanhMuc;
import com.example.thiennguyen.view.model.NguoiDung;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChiTietChienDichHomeActivity extends AppCompatActivity {
    ImageView imageViewhinhAnhctcd, img_back_tohome;
    TextView tvdm_ctcd, tvtencdctcd_home, tvsoTienMucTieu_ctcd_home, tvsongayconlai_ctcd_home, tvsoTienHienTai_ctcd_home, tvmoTa_ctcd_home;
    List<ChienDich> chienDichListHome;
    List<DanhMuc> danhMuclistHome;
    List<NguoiDung> nguoiDungListHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_chien_dich_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUI();
        postInfochiendich();
        initListener();
    }

    private void initListener() {
        img_back_tohome.setOnClickListener(v -> {
            finish();
        });
    }

    private void postInfochiendich() {
        Intent intent = getIntent();
        int idChienDich = Integer.parseInt(intent.getStringExtra("ID_CHIEN_DICH"));

        for (ChienDich cd :chienDichListHome){
            if (cd.getIdCd()==idChienDich){
                hienThiChiTiet(cd);
                break;
            }
        }
    }

    private void hienThiChiTiet(ChienDich cd) {
        tvtencdctcd_home.setText(cd.getTenCd());
        tvdm_ctcd.setText(cd.getDanhMuc().getTenDm());
        tvmoTa_ctcd_home.setText(cd.getMoTa());
//        tvsongayconlai_ctcd_home.setText(cd.getTenCd());
        tvsoTienHienTai_ctcd_home.setText(cd.getSoTienHienTai().toString() +" đ");
        tvsoTienMucTieu_ctcd_home.setText(cd.getSoTienMucTieu().toString() + " đ");
        Glide.with(this)
                .load(cd.getHinhAnh())
                .placeholder(R.drawable.chiendich_image)
                .error(R.drawable.chiendich_image)
                .into(imageViewhinhAnhctcd);
    }





    private void initUI() {
        imageViewhinhAnhctcd = findViewById(R.id.imageViewhinhAnhctcd);
        tvdm_ctcd = findViewById(R.id.tvdm_ctcd);
        tvtencdctcd_home = findViewById(R.id.tvtencdctcd_home);
        tvsoTienMucTieu_ctcd_home = findViewById(R.id.tvsoTienMucTieu_ctcd_home);
        tvsongayconlai_ctcd_home = findViewById(R.id.tvsongayconlai_ctcd_home);
        tvsoTienHienTai_ctcd_home = findViewById(R.id.tvsoTienHienTai_ctcd_home);
        tvmoTa_ctcd_home = findViewById(R.id.tvmoTa_ctcd_home);
        img_back_tohome = findViewById(R.id.image_back_listcd_list_home);
    }
}