package com.example.thiennguyen.view.bangtin;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.thiennguyen.R;
import java.util.List;

public class NewsPostAdapter extends RecyclerView.Adapter<NewsPostAdapter.PostViewHolder> {

    private List<NewsPost> posts;

    public NewsPostAdapter(List<NewsPost> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        NewsPost post = posts.get(position);

        holder.tvUsername.setText(post.username);
        holder.tvTime.setText(post.time);
        holder.tvContent.setText(post.content);
        holder.imgMain.setImageResource(post.imgMain);
        holder.imgSmall1.setImageResource(post.imgSmall1);
        holder.imgSmall2.setImageResource(post.imgSmall2);

        // Cập nhật lượt thích
        holder.tvLikeCount.setText(post.likeCount + " lượt thích");
        holder.btnLike.setImageResource(post.isLiked
                ? R.drawable.bangtin_heart_filled
                : R.drawable.bangtin_heart_outline);

        // Xử lý nhấn thích (giữ nguyên như cũ)
        holder.btnLike.setOnClickListener(v -> {
            if (post.isLiked) {
                post.likeCount--;
                post.isLiked = false;
            } else {
                post.likeCount++;
                post.isLiked = true;
            }
            notifyItemChanged(position); // ĐÃ SỬA: bỏ chữ "gems" thừa
        });

        // Cập nhật số bình luận
        if (post.commentCount == 0) {
            holder.tvCommentCount.setText("Bình luận");
        } else {
            holder.tvCommentCount.setText(post.commentCount + " bình luận");
        }

        // THÊM MỚI: Click vào vùng bình luận → mở màn hình comment
        holder.itemView.findViewById(R.id.bangtin_layout_comment_area).setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), BangTinCommentActivity.class);
            intent.putExtra("post_position", position);
            intent.putExtra("post_username", post.username);
            intent.putExtra("post_time", post.time);
            intent.putExtra("post_content", post.content);
            intent.putExtra("comment_count", post.commentCount);

            // Thay đoạn cũ bằng đoạn này (chỉ thêm 1 dòng kiểm tra)
            if (post.comments == null) {
                post.comments = new ArrayList<>();
            }
            intent.putParcelableArrayListExtra("post_comments", post.comments);

            // Mở activity và chờ kết quả
            if (v.getContext() instanceof AppCompatActivity) {
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvTime, tvContent, tvLikeCount, tvCommentCount;
        ImageView imgMain, imgSmall1, imgSmall2, btnLike;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            imgMain = itemView.findViewById(R.id.imgMain);
            imgSmall1 = itemView.findViewById(R.id.imgSmall1);
            imgSmall2 = itemView.findViewById(R.id.imgSmall2);
            btnLike = itemView.findViewById(R.id.btnLike);
        }
    }
    // ================= THÊM TỪ ĐÂY TRỞ XUỐNG =================
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
// =========================================================
}