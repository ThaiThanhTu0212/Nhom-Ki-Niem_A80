package com.example.thiennguyen.view.ungho;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.ChienDich;
import com.example.thiennguyen.view.model.DanhMuc;
import com.example.thiennguyen.view.model.NguoiDung;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UngHoFragment extends Fragment {
    RecyclerView chiendich_ungHo_recycleID;
    List<ChienDich> chienDichList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ung_ho, container, false);
        initData();
        initUi(view);
        return  view;
    }
    private void initUi(View view) {
        chiendich_ungHo_recycleID = view.findViewById(R.id.chiendich_ungHo_recycleID);
        chiendich_ungHo_recycleID.setLayoutManager(new LinearLayoutManager(getContext()));
        ChienDichUngHoAdapter chiendichUngHoAdapter = new ChienDichUngHoAdapter(chienDichList);
        chiendich_ungHo_recycleID.setAdapter(chiendichUngHoAdapter);
    }

    private void initData() {

    }


}