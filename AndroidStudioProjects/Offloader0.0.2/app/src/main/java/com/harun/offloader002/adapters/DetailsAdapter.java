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
import com.harun.offloader002.fragments.DetailFragment;

/**
 * Created by HARUN on 4/7/2016.
 */
public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.TransactionViewHolder> {
    public static final String LOG_TAG = DetailsAdapter.class.getSimpleName();

    private Cursor mCursor;
    final private Context mContext;
    final private View mEmptyView;

    public DetailsAdapter(Context context, View emptyView) {
        this.mContext = context;
        this.mEmptyView = emptyView;
        Log.w(LOG_TAG, "DetailsAdapter: ");
    }

    public  class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView detailsTextView;
        public TextView dateTextView;

        public TransactionViewHolder(View itemView) {
            super(itemView);

            detailsTextView = (TextView) itemView.findViewById(R.id.item_details_amount);
            dateTextView = (TextView) itemView.findViewById(R.id.item_details_date_time);
        }

    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details, parent, false);
        rootView.setFocusable(true);
        return new TransactionViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String transactionAmount = mCursor.getString(DetailFragment.COL_AMOUNT);
        String dateTime = mCursor.getString(DetailFragment.COL_DATE_TIME);
        String description = mCursor.getString(DetailFragment.COL_DATE_TIME);

        Log.w(LOG_TAG, "onBindViewHolder: "+description+", "+transactionAmount+", "+dateTime);

        holder.detailsTextView.setText(transactionAmount);
        holder.dateTextView.setText( dateTime);

    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        Log.w(LOG_TAG, "getItemViewType: "+position);
        return position;
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
