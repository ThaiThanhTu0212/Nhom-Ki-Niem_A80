package com.example.thiennguyen.view.TrangChu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.NguoiDung;

import java.util.List;

public class ToChucHomeAdapter extends RecyclerView.Adapter<ToChucHomeAdapter.ToChucHolder> {
    public interface OnItemClickListener {
        void onItemClick(NguoiDung nguoiDung);
    }
    OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    List<NguoiDung> nguoiDungList;

    public ToChucHomeAdapter(List<NguoiDung> nguoiDungList) {
        this.nguoiDungList = nguoiDungList;
    }

    @NonNull
    @Override
    public ToChucHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nguoidung_home,parent,false);
        return new ToChucHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToChucHolder holder, int position) {
        NguoiDung nguoiDung = nguoiDungList.get(position);
        if (nguoiDung==null)return;
        holder.tvTenNDHome.setText(nguoiDung.getHoTen());

        Glide.with(holder.itemView.getContext())
                .load(nguoiDung.getAvatarUrl()) // Đã sửa thành getAvatarUrl()
                .placeholder(R.drawable.nguoidung) // ảnh hiển thị tạm khi đang tải
                .error(R.drawable.nguoidung)         // ảnh lỗi fallback (có thể dùng lại ảnh trong drawable)
                .into(holder.imageViewNguoiDungHome);
        holder.itemView.setOnClickListener(v -> {
            if (listener!=null){
                listener.onItemClick(nguoiDung);
            }
        });

    }

    @Override
    public int getItemCount() {
        return nguoiDungList != null?nguoiDungList.size():0;
    }

    public class ToChucHolder extends RecyclerView.ViewHolder {
        ImageView imageViewNguoiDungHome;
        TextView tvTenNDHome;
        public ToChucHolder(@NonNull View itemView) {
            super(itemView);
            tvTenNDHome = itemView.findViewById(R.id.tvTenNDHome);
            imageViewNguoiDungHome = itemView.findViewById(R.id.imageViewNguoiDungHome);
        }
    }
}
