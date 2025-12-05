package com.example.thiennguyen.view.bangtin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;
import com.example.thiennguyen.api.bangtin.BinhLuan;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<BinhLuan> commentList;

    public CommentAdapter(List<BinhLuan> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        BinhLuan comment = commentList.get(position);
        holder.authorName.setText("Người dùng " + comment.idNguoiBinhLuan);
        holder.content.setText(comment.noiDung);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView authorName;
        TextView content;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.comment_author_name);
            content = itemView.findViewById(R.id.comment_content);
        }
    }
}
