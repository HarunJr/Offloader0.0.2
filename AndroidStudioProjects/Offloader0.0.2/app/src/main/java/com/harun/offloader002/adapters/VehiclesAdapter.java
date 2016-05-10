package com.harun.offloader002.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harun.offloader002.R;
import com.harun.offloader002.fragments.MainFragment;

/**
 * Created by HARUN on 4/7/2016.
 */
public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.ViewHolder> {
    public static final String LOG_TAG = VehiclesAdapter.class.getSimpleName();
    private Cursor mCursor;
    final private VehiclesAdapterOnClickHandler mClickHandler;
    final private View mEmptyView;
    final private Context mContext;
    public VehiclesAdapter(Context context, VehiclesAdapterOnClickHandler mClickHandler, View emptyView) {
        this.mClickHandler = mClickHandler;
        mEmptyView = emptyView;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView vehicleView;
        public final TextView amountView;
        public final TextView dateView;

        public ViewHolder(View view){
            super(view);
            vehicleView = (TextView) view.findViewById(R.id.item_vehicle_reg);
            amountView = (TextView) view.findViewById(R.id.item_vehicle_amount);
            dateView = (TextView) view.findViewById(R.id.item_vehicle_date_time);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            int vehicleId = mCursor.getInt(MainFragment.COL_VEHICLE_ID);
            String vehicleReg = mCursor.getString(MainFragment.COL_VEHICLE_REGISTRATION);
            mClickHandler.onClick(vehicleId, vehicleReg, this);
        }
    }

    public static interface VehiclesAdapterOnClickHandler {
        void onClick(int vehicleId, String vehicleReg, ViewHolder vh);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicle, parent, false);
        view.setFocusable(true);
//        ViewHolder viewHolder = new ViewHolder(view);
//        view.setTag(viewHolder);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        Log.w(LOG_TAG, "onBindViewHolder: "+mCursor.getCount()+", "+position);

        String vehicleReg = mCursor.getString(MainFragment.COL_VEHICLE_REGISTRATION);
        // Find TextView and set formatted date on it
        holder.vehicleView.setText(vehicleReg);

        // Read weather amount from cursor
        String amount = mCursor.getString(MainFragment.COL_VEHICLE_AMOUNT);
        // Find TextView and set amount on it
        holder.amountView.setText(amount);

        String dateTime = mCursor.getString(MainFragment.COL_LAST_TRANSACTION_DATE_TIME);
        holder.dateView.setText(dateTime);
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        Log.w(LOG_TAG, "swapCursor: "+mCursor.getCount());
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        mCursor = newCursor;
        notifyDataSetChanged();

        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
//        Log.w(LOG_TAG, "swapCursor: "+mCursor.getCount());
    }

    public Cursor getCursor() {
        Log.w(LOG_TAG, "swapCursor: "+mCursor.getCount());
        return mCursor;
    }


//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_vehicle, parent, false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        view.setTag(viewHolder);
//
//        return view;
//    }

//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
////        TextView tv = (TextView) view;
////        tv.setText(cursor.getString(VehicleFragment.COL_VEHICLE_REGISTRATION));
//
//        ViewHolder viewHolder = (ViewHolder) view.getTag();
//
//        int weatherId = cursor.getInt(MainFragment.COL_VEHICLE_ID);
//        // Use placeholder image for now
//
//        // Read date from cursor
//        String vehicleReg = cursor.getString(MainFragment.COL_VEHICLE_REGISTRATION);
//        // Find TextView and set formatted date on it
//        viewHolder.vehicleView.setText(vehicleReg);
//
//        // Read weather amount from cursor
//        String amount = cursor.getString(MainFragment.COL_VEHICLE_AMOUNT);
//        // Find TextView and set amount on it
//        viewHolder.amountView.setText(amount);
//
//        String dateTime = cursor.getString(MainFragment.COL_LAST_TRANSACTION_DATE_TIME);
//        viewHolder.dateView.setText(dateTime);
//
//        // Read user preference for metric or imperial temperature units
//        //       boolean isMetric = Utility.isMetric(context);
//        //        Read high temperature from cursor
//
//        // Read low temperature from cursor
////        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
////        TextView lowView = (TextView) view.findViewById(R.id.list_item_low_textview);
////        lowView.setText(Utility.formatTemperature(low, isMetric));
//
//
//    }
}
