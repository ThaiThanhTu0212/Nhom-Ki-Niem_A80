package com.example.thiennguyen.view.bangtin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;
import com.example.thiennguyen.api.bangtin.CommentDto;
import com.example.thiennguyen.api.bangtin.CommentRequest;
import com.example.thiennguyen.api.bangtin.LikeCommentResponse;
import com.example.thiennguyen.api.bangtin.NewsPost;
import com.example.thiennguyen.api.bangtin.RetrofitClient;
// THÊM DÒNG NÀY ĐỂ IMPORT LỚP BinhLuan
import com.example.thiennguyen.view.bangtin.BinhLuan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BangTinCommentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private List<BinhLuan> commentList; // Bây giờ BinhLuan đã được nhận diện
    private EditText edtCommentInput;
    private ImageView btnSendComment;

    private int postId;
    private int postPosition;
    private boolean hasNewComment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bang_tin_comment);

        Toolbar toolbar = findViewById(R.id.comment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.comment_recyclerView);
        edtCommentInput = findViewById(R.id.edt_comment_input);
        btnSendComment = findViewById(R.id.btn_send_comment);

        commentList = new ArrayList<>();
        adapter = new CommentAdapter(this, commentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        NewsPost currentPost = (NewsPost) intent.getSerializableExtra("post_data");
        postPosition = intent.getIntExtra("post_position", -1);

        if (currentPost != null) {
            postId = currentPost.id;
            loadComments();
        } else {
            Toast.makeText(this, "Lỗi: Không có dữ liệu bài viết", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnSendComment.setOnClickListener(v -> sendComment());
    }

    private void loadComments() {
        RetrofitClient.getService().getComments(postId).enqueue(new Callback<List<CommentDto>>() {
            @Override
            public void onResponse(Call<List<CommentDto>> call, Response<List<CommentDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    commentList.clear();
                    for (CommentDto dto : response.body()) {
                        BinhLuan uiComment = new BinhLuan(dto.tenNguoiBinhLuan, dto.noiDung, dto.ngayBinhLuan, R.drawable.tinh_nguyen_vien_an_avatar);
                        commentList.add(uiComment);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(BangTinCommentActivity.this, "Lỗi khi tải bình luận", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CommentDto>> call, Throwable t) {
                Toast.makeText(BangTinCommentActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendComment() {
        String content = edtCommentInput.getText().toString().trim();
        if (content.isEmpty()) {
            return;
        }

        // TODO: Thay thế 1 bằng ID người dùng thật
        CommentRequest request = new CommentRequest(1, content);

        RetrofitClient.getService().postComment(postId, request).enqueue(new Callback<LikeCommentResponse>() {
            @Override
            public void onResponse(Call<LikeCommentResponse> call, Response<LikeCommentResponse> response) {
                if (response.isSuccessful()) {
                    edtCommentInput.setText("");
                    hasNewComment = true;
                    Toast.makeText(BangTinCommentActivity.this, "Đã gửi bình luận", Toast.LENGTH_SHORT).show();
                    loadComments(); // Tải lại để cập nhật
                } else {
                    Toast.makeText(BangTinCommentActivity.this, "Không thể gửi bình luận", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LikeCommentResponse> call, Throwable t) {
                Toast.makeText(BangTinCommentActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish() {
        if (hasNewComment) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("post_position", postPosition);
            setResult(Activity.RESULT_OK, resultIntent);
        }
        super.finish();
    }
}
