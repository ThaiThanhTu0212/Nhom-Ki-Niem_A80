package com.example.thiennguyen.view.TrangChu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.ChienDich;

import java.util.List;

public class ChienDichHomeAdapter extends RecyclerView.Adapter<ChienDichHomeAdapter.ChienDichHomeHolder> {
    private List<ChienDich> chienDichList;

    public ChienDichHomeAdapter(List<ChienDich> chienDichList) {
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
        ChienDich chienDich = chienDichList.get(position);
        if (chienDich== null)return;
        holder.tvtenCdHome.setText(chienDich.getTenCd());
        holder.tvsoTienHienTaiHome.setText(chienDich.getSoTienHienTai().toString());
        holder.imageViewCDhome.setImageResource(R.drawable.chiendich_image);
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
