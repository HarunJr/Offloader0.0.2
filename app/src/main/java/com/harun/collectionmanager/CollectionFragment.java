package com.harun.collectionmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class CollectionFragment extends Fragment {
    private static final String LOG_TAG = CollectionFragment.class.getSimpleName();
    private FetchVehicleTask dataSource;
    protected EditText mCollectionInput;
    protected Button mSubmitCollectionButton;
    private Menu optionsMenu;
    private DetailActivity mDetailActivity;


    private int mVehicleId;

    public CollectionFragment(){

    }


    public static CollectionFragment newInstance(Bundle args) {
        CollectionFragment fragmentInstance = new CollectionFragment();
        if(args != null)
        {
            fragmentInstance.setArguments(args);
        }
        return fragmentInstance;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getActivity().getIntent().getExtras();;
        if (arguments != null)
        {
            // ToDo: This represents the clicked item from MainActivity#VehicleFragment
            mVehicleId = arguments.getInt(DetailFragment.VEHICLE_ID, 0);
            Log.w(LOG_TAG, "VEHICLEID:" + mVehicleId);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context mContext = getActivity();
        dataSource = new FetchVehicleTask(mContext);
        dataSource.open();
        View rootView = inflater.inflate(R.layout.fragment_collection, container, false);

        mCollectionInput = (EditText) rootView.findViewById(R.id.collection_input);
        mSubmitCollectionButton = (Button) rootView.findViewById(R.id.submit_collection_button);
        showSoftKeyboard(mCollectionInput);

        mSubmitCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(mCollectionInput);
                double amount = Double.parseDouble(mCollectionInput.getText().toString());
                String description = "This a Collection";
                String dateTime = dataSource.getDateTime();
                int type = 1;

                long id = dataSource.addTransaction(amount, type, description, dateTime, mVehicleId);

                if (id < 0) {
                    Toast.makeText(getActivity(), "Unsuccessful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), amount + "||" + type + "||" + description + "||" + dateTime, Toast.LENGTH_LONG).show();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                    //             startActivity(new Intent(getActivity(), DetailActivity.class));

                }

            }
        });

        return rootView;
    }

//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
//    {
//        super.onCreateOptionsMenu(menu, inflater);
//        // Clear old menu.
//        //menu.clear();
//        // Inflate new menu.
//        inflater.inflate(R.menu.menu_collection, menu);
//        this.optionsMenu = menu;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings)
////        {
////            return true;
////        }
//        if(id == android.R.id.home)
//        {
//            FragmentManager fm = getActivity().getSupportFragmentManager();
//            fm.popBackStack("DFTAG", 0);
//        }
//         else if(id == R.id.action_input_expense)
//        {
//            ExpenseFragment expenseFragment = new ExpenseFragment();
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.container, expenseFragment);
//            fragmentTransaction.addToBackStack("replaceWithExpense");
//
//            fragmentTransaction.commit();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        mTransactionInput.requestFocus();
//
//        mTransactionInput.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                InputMethodManager keyboard = (InputMethodManager)
//                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                keyboard.showSoftInput(mTransactionInput, 0);
//            }
//        }, 200); //use 300 to make it run when coming back from lock screen
//    }


    public void showSoftKeyboard(View view) {
        if (mCollectionInput.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput( InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    public void hideSoftKeyboard(View view) {
        if (mCollectionInput.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0,InputMethodManager.SHOW_IMPLICIT);
        }
    }

}
