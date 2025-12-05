package com.example.thiennguyen.view.thongbao;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.ChienDich;
import com.example.thiennguyen.view.ungho.ChiTietPagerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ThongBaoFragment extends Fragment implements ThongBaoAdapter.OnThongBaoClickListener {

    private ArrayList<ChienDich> chienDichList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chienDichList = getArguments().getParcelableArrayList("chien_dich_list");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_bao_layout, container, false);

        ImageView imgClose = view.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewThongBao);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (chienDichList != null && !chienDichList.isEmpty()) {
            ThongBaoAdapter adapter = new ThongBaoAdapter(chienDichList, this);
            recyclerView.setAdapter(adapter);
        } else {
            view.findViewById(R.id.tv_empty_notification).setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setBottomNavVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        setBottomNavVisibility(View.VISIBLE);
    }

    private void setBottomNavVisibility(int visibility) {
        if (getActivity() != null) {
            BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavID);
            if (bottomNav != null) {
                bottomNav.setVisibility(visibility);
            }
        }
    }

    @Override
    public void onThongBaoClick(int position) {
        ChiTietPagerFragment chiTietPagerFragment = ChiTietPagerFragment.newInstance(chienDichList, position);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, chiTietPagerFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
