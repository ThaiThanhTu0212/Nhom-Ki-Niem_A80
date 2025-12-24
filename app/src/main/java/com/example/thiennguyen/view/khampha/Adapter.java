package com.example.thiennguyen.view.khampha;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;

import java.util.Arrays;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final List<String> items;
    private final String type;

    public Adapter(String type) {
        this.type = type;
        if (type.equals("Chiến dịch")) {
            items = Arrays.asList("Trồng cây xanh", "HiGreen Challenge", "Tiếp sức đến trường");
        } else if (type.equals("Sự kiện")) {
            items = Arrays.asList("Fun Fit Fest", "Marathon vì biển đảo", "Ngày hội thiện nguyện");
        } else {
            items = Arrays.asList("Nguyễn Thị Mộng", "Phạm Thị Vọng", "Trần Thị Ngọc Em");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.khampha_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items.get(position);
        holder.txtTitle.setText(item);

        // 🎨 Đổi màu nền theo loại danh mục
        if (type.equals("Chiến dịch")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#C8E6C9")); // xanh lá nhạt
        } else if (type.equals("Sự kiện")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFF9C4")); // vàng nhạt
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFCCBC")); // cam nhạt
        }

        // ✨ Hiệu ứng nhấn nhẹ
        holder.itemView.setOnClickListener(v -> {
            v.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(100)
                    .withEndAction(() -> v.animate().scaleX(1f).scaleY(1f).setDuration(100))
                    .start();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView txtTitle;
        final CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            cardView = (CardView) itemView;
        }
    }
}
