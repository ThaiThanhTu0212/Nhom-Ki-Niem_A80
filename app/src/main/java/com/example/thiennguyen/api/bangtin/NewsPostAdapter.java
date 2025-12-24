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

import java.util.List;

public class NewsPostAdapter extends RecyclerView.Adapter<NewsPostAdapter.NewsPostViewHolder> {

    private final List<NewsPost> postList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onCommentClick(int position);
        void onLikeClick(int position);
        void onShareClick(int position);
        void onMoreOptionsClick(int position, View view);
        void onDonateClick(int position); // ĐÃ THÊM
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public NewsPostAdapter(List<NewsPost> postList) {
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
        public ImageView btnShare;
        public ImageView btnMoreOptions;
        public LinearLayout commentArea;
        public ImageView btnDonate;

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
            btnShare = itemView.findViewById(R.id.btnShare);
            commentArea = itemView.findViewById(R.id.bangtin_layout_comment_area);
            btnMoreOptions = itemView.findViewById(R.id.btnMoreOptions);
            btnDonate = itemView.findViewById(R.id.btn_donate_hand);

            View.OnClickListener clickListener = v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        int id = v.getId();
                        if (id == R.id.bangtin_layout_comment_area) {
                            listener.onCommentClick(position);
                        } else if (id == R.id.btnLike) {
                            listener.onLikeClick(position);
                        } else if (id == R.id.btnShare) {
                            listener.onShareClick(position);
                        } else if (id == R.id.btnMoreOptions) {
                            listener.onMoreOptionsClick(position, v);
                        } else if (id == R.id.btn_donate_hand) {
                            listener.onDonateClick(position);
                        }
                    }
                }
            };

            commentArea.setOnClickListener(clickListener);
            btnLike.setOnClickListener(clickListener);
            btnShare.setOnClickListener(clickListener);
            btnMoreOptions.setOnClickListener(clickListener);
            btnDonate.setOnClickListener(clickListener);
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

            likeCount.setText(String.valueOf(post.likeCount) + " lượt thích");
            commentCount.setText(String.valueOf(post.commentCount) + " Bình luận");
            btnLike.setImageResource(post.isLiked ? R.drawable.bangtin_heart_filled : R.drawable.bangtin_heart_outline);
        }
    }
}
