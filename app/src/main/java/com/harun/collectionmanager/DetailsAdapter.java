package com.harun.collectionmanager;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harun.collectionmanager.data.VehicleContract;

/**
 * Created by HARUN on 8/24/2015.
 */
public class DetailsAdapter extends CursorAdapter {
    public DetailsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_detail, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int type = cursor.getInt(cursor.getColumnIndex(VehicleContract.TransactionEntry.COLUMN_TYPE));
        if(type == 0){
            TextView tv = (TextView) view;
            tv.setText("-"+cursor.getString(cursor.getColumnIndex(VehicleContract.TransactionEntry.COLUMN_AMOUNT)));
        }else if(type == 1){
            TextView tv = (TextView) view;
            tv.setText(cursor.getString(cursor.getColumnIndex(VehicleContract.TransactionEntry.COLUMN_AMOUNT)));
        }

    }
}