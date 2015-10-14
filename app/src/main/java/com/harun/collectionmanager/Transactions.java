package com.harun.collectionmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.harun.collectionmanager.tabs.SlidingTabLayout;

public class Transactions extends FragmentActivity {
    private ViewPager mViewPager = null;
//    private Toolbar toolbar;
    private SlidingTabLayout mTabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

//        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mViewPager = (ViewPager) findViewById(R.id.pager);
//        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);

        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new MyAdapter(fragmentManager));
//        mTabs.setDistributeEvenly(true);
//        mTabs.setViewPager(mViewPager);


    }

}

class MyAdapter extends FragmentStatePagerAdapter {

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new CollectionFragment();
                break;
            case 1:
                fragment = new ExpenseFragment();
                break;
            default: //something witch won't crush the app
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "COLLECTION";
        }
        if (position == 1) {
            return "EXPENSE";
        }
        return null;
    }
}
