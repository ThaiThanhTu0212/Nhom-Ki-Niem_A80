package com.example.thiennguyen.view.taikhoan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thiennguyen.R;
import com.example.thiennguyen.view.MainActivity;
import com.example.thiennguyen.view.data.ApiClient;
import com.example.thiennguyen.view.data.DTO.ApiResponse;
import com.example.thiennguyen.view.data.DTO.Response.AuthenticationResponse;
import com.example.thiennguyen.view.data.DTO.request.AuthenticationRequest;
import com.example.thiennguyen.view.data.api.AuthenticationApi;
import com.example.thiennguyen.view.data.sharepreference.DataLocalManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmailLogin, edtPasswordLogin;
    TextView txtForgotPassLogin;
    Button btnLogin;
    AuthenticationApi authenticationApi = ApiClient.getRetrofit().create(AuthenticationApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUI();
        initListener();
    }
    public void checkLogin(String email, String password) {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);
        Call<ApiResponse<AuthenticationResponse>> callLogin = authenticationApi.login(authenticationRequest);

        callLogin.enqueue(new Callback<ApiResponse<AuthenticationResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<AuthenticationResponse>> call, Response<ApiResponse<AuthenticationResponse>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    // Đăng nhập thành công
                    DataLocalManager.setToken(response.body().getResult().getToken());
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }

                // ❌ Sai email hoặc mật khẩu
                Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse<AuthenticationResponse>> call, Throwable t) {
                // ❌ Lỗi mạng hoặc lỗi server
                Toast.makeText(LoginActivity.this, "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initListener() {
        btnLogin.setOnClickListener(v -> {
            String email = edtEmailLogin.getText().toString().trim();
            String password = edtPasswordLogin.getText().toString().trim();

            checkLogin(email, password);

        });
    }

    private void initUI() {
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        txtForgotPassLogin = findViewById(R.id.txtForgotPassLogin);
        btnLogin = findViewById(R.id.btnLogin);

    }
}