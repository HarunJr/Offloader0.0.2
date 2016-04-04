package com.harun.offloader002.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.harun.offloader002.Fragments.MainFragment;
import com.harun.offloader002.R;

public class MainActivity extends AppCompatActivity {
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

}
