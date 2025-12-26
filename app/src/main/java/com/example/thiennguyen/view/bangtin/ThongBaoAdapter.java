package com.example.thiennguyen.view.bangtin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;

import java.util.List;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ThongBaoViewHolder> {

    private Context context;
    private List<ThongBao> thongBaoList;

    public ThongBaoAdapter(Context context, List<ThongBao> thongBaoList) {
        this.context = context;
        this.thongBaoList = thongBaoList;
    }

    @NonNull
    @Override
    public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thong_bao, parent, false);
        return new ThongBaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongBaoViewHolder holder, int position) {
        ThongBao thongBao = thongBaoList.get(position);
        holder.content.setText(thongBao.getContent());
        holder.avatar.setImageResource(thongBao.getAvatarResId());
    }

    @Override
    public int getItemCount() {
        return thongBaoList.size();
    }

    public static class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar; // ĐÃ SỬA: từ CircleImageView thành ImageView
        TextView content;

        public ThongBaoViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.imgAvatar);
            content = itemView.findViewById(R.id.tvThongBaoContent);
        }
    }
}
