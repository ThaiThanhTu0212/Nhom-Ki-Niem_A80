package com.example.thiennguyen.view.bangtin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.thiennguyen.R;

public class ThongBaoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongbao, container, false);

        // Có thể để trống hoặc thêm dòng chữ "Chưa có thông báo"
        TextView tv = view.findViewById(R.id.tv_empty_notification);
        if (tv != null) tv.setText("Chưa có thông báo nào");

        return view;
    }
}