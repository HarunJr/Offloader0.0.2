package com.harun.offloader002.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.harun.offloader002.Constants;
import com.harun.offloader002.fragments.AddCollection;
import com.harun.offloader002.fragments.AddExpense;
import com.harun.offloader002.fragments.DetailFragment;
import com.harun.offloader002.R;
import com.harun.offloader002.fragments.TransactionFragment;

public class DetailsActivity extends AppCompatActivity
        implements DetailFragment.OnFabPressedListener, AddCollection.OnSendCollectionListener, AddExpense.OnSendExpenseListener{
    public static final String LOG_TAG = DetailsActivity.class.getSimpleName();
    Bundle args = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.w(LOG_TAG, "DetailsActivity");

        Intent intent = getIntent();
        int vehicleId = intent.getIntExtra(Constants.VEHICLE_ID, 0);
        String vehicleReg = intent.getStringExtra(Constants.VEHICLE_REG);
        Log.w(LOG_TAG, "onCreate "+vehicleId+", "+vehicleReg);

        addDetailsFragment(vehicleId, vehicleReg);
    }

    private void addDetailsFragment(int vehicleId, String vehicleReg){
        args.putInt(Constants.VEHICLE_ID, vehicleId);
        args.putString(Constants.VEHICLE_REG, vehicleReg);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.addDetails_container, detailFragment);
        fragmentTransaction.commit();
    }

    private void addTransactionFragment(int vehicleId, String vehicleReg) {
        args.putInt(Constants.VEHICLE_ID, vehicleId);
        args.putString(Constants.VEHICLE_REG, vehicleReg);
        Log.w(LOG_TAG, "addTransactionFragment "+vehicleId+", "+vehicleReg);

        TransactionFragment transactionFragment = new TransactionFragment();
        transactionFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.addDetails_container, transactionFragment);
        fragmentTransaction.commit();
    }

    private void replaceWithDetailFragment(int vehicleId, String vehicleReg){
        args.putInt(Constants.VEHICLE_ID, vehicleId);
//        args.putString(Constants.VEHICLE_REG, vehicleReg);

        Log.w(LOG_TAG, "replaceWithDetailFragment "+vehicleId+", "+vehicleReg);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.addDetails_container, detailFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onFabPressed(int vehicleId, String vehicleReg) {
        addTransactionFragment(vehicleId, vehicleReg);
    }


    @Override
    public void onCollectionButtonClicked(int vehicleId, String vehicleReg) {
        replaceWithDetailFragment(vehicleId, vehicleReg);
    }

    @Override
    public void onExpenseButtonClicked(int vehicleId, String vehicleReg) {
        replaceWithDetailFragment(vehicleId, vehicleReg);
    }
}
