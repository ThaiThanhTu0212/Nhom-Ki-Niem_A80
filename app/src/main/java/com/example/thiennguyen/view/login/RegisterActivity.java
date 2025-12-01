package com.example.thiennguyen.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thiennguyen.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private LinearLayout emailLayout, phoneLayout;
    private TextInputEditText edtEmail, edtPassword, edtConfirmPassword, edtPhoneNumber;
    private Button btnRegister;
    private TextView tvLogin;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        // Khởi tạo các view
        tabLayout = findViewById(R.id.register_tab_layout);
        emailLayout = findViewById(R.id.register_email_layout);
        phoneLayout = findViewById(R.id.register_phone_layout);
        edtEmail = findViewById(R.id.register_edt_email);
        edtPassword = findViewById(R.id.register_edt_password);
        edtConfirmPassword = findViewById(R.id.register_edt_confirm_password);
        edtPhoneNumber = findViewById(R.id.register_edt_phone);
        btnRegister = findViewById(R.id.register_btn_register);
        tvLogin = findViewById(R.id.register_tv_login);

        setupVerificationCallbacks();

        // Xử lý chuyển đổi giữa các tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    emailLayout.setVisibility(View.VISIBLE);
                    phoneLayout.setVisibility(View.GONE);
                } else {
                    emailLayout.setVisibility(View.GONE);
                    phoneLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        // Xử lý nút đăng ký
        btnRegister.setOnClickListener(v -> {
            int selectedTab = tabLayout.getSelectedTabPosition();
            if (selectedTab == 0) {
                registerWithEmail();
            } else {
                registerWithPhoneNumber();
            }
        });

        tvLogin.setOnClickListener(v -> finish());
    }

    private void registerWithEmail() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo tài khoản bằng Email
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Gửi email xác thực và quay về màn hình đăng nhập
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            user.sendEmailVerification().addOnCompleteListener(task2 -> {
                                if (task2.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công. Vui lòng kiểm tra email để xác thực.", Toast.LENGTH_LONG).show();
                                    finish(); // Quay lại Login
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Không thể gửi email xác thực.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void registerWithPhoneNumber() {
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        if (phoneNumber.isEmpty() || !phoneNumber.startsWith("+")) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại đúng định dạng (+84...)", Toast.LENGTH_SHORT).show();
            return;
        }
        sendVerificationCode(phoneNumber);
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Toast.makeText(this, "Đang gửi mã OTP...", Toast.LENGTH_SHORT).show();
    }

    private void setupVerificationCallbacks() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // Bỏ qua
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(RegisterActivity.this, "Gửi OTP thất bại: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(RegisterActivity.this, "Mã OTP đã được gửi.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, OtpVerificationActivity.class);
                intent.putExtra("verificationId", verificationId);
                // Báo cho màn hình OTP biết đây là luồng đăng ký mới (không phải liên kết tài khoản)
                intent.putExtra("isNewRegistration", true);
                startActivity(intent);
            }
        };
    }
}
