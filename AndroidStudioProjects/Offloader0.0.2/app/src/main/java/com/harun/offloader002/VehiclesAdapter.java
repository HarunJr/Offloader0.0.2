package com.harun.offloader002;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harun.offloader002.fragments.MainFragment;

/**
 * Created by HARUN on 4/7/2016.
 */
public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.OffloaderViewHolder> {
    public static final String LOG_TAG = VehiclesAdapter.class.getSimpleName();

    private Cursor mCursor;
    final private Context mContext;

    public VehiclesAdapter(Context context) {
        this.mContext = context;
        Log.w(LOG_TAG, "VehiclesAdapter: ");
    }

    public class OffloaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView registrationTextView;
        public TextView amountView;
        public TextView dateTimeView;

        public OffloaderViewHolder(View itemView) {
            super(itemView);

            registrationTextView = (TextView) itemView.findViewById(R.id.item_vehicle_reg);
            amountView = (TextView) itemView.findViewById(R.id.item_vehicle_amount);
            dateTimeView = (TextView) itemView.findViewById(R.id.item_vehicle_date_time);
            //set listener generating onClick... Create ClickListener interface
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }

    }

    @Override
    public OffloaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicle, parent, false);
        rootView.setFocusable(true);
        return new OffloaderViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(OffloaderViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String vehicleReg = mCursor.getString(MainFragment.COL_VEHICLE_REGISTRATION);
        String transactionAmount = mCursor.getString(MainFragment.COL_VEHICLE_AMOUNT);
        String lastTransactionDateTime = mCursor.getString(MainFragment.COL_LAST_TRANSACTION_DATE_TIME);

        Log.w(LOG_TAG, "From SQLite: "+vehicleReg+", "+transactionAmount+", "+lastTransactionDateTime);

        holder.registrationTextView.setText(vehicleReg);
        holder.amountView.setText( transactionAmount);
        holder.dateTimeView.setText( lastTransactionDateTime);

    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        Log.w(LOG_TAG, "swapCursor: "+position);
        return position;
    }

    public void swapCursor(Cursor newCursor){
        Log.w(LOG_TAG, "swapCursor: "+newCursor.getCount());
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor(){
        return mCursor;
    }

}
