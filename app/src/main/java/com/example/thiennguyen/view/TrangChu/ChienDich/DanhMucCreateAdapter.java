package com.example.thiennguyen.view.TrangChu.ChienDich;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.thiennguyen.view.model.DanhMuc;

import java.util.List;

public class DanhMucCreateAdapter extends ArrayAdapter<DanhMuc> {
    public DanhMucCreateAdapter(@NonNull Context context, @NonNull List<DanhMuc> objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = (TextView) super.getView(position, convertView, parent);
        tv.setText(getItem(position).getTenDm());
        return tv;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
        tv.setText(getItem(position).getTenDm());
        return tv;
    }

}
