package com.example.thiennguyen.view.bangtin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
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
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvTime, tvContent, tvLikeCount, tvCommentCount;
        ImageView imgAvatar, imgMain, imgSmall1, imgSmall2;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvContent = itemView.findViewById(R.id.tvContent);
            imgMain = itemView.findViewById(R.id.imgMain);
            imgSmall1 = itemView.findViewById(R.id.imgSmall1); // cần thêm ID
            imgSmall2 = itemView.findViewById(R.id.imgSmall2); // cần thêm ID
        }
    }
}