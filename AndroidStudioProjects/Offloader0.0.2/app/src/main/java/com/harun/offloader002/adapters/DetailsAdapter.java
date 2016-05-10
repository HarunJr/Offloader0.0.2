package com.harun.offloader002.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harun.offloader002.Information;
import com.harun.offloader002.R;
import com.harun.offloader002.data.VehicleContract;
import com.harun.offloader002.fragments.DetailFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by HARUN on 4/7/2016.
 */
public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String LOG_TAG = DetailsAdapter.class.getSimpleName();

    private Cursor mCursor;
    final private Context mContext;
    final private View mEmptyView;
    List<Information> data = Collections.emptyList();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public DetailsAdapter(Context context, View emptyView) {
        this.mContext = context;
        this.mEmptyView = emptyView;
        Log.w(LOG_TAG, "DetailsAdapter: ");
    }

    public  class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView amountTextView;
        public TextView dateTextView;

        public TransactionViewHolder(View itemView) {
            super(itemView);

            amountTextView = (TextView) itemView.findViewById(R.id.item_details_amount);
            dateTextView = (TextView) itemView.findViewById(R.id.item_details_date_time);
        }

    }
    public  class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView headerTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            headerTextView = (TextView) itemView.findViewById(R.id.header_text_view);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER){
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_header, parent, false);
            rootView.setFocusable(true);
            return new HeaderViewHolder(rootView);

        }else {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details, parent, false);
            rootView.setFocusable(true);
            return new TransactionViewHolder(rootView);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int dayTimeIndex = mCursor.getColumnIndex(VehicleContract.TransactionEntry.COLUMN_DATE_TIME);
        String dayTime = mCursor.getString(dayTimeIndex);

        int transactionId = mCursor.getInt(DetailFragment.COL_TRANSACTION_ID);
        String transactionAmount = mCursor.getString(DetailFragment.COL_AMOUNT);
        String dateTime = mCursor.getString(DetailFragment.COL_DATE_TIME);
        String description = mCursor.getString(DetailFragment.COL_DATE_TIME);

        Log.w(LOG_TAG, "onBindViewHolder: "+description+", "+transactionAmount+", "+dateTime);

//        holder.amountTextView.setText(transactionAmount);
//        holder.dateTextView.setText( dateTime);
//        Information current = getData(transactionId, transactionAmount, dateTime, description).get(position);


        if (holder instanceof HeaderViewHolder){
            for (int i=0; i< mCursor.getCount(); i++){
                ((HeaderViewHolder) holder).headerTextView.setText(dayTime);
            }
        }else {
            for (int j=0; j< mCursor.getCount(); j++){
                for (int k=0; k<mCursor.getCount(); k++){
                    ((TransactionViewHolder)holder).amountTextView.setText(transactionAmount);
                    ((TransactionViewHolder)holder).dateTextView.setText(dateTime);
                }
            }
        }
    }

    public  List<Information> getData(int transactionId, String transactionAmount, String dateTime, String description){
        List<Information> data = new ArrayList<>();
        for (int i=0; i<transactionAmount.length(); i++){
            Information current = new Information();
            current.setTransactionId(transactionId);
            current.setTransactionAmount(transactionAmount);
            current.setDateTime(dateTime);
            data.add(current);
        }
        return data;
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        Log.w(LOG_TAG, "getItemViewType: "+mCursor.getCount());
        return mCursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        Log.w(LOG_TAG, "getItemViewType: "+position);
        if (position == 0 ){
            return TYPE_HEADER;
        }else {
            return TYPE_ITEM;
        }
    }

    public void swapCursor(Cursor newCursor){
        Log.w(LOG_TAG, "swapCursor: "+newCursor.getCount());
        mCursor = newCursor;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE: View.GONE);
    }

    public Cursor getCursor(){
        return mCursor;
    }

}
