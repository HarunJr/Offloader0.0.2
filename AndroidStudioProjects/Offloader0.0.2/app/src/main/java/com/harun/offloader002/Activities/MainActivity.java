package com.harun.offloader002.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.harun.offloader002.Constants;
import com.harun.offloader002.R;
import com.harun.offloader002.fragments.MainFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback{
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addMainFragment();
    }

    private void addMainFragment(){
        MainFragment mainFragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, mainFragment);
        fragmentTransaction.commit();

        Log.w(LOG_TAG, "MainFragment has been added");
    }

    @Override
    public void onItemSelected(int vehicleId, String vehicleReg) {
        Log.w(LOG_TAG, "onItemSelected "+vehicleId+", "+vehicleReg);
        startActivity(new Intent(getApplicationContext(), DetailsActivity.class)
        .putExtra(Constants.VEHICLE_ID, vehicleId)
        .putExtra(Constants.VEHICLE_REG, vehicleReg));
    }
}
