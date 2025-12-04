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
import com.example.thiennguyen.view.model.ChienDich;

import java.util.List;

public class ListChienDichHomeAdapter extends RecyclerView.Adapter<ListChienDichHomeAdapter.ListChienDichHomeHolder> {
    public interface OnItemClickListener {
        void onItemClick(ChienDich chienDich);
    }
    OnItemClickListener listener;


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    List<ChienDich> chienDichList;

    public ListChienDichHomeAdapter(List<ChienDich> chienDichList) {
        this.chienDichList = chienDichList;
    }

    @NonNull
    @Override
    public ListChienDichHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listchiendich_home,parent,false);
        return new ListChienDichHomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListChienDichHomeHolder holder, int position) {
        ChienDich chienDich = chienDichList.get(position);
        if (chienDich== null)return;
        holder.tvTenlistCd_home.setText(chienDich.getTenCd());
        holder.tvSotienHienTai_listcd_home.setText(chienDich.getSoTienHienTai().toString());
        Glide.with(holder.itemView.getContext())
                .load(chienDich.getHinhAnh())
                .placeholder(R.drawable.chiendich_image)
                .error(R.drawable.chiendich_image)
                .into(holder.imgviewListcd_home);
        holder.itemView.setOnClickListener(v -> {
            if (listener!= null){
                listener.onItemClick(chienDich);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chienDichList!= null?chienDichList.size():0;
    }

    public class ListChienDichHomeHolder extends RecyclerView.ViewHolder {
        ImageView imgviewListcd_home;
        TextView tvTenlistCd_home, tvSotienHienTai_listcd_home, tvLuotUngho_listcd_home, tvNgayConLai_listcd_home;
        public ListChienDichHomeHolder(@NonNull View itemView) {
            super(itemView);
            imgviewListcd_home = itemView.findViewById(R.id.imgviewListcd_home);
            tvTenlistCd_home = itemView.findViewById(R.id.tvTenlistCd_home);
            tvSotienHienTai_listcd_home = itemView.findViewById(R.id.tvSotienHienTai_listcd_home);

        }
    }
}
