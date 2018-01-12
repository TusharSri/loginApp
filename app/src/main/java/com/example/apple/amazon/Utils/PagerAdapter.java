package com.example.apple.amazon.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.apple.amazon.Fragments.ActivityFragment;
import com.example.apple.amazon.Fragments.CategoryFragment;
import com.example.apple.amazon.Fragments.ShopFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                CategoryFragment tab1 = new CategoryFragment();
                return tab1;
            case 1:
                ShopFragment tab2 = new ShopFragment();
                return tab2;
            case 2:
                ActivityFragment tab3 = new ActivityFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}