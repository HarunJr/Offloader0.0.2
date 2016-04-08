package com.harun.offloader002.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.harun.offloader002.fragments.DetailFragment;
import com.harun.offloader002.R;

public class DetailsActivity extends AppCompatActivity {
    public static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.w(LOG_TAG, "DetailsActivity");

        Intent intent = getIntent();
        String vehicleReg = intent.getStringExtra(DetailFragment.VEHICLE_REG);
        int vehicleId = intent.getIntExtra(DetailFragment.VEHICLE_ID, 0);
        Log.w(LOG_TAG, "onCreate "+vehicleId+", "+vehicleReg);

        addDetailsFragment(vehicleReg, vehicleId);
    }

    private void addDetailsFragment(String vehicleReg, int vehicleId){
        Bundle args = new Bundle();
        args.putInt(DetailFragment.VEHICLE_ID, vehicleId);
        args.putString(DetailFragment.VEHICLE_REG, vehicleReg);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.addDetails_container, detailFragment);
        fragmentTransaction.commit();
    }

}
