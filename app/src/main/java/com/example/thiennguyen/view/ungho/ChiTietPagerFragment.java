package com.example.thiennguyen.view.ungho;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.ChienDich;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ChiTietPagerFragment extends Fragment {

    private static final String ARG_CHIEN_DICH_LIST = "chien_dich_list";
    private static final String ARG_POSITION = "position";

    private ArrayList<ChienDich> chienDichList;
    private int startPosition;

    public static ChiTietPagerFragment newInstance(ArrayList<ChienDich> chienDichList, int position) {
        ChiTietPagerFragment fragment = new ChiTietPagerFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_CHIEN_DICH_LIST, chienDichList);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chienDichList = getArguments().getParcelableArrayList(ARG_CHIEN_DICH_LIST);
            startPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chi_tiet_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager2 viewPager = view.findViewById(R.id.viewPagerChiTiet);
        ImageView imgClose = view.findViewById(R.id.imgClosePager);

        if (chienDichList != null) {
            ChiTietChienDichPagerAdapter adapter = new ChiTietChienDichPagerAdapter(this, chienDichList);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(startPosition, false);
        }

        imgClose.setOnClickListener(v -> getParentFragmentManager().popBackStack());
    }

    @Override
    public void onResume() {
        super.onResume();
        setBottomNavVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Only show the nav bar if the fragment is being removed from the back stack
        if (isRemoving()) {
            setBottomNavVisibility(View.VISIBLE);
        }
    }

    private void setBottomNavVisibility(int visibility) {
        if (getActivity() != null) {
            BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavID);
            if (bottomNav != null) {
                bottomNav.setVisibility(visibility);
            }
        }
    }
}
