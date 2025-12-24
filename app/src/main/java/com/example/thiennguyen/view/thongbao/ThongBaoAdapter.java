package com.example.thiennguyen.view.thongbao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.ChienDich;

import java.util.List;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ThongBaoViewHolder> {

    private final List<ChienDich> chienDichList;
    private final OnThongBaoClickListener listener;

    public interface OnThongBaoClickListener {
        void onThongBaoClick(int position);
    }

    public ThongBaoAdapter(List<ChienDich> chienDichList, OnThongBaoClickListener listener) {
        this.chienDichList = chienDichList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_bao, parent, false);
        return new ThongBaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongBaoViewHolder holder, int position) {
        ChienDich chienDich = chienDichList.get(position);
        holder.bind(chienDich, listener, position);
    }

    @Override
    public int getItemCount() {
        return chienDichList.size();
    }

    static class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvContent;

        public ThongBaoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvContent = itemView.findViewById(R.id.tvThongBaoContent);
        }

        public void bind(final ChienDich chienDich, final OnThongBaoClickListener listener, final int position) {
            String content = "Tổ chức \"" + chienDich.getNguoiToChuc().getHoTen() + "\" vừa có cập nhật mới về chiến dịch '" + chienDich.getTenCd() + "'.";
            tvContent.setText(content);

            Glide.with(itemView.getContext())
                    .load(chienDich.getHinhAnh()) // Using campaign image as avatar
                    .into(imgAvatar);

            itemView.setOnClickListener(v -> listener.onThongBaoClick(position));
        }
    }
}
