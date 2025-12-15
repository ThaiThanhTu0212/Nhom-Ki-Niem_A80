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

public class HoanCanhAdapter extends RecyclerView.Adapter<HoanCanhAdapter.ViewHolder> {

    private final List<HoanCanhItem> list;

    public HoanCanhAdapter(List<HoanCanhItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.khampha_item_hoancanh, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoanCanhItem item = list.get(position);

        holder.imgThumb.setImageResource(item.getImageRes());
        holder.txtTitle.setText(item.getTitle());
        holder.txtTag.setText(item.getTag());
        holder.txtLocation.setText(item.getLocation());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgThumb;
        final TextView txtTitle;
        final TextView txtTag;
        final TextView txtLocation;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Khớp với id trong khampha_item_hoancanh.xml
            imgThumb = itemView.findViewById(R.id.imgThumb);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtTag = itemView.findViewById(R.id.txtTag);
            txtLocation = itemView.findViewById(R.id.txtAddress);
        }
    }
}
