package com.example.thiennguyen.view.bangtin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;
import com.example.thiennguyen.api.bangtin.BinhLuan;
import com.example.thiennguyen.api.bangtin.CommentRequest;
import com.example.thiennguyen.api.bangtin.NewsPost;
import com.example.thiennguyen.api.bangtin.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BangTinCommentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private List<BinhLuan> commentList;
    private EditText edtCommentInput;
    private ImageView btnSendComment;

    private NewsPost currentPost;
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
        adapter = new CommentAdapter(commentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        currentPost = (NewsPost) intent.getSerializableExtra("post_data");
        postPosition = intent.getIntExtra("post_position", -1);

        if (currentPost != null) {
            loadComments();
        } else {
            Toast.makeText(this, "Lỗi: Không có dữ liệu bài viết", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnSendComment.setOnClickListener(v -> sendComment());
    }

    private void loadComments() {
        RetrofitClient.getService().getCommentsForPost(currentPost.id).enqueue(new Callback<List<BinhLuan>>() {
            @Override
            public void onResponse(Call<List<BinhLuan>> call, Response<List<BinhLuan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    commentList.clear();
                    commentList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(BangTinCommentActivity.this, "Lỗi khi tải bình luận", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BinhLuan>> call, Throwable t) {
                Toast.makeText(BangTinCommentActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendComment() {
        String content = edtCommentInput.getText().toString().trim();
        if (content.isEmpty()) {
            return;
        }

        // Giả sử ID người dùng đang đăng nhập là 1
        int currentUserId = 1; 
        CommentRequest request = new CommentRequest(currentUserId, content);

        RetrofitClient.getService().addCommentToPost(currentPost.id, request).enqueue(new Callback<BinhLuan>() {
            @Override
            public void onResponse(Call<BinhLuan> call, Response<BinhLuan> response) {
                if (response.isSuccessful() && response.body() != null) {
                    commentList.add(response.body());
                    adapter.notifyItemInserted(commentList.size() - 1);
                    recyclerView.scrollToPosition(commentList.size() - 1);
                    edtCommentInput.setText("");
                    hasNewComment = true; // Đánh dấu là đã có bình luận mới
                } else {
                    Toast.makeText(BangTinCommentActivity.this, "Không thể gửi bình luận", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BinhLuan> call, Throwable t) {
                Toast.makeText(BangTinCommentActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish() {
        // Khi đóng màn hình, gửi kết quả về cho Fragment
        if (hasNewComment && postPosition != -1) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("post_position", postPosition);
            setResult(Activity.RESULT_OK, resultIntent);
        }
        super.finish();
    }
}
