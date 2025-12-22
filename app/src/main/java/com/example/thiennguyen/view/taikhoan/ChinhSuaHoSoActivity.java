package com.example.thiennguyen.view.taikhoan;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.NguoiDung;

public class ChinhSuaHoSoActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtEmail;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_ho_so);

        edtName = findViewById(R.id.edtEditName);
        edtPhone = findViewById(R.id.edtEditPhone);
        edtEmail = findViewById(R.id.edtEditEmail);
        btnSave = findViewById(R.id.btnSaveProfile);

        // Nhận dữ liệu từ Fragment gửi sang
        NguoiDung user = (NguoiDung) getIntent().getSerializableExtra("user_data");
        if (user != null) {
            edtName.setText(user.getHoTen());
            edtPhone.setText(user.getSoDienThoai());
            edtEmail.setText(user.getEmail());
        }

        btnSave.setOnClickListener(v -> {
            // TODO: Code lưu dữ liệu vào Database tại đây
            Toast.makeText(this, "Đã lưu thông tin!", Toast.LENGTH_SHORT).show();
            finish(); // Đóng màn hình này
        });
    }
}