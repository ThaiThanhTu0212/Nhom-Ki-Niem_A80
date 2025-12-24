package com.example.thiennguyen.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpVerificationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String verificationId;
    private TextView tvResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        mAuth = FirebaseAuth.getInstance();
        verificationId = getIntent().getStringExtra("verificationId");

        TextInputEditText edtCode = findViewById(R.id.otp_edt_code);
        MaterialButton btnVerify = findViewById(R.id.otp_btn_verify);
        tvResend = findViewById(R.id.otp_tv_resend);

        edtCode.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) verifyOtp(s.toString());
            }
        });

        btnVerify.setOnClickListener(v -> {
            String code = edtCode.getText().toString().trim();
            if (code.length() == 6) verifyOtp(code);
            else Toast.makeText(this, "Vui lòng nhập đủ 6 số", Toast.LENGTH_SHORT).show();
        });

        tvResend.setOnClickListener(v -> Toast.makeText(this, "Đã gửi lại OTP", Toast.LENGTH_SHORT).show());

        startTimer();
    }

    private void verifyOtp(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();

                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        user.getIdToken(true).addOnSuccessListener(result -> {
                            String idToken = result.getToken();
                            callBackend(idToken); // GỌI API ASP.NET CORE
                        });
                    }
                    goToMain();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Mã OTP sai hoặc hết hạn", Toast.LENGTH_LONG).show();
                });
    }

    private void callBackend(String idToken) {
        // Gọi API: POST /api/firebaseauth/login với body { "token": idToken }
        // Sau khi thành công → lưu JWT → vào Main
        goToMain();
    }

    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void startTimer() {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvResend.setText("Gửi lại sau " + millisUntilFinished / 1000 + "s");
                tvResend.setEnabled(false);
            }
            public void onFinish() {
                tvResend.setText("Gửi lại");
                tvResend.setEnabled(true);
            }
        }.start();
    }
}