package com.example.thiennguyen.view.bangtin;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.api.bangtin.NewsPost;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsPostAdapter extends RecyclerView.Adapter<NewsPostAdapter.NewsPostViewHolder> {

    private final List<NewsPost> postList;
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
        // Khai báo toàn bộ view từ layout item_news_post.xml
        CircleImageView authorAvatar;
        TextView authorName, postTime, postContent, likeCount, commentCount, likeButton, commentButton, shareButton;
        RoundedImageView postImage;
        ImageView optionsButton;

        // Donation Section Views
        ConstraintLayout donationSection;
        ProgressBar donationProgressBar;
        ImageView postDonateButton;
        TextView donationDonatorsCount;
        TextView donationDaysLeft;

        public NewsPostViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            // Ánh xạ các view cơ bản
            authorAvatar = itemView.findViewById(R.id.post_author_avatar);
            authorName = itemView.findViewById(R.id.post_author_name);
            postTime = itemView.findViewById(R.id.post_time);
            postContent = itemView.findViewById(R.id.post_content);
            postImage = itemView.findViewById(R.id.post_image);
            likeCount = itemView.findViewById(R.id.post_like_count);
            commentCount = itemView.findViewById(R.id.post_comment_count);
            likeButton = itemView.findViewById(R.id.post_like_button);
            commentButton = itemView.findViewById(R.id.post_comment_button);
            shareButton = itemView.findViewById(R.id.post_share_button);
            optionsButton = itemView.findViewById(R.id.post_options_button);

            // Ánh xạ các view của khu vực ủng hộ
            donationSection = itemView.findViewById(R.id.donation_section);
            donationProgressBar = itemView.findViewById(R.id.donation_progress_bar);
            postDonateButton = itemView.findViewById(R.id.post_donate_button);
            donationDonatorsCount = itemView.findViewById(R.id.donation_donators_count);
            donationDaysLeft = itemView.findViewById(R.id.donation_days_left);

            // Thiết lập sự kiện click
            setupClickListeners(listener);
        }

        private void setupClickListeners(final OnItemClickListener listener) {
            View.OnClickListener clickListener = v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        int id = v.getId();
                        if (id == R.id.post_like_button) {
                            listener.onLikeClick(position);
                        } else if (id == R.id.post_comment_button) {
                            listener.onCommentClick(position);
                        } else if (id == R.id.post_share_button) {
                            listener.onShareClick(position);
                        } else if (id == R.id.post_options_button) {
                            listener.onMoreOptionsClick(position, v);
                        } else if (id == R.id.post_donate_button) {
                            listener.onDonateClick(position);
                        }
                    }
                }
            };

            likeButton.setOnClickListener(clickListener);
            commentButton.setOnClickListener(clickListener);
            shareButton.setOnClickListener(clickListener);
            optionsButton.setOnClickListener(clickListener);
            postDonateButton.setOnClickListener(clickListener);
        }

        public void bind(NewsPost post) {
            authorName.setText(post.author);
            postTime.setText(post.time);
            postContent.setText(post.content);
            authorAvatar.setImageResource(post.avatarResource); // Giả sử có avatar resource

            // Xử lý hiển thị hình ảnh
            if (post.imageUrl != null && !post.imageUrl.isEmpty()) {
                postImage.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext()).load(post.imageUrl).into(postImage);
            } else {
                postImage.setVisibility(View.GONE);
            }

            // Cập nhật số liệu thống kê
            commentCount.setText(post.commentCount + " bình luận");
            updateLikeStatus(post);

            // LOGIC QUAN TRỌNG NHẤT: HIỂN THỊ HOẶC ẨN KHU VỰC ỦNG HỘ
            if (post.showDonation) {
                donationSection.setVisibility(View.VISIBLE);
                donationProgressBar.setProgress(post.donationProgress);
                donationDonatorsCount.setText(post.donatorsCount + " người ủng hộ");
                donationDaysLeft.setText(post.daysLeft + " ngày còn lại");
            } else {
                donationSection.setVisibility(View.GONE);
            }
        }

        public void updateLikeStatus(NewsPost post) {
            likeCount.setText(post.likeCount + " lượt thích");

            if (post.isLiked) {
                likeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_filled, 0, 0, 0);
                TextViewCompat.setCompoundDrawableTintList(likeButton, ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.primary)));
                likeButton.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.primary));
                likeButton.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                likeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like_outline, 0, 0, 0);
                TextViewCompat.setCompoundDrawableTintList(likeButton, ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), R.color.gray_text)));
                likeButton.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.gray_text));
                likeButton.setTypeface(Typeface.DEFAULT);
            }
        }
    }
}
