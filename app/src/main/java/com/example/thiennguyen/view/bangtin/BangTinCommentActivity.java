package com.example.thiennguyen.view.bangtin;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thiennguyen.R;
import java.util.ArrayList;
import android.app.Activity;  // ← cần để dùng Activity.RESULT_OK

public class BangTinCommentActivity extends AppCompatActivity {

    private ArrayList<Comment> comments;
    private BangTinCommentAdapter adapter;
    private int postPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bangtin_activity_comment);

        // Nhận dữ liệu
        postPosition = getIntent().getIntExtra("post_position", -1);
        String username = getIntent().getStringExtra("post_username");
        String time = getIntent().getStringExtra("post_time");
        String content = getIntent().getStringExtra("post_content");
        // Trong onCreate() của BangTinCommentActivity.java
        comments = getIntent().getParcelableArrayListExtra("post_comments");
        if (comments == null) {
            comments = new ArrayList<>();  // Đã có dòng này rồi → an toàn tuyệt đối
        }

        // Hiển thị bài viết
        TextView tvAuthor = findViewById(R.id.bangtin_tv_post_author);
        TextView tvTime = findViewById(R.id.bangtin_tv_post_time);
        TextView tvContent = findViewById(R.id.bangtin_tv_post_content);
        tvAuthor.setText(username);
        tvTime.setText(time);
        tvContent.setText(content);

        // Back + RecyclerView
        findViewById(R.id.bangtin_btn_back).setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.bangtin_recycler_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BangTinCommentAdapter(comments);
        recyclerView.setAdapter(adapter);

        // Gửi bình luận
        EditText edtComment = findViewById(R.id.bangtin_edt_comment);
        ImageView btnSend = findViewById(R.id.bangtin_btn_send);

        btnSend.setOnClickListener(v -> {
            String text = edtComment.getText().toString().trim();
            if (!text.isEmpty()) {
                Comment c = new Comment("Bạn", text, "Vừa xong", R.drawable.bangtin_avatar_default);
                comments.add(0, c);
                adapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);
                edtComment.setText("");

                Intent result = new Intent();
                result.putExtra("post_position", postPosition);
                result.putExtra("new_comment_count", comments.size());
                setResult(Activity.RESULT_OK, result);  // ← dùng Activity.RESULT_OK
            }
        });
    }
}