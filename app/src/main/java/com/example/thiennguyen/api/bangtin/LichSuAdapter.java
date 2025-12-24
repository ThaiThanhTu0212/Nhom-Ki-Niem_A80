package com.example.thiennguyen.api.bangtin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.GiaoDich;

import java.util.List;

public class LichSuAdapter extends RecyclerView.Adapter<LichSuAdapter.LichSuViewHolder> {

    private final List<GiaoDich> giaoDichList;
    private final Context context;

    public LichSuAdapter(Context context, List<GiaoDich> giaoDichList) {
        this.context = context;
        this.giaoDichList = giaoDichList;
    }

    @NonNull
    @Override
    public LichSuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_su, parent, false);
        return new LichSuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LichSuViewHolder holder, int position) {
        GiaoDich giaoDich = giaoDichList.get(position);
        holder.bind(giaoDich);
    }

    @Override
    public int getItemCount() {
        return giaoDichList != null ? giaoDichList.size() : 0;
    }

    class LichSuViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivStatusIcon;
        private final TextView tvTenChienDich;
        private final TextView tvNgayGiaoDich;
        private final TextView tvSoTien;

        public LichSuViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStatusIcon = itemView.findViewById(R.id.ivStatusIcon);
            tvTenChienDich = itemView.findViewById(R.id.tvTenChienDich);
            tvNgayGiaoDich = itemView.findViewById(R.id.tvNgayGiaoDich);
            tvSoTien = itemView.findViewById(R.id.tvSoTien);
        }

        public void bind(GiaoDich giaoDich) {
            tvTenChienDich.setText(giaoDich.getTenChienDich());
            tvNgayGiaoDich.setText(giaoDich.getNgayGiaoDich());
            tvSoTien.setText(String.format("-%sđ", giaoDich.getSoTien()));

            if (giaoDich.isThanhCong()) {
                ivStatusIcon.setImageResource(R.drawable.ic_success);
                tvSoTien.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                // Bạn có thể tạo một icon cho trạng thái thất bại, ví dụ ic_failure
                // ivStatusIcon.setImageResource(R.drawable.ic_failure);
                // tvSoTien.setTextColor(ContextCompat.getColor(context, R.color.grey));
            }
        }
    }
}
