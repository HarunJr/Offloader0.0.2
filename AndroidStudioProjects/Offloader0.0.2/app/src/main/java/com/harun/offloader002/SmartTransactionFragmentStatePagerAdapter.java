package com.harun.offloader002;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.harun.offloaderdesign.fragments.subFragments.AddCollectionFragment;
import com.harun.offloaderdesign.fragments.subFragments.AddExpenseFragment;


public class SmartTransactionFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"ADD COLLECTION", "ADD EXPENSE"};
    private Context context;

    public SmartTransactionFragmentStatePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                // Home -Tweet fragment activity
                return AddCollectionFragment.newInstance(position);
            }
            case 1: {
                // Favourite fragment activity
                return AddExpenseFragment.newInstance(position);
            }
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
