package com.example.thiennguyen.view.khampha;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    private final List<BannerItem> banners;

    public BannerAdapter(List<BannerItem> banners) {
        this.banners = banners;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.khampha_item_banner_gradient, parent, false);
        return new BannerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        BannerItem banner = banners.get(position);

        // Gradient nền
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{banner.getColorStart(), banner.getColorEnd()}
        );
        gradient.setCornerRadius(24f);
        holder.cardView.setBackground(gradient);

        holder.tvTitle.setText(banner.getTitle());
        holder.tvSubtitle.setText(banner.getSubtitle());
        holder.imgIcon.setImageResource(banner.getIconRes());
    }

    @Override
    public int getItemCount() {
        return banners.size();
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTitle, tvSubtitle;
        ImageView imgIcon;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardBanner);
            tvTitle = itemView.findViewById(R.id.tvBannerTitle);
            tvSubtitle = itemView.findViewById(R.id.tvBannerSubtitle);
            imgIcon = itemView.findViewById(R.id.imgBannerIcon);
        }
    }
}
