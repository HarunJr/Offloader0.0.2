package com.harun.collectionmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExpenseFragment extends Fragment {
    private static final String LOG_TAG = ExpenseFragment.class.getSimpleName();
    private FetchVehicleTask dataSource;
    protected EditText mExpenseInput;
    protected Button mSubmitExpenseButton;

    private int mVehicleId;


    public ExpenseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       setHasOptionsMenu(true);

        Bundle arguments = getActivity().getIntent().getExtras();;
        if (arguments != null)
        {
            // ToDo: This represents the clicked item from MainActivity#VehicleFragment
            mVehicleId = arguments.getInt(DetailFragment.VEHICLE_ID, 0);
            Log.w(LOG_TAG, "TWEETID:" + mVehicleId);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context mContext = getActivity();
        dataSource = new FetchVehicleTask(mContext);
        dataSource.open();
        View rootView = inflater.inflate(R.layout.fragment_expense, container, false);

        mExpenseInput = (EditText) rootView.findViewById(R.id.expense_input);
        mSubmitExpenseButton = (Button) rootView.findViewById(R.id.submit_expense_button);
        showSoftKeyboard(mExpenseInput);

        mSubmitExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(mExpenseInput);
                double amount = Double.parseDouble(mExpenseInput.getText().toString());
                String description = "This is an Expense";
                String dateTime = dataSource.getDateTime();
                int type = 0;

                long id = dataSource.addTransaction(amount, type, description, dateTime, mVehicleId);

                if(id < 0){
                    Toast.makeText(getActivity(), "Unsuccessful", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), amount+"||"+type + "||"+description+"||"+ dateTime, Toast.LENGTH_LONG).show();
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
//        inflater.inflate(R.menu.menu_expense, menu);
////        this.optionsMenu = menu;
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
//        if(id == R.id.action_input_collection)
//        {
//            CollectionFragment collectionFragment = new CollectionFragment();
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.container, collectionFragment);
//            fragmentTransaction.addToBackStack(null);
//
//            fragmentTransaction.commit();
//        }
////        if(id == R.id.action_input_expense)
////        {
////            ExpenseFragment expenseFragment = new ExpenseFragment();
////            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
////            fragmentTransaction.replace(R.id.container, expenseFragment);
////            fragmentTransaction.addToBackStack(null);
////
////            fragmentTransaction.commit();
////        }
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
        if (mExpenseInput.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput( InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    public void hideSoftKeyboard(View view) {
        if (mExpenseInput.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0,InputMethodManager.SHOW_IMPLICIT);
        }
    }

}
