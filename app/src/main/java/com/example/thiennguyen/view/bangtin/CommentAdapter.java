package com.example.thiennguyen.view.bangtin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;
// SỬA Ở ĐÂY: Import đúng lớp BinhLuan từ package 'view.bangtin'
import com.example.thiennguyen.view.bangtin.BinhLuan;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private List<BinhLuan> commentList;

    // Bây giờ hàm khởi tạo này sẽ chấp nhận List<BinhLuan> từ BangTinCommentActivity
    public CommentAdapter(Context context, List<BinhLuan> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        BinhLuan comment = commentList.get(position);

        // SỬA Ở ĐÂY: Sử dụng các phương thức getter từ lớp BinhLuan trong package 'view'
        holder.username.setText(comment.getAuthor());
        holder.content.setText(comment.getContent());
        holder.time.setText(comment.getTime());
        holder.avatar.setImageResource(comment.getAvatarRes());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        CircleImageView avatar;
        TextView username, content, time;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.comment_avatar);
            username = itemView.findViewById(R.id.comment_username);
            content = itemView.findViewById(R.id.comment_content);
            time = itemView.findViewById(R.id.comment_time);
        }
    }
}
