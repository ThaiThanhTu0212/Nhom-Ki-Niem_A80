package com.example.thiennguyen.view.khampha;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;

import java.util.Arrays;
import java.util.List;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

    private final List<String> items;

    public DemoAdapter(String type) {
        if (type.equals("Chiến dịch")) {
            items = Arrays.asList("Trồng cây xanh", "HiGreen Challenge");
        } else if (type.equals("Sự kiện")) {
            items = Arrays.asList("Fun Fit Fest", "Marathon vì biển đảo");
        } else {
            items = Arrays.asList("Nguyễn Thị Mộng", "Phạm Thị Vọng", "Trần Thị Ngọc Em");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.khampha_item_demo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
