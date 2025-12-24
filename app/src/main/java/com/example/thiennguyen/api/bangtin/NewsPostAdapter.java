package com.example.thiennguyen.api.bangtin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;

import java.util.ArrayList;

public class NewsPostAdapter extends RecyclerView.Adapter<NewsPostAdapter.NewsPostViewHolder> {

    private Context context;
    private ArrayList<NewsPost> postList;

    public NewsPostAdapter(Context context, ArrayList<NewsPost> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public NewsPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news_post, parent, false);
        return new NewsPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsPostViewHolder holder, int position) {
        NewsPost currentPost = postList.get(position);

        holder.author.setText(currentPost.author);
        holder.time.setText(currentPost.time);
        holder.content.setText(currentPost.content);
        holder.likeCount.setText(String.valueOf(currentPost.likeCount));
        holder.commentCount.setText(String.valueOf(currentPost.commentCount));

        if (currentPost.imageUrl != null && !currentPost.imageUrl.isEmpty()) {
            holder.postImage.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(currentPost.imageUrl)
                    .into(holder.postImage);
        } else {
            holder.postImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class NewsPostViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView author;
        public TextView time;
        public TextView content;
        public ImageView postImage;
        public TextView likeCount;
        public TextView commentCount;

        public NewsPostViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.imgAvatar);
            author = itemView.findViewById(R.id.tvUsername);
            time = itemView.findViewById(R.id.tvTime);
            content = itemView.findViewById(R.id.tvContent);
            postImage = itemView.findViewById(R.id.imgMain);
            likeCount = itemView.findViewById(R.id.tvLikeCount);
            commentCount = itemView.findViewById(R.id.tvCommentCount);
        }
    }
}
