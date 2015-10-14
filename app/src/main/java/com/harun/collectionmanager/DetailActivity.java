package com.harun.collectionmanager;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DetailActivity extends AppCompatActivity{
    public final String DETAIL_FRAGMENT_TAG = "DFTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new DetailFragment())
//                    .commit();
//        }

        setContentView(R.layout.activity_detail);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            else {
                int vehicle_id = getIntent().getIntExtra(DetailFragment.VEHICLE_ID, 0);
                //int date = getIntent().getIntExtra(DetailsFragment.ID_KEY);
                Log.w("TWEETID", "" + vehicle_id);

                Bundle args = new Bundle();
                args.putInt(DetailFragment.VEHICLE_ID, vehicle_id);
                showDetailFragment(args);
            }
        }
    }

        protected void showDetailFragment(Bundle args)
        {
            FragmentManager fm = getSupportFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();

            DetailFragment fragment =  DetailFragment.newInstance(args);

            ft.add(R.id.container, fragment, DETAIL_FRAGMENT_TAG);
//            ft.addToBackStack(null);
            ft.commit();
        }

//    @Override
//    public void onBackPressed() {
//
//        int count = getFragmentManager().getBackStackEntryCount();
//
//        if (count == 0)
//
//        {
//            super.onBackPressed();
//            //additional code
//        } else {
//            getFragmentManager().popBackStack();
//        }
//
//    }




    // ToDo: Commented this out since, the child fragment will handle the menu items now
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_detail, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings)
//        {
//            return true;
//        }
//        else if(id == R.id.action_input_collection)
//        {
//            CollectionFragment collectionFragment = new CollectionFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.container, collectionFragment);
//            fragmentTransaction.addToBackStack(null);
//
//            fragmentTransaction.commit();
//        }
//        else if(id == R.id.action_input_expense)
//        {
//            ExpenseFragment expenseFragment = new ExpenseFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.container, expenseFragment);
//            fragmentTransaction.addToBackStack(null);
//
//            fragmentTransaction.commit();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public void onVehicleSelectedForTransaction(int vehicleId)
//    {
//        startActivity(new Intent(this, TransactionActivity.class));
//        Intent intent = new Intent(this, TransactionActivity.class).putExtra(DetailFragment.VEHICLE_ID, vehicleId);
//        startActivity(intent);
//    }
}
