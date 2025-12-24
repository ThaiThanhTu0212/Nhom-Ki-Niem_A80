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
import com.example.thiennguyen.view.data.DTO.Response.NguoiDungResponse;
import com.example.thiennguyen.view.model.ChienDich;
import com.example.thiennguyen.view.model.NguoiDung;

import java.util.List;
import java.util.zip.Inflater;

public class NguoiDungHomeAdapter extends RecyclerView.Adapter<NguoiDungHomeAdapter.NguoiDungHolder> {
    public interface OnItemClickListener {
        void onItemClick(NguoiDungResponse nguoiDung);
    }
    OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    List<NguoiDungResponse> nguoiDungList;

    public NguoiDungHomeAdapter(List<NguoiDungResponse> nguoiDungList) {
        this.nguoiDungList = nguoiDungList;
    }

    @NonNull
    @Override
    public NguoiDungHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nguoidung_home,parent,false);
        return new NguoiDungHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiDungHolder holder, int position) {
        NguoiDungResponse nguoiDung = nguoiDungList.get(position);
        if (nguoiDung==null)return;
        holder.tvTenNDHome.setText(nguoiDung.getHoTen());

        Glide.with(holder.itemView.getContext())
                .load(nguoiDung.getAvatar())
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

    public class NguoiDungHolder extends RecyclerView.ViewHolder {
        ImageView imageViewNguoiDungHome;
        TextView tvTenNDHome;
        public NguoiDungHolder(@NonNull View itemView) {
            super(itemView);
            tvTenNDHome = itemView.findViewById(R.id.tvTenNDHome);
            imageViewNguoiDungHome = itemView.findViewById(R.id.imageViewNguoiDungHome);
        }
    }
}
