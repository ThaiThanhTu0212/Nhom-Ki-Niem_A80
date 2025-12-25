package com.example.thiennguyen.view.TrangChu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.data.DTO.Response.ChienDichResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListChienDichHomeAdapter extends RecyclerView.Adapter<ListChienDichHomeAdapter.ListChienDichHomeHolder> {
    public interface OnItemClickListener {
        void onItemClick(ChienDichResponse chienDich);
    }
    OnItemClickListener listener;


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    List<ChienDichResponse> chienDichList;

    public ListChienDichHomeAdapter(List<ChienDichResponse> chienDichList) {
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
        ChienDichResponse chienDich = chienDichList.get(position);
        if (chienDich== null)return;
        holder.tvTenlistCd_home.setText(chienDich.getTenCd());
        holder.tvSotienHienTai_listcd_home.setText(chienDich.getSoTienHienTai().toString());

        if (chienDich.getSoTienMucTieu() != null && chienDich.getSoTienMucTieu().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal progress = chienDich.getSoTienHienTai().divide(chienDich.getSoTienMucTieu(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            holder.progressBar_listcd_home.setProgress(progress.intValue());
        } else {
            holder.progressBar_listcd_home.setProgress(0);
        }

        if (chienDich.getNgayKetThuc() != null) {
            long diff = chienDich.getNgayKetThuc().getTime() - System.currentTimeMillis();
            if (diff > 0) {
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                holder.tvNgayConLai_listcd_home.setText("Còn " + days + " ngày");
            } else {
                holder.tvNgayConLai_listcd_home.setText("Đã kết thúc");
            }
        } else {
            holder.tvNgayConLai_listcd_home.setText("");
        }

        // The user did not provide a way to get the number of supporters, so I will set it to "0"
        holder.tvLuotUngho_listcd_home.setText("0");

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
        ProgressBar progressBar_listcd_home;
        public ListChienDichHomeHolder(@NonNull View itemView) {
            super(itemView);
            imgviewListcd_home = itemView.findViewById(R.id.imgviewListcd_home);
            tvTenlistCd_home = itemView.findViewById(R.id.tvTenlistCd_home);
            tvSotienHienTai_listcd_home = itemView.findViewById(R.id.tvSotienHienTai_listcd_home);
            tvLuotUngho_listcd_home = itemView.findViewById(R.id.tvLuotUngho_listcd_home);
            tvNgayConLai_listcd_home = itemView.findViewById(R.id.tvNgayConLai_listcd_home);
            progressBar_listcd_home = itemView.findViewById(R.id.progressBar_listcd_home);
        }
    }
}
