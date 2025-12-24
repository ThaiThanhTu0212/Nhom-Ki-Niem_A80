package com.example.thiennguyen.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thiennguyen.R;
import com.example.thiennguyen.view.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edtEmail, edtPassword;
    private Button btnLogin, btnLoginWithPhone;   // THÊM NÚT MỚI
    private TextView tvRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.login_edt_email);
        edtPassword = findViewById(R.id.login_edt_password);
        btnLogin = findViewById(R.id.login_btn_login);
        btnLoginWithPhone = findViewById(R.id.login_btn_phone);   // ID mới trong layout
        tvRegister = findViewById(R.id.login_tv_register);

        btnLogin.setOnClickListener(v -> loginUserWithEmail());

        // NÚT MỚI: ĐĂNG NHẬP BẰNG SỐ ĐIỆN THOẠI
        btnLoginWithPhone.setOnClickListener(v -> showPhoneLoginDialog());

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    // ĐĂNG NHẬP EMAIL + MẬT KHẨU (giữ nguyên như cũ)
    private void loginUserWithEmail() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Vui lòng xác thực email trước!", Toast.LENGTH_LONG).show();
                            mAuth.signOut();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    // HIỆN DIALOG NHẬP SỐ ĐIỆN THOẠI + GỬI OTP
    private void showPhoneLoginDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Đăng nhập bằng số điện thoại");

        final TextInputEditText input = new TextInputEditText(this);
        input.setHint("Nhập số điện thoại (VD: 912345678)");
        input.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

        builder.setPositiveButton("Gửi OTP", (dialog, which) -> {
            String phone = input.getText().toString().trim();
            if (phone.isEmpty() || phone.length() < 9) {
                Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            String fullPhone = "+84" + phone.replaceFirst("^0", ""); // chuyển 09 → +849
            sendOtpToPhone(fullPhone);
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    // GỬI OTP QUA SỐ ĐIỆN THOẠI
    private void sendOtpToPhone(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(com.google.firebase.FirebaseException e) {
                        Toast.makeText(LoginActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        Toast.makeText(LoginActivity.this, "Đã gửi OTP đến " + phoneNumber, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, OtpVerificationActivity.class);
                        intent.putExtra("verificationId", verificationId);
                        startActivity(intent);
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // TỰ ĐỘNG ĐĂNG NHẬP NẾU XÁC THỰC TỰ ĐỘNG (hiếm xảy ra)
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        goToMain();
                    }
                });
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
