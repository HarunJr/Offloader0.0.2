package com.harun.collectionmanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.harun.collectionmanager.data.VehicleProvider;

import static com.harun.collectionmanager.data.VehicleContract.TransactionEntry;
import static com.harun.collectionmanager.data.VehicleContract.VehicleEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class VehicleFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String LOG_TAG = VehicleFragment.class.getSimpleName();

    private final int VEHICLE_LOADER = 0;
    //    ArrayAdapter<String> mVehicleAdapter;
    private VehicleAdapter mVehicleAdapter;
    private FetchVehicleTask mVehicleFetch;
    private VehicleProvider mVehicleProvider;

    private static final String[] VEHICLE_COLUMN = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            VehicleEntry.TABLE_NAME + "." + VehicleEntry._ID,
            VehicleEntry.COLUMN_VEHICLE_REGISTRATION,
            VehicleEntry.COLUMN_DATE_TIME,
            TransactionEntry.COLUMN_AMOUNT,
            TransactionEntry.COLUMN_DATE_TIME,
//            TransactionEntry.COLUMN_TYPE
    };

    // These indices are tied to FORECAST_COLUMNS.  If FORECAST_COLUMNS changes, these
    // must change.
    static final int COL_VEHICLE_ID = 0;
    static final int COL_VEHICLE_REGISTRATION = 1;
    static final int COL_DATE_TIME = 2;
    static final int COL_AMOUNT = 3;
    static final int COL_TIME = 4;
//    static final int COL_TYPE = 5;

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         * @param vehicleId
         */
        public void onItemSelected(int vehicleId);
    }


    public VehicleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
        //viewReg();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.vehicle_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add_vehicle) {
            startActivity(new Intent(getActivity(), AddVehicleActivity.class));
            return true;
        }else if(id == R.id.action_refresh_vehicle_list){
            // viewReg();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // The CursorAdapter will take data from our cursor and populate the ListView.
        mVehicleAdapter = new VehicleAdapter(getActivity(), null, 0);
        View rootView  = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listView_vehicle);
        listView.setAdapter(mVehicleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                //ToDo: Prefer to use Vehicle_Id because it is more convenient
                //String vehicle = cursor.getString(COL_VEHICLE_REGISTRATION);

                if (cursor != null)
                {
                    int vehicle = cursor.getInt(COL_VEHICLE_ID);
                    ((Callback) getActivity()).onItemSelected(vehicle);
                    Log.w(LOG_TAG, "VEHICLEID:" + vehicle);

                }

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        getLoaderManager().initLoader(VEHICLE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(
                getActivity(),
                VehicleEntry.CONTENT_URI,
                VEHICLE_COLUMN,
                null,
                null,
                null
        );

        return cursorLoader;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor data) {
        mVehicleAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mVehicleAdapter.swapCursor(null);
    }
}
