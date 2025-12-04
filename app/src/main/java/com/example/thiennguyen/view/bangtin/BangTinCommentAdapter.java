package com.example.thiennguyen.view.bangtin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.thiennguyen.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;

public class BangTinCommentAdapter extends RecyclerView.Adapter<BangTinCommentAdapter.CommentViewHolder> {

    private List<Comment> comments;

    public BangTinCommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bangtin_item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment c = comments.get(position);
        holder.tvAuthor.setText(c.author);
        holder.tvContent.setText(c.content);
        holder.tvTime.setText(c.time);
        holder.imgAvatar.setImageResource(c.avatarRes);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgAvatar;
        TextView tvAuthor, tvContent, tvTime;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.bangtin_img_avatar);
            tvAuthor = itemView.findViewById(R.id.bangtin_tv_author);
            tvContent = itemView.findViewById(R.id.bangtin_tv_content);
            tvTime = itemView.findViewById(R.id.bangtin_tv_time);
        }
    }
}