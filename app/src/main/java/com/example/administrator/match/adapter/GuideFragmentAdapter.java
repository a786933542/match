package com.example.administrator.match.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class GuideFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment>list;

    public GuideFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

}
