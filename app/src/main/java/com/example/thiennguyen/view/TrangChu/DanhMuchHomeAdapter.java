package com.example.thiennguyen.view.TrangChu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.DanhMuc;

import java.util.List;

public class DanhMuchHomeAdapter extends RecyclerView.Adapter<DanhMuchHomeAdapter.DanhMucHomeHolder> {
    List<DanhMuc> danhMucListHome;

    public DanhMuchHomeAdapter(List<DanhMuc> danhMucListHome) {
        this.danhMucListHome = danhMucListHome;
    }

    @NonNull
    @Override
    public DanhMucHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_danhmuc_home,parent,false);
        return new DanhMucHomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhMucHomeHolder holder, int position) {
        DanhMuc danhMucHome= danhMucListHome.get(position);
        if (danhMucHome==null)return;
        holder.tvtenDmHome.setText(danhMucHome.getTenDm());
    }

    @Override
    public int getItemCount() {
        return danhMucListHome!= null?danhMucListHome.size():0;
    }

    public class DanhMucHomeHolder extends RecyclerView.ViewHolder {
        TextView tvtenDmHome;
        public DanhMucHomeHolder(@NonNull View itemView) {
            super(itemView);
            tvtenDmHome = itemView.findViewById(R.id.tvtenDmHome);
        }
    }
}
