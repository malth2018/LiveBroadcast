package com.example.livebroadcast;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2015/11/10 0010.
 */
public class LivePageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public LivePageAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public LivePageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        // TODO Auto-generated constructor stub
        this.fragments = fragments;
    }


    @Override
    public int getCount() {
        return fragments.size();
    }
}
