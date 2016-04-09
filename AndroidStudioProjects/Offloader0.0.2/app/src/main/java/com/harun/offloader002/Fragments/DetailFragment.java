package com.harun.offloader002.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.harun.offloader002.Constants;
import com.harun.offloader002.R;


public class DetailFragment extends Fragment {
    public static final String LOG_TAG = DetailFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private int vehicleId;
    private String vehicleReg;

    private OnFabPressedListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Bundle args) {
        DetailFragment fragment = new DetailFragment();
        if (args!= null){
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
            Log.w(LOG_TAG, "onCreate "+vehicleId+", "+vehicleReg);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Constants.toolbar = (Toolbar) rootView.findViewById(R.id.details_tool_bar);

        RecyclerView mRecyclerView;
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvDetails);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hi there", Toast.LENGTH_LONG).show();
                ((OnFabPressedListener) getActivity()).onFabPressed(vehicleId, vehicleReg);

       //         addTransactionFragment(vehicleId, vehicleReg);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(Constants.toolbar);

        assert appCompatActivity.getSupportActionBar() != null;
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setTitle(vehicleReg);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == android.R.id.home){
//
//            startActivity(new Intent(getContext(), MainActivity.class));
//            Log.w(LOG_TAG, "Home button clicked");
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFabPressedListener) {
            mListener = (OnFabPressedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFabPressedListener {
        void onFabPressed(int vehicleId, String vehicleReg);
    }
}
