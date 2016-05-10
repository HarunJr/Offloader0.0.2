package com.harun.offloader002.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.harun.offloader002.R;
import com.harun.offloader002.data.VehicleContract;
import com.harun.offloader002.fragments.DetailFragment;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by HARUN on 5/9/2016.
 */
public class DetailsCursorAdapter extends CursorAdapter implements StickyListHeadersAdapter {

    final private Context mContext;
    final private Cursor mCursor;
    private SparseIntArray sectionMap = new SparseIntArray();

    public DetailsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.mContext = context;
        this.mCursor = c;

        if (sectionMap == null) {
            sectionMap = new SparseIntArray();
        }
        sectionMap.clear();
    }

    @Override
    public long getHeaderId(int position) {
//        mCursor.moveToPosition(position);
//        int j = mCursor.getColumnIndexOrThrow(VehicleContract.TransactionEntry.COLUMN_DATE_TIME);
//        String name = mCursor.getString(j);
//        return name.charAt(0);
        return sectionMap.get(position);
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        final HeaderHolder holder;
        final Cursor c = getCursor();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.details_header, parent, false);
            holder = new HeaderHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderHolder) convertView.getTag();
        }
        c.moveToPosition(position);
        int j = c.getColumnIndexOrThrow(VehicleContract.TransactionEntry.COLUMN_DATE_TIME);
        String dateTime = c.getString(j);
        holder.headerDateView.setText(dateTime);

        return convertView;
    }

    public class HeaderHolder {
        public TextView headerDateView;

        public HeaderHolder(View view) {
            headerDateView = (TextView) view.findViewById(R.id.header_text_view);
        }
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public static class ViewHolder {
        public TextView amountTextView;
        public TextView dateTextView;

        public ViewHolder(View itemView) {
            amountTextView = (TextView) itemView.findViewById(R.id.item_details_amount);
            dateTextView = (TextView) itemView.findViewById(R.id.item_details_date_time);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String transactionAmount = cursor.getString(DetailFragment.COL_AMOUNT);
        String dateTime = cursor.getString(DetailFragment.COL_DATE_TIME);
        String description = cursor.getString(DetailFragment.COL_DATE_TIME);

        viewHolder.amountTextView.setText(transactionAmount);
        viewHolder.dateTextView.setText(dateTime);

    }


}
