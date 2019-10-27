package com.example.tendertest1.fragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List <Fragment> list = new ArrayList<>();
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    public void addFragment (Fragment fragment){
        list.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
