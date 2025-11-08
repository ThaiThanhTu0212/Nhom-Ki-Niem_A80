package com.example.thiennguyen.view.TrangChu;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NguoiDungHomeAdapter extends RecyclerView.Adapter<NguoiDungHomeAdapter.NguoiDungHolder> {
    @NonNull
    @Override
    public NguoiDungHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiDungHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NguoiDungHolder extends RecyclerView.ViewHolder {
        public NguoiDungHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
