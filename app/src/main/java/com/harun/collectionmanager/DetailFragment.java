package com.harun.collectionmanager;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.ListView;
import android.widget.TextView;

import static com.harun.collectionmanager.data.VehicleContract.TransactionEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String VEHICLE_URI = "URI";
    public static final String VEHICLE_ID = "_id";

    private DetailsAdapter mDetailsAdapter;

    private TextView mVehicleView;
    private Uri mUri;
    private String mVehicleReg;

    DetailFragment mDetailFragment;
    // ToDo : To hold the vehicle id
    private int mVehicleId;
    // ToDo : To inflate fragment menu items to bar
    private Menu optionsMenu;


    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    private static final String VEHICLE_SHARE_HASHTAG = " #OffloadApp";


    private static final int DETAIL_LOADER = 0;

    private static final String[] TRANSACTION_COLUMNS = {
            TransactionEntry.TABLE_NAME+"."+ TransactionEntry._ID,
            TransactionEntry.COLUMN_AMOUNT,
            TransactionEntry.COLUMN_TYPE,
            TransactionEntry.COLUMN_DESCRIPTION,
            TransactionEntry.COLUMN_DATE_TIME,

    };

    static final int COL_TRANSACTION_ID = 0;
    static final int COL_AMOUNT = 1;
    static final int COL_TYPE = 2;
    static final int COL_DESCRIPTION = 3;
    static final int COL_DATE_TIME = 4;

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         * @param vehicleId
         */
        public void onVehicleSelectedForTransaction(int vehicleId);
    }


    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    public static DetailFragment newInstance(Bundle args)
    {
        DetailFragment fragmentInstance = new DetailFragment();
        if(args != null)
        {
            fragmentInstance.setArguments(args);
        }
        return fragmentInstance;
        // }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
        // Get the vehicle data passed from MainActivity Bundle Object
        Bundle arguments = getArguments();
        if (arguments != null)
        {
            // ToDo : prefer using vehicle_id, its more convenient
            // This represents the clicked item from MainActivity#VehicleFragment
            // mVehicleReg = arguments.getString(DetailFragment.VEHICLE_URI, null);
            //Log.w(LOG_TAG, "TWEETID:" + mVehicleReg);
            mVehicleId = arguments.getInt(DetailFragment.VEHICLE_ID, 0);
            Log.w(LOG_TAG, "TWEETID:" + mVehicleId);
        }

    }

    // ToDo : added menu handling inside the Detailragment
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        // Clear old menu.
        //menu.clear();
        // Inflate new menu.
        inflater.inflate(R.menu.menu_detail, menu);
        this.optionsMenu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings)
//        {
//            return true;
//        }
         if(id == R.id.action_input_collection)
        {
            startActivity(new Intent(getActivity(), Transactions.class));
//            CollectionFragment collectionFragment = new CollectionFragment();
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.container, collectionFragment);
//            fragmentTransaction.addToBackStack("replaceWithCollection");
//
//            fragmentTransaction.commit();
        }
        else if(id == R.id.action_input_expense)
        {
            ExpenseFragment expenseFragment = new ExpenseFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, expenseFragment);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        mDetailsAdapter = new DetailsAdapter(getActivity(), null, 0);
        View rootView  = inflater.inflate(R.layout.fragment_detail, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView_detail);
        listView.setAdapter(mDetailsAdapter);

/**Use this FOR TEXT DISPLAY WITHOUT LIST**/
        //        mVehicleView = (TextView) rootView.findViewById(R.id.detail_text);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(DetailFragment.VEHICLE_ID))
        {
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);

        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        Log.v(LOG_TAG, "In onCreateLoader");

        Uri transactionForVehicleIdUri = TransactionEntry.buildVehicleTransactionUri(mVehicleId);

        Log.v(LOG_TAG, "" +transactionForVehicleIdUri);

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
        Log.v(LOG_TAG, "In onLoadFinished");
        if (data != null && data.moveToFirst()  )
        {
//            String transactionAmount = data.getString(data.getColumnIndex(TransactionEntry.COLUMN_AMOUNT));
//            mVehicleView.setText(transactionAmount);
            mDetailsAdapter.swapCursor(data);
        }

        /*double transactionAmount = data.getDouble(data.getColumnIndex(TransactionEntry.COLUMN_AMOUNT));
        String transactionDate = data.getString(data.getColumnIndex(TransactionEntry.COLUMN_DATE_TIME));
        String transactionDesc = data.getString(data.getColumnIndex(TransactionEntry.COLUMN_DESCRIPTION));

        String mVehicle = String.format("%s - %s - %s", transactionAmount, transactionDesc, transactionDate);


        TextView detailTextView = (TextView)getView().findViewById(R.id.detail_text);
        detailTextView.setText(mVehicle);*/


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
