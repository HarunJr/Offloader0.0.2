package com.harun.collectionmanager;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class VehicleAdapter extends CursorAdapter {
    public VehicleAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    private static class ViewHolder{
        public final ImageView iconView;
        public final TextView vehicleView;
        public final TextView amountView;
        public final TextView dateView;

        public ViewHolder(View view){
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            vehicleView = (TextView) view.findViewById(R.id.list_item_vehicle_textview);
            amountView = (TextView) view.findViewById(R.id.list_item_amount_textview);
            dateView = (TextView) view.findViewById(R.id.list_item_date_time_textview);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_vehicle, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
//        TextView tv = (TextView) view;
//        tv.setText(cursor.getString(VehicleFragment.COL_VEHICLE_REGISTRATION));

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int weatherId = cursor.getInt(VehicleFragment.COL_VEHICLE_ID);
        // Use placeholder image for now
        viewHolder.iconView.setImageResource(R.drawable.ic_action_bus);

        // Read date from cursor
        String vehicleReg = cursor.getString(VehicleFragment.COL_VEHICLE_REGISTRATION);
        // Find TextView and set formatted date on it
        viewHolder.vehicleView.setText(vehicleReg);

        // Read weather amount from cursor
        String amount = cursor.getString(VehicleFragment.COL_AMOUNT);
        // Find TextView and set amount on it
        viewHolder.amountView.setText(amount);

        String dateTime = cursor.getString(VehicleFragment.COL_TIME);
        viewHolder.dateView.setText(dateTime);

        // Read user preference for metric or imperial temperature units
 //       boolean isMetric = Utility.isMetric(context);
 //        Read high temperature from cursor

        // Read low temperature from cursor
//        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
//        TextView lowView = (TextView) view.findViewById(R.id.list_item_low_textview);
//        lowView.setText(Utility.formatTemperature(low, isMetric));


    }
}
