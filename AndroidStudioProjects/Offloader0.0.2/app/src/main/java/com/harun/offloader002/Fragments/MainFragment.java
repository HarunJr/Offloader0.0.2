package com.harun.offloader002.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.harun.offloader002.Constants;
import com.harun.offloader002.DividerItemDecoration;
import com.harun.offloader002.tasks.FetchVehicleTask;
import com.harun.offloader002.R;
import com.harun.offloader002.adapters.VehiclesAdapter;
import com.harun.offloader002.data.VehicleContract;
import com.harun.offloader002.sync.OffloaderSyncAdapter;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = MainFragment.class.getSimpleName();
    private final int VEHICLE_LOADER = 0;

    private VehiclesAdapter mVehiclesAdapter;
    private FetchVehicleTask mFetchVehicleTask;

    private RecyclerView mRecyclerView;
    ListView mListView;
    private int mPosition = RecyclerView.NO_POSITION;
    private static final String SELECTED_KEY = "selected_position";

    private static final String[] VEHICLE_COLUMN = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            VehicleContract.VehicleEntry.TABLE_NAME + "." + VehicleContract.VehicleEntry.COLUMN_VEHICLE_ID,
            VehicleContract.VehicleEntry.COLUMN_VEHICLE_REGISTRATION,
            VehicleContract.VehicleEntry.COLUMN_VEHICLE_REGISTRATION_DATE,
            VehicleContract.VehicleEntry.COLUMN_VEHICLE_AMOUNT,
            VehicleContract.VehicleEntry.COLUMN_LAST_TRANSACTION_DATE_TIME,
//            TransactionEntry.COLUMN_TYPE
    };

    // These indices are tied to FORECAST_COLUMNS.  If FORECAST_COLUMNS changes, these
    // must change.
    public static final int COL_VEHICLE_ID = 0;
    public static final int COL_VEHICLE_REGISTRATION = 1;
    public static final int COL_REG_DATE_TIME = 2;
    public static final int COL_VEHICLE_AMOUNT = 3;
    public static final int COL_LAST_TRANSACTION_DATE_TIME = 4;
//    static final int COL_TYPE = 5;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public interface Callback {
        void onItemSelected(int vehicleId, String vehicleReg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Constants.toolbar = (Toolbar) rootView.findViewById(R.id.main_tool_bar);
        View emptyView = rootView.findViewById(R.id.recyclerview_vehicle_empty);

//        mVehiclesAdapter = new VehiclesAdapter(mClickHandler, getActivity());
//        ListView listView = (ListView) rootView.findViewById(R.id.listview_vehicles);
//        listView.setAdapter(mVehiclesAdapter);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvVehicles);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        mVehiclesAdapter = new VehiclesAdapter(getActivity(), new VehiclesAdapter.VehiclesAdapterOnClickHandler() {
            @Override
            public void onClick(int vehicleId, String vehicleReg, VehiclesAdapter.ViewHolder vh) {
                ((Callback) getActivity()).onItemSelected(vehicleId, vehicleReg);

                Log.w(LOG_TAG, "onCreateView " + vehicleId + ", " + vehicleReg);
                mPosition = vh.getAdapterPosition();

            }
        }, emptyView);

        mRecyclerView.setAdapter(mVehiclesAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            updateVehicles();
            return true;
        } else if (id == R.id.action_add_vehicle) {
            addVehicle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(VEHICLE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(Constants.toolbar);

        assert appCompatActivity.getSupportActionBar() != null;
        appCompatActivity.getSupportActionBar().setTitle("Vehicles");

        Log.w(LOG_TAG, "Toolbar has been added");
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.w(LOG_TAG, "onCreateLoader: ");
        String sortOrder = VehicleContract.VehicleEntry.COLUMN_LAST_TRANSACTION_DATE_TIME + " DESC";

        return new CursorLoader(
                getActivity(),
                VehicleContract.VehicleEntry.CONTENT_URI,
                VEHICLE_COLUMN,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.w(LOG_TAG, "onLoadFinished: " + data.getCount());

        mVehiclesAdapter.swapCursor(data);

        if (mPosition != RecyclerView.NO_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            mRecyclerView.smoothScrollToPosition(mPosition);
        }
        //TODO: Update EmptyView
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.w(LOG_TAG, "onLoaderReset: ");
        mVehiclesAdapter.swapCursor(null);
    }

    private void addVehicle() {
        AddVehicleFragment addVehicleFragment = new AddVehicleFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, addVehicleFragment);
        fragmentTransaction.commit();
    }

    private void updateVehicles() {

//        FetchVehicleTask vehicleTask = new FetchVehicleTask(getContext());
//        vehicleTask.execute();

//        Intent intent = new Intent(getActivity(), OffloaderService.class);
//        getActivity().startService(intent);
        OffloaderSyncAdapter.syncImmediately(getContext());
        Log.w(LOG_TAG, "updateVehicles: ");
    }

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

    @Override
    public void onStart() {
        super.onStart();
        updateVehicles();
    }

}
