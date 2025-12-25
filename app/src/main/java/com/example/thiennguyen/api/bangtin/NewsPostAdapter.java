package com.example.thiennguyen.api.bangtin;

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
        // Header & Content
        CircleImageView authorAvatar;
        TextView authorName;
        TextView postTime;
        TextView postContent;
        RoundedImageView postImage;
        ImageView optionsButton;

        // Donation Section
        ConstraintLayout donationSection;
        ProgressBar donationProgressBar;
        TextView donatorsCount;
        TextView daysLeft;
        ImageView donateButton;

        // Stats & Actions
        TextView likeCount;
        TextView commentCount;
        TextView likeButton;
        TextView commentButton;
        TextView shareButton;

        public NewsPostViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            // Ánh xạ view
            authorAvatar = itemView.findViewById(R.id.post_author_avatar);
            authorName = itemView.findViewById(R.id.post_author_name);
            postTime = itemView.findViewById(R.id.post_time);
            postContent = itemView.findViewById(R.id.post_content);
            postImage = itemView.findViewById(R.id.post_image);
            optionsButton = itemView.findViewById(R.id.post_options_button);

            donationSection = itemView.findViewById(R.id.donation_section);
            donationProgressBar = itemView.findViewById(R.id.donation_progress_bar);
            donatorsCount = itemView.findViewById(R.id.donation_donators_count);
            daysLeft = itemView.findViewById(R.id.donation_days_left);
            donateButton = itemView.findViewById(R.id.post_donate_button);

            likeCount = itemView.findViewById(R.id.post_like_count);
            commentCount = itemView.findViewById(R.id.post_comment_count);
            likeButton = itemView.findViewById(R.id.post_like_button);
            commentButton = itemView.findViewById(R.id.post_comment_button);
            shareButton = itemView.findViewById(R.id.post_share_button);

            // Gán sự kiện click
            likeButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) listener.onLikeClick(position);
                }
            });

            commentButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) listener.onCommentClick(position);
                }
            });

            shareButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) listener.onShareClick(position);
                }
            });

            optionsButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) listener.onMoreOptionsClick(position, v);
                }
            });

            donateButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) listener.onDonateClick(position);
                }
            });
        }

        public void bind(NewsPost post) {
            authorName.setText(post.author);
            postTime.setText(post.time);
            postContent.setText(post.content);

            Glide.with(itemView.getContext()).load(post.avatarResource).placeholder(R.drawable.tinh_nguyen_vien_an_avatar).error(R.drawable.tinh_nguyen_vien_an_avatar).into(authorAvatar);

            if (post.imageUrl != null && !post.imageUrl.isEmpty()) {
                postImage.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext()).load(post.imageUrl).placeholder(R.drawable.placeholder_image).error(R.drawable.bangtin_img_default_post).into(postImage);
            } else {
                postImage.setVisibility(View.GONE);
            }

            // Xử lý hiển thị khu vực quyên góp
            if (post.showDonation) {
                donationSection.setVisibility(View.VISIBLE);
                donationProgressBar.setProgress(post.donationProgress);
                donatorsCount.setText(post.donatorsCount + " người ủng hộ");
                daysLeft.setText(post.daysLeft + " ngày còn lại");
            } else {
                donationSection.setVisibility(View.GONE);
            }

            commentCount.setText(post.commentCount + " bình luận");
            updateLikeStatus(post);
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
