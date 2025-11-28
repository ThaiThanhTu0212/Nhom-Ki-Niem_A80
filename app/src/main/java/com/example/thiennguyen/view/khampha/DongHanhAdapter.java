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

public class DongHanhAdapter extends RecyclerView.Adapter<DongHanhAdapter.ViewHolder> {

    private final List<DongHanhItem> list;

    public DongHanhAdapter(List<DongHanhItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.khampha_item_donghanh, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DongHanhItem item = list.get(position);

        holder.imgThumb.setImageResource(item.getImageRes());
        holder.txtTitle.setText(item.getTitle());
        holder.txtAmount.setText(item.getAmount());
        holder.txtDate.setText(item.getStartDate());
        holder.txtSponsor.setText(item.getSponsor());
        holder.imgRank.setImageResource(getRankIcon(item.getRank()));
    }

    private int getRankIcon(int rank) {
        switch (rank) {
            case 1: return R.drawable.khampha_rank1;
            case 2: return R.drawable.khampha_rank2;
            case 3: return R.drawable.khampha_rank3;
            default: return R.drawable.khampha_rank1;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgThumb, imgRank;
        TextView txtTitle, txtAmount, txtDate, txtSponsor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgThumb = itemView.findViewById(R.id.imgThumb);
            imgRank = itemView.findViewById(R.id.imgRank);
            txtTitle = itemView.findViewById(R.id.txtCampaignTitle);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtDate = itemView.findViewById(R.id.txtStartDate);
            txtSponsor = itemView.findViewById(R.id.txtSponsor);
        }
    }
}
