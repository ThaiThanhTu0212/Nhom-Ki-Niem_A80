package com.example.thiennguyen.view.ungho;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.thiennguyen.view.model.ChienDich;

import java.util.List;

public class ChiTietChienDichPagerAdapter extends FragmentStateAdapter {

    private final List<ChienDich> chienDichList;

    public ChiTietChienDichPagerAdapter(@NonNull Fragment fragment, List<ChienDich> chienDichList) {
        super(fragment);
        this.chienDichList = chienDichList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ChienDich chienDich = chienDichList.get(position);
        return ChiTietChienDichFragment.newInstance(chienDich);
    }

    @Override
    public int getItemCount() {
        return chienDichList.size();
    }
}
