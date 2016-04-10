package com.harun.offloader002.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harun.offloader002.Constants;
import com.harun.offloader002.FetchTransactionTask;
import com.harun.offloader002.R;
import com.harun.offloader002.adapters.DetailsAdapter;
import com.harun.offloader002.data.VehicleContract;

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private final int TRANSACTION_LOADER = 0;

    private DetailsAdapter mDetailsAdapter;

    private int vehicleId;
    private String vehicleReg;

    private static final String[] TRANSACTION_COLUMNS = {
            VehicleContract.TransactionEntry.TABLE_NAME + "." + VehicleContract.TransactionEntry.COLUMN_TRANSACTION_ID,
            VehicleContract.TransactionEntry.COLUMN_AMOUNT,
            VehicleContract.TransactionEntry.COLUMN_TYPE,
            VehicleContract.TransactionEntry.COLUMN_DESCRIPTION,
            VehicleContract.TransactionEntry.COLUMN_DATE_TIME,
    };

    public static final int COL_TRANSACTION_ID = 0;
    public static final int COL_AMOUNT = 1;
    public static final int COL_TYPE = 2;
    public static final int COL_DESCRIPTION = 3;
    public static final int COL_DATE_TIME = 4;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Bundle args) {
        DetailFragment fragment = new DetailFragment();
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
            Log.w(LOG_TAG, "onCreate " + vehicleId + ", " + vehicleReg);
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
        View emptyView = rootView.findViewById(R.id.recyclerview_details_empty);

        mDetailsAdapter = new DetailsAdapter(getActivity(), emptyView);

        mRecyclerView.setAdapter(mDetailsAdapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((OnFabPressedListener) getActivity()).onFabPressed(vehicleId, vehicleReg);

                //         addTransactionFragment(vehicleId, vehicleReg);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(TRANSACTION_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(Constants.toolbar);

        assert appCompatActivity.getSupportActionBar() != null;
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setTitle(vehicleReg);
    }

    private void updateTransactions() {

        FetchTransactionTask transactionTask = new FetchTransactionTask(getContext());
        transactionTask.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateTransactions();
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri transactionForVehicleIdUri = VehicleContract.TransactionEntry.buildVehicleTransactionUri(vehicleId);

        return new CursorLoader(
                getActivity(),
                transactionForVehicleIdUri,
                TRANSACTION_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            mDetailsAdapter.swapCursor(data);
            Log.w(LOG_TAG, "onLoadFinished: " + data.getCount());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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
