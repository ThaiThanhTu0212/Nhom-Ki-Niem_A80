package com.example.thiennguyen.view.taikhoan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
<<<<<<< HEAD
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.NguoiDung;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ChinhSuaHoSoActivity extends AppCompatActivity {

    // Khai báo đúng kiểu View trong layout Material Design
    private TextInputEditText edtHoTen, edtPhone, edtEmail;
    private ImageView ivAvatarEdit, btnBack;
    private MaterialButton btnSave;
=======
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
>>>>>>> main
    private NguoiDung user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_ho_so);

<<<<<<< HEAD
        initViews();
        loadData();
        setupListeners();
    }

    private void initViews() {
        // Ánh xạ ID khớp với activity_chinh_sua_ho_so.xml
        edtHoTen = findViewById(R.id.edtHoTen);
        edtPhone = findViewById(R.id.edtSoDienThoai);
        edtEmail = findViewById(R.id.edtEmail);
        ivAvatarEdit = findViewById(R.id.ivAvatarEdit);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnLuuThayDoi);
    }

    private void loadData() {
        // Nhận dữ liệu từ Fragment gửi sang
=======
        // Ánh xạ View
        edtHoTen = findViewById(R.id.edtHoTen);
        edtPhone = findViewById(R.id.edtSoDienThoai);
        edtEmail = findViewById(R.id.edtEmail);
        ivAvatarEdit = findViewById(R.id.ivAvatarEdit); // Cần có ID này trong XML
        btnBack = findViewById(R.id.btnBack);           // Cần có ID này trong XML
        btnSave = findViewById(R.id.btnLuuThayDoi);     // Cần có ID này trong XML

        // Nhận dữ liệu
>>>>>>> main
        user = (NguoiDung) getIntent().getSerializableExtra("user_data");
        if (user != null) {
            edtHoTen.setText(user.getHoTen());
            edtPhone.setText(user.getSoDienThoai());
            edtEmail.setText(user.getEmail());
<<<<<<< HEAD

            // Load ảnh avatar hiện tại
            if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
                Glide.with(this)
                        .load(user.getAvatarUrl())
                        .circleCrop()
                        .into(ivAvatarEdit);
            }
        }
    }

    private void setupListeners() {
        // Xử lý nút Back
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Xử lý nút Lưu
        if (btnSave != null) {
            btnSave.setOnClickListener(v -> saveProfile());
        }
    }

    private void saveProfile() {
        String name = String.valueOf(edtHoTen.getText()).trim();
        String phone = String.valueOf(edtPhone.getText()).trim();
        String email = String.valueOf(edtEmail.getText()).trim();

        // Validate dữ liệu
        if (TextUtils.isEmpty(name)) {
            edtHoTen.setError("Tên không được để trống");
            edtHoTen.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError("Số điện thoại không được để trống");
            edtPhone.requestFocus();
            return;
        }

        // Cập nhật vào Model
        if (user != null) {
            user.setHoTen(name);
            user.setSoDienThoai(phone);
            user.setEmail(email);
        }

        // Đóng gói trả về Fragment
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updated_user", user);
        setResult(Activity.RESULT_OK, resultIntent);
        finish(); // Đóng màn hình
=======
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
>>>>>>> main
    }
}