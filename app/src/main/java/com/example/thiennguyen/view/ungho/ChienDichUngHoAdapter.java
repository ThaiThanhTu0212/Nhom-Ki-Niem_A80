package com.example.thiennguyen.view.ungho;

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
import com.example.thiennguyen.view.model.ChienDich;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ChienDichUngHoAdapter extends RecyclerView.Adapter<ChienDichUngHoAdapter.ChienDichUngHoViewHolder> {
    private final List<ChienDich> chienDichList;
    private final OnChienDichClickListener onChienDichClickListener;

    public ChienDichUngHoAdapter(List<ChienDich> chienDichList, OnChienDichClickListener onChienDichClickListener) {
        this.chienDichList = chienDichList;
        this.onChienDichClickListener = onChienDichClickListener;
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
        holder.bind(chienDich, onChienDichClickListener);
    }

    @Override
    public int getItemCount() {
        return chienDichList != null ? chienDichList.size() : 0;
    }

    public interface OnChienDichClickListener {
        void onChienDichClick(ChienDich chienDich);
    }

    static class ChienDichUngHoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgchiendichUngHo;
        TextView tvTenChienDichUngHo, tvSotienHienTai, tvSotienMucTieu;
        ProgressBar progressBarItem;

        public ChienDichUngHoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgchiendichUngHo = itemView.findViewById(R.id.imgchiendichUngHo);
            tvTenChienDichUngHo = itemView.findViewById(R.id.tvTenChienDichUngHo);
            tvSotienHienTai = itemView.findViewById(R.id.tvSotienHienTai);
            tvSotienMucTieu = itemView.findViewById(R.id.tvSotienMucTieu);
            progressBarItem = itemView.findViewById(R.id.progressBarItem);
        }

        void bind(final ChienDich chienDich, final OnChienDichClickListener listener) {
            tvTenChienDichUngHo.setText(chienDich.getTenCd());

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            tvSotienHienTai.setText(currencyFormat.format(chienDich.getSoTienHienTai()));
            tvSotienMucTieu.setText("trên " + currencyFormat.format(chienDich.getSoTienMucTieu()));

            Glide.with(itemView.getContext())
                    .load(chienDich.getHinhAnh())
                    .into(imgchiendichUngHo);

            if (chienDich.getSoTienMucTieu().doubleValue() > 0) {
                int progress = chienDich.getSoTienHienTai().multiply(new java.math.BigDecimal(100))
                        .divide(chienDich.getSoTienMucTieu(), RoundingMode.HALF_UP).intValue();
                progressBarItem.setProgress(progress);
            } else {
                progressBarItem.setProgress(0);
            }

            itemView.setOnClickListener(v -> listener.onChienDichClick(chienDich));
        }
    }
}
// a

