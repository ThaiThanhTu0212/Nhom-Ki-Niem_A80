package com.example.thiennguyen.view.ungho;

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

public class ChienDichUngHoAdapter extends RecyclerView.Adapter<ChienDichUngHoAdapter.ChienDichUngHoViewHolder> {
    List<ChienDich> chienDichList;

    public ChienDichUngHoAdapter(List<ChienDich> chienDichList) {
        this.chienDichList = chienDichList;
    }

    @NonNull
    @Override
    public ChienDichUngHoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chiendich_ungho, parent, false);

        return new ChienDichUngHoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChienDichUngHoViewHolder holder, int position) {
        ChienDich chienDich = chienDichList.get(position);

        holder.tvTenChienDichUngHo.setText(chienDich.getTenCd());
        holder.tvSotienHienTai.setText(chienDich.getSoTienHienTai().toString()+" VND");
        holder.tvSotienMucTieu.setText(chienDich.getSoTienMucTieu().toString()+" VND");
        Glide.with(holder.itemView.getContext())
                .load(chienDich.getHinhAnh())
                .into(holder.imgchiendichUngHo);
    }

    @Override
    public int getItemCount() {
        return chienDichList!=null? chienDichList.size():0;
    }

    public class ChienDichUngHoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgchiendichUngHo;
        TextView tvTenChienDichUngHo,tvSotienHienTai,tvSotienMucTieu;
        public ChienDichUngHoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgchiendichUngHo = itemView.findViewById(R.id.imgchiendichUngHo);
            tvTenChienDichUngHo = itemView.findViewById(R.id.tvTenChienDichUngHo);
            tvSotienHienTai = itemView.findViewById(R.id.tvSotienHienTai);
            tvSotienMucTieu = itemView.findViewById(R.id.tvSotienMucTieu);
        }
    }
}
// a

