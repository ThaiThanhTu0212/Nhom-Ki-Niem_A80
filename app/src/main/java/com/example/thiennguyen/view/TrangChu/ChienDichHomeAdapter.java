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
import com.example.thiennguyen.view.data.DTO.Response.ChienDichResponse;
import com.example.thiennguyen.view.model.ChienDich;

import java.util.List;

public class ChienDichHomeAdapter extends RecyclerView.Adapter<ChienDichHomeAdapter.ChienDichHomeHolder> {
    public interface OnItemClickListener {
        void onItemClick(ChienDichResponse chienDich);
    }
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private List<ChienDichResponse> chienDichList;

    public ChienDichHomeAdapter(List<ChienDichResponse> chienDichList) {
        this.chienDichList = chienDichList;
    }

    @NonNull
    @Override
    public ChienDichHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chiendich_home,parent,false);
        return new ChienDichHomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChienDichHomeHolder holder, int position) {
        ChienDichResponse chienDich = chienDichList.get(position);
        if (chienDich== null)return;
        holder.tvtenCdHome.setText(chienDich.getTenCd());
        holder.tvsoTienHienTaiHome.setText("Số đã ủng hộ tại: "+chienDich.getSoTienHienTai().toString()+ " VND");
        Glide.with(holder.itemView.getContext())
                .load(chienDich.getHinhAnh())
                .placeholder(R.drawable.chiendich_image) // ảnh hiển thị tạm khi đang tải
                .error(R.drawable.chiendich_image)         // ảnh lỗi fallback (có thể dùng lại ảnh trong drawable)
                .into(holder.imageViewCDhome);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null){
                listener.onItemClick(chienDich);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (chienDichList != null) ? chienDichList.size() : 0;
    }

    public class ChienDichHomeHolder extends RecyclerView.ViewHolder {
        TextView tvtenCdHome,tvsoTienHienTaiHome;
        ImageView imageViewCDhome;
        public ChienDichHomeHolder(@NonNull View itemView) {
            super(itemView);
            tvtenCdHome = itemView.findViewById(R.id.tvtenCdHome);
            tvsoTienHienTaiHome = itemView.findViewById(R.id.tvsoTienHienTaiHome);
            imageViewCDhome = itemView.findViewById(R.id.imageViewCDhome);
        }
    }
}
