package com.harun.collectionmanager;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;

import com.harun.collectionmanager.data.VehicleContract;
import com.harun.collectionmanager.data.VehicleDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by HARUN on 7/22/2015.
 */
public class FetchVehicleTask extends AsyncTask<String, Void, Void>{

    private final String LOG_TAG = FetchVehicleTask.class.getSimpleName();

    private final Context mContext;

    private VehicleDbHelper dbHelper;

    private SQLiteDatabase db;
    private String[] vehicleColumns = { VehicleContract.VehicleEntry.COLUMN_VEHICLE_REGISTRATION, VehicleContract.VehicleEntry.COLUMN_DATE_TIME};

    long vehicleRowId;

    public FetchVehicleTask(Context context) {
        mContext =context;
        dbHelper = new VehicleDbHelper(mContext);
    }

    private boolean DEBUG = true;



    public String getDateTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    long addVehicle(String registration, String dateTime) {
        // First, check if the location with this city name exists in the db
//        Cursor vehicleCursor = mContext.getContentResolver().query(
//                VehicleContract.VehicleEntry.CONTENT_URI,
//                new String[]{VehicleContract.VehicleEntry._ID},
//                VehicleContract.VehicleEntry.COLUMN_VEHICLE_REGISTRATION + " = ?",
//                new String[]{registration},
//                null);
//        if (vehicleCursor.moveToFirst()) {
//            int vehicleIdIndex = vehicleCursor.getColumnIndex(VehicleContract.VehicleEntry._ID);
//            vehicleRowId = vehicleCursor.getLong(vehicleIdIndex);
//        } else {
            // Now that the content provider is set up, inserting rows of data is pretty simple.
            // First create a ContentValues object to hold the data you want to insert.
            ContentValues vehicleValues = new ContentValues();
            // Then add the data, along with the corresponding name of the data type,
            // so the content provider knows what kind of value is being inserted.
            vehicleValues.put(VehicleContract.VehicleEntry.COLUMN_DATE_TIME, dateTime);
            vehicleValues.put(VehicleContract.VehicleEntry.COLUMN_VEHICLE_REGISTRATION, registration);
            // Finally, insert location data into the database.
            Uri insertedUri = mContext.getContentResolver().insert(
                    VehicleContract.VehicleEntry.CONTENT_URI,
                    vehicleValues
            );
            // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
            vehicleRowId = ContentUris.parseId(insertedUri);
            return vehicleRowId;

    }
//        vehicleCursor.close();
        // Wait, that worked?  Yes!
//        return vehicleRowId;
//    }

    long addTransaction(double amount, int type, String description, String dateTime, int vehicleId) {
        long transactionId;

        // First, check if the location with this city name exists in the db
//        Cursor transactionCursor = mContext.getContentResolver().query(
//                VehicleContract.TransactionEntry.CONTENT_URI,
//                new String[]{VehicleContract.TransactionEntry._ID},
//                VehicleContract.TransactionEntry.COLUMN_AMOUNT + " = ?",
//                null, null);
//
//        if (transactionCursor.moveToFirst()) {
//            int transactionIdIndex = transactionCursor.getColumnIndex(VehicleContract.TransactionEntry._ID);
//            transactionId = transactionCursor.getLong(transactionIdIndex);
//        } else {
            // Now that the content provider is set up, inserting rows of data is pretty simple.
            // First create a ContentValues object to hold the data you want to insert.
            ContentValues transactionValues = new ContentValues();

            // Then add the data, along with the corresponding name of the data type,
            // so the content provider knows what kind of value is being inserted.
            transactionValues.put(VehicleContract.TransactionEntry.COLUMN_AMOUNT, amount);
            transactionValues.put(VehicleContract.TransactionEntry.COLUMN_VEHICLE_KEY, vehicleId);
            transactionValues.put(VehicleContract.TransactionEntry.COLUMN_TYPE, type);
            transactionValues.put(VehicleContract.TransactionEntry.COLUMN_DESCRIPTION, description);
            transactionValues.put(VehicleContract.TransactionEntry.COLUMN_DATE_TIME, dateTime);

            // Finally, insert location data into the database.
            Uri insertedUri = mContext.getContentResolver().insert(
                    VehicleContract.TransactionEntry.CONTENT_URI,
                    transactionValues
            );

            // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
            transactionId = ContentUris.parseId(insertedUri);
        return transactionId;
        }


//        transactionCursor.close();
//        // Wait, that worked?  Yes!
//        return transactionId;
//    }

    @Override
    protected Void doInBackground(String... params) {
        return null;
    }

    public void close() {
    dbHelper.close();
}

}
