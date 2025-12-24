package com.example.thiennguyen.view.khampha;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;

import java.util.List;

public class SuKienAdapter extends RecyclerView.Adapter<SuKienAdapter.ViewHolder> {

    private List<SuKienItem> suKienItemList;

    public SuKienAdapter(List<SuKienItem> suKienItemList) {
        this.suKienItemList = suKienItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.khampha_item_sukien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SuKienItem suKienItem = suKienItemList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(suKienItem.getBannerRes())
                .into(holder.imgBanner);

        holder.txtTitle.setText(suKienItem.getTitle());
        holder.txtDate.setText(suKienItem.getDate());
        holder.txtInterested.setText(suKienItem.getInterested());
        holder.txtTag.setText(suKienItem.getTag());

        holder.itemView.setOnClickListener(v -> {
            Intent intent;
            // Check position to open the correct detail screen
            if (position == 0) {
                intent = new Intent(v.getContext(), ChiTietSuKien1Activity.class);
            } else if (position == 1) {
                intent = new Intent(v.getContext(), ChiTietSuKien2Activity.class);
            } else {
                // Default or other events can go here
                intent = new Intent(v.getContext(), ChiTietSuKien2Activity.class);
            }
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return suKienItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBanner;
        TextView txtTitle, txtDate, txtInterested, txtTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBanner = itemView.findViewById(R.id.imgBanner);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtInterested = itemView.findViewById(R.id.txtInterested);
            txtTag = itemView.findViewById(R.id.txtTag);
        }
    }
}
