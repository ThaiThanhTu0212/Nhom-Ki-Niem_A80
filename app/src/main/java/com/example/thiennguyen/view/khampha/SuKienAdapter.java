package com.example.thiennguyen.view.khampha;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;

import java.util.List;

public class SuKienAdapter extends RecyclerView.Adapter<SuKienAdapter.ViewHolder> {

    private final List<SuKienItem> list;

    public SuKienAdapter(List<SuKienItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.khampha_item_sukien, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SuKienItem item = list.get(position);

        holder.imgBanner.setImageResource(item.getBannerRes());
        holder.txtTag.setText(item.getTag());
        holder.txtTitle.setText(item.getTitle());
        holder.txtDate.setText(item.getDate());
        holder.txtInterested.setText(item.getInterested());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgBanner;
        final TextView txtTag;
        final TextView txtTitle;
        final TextView txtDate;
        final TextView txtInterested;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBanner = itemView.findViewById(R.id.imgBanner);
            txtTag = itemView.findViewById(R.id.txtTag);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtInterested = itemView.findViewById(R.id.txtInterested);
        }
    }
}
