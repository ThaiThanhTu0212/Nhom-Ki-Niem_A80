package com.example.thiennguyen.view.bangtin;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thiennguyen.R;
import com.example.thiennguyen.api.bangtin.ApiService;
import com.example.thiennguyen.api.bangtin.DonationRequest;
import com.example.thiennguyen.api.bangtin.DonationResponse;
import com.example.thiennguyen.api.bangtin.RetrofitClient;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChuyenTienActivity extends AppCompatActivity {

    private int postId;
    private String receiverName;

    private TextView tvReceiverName;
    private EditText edtAmount;
    private EditText edtMessage;
    private Button btnConfirm;
    private final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuyen_tien);

        // Lấy dữ liệu từ Intent
        postId = getIntent().getIntExtra("POST_ID", -1);
        receiverName = getIntent().getStringExtra("RECEIVER_NAME");

        // Gán Views
        bindViews();

        // Cập nhật UI và xử lý sự kiện
        setupUI();
        setupListeners();
    }

    private void bindViews() {
        tvReceiverName = findViewById(R.id.tv_receiver_name);
        edtAmount = findViewById(R.id.edt_amount);
        edtMessage = findViewById(R.id.edt_message);
        btnConfirm = findViewById(R.id.btn_confirm_donation);
    }

    private void setupUI() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvReceiverName.setText(receiverName);
    }

    private void setupListeners() {
        findViewById(R.id.btn_50k).setOnClickListener(v -> edtAmount.setText("50000"));
        findViewById(R.id.btn_100k).setOnClickListener(v -> edtAmount.setText("100000"));
        findViewById(R.id.btn_200k).setOnClickListener(v -> edtAmount.setText("200000"));

        setupAmountFormatting();
        btnConfirm.setOnClickListener(v -> confirmDonation());
    }

    private void setupAmountFormatting() {
        edtAmount.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    edtAmount.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[,.]", "");

                    try {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = decimalFormat.format(parsed);
                        current = formatted;
                        edtAmount.setText(formatted);
                        edtAmount.setSelection(formatted.length());
                    } catch (NumberFormatException e) {
                        // Xử lý nếu chuỗi không phải là số
                    }

                    edtAmount.addTextChangedListener(this);
                }
            }
        });
    }

    private void confirmDonation() {
        String amountStr = edtAmount.getText().toString().replaceAll("[,.]", "");
        String message = edtMessage.getText().toString().trim();

        if (postId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy ID bài viết.", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = 0;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            // Xử lý trường hợp chuỗi rỗng
        }

        if (amount <= 0) {
            Toast.makeText(this, "Vui lòng nhập số tiền hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Thay thế userId bằng ID của người dùng đang đăng nhập thực tế (lấy từ SharedPreferences)
        int userId = 1; 

        DonationRequest donationRequest = new DonationRequest(userId, postId, (int) amount, message);
        sendDonationToApi(donationRequest);
    }

    private void sendDonationToApi(DonationRequest request) {
        setConfirmButtonState(false); // Vô hiệu hóa nút bấm

        ApiService apiService = RetrofitClient.getService();
        if (apiService == null) {
            Toast.makeText(this, "Không thể khởi tạo kết nối máy chủ", Toast.LENGTH_SHORT).show();
            setConfirmButtonState(true); // Kích hoạt lại nút bấm
            return;
        }

        apiService.postDonation(request).enqueue(new Callback<DonationResponse>() {
            @Override
            public void onResponse(Call<DonationResponse> call, Response<DonationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleSuccessfulDonation(response.body());
                } else {
                    handleFailedDonation("Có lỗi xảy ra: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DonationResponse> call, Throwable t) {
                handleFailedDonation("Lỗi mạng: " + t.getMessage());
            }
        });
    }

    private void handleSuccessfulDonation(DonationResponse donationResponse) {
        Toast.makeText(this, donationResponse.getMessage(), Toast.LENGTH_LONG).show();
        setResult(Activity.RESULT_OK);
        // Tạo độ trễ 2 giây trước khi đóng màn hình
        new Handler(Looper.getMainLooper()).postDelayed(this::finish, 2000);
    }

    private void handleFailedDonation(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        setConfirmButtonState(true); // Kích hoạt lại nút bấm khi có lỗi
    }

    private void setConfirmButtonState(boolean isEnabled) {
        btnConfirm.setEnabled(isEnabled);
        btnConfirm.setText(isEnabled ? "Xác nhận ủng hộ" : "Đang xử lý...");
    }
}
