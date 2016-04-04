package com.harun.offloader002.Activities;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.harun.offloader002.Fragments.DetailFragment;
import com.harun.offloader002.R;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        addDetailsFragment();
    }

    private void addDetailsFragment(){
        DetailFragment detailFragment = new DetailFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.addDetails_container, detailFragment);
        fragmentTransaction.commit();
    }

}
