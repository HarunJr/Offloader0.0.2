package com.harun.offloader002.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harun.offloader002.Constants;
import com.harun.offloader002.R;
import com.harun.offloader002.adapters.SmartTransactionFragmentStatePagerAdapter;

public class TransactionFragment extends Fragment {
    public static final String LOG_TAG = TransactionFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ViewPager mPager;
    private TabLayout mTabs;

    // TODO: Rename and change types of parameters
    private int vehicleId;
    private String vehicleReg;

    //   private OnFragmentInteractionListener mListener;

    public TransactionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TransactionFragment newInstance(Bundle args) {
        TransactionFragment fragment = new TransactionFragment();
        if (args != null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vehicleId = getArguments().getInt(Constants.VEHICLE_ID, 0);
            vehicleReg = getArguments().getString(Constants.VEHICLE_REG);
            Log.w(LOG_TAG, "onCreate " + vehicleId + ", " + vehicleReg + " = " + getArguments());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_transaction, container, false);
        Constants.toolbar = (Toolbar) rootView.findViewById(R.id.transactions_tool_bar);

        this.mPager = (ViewPager) rootView.findViewById(R.id.transactionViewpager);

        // Give the TabLayout the ViewPager
        this.mTabs = (TabLayout) rootView.findViewById(R.id.tab_layout);
        this.mTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //setUp toolbar for home button
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(Constants.toolbar);

        assert appCompatActivity.getSupportActionBar() != null;
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setTitle(vehicleReg);

        // Set an Adapter: pass data, etc.
//        mPager.setAdapter(new SmartTransactionFragmentStatePagerAdapter(getActivity().getSupportFragmentManager(), getActivity().getApplicationContext()));
        SmartTransactionFragmentStatePagerAdapter pagerAdapter = new SmartTransactionFragmentStatePagerAdapter(
                getActivity().getSupportFragmentManager(), getActivity().getApplicationContext(), vehicleId, vehicleReg);
        this.mPager.setAdapter(pagerAdapter);
        // Bind the slidingTabStrips to the ViewPager
        this.mTabs.setupWithViewPager(mPager);

    }


//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
