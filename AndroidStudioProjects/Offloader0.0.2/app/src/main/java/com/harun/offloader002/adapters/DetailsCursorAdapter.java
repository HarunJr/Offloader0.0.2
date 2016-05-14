package com.harun.offloader002.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.harun.offloader002.DateHelper;
import com.harun.offloader002.R;
import com.harun.offloader002.data.VehicleContract;
import com.harun.offloader002.fragments.DetailFragment;

import java.util.Calendar;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by HARUN on 5/9/2016.
 */
public class DetailsCursorAdapter extends CursorAdapter implements StickyListHeadersAdapter {
    public static final String LOG_TAG = DetailsCursorAdapter.class.getSimpleName();

    final private Context mContext;
    final private Cursor mCursor;
    public int groupID = 0;
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
        final DateHelper dateHelper = new DateHelper();
        final Cursor c = getCursor();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.details_header, parent, false);
            holder = new HeaderHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderHolder) convertView.getTag();
        }
        c.moveToPosition(position);
        long dateInMillis = c.getLong(c.getColumnIndex(VehicleContract.TransactionEntry.COLUMN_DATE_TIME));
        holder.headerDateView.setText(DateHelper.getFormattedDayString( dateInMillis));
        Log.w(LOG_TAG, "getHeaderView: " + DateHelper.getFormattedDateTimeString(this, dateInMillis) + position);

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
        public TextView descriptionTextView;

        public ViewHolder(View itemView) {
            amountTextView = (TextView) itemView.findViewById(R.id.item_details_amount);
            dateTextView = (TextView) itemView.findViewById(R.id.item_details_date_time);
            descriptionTextView = (TextView) itemView.findViewById(R.id.item_details_description);
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
        int position = cursor.getPosition();

        // -- Calculating whether this row belongs to a specific group or
        // not. -- //
        if (isANewGroup(cursor, position)) {
            // Log.d(TAG, "New group found at position " + position);
            groupID += 1;
        }

        // If section map doesn't contain a valid groupID for this
        // position, add to it.
        if (sectionMap.get(position, -1) == -1) {
            sectionMap.put(position, groupID);
        } else {
            groupID = sectionMap.get(position);
        }

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String transactionAmount = cursor.getString(DetailFragment.COL_AMOUNT);
        String description = cursor.getString(DetailFragment.COL_DESCRIPTION);
        int type = cursor.getInt(DetailFragment.COL_TYPE);
        long dateTime = cursor.getLong(DetailFragment.COL_DATE_TIME);
        String formattedDateTime = DateHelper.getFormattedTimeString(dateTime);
        Log.w(LOG_TAG, "bindView: " + transactionAmount);

        if (type == 1){
            viewHolder.amountTextView.setText(transactionAmount);
            viewHolder.dateTextView.setText(formattedDateTime);
            viewHolder.descriptionTextView.setText(description);

        }else {
            viewHolder.amountTextView.setText(transactionAmount);
            viewHolder.dateTextView.setText(formattedDateTime);
        }
    }

    private boolean isANewGroup(Cursor cursor, int position) {
        if (position == 0) {
            return false;
        }
        // Get date values for current and previous data items
        long nWhenThis = cursor.getLong(cursor.getColumnIndex(VehicleContract.TransactionEntry.COLUMN_DATE_TIME));

        cursor.moveToPosition(position - 1);
        long nWhenPrev = cursor.getLong(cursor.getColumnIndex(VehicleContract.TransactionEntry.COLUMN_DATE_TIME));

        // Restore cursor position
        cursor.moveToPosition(position);

        // Compare date values, ignore time values
        Calendar calThis = Calendar.getInstance();
        calThis.setTimeInMillis(nWhenThis);

        Calendar calPrev = Calendar.getInstance();
        calPrev.setTimeInMillis(nWhenPrev);

        int nDayThis = calThis.get(Calendar.DAY_OF_YEAR);
        int nDayPrev = calPrev.get(Calendar.DAY_OF_YEAR);

        return nDayThis != nDayPrev
                || calThis.get(Calendar.YEAR) != calPrev.get(Calendar.YEAR);
    }
}
