package com.example.thiennguyen.api.bangtin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;

import java.util.ArrayList;

public class NewsPostAdapter extends RecyclerView.Adapter<NewsPostAdapter.NewsPostViewHolder> {

    private final ArrayList<NewsPost> postList;
    private OnItemClickListener listener;

    // Sửa lại Interface để xử lý các sự kiện click riêng biệt
    public interface OnItemClickListener {
        void onCommentClick(int position);
        void onLikeClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public NewsPostAdapter(ArrayList<NewsPost> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public NewsPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_post, parent, false);
        return new NewsPostViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsPostViewHolder holder, int position) {
        NewsPost currentPost = postList.get(position);

        holder.author.setText(currentPost.author);
        holder.time.setText(currentPost.time);
        holder.content.setText(currentPost.content);
        holder.avatar.setImageResource(currentPost.avatarResource);

        if (currentPost.imageResource != 0) {
            holder.postImage.setImageResource(currentPost.imageResource);
            holder.postImage.setVisibility(View.VISIBLE);
        } else {
            holder.postImage.setVisibility(View.GONE);
        }

        holder.commentCount.setText(String.valueOf(currentPost.commentCount));
        // TODO: Cập nhật cả số lượt thích
    }

    @Override
    public int getItemCount() {
        return postList != null ? postList.size() : 0;
    }

    // Lớp ViewHolder
    public static class NewsPostViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView author;
        public TextView time;
        public TextView content;
        public ImageView postImage;
        public TextView commentCount;
        public ImageView btnLike;
        public LinearLayout commentArea;

        public NewsPostViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            avatar = itemView.findViewById(R.id.imgAvatar);
            author = itemView.findViewById(R.id.tvUsername);
            time = itemView.findViewById(R.id.tvTime);
            content = itemView.findViewById(R.id.tvContent);
            postImage = itemView.findViewById(R.id.imgMain);
            commentCount = itemView.findViewById(R.id.tvCommentCount);
            btnLike = itemView.findViewById(R.id.btnLike); // ID của nút thích
            commentArea = itemView.findViewById(R.id.bangtin_layout_comment_area); // ID của khu vực bình luận

            // Xóa bỏ listener chung trên itemView
            // itemView.setOnClickListener(...);

            // Gán listener riêng cho khu vực bình luận
            commentArea.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onCommentClick(position);
                    }
                }
            });

            // Gán listener riêng cho nút thích
            btnLike.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onLikeClick(position);
                    }
                }
            });
        }
    }
}
