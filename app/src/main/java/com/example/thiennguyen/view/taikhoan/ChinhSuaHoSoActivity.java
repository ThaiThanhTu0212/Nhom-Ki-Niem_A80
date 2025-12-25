package com.example.thiennguyen.view.taikhoan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.NguoiDung;

public class ChinhSuaHoSoActivity extends AppCompatActivity {

    private EditText edtHoTen, edtPhone, edtEmail;
    private ImageView ivAvatarEdit, btnBack;
    private Button btnSave;
    private NguoiDung user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_ho_so);

        // Ánh xạ View
        edtHoTen = findViewById(R.id.edtHoTen);
        edtPhone = findViewById(R.id.edtSoDienThoai);
        edtEmail = findViewById(R.id.edtEmail);
        ivAvatarEdit = findViewById(R.id.ivAvatarEdit); // Cần có ID này trong XML
        btnBack = findViewById(R.id.btnBack);           // Cần có ID này trong XML
        btnSave = findViewById(R.id.btnLuuThayDoi);     // Cần có ID này trong XML

        // Nhận dữ liệu
        user = (NguoiDung) getIntent().getSerializableExtra("user_data");
        if (user != null) {
            edtHoTen.setText(user.getHoTen());
            edtPhone.setText(user.getSoDienThoai());
            edtEmail.setText(user.getEmail());
            if (user.getAvatarUrl() != null) {
                Glide.with(this).load(user.getAvatarUrl()).circleCrop().into(ivAvatarEdit);
            }
        }

        // Sự kiện
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String name = edtHoTen.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edtHoTen.setError("Vui lòng nhập tên");
            return;
        }

        if (user != null) {
            user.setHoTen(name);
            user.setSoDienThoai(phone);
            user.setEmail(edtEmail.getText().toString().trim());
        }

        Intent result = new Intent();
        result.putExtra("updated_user", user);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}