package com.example.thiennguyen.view.TrangChu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.NguoiDung;

import java.util.List;

public class ToChucHomeAdapter extends RecyclerView.Adapter<ToChucHomeAdapter.ToChucHomeHolder> {
    public interface OnItemClickListener {
        void onItemClick(NguoiDung nguoiDung);
    }
    NguoiDungHomeAdapter.OnItemClickListener listener;

    public void setListener(NguoiDungHomeAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    List<NguoiDung> nguoiDungList;

    public ToChucHomeAdapter(List<NguoiDung> nguoiDungList) {
        this.nguoiDungList = nguoiDungList;
    }

    @NonNull
    @Override
    public ToChucHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tochuc_home,parent,false);
        return new ToChucHomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToChucHomeHolder holder, int position) {
        NguoiDung nguoiDung = nguoiDungList.get(position);
        holder.tvTenTochucHome.setText(nguoiDung.getHoTen());
        Glide.with(holder.itemView.getContext())
                .load(nguoiDung.getAvatar())
                .placeholder(R.drawable.tai_khoan)
                .error(R.drawable.tai_khoan)
                .into(holder.imgviewTochucHome);
        holder.itemView.setOnClickListener(v -> {
            if (listener!=null){
                listener.onItemClick(nguoiDung);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nguoiDungList!=null? nguoiDungList.size():0;
    }

    public class ToChucHomeHolder extends RecyclerView.ViewHolder {
        ImageView imgviewTochucHome;
        TextView tvTenTochucHome;
        Button btnFolowTochucHome;

        public ToChucHomeHolder(@NonNull View itemView) {
            super(itemView);
            btnFolowTochucHome = itemView.findViewById(R.id.btnFolowTochucHome);
            imgviewTochucHome = itemView.findViewById(R.id.imgviewTochucHome);
            tvTenTochucHome = itemView.findViewById(R.id.tvTenTochucHome);
        }
    }
}
