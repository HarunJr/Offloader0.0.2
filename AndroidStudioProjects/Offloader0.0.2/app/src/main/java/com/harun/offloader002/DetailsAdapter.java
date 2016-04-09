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
public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.OffloaderViewHolder> {
    public static final String LOG_TAG = DetailsAdapter.class.getSimpleName();

    private Cursor mCursor;
    final private Context mContext;
    final private DetailsAdapterOnClickHandler mClickHandler;
    final private View mEmptyView;
    String vehicleReg;

    public DetailsAdapter(Context context, DetailsAdapterOnClickHandler clickHandler, View emptyView) {
        this.mContext = context;
        this.mClickHandler = clickHandler;
        this.mEmptyView = emptyView;
        Log.w(LOG_TAG, "VehiclesAdapter: ");
    }

    public  class OffloaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView detailsTextView;
        public TextView dateTextView;

        public OffloaderViewHolder(View itemView) {
            super(itemView);

            detailsTextView = (TextView) itemView.findViewById(R.id.item_details_amount);
            dateTextView = (TextView) itemView.findViewById(R.id.item_details_date_time);
            //set listener generating onClick... Create ClickListener interface
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            int vehicleId = mCursor.getInt(MainFragment.COL_VEHICLE_ID);
            Log.w(LOG_TAG, "onClick "+vehicleId+", "+vehicleReg);

            mClickHandler.onClick(vehicleId, vehicleReg, this);
            Log.w(LOG_TAG, "onClick "+vehicleId+", "+vehicleReg);

        }
    }

    public interface DetailsAdapterOnClickHandler {
        void onClick(int vehicleId, String vehicleReg, OffloaderViewHolder vh);
    }


    @Override
    public OffloaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details, parent, false);
        rootView.setFocusable(true);
        return new OffloaderViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(OffloaderViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        vehicleReg = mCursor.getString(MainFragment.COL_VEHICLE_REGISTRATION);
        String transactionAmount = mCursor.getString(MainFragment.COL_VEHICLE_AMOUNT);
        String lastTransactionDateTime = mCursor.getString(MainFragment.COL_LAST_TRANSACTION_DATE_TIME);

        Log.w(LOG_TAG, "onBindViewHolder: "+vehicleReg+", "+transactionAmount+", "+lastTransactionDateTime);

        holder.detailsTextView.setText(vehicleReg);
        holder.dateTextView.setText( transactionAmount);

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
//        Log.w(LOG_TAG, "swapCursor: "+newCursor.getCount());
        mCursor = newCursor;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE: View.GONE);
    }

    public Cursor getCursor(){
        return mCursor;
    }

}
