package com.example.thiennguyen.api.bangtin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewsPostAdapter extends RecyclerView.Adapter<NewsPostAdapter.NewsPostViewHolder> {

    private final ArrayList<NewsPost> postList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onCommentClick(int position);
        void onLikeClick(int position);
        void onShareClick(int position);
        void onMoreOptionsClick(int position, View view);
        void onDonateClick(int position);
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
        // Views cũ
        ImageView avatar, postImage, btnLike, btnShare, btnMoreOptions;
        TextView author, time, content, commentCount, likeCount;
        LinearLayout commentArea;
        
        // Views mới cho thanh ủng hộ
        RelativeLayout donationBar;
        TextView tvDonationTitle, tvDonationPercentage, tvDonationInfo;
        ProgressBar donationProgress;
        ImageView btnDonateHand;

        public NewsPostViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            // Gán ID cho views cũ
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
            
            // Gán ID cho views mới
            donationBar = itemView.findViewById(R.id.donation_bar);
            tvDonationTitle = itemView.findViewById(R.id.tv_donation_title);
            donationProgress = itemView.findViewById(R.id.donation_progress);
            tvDonationPercentage = itemView.findViewById(R.id.tv_donation_percentage);
            btnDonateHand = itemView.findViewById(R.id.btn_donate_hand);
            tvDonationInfo = itemView.findViewById(R.id.tv_donation_info);

            // Set Listeners
            commentArea.setOnClickListener(v -> handleClick(listener, listener::onCommentClick));
            btnLike.setOnClickListener(v -> handleClick(listener, listener::onLikeClick));
            btnShare.setOnClickListener(v -> handleClick(listener, listener::onShareClick));
            btnMoreOptions.setOnClickListener(v -> {
                 if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onMoreOptionsClick(position, v);
                    }
                }
            });
            donationBar.setOnClickListener(v -> handleClick(listener, listener::onDonateClick));
            btnDonateHand.setOnClickListener(v -> handleClick(listener, listener::onDonateClick));
        }

        private void handleClick(OnItemClickListener listener, SinglePositionClickListener clickListener) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onPositionClick(position);
                }
            }
        }

        @FunctionalInterface
        interface SinglePositionClickListener {
            void onPositionClick(int position);
        }

        public void bind(NewsPost post) {
            // Bind dữ liệu cũ
            author.setText(post.author);
            time.setText(post.time);
            content.setText(post.content);
            avatar.setImageResource(post.avatarResource);
            updateLikeStatus(post);
            commentCount.setText(String.format(Locale.getDefault(), "%d Bình luận", post.commentCount));

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
            
            // Bind dữ liệu mới cho thanh ủng hộ
            if (post.showDonationBar) {
                donationBar.setVisibility(View.VISIBLE);
                tvDonationInfo.setVisibility(View.VISIBLE);
                donationProgress.setProgress(post.donationProgress);
                tvDonationPercentage.setText(String.format(Locale.getDefault(), "%d%%", post.donationProgress));
                tvDonationInfo.setText(String.format(Locale.getDefault(), "%d người đã ủng hộ • Còn lại %d ngày", post.donatorsCount, post.daysLeft));
                
                if(post.donatorsCount == 0){
                    tvDonationTitle.setText("Hãy là người ủng hộ đầu tiên");
                } else {
                    tvDonationTitle.setText("Chung tay quyên góp");
                }
            } else {
                donationBar.setVisibility(View.GONE);
                tvDonationInfo.setVisibility(View.GONE);
            }
        }

        public void updateLikeStatus(NewsPost post) {
            likeCount.setText(String.format(Locale.getDefault(), "%d lượt thích", post.likeCount));
            if (post.isLiked) {
                btnLike.setImageResource(R.drawable.bangtin_heart_filled);
            } else {
                btnLike.setImageResource(R.drawable.bangtin_heart_outline);
            }
        }
    }
}
