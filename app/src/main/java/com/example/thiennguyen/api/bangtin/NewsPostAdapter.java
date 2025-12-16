package com.example.thiennguyen.api.bangtin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;

import java.util.ArrayList;
import java.util.List;

public class NewsPostAdapter extends RecyclerView.Adapter<NewsPostAdapter.NewsPostViewHolder> {

    private final ArrayList<NewsPost> postList;
    private OnItemClickListener listener;

    // ĐÃ THÊM onShareClick VÀO INTERFACE
    public interface OnItemClickListener {
        void onCommentClick(int position);
        void onLikeClick(int position);
        void onShareClick(int position);
        void onMoreOptionsClick(int position, View view);
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
        holder.bind(currentPost);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsPostViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            if (payloads.contains("like_status_changed")) {
                NewsPost currentPost = postList.get(position);
                holder.updateLikeStatus(currentPost);
            }
        }
    }

    @Override
    public int getItemCount() {
        return postList != null ? postList.size() : 0;
    }

    public static class NewsPostViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView author;
        public TextView time;
        public TextView content;
        public ImageView postImage;
        public TextView commentCount;
        public TextView likeCount;
        public ImageView btnLike;
        public ImageView btnShare; // Khai báo nút Share
        public ImageView btnMoreOptions;
        public LinearLayout commentArea;

        public NewsPostViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            avatar = itemView.findViewById(R.id.imgAvatar);
            author = itemView.findViewById(R.id.tvUsername);
            time = itemView.findViewById(R.id.tvTime);
            content = itemView.findViewById(R.id.tvContent);
            postImage = itemView.findViewById(R.id.imgMain);
            commentCount = itemView.findViewById(R.id.tvCommentCount);
            likeCount = itemView.findViewById(R.id.tvLikeCount);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnShare = itemView.findViewById(R.id.btnShare); // Gán ID cho nút Share
            commentArea = itemView.findViewById(R.id.bangtin_layout_comment_area);
            btnMoreOptions = itemView.findViewById(R.id.btnMoreOptions);

            commentArea.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onCommentClick(position);
                    }
                }
            });

            btnLike.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onLikeClick(position);
                    }
                }
            });

            // THÊM LISTENER CHO NÚT SHARE
            if (btnShare != null) { // Kiểm tra null để tránh crash nếu chưa có ID
                btnShare.setOnClickListener(v -> {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onShareClick(position);
                        }
                    }
                });
            }

            btnMoreOptions.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onMoreOptionsClick(position, v);
                    }
                }
            });
        }

        public void bind(NewsPost post) {
            author.setText(post.author);
            time.setText(post.time);
            content.setText(post.content);
            avatar.setImageResource(post.avatarResource);

            if (post.imageUrl != null && !post.imageUrl.isEmpty()) {
                postImage.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext())
                     .load(post.imageUrl)
                     .placeholder(R.drawable.placeholder_image)
                     .error(R.drawable.bangtin_img_default_post)
                     .into(postImage);
            } else {
                postImage.setVisibility(View.GONE);
            }
            updateLikeStatus(post);
            commentCount.setText(String.valueOf(post.commentCount) + " Bình luận");
        }

        public void updateLikeStatus(NewsPost post) {
            likeCount.setText(String.valueOf(post.likeCount) + " lượt thích");
            if (post.isLiked) {
                btnLike.setImageResource(R.drawable.bangtin_heart_filled);
            } else {
                btnLike.setImageResource(R.drawable.bangtin_heart_outline);
            }
        }
    }
}
