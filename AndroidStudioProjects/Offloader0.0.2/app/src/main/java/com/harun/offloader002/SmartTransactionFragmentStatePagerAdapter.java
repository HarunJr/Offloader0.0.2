package com.harun.offloader002;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.harun.offloader002.fragments.AddCollection;
import com.harun.offloader002.fragments.AddExpense;


public class SmartTransactionFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"ADD COLLECTION", "ADD EXPENSE"};
    private Context context;
    private int type;

    private int vehicleId;
    private String vehicleReg;

    public SmartTransactionFragmentStatePagerAdapter(FragmentManager fm, Context context, int vehicleId, String vehicleReg) {
        super(fm);
        this.context = context;
        this.vehicleId = vehicleId;
        this.vehicleReg = vehicleReg;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                // Home -Tweet fragment activity
                return AddCollection.newInstance(position, vehicleId, vehicleReg);
            }
            case 1: {
                // Favourite fragment activity
                return AddExpense.newInstance(position, vehicleId, vehicleReg);
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
