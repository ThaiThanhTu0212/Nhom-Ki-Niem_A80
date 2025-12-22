package com.example.thiennguyen.view.taikhoan;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.thiennguyen.R;

public class ChinhSuaHoSoActivity extends AppCompatActivity {

    private EditText edtName, edtEmail;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_ho_so);

        // Ánh xạ
        edtName = findViewById(R.id.edtName); // Nhớ tạo ID này bên XML
        btnSave = findViewById(R.id.btnSave);

        // Xử lý nút Lưu
        btnSave.setOnClickListener(v -> {
            // Logic lưu dữ liệu vào Database hoặc API
            finish(); // Đóng màn hình này quay về Profile
        });
    }
}