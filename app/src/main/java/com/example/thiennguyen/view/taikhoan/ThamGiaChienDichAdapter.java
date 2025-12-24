package com.example.thiennguyen.view.taikhoan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;
import com.example.thiennguyen.view.data.DTO.Response.ThamGiaChienDichDetailResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThamGiaChienDichAdapter extends RecyclerView.Adapter<ThamGiaChienDichAdapter.ThamGiaChienDichViewHolder> {
    private List<ThamGiaChienDichDetailResponse> thamGiaChienDichList;

    public ThamGiaChienDichAdapter() {
        this.thamGiaChienDichList = new ArrayList<>();
    }

    public void setThamGiaChienDichList(List<ThamGiaChienDichDetailResponse> list) {
        this.thamGiaChienDichList = list != null ? list : new ArrayList<>();
        android.util.Log.d("ThamGiaAdapter", "Setting list with " + this.thamGiaChienDichList.size() + " items");
        notifyDataSetChanged();
        android.util.Log.d("ThamGiaAdapter", "notifyDataSetChanged called");
    }

    @NonNull
    @Override
    public ThamGiaChienDichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tham_gia_chien_dich, parent, false);
        return new ThamGiaChienDichViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThamGiaChienDichViewHolder holder, int position) {
        android.util.Log.d("ThamGiaAdapter", "Binding item at position " + position);
        ThamGiaChienDichDetailResponse item = thamGiaChienDichList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return thamGiaChienDichList != null ? thamGiaChienDichList.size() : 0;
    }

    static class ThamGiaChienDichViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenChienDich;
        TextView tvTrangThai;
        TextView tvNgayDangKy;

        public ThamGiaChienDichViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenChienDich = itemView.findViewById(R.id.tvTenChienDich);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            tvNgayDangKy = itemView.findViewById(R.id.tvNgayDangKy);
        }

        void bind(ThamGiaChienDichDetailResponse item) {
            tvTenChienDich.setText(item.getTenChienDich() != null ? item.getTenChienDich() : "");

            // Format trạng thái
            String trangThai = item.getTrangThai();
            if (trangThai != null) {
                switch (trangThai) {
                    case "cho_duyet":
                        tvTrangThai.setText("Chờ duyệt");
                        tvTrangThai.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.holo_orange_dark));
                        break;
                    case "da_duyet":
                        tvTrangThai.setText("Đã duyệt");
                        tvTrangThai.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.holo_green_dark));
                        break;
                    case "tu_choi":
                        tvTrangThai.setText("Từ chối");
                        tvTrangThai.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.holo_red_dark));
                        break;
                    default:
                        tvTrangThai.setText(trangThai);
                        tvTrangThai.setTextColor(ContextCompat.getColor(itemView.getContext(), android.R.color.black));
                        break;
                }
            } else {
                tvTrangThai.setText("");
            }

            // Format ngày đăng ký
            LocalDateTime ngayDangKy = item.getNgayDangKy();
            if (ngayDangKy != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.getDefault());
                tvNgayDangKy.setText(ngayDangKy.format(formatter));
            } else {
                tvNgayDangKy.setText("");
            }
        }
    }
}

