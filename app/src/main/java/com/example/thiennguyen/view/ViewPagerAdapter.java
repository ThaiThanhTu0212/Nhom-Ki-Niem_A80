package com.example.thiennguyen.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.thiennguyen.view.ChuTrang.HomeFragment;
import com.example.thiennguyen.view.bangtin.BangTinFragment;
import com.example.thiennguyen.view.khampha.KhamPhaFragment;
import com.example.thiennguyen.view.ungho.UngHoFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return  new HomeFragment();
            case 1:return  new UngHoFragment();
            case 2:return  new BangTinFragment();
            case 3:return  new KhamPhaFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
