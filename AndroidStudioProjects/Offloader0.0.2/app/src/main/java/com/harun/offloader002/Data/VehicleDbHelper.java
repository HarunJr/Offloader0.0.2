package com.harun.offloader002.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.harun.offloader002.R;

import static com.harun.offloader002.data.VehicleContract.TransactionEntry;
import static com.harun.offloader002.data.VehicleContract.VehicleEntry;


public class VehicleDbHelper extends SQLiteOpenHelper {
    private Context mContext;

    public static final String DATABASE_NAME = "Offloader002_DataBase.db";
    private static final int DATABASE_VERSION = 1;

    final String SQL_CREATE_VEHICLE_TABLE = "CREATE TABLE "
            + VehicleEntry.TABLE_NAME + " ("
            + VehicleEntry.COLUMN_VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + VehicleEntry.COLUMN_VEHICLE_REGISTRATION + " TEXT NOT NULL, "
            + VehicleEntry.COLUMN_VEHICLE_AMOUNT + " REAL NOT NULL, "
            + VehicleEntry.COLUMN_VEHICLE_REGISTRATION_DATE + " INTEGER NOT NULL, "
            + VehicleEntry.COLUMN_LAST_TRANSACTION_DATE_TIME + " INTEGER NOT NULL, "

            + "UNIQUE (" + VehicleEntry.COLUMN_VEHICLE_ID + ") ON CONFLICT REPLACE );";

    final String SQL_CREATE_TRANSACTION_TABLE = "CREATE TABLE "
            + TransactionEntry.TABLE_NAME + " ("
            + TransactionEntry.COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TransactionEntry.COLUMN_VEHICLE_KEY + " INTEGER NOT NULL, "
            + TransactionEntry.COLUMN_AMOUNT + " REAL NOT NULL, "
            + TransactionEntry.COLUMN_TYPE + " INTEGER NOT NULL, "
            + TransactionEntry.COLUMN_DATE_TIME + " INTEGER NOT NULL, "
            + TransactionEntry.COLUMN_DESCRIPTION + " VARCHAR(255), "
            + "FOREIGN KEY (" + TransactionEntry.COLUMN_VEHICLE_KEY + ") REFERENCES "
            + VehicleEntry.TABLE_NAME + "(" + VehicleEntry.COLUMN_VEHICLE_ID +
            ") " + ");";

    final String SQL_DROP_VEHICLE_TABLE = "DROP TABLE IF EXISTS " + VehicleEntry.TABLE_NAME;
    final String SQL_DROP_TRANSACTION_TABLE = "DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME;

    public VehicleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
//        Toast.makeText(mContext, context.getString(R.string.constructor_call), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL(SQL_CREATE_VEHICLE_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_TRANSACTION_TABLE);
//            Toast.makeText(mContext, mContext.getString(R.string.on_create_call), Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(mContext, "" + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            Toast.makeText(mContext, mContext.getString(R.string.on_upgrade_call), Toast.LENGTH_LONG).show();

            sqLiteDatabase.execSQL(SQL_DROP_VEHICLE_TABLE);
            sqLiteDatabase.execSQL(SQL_DROP_TRANSACTION_TABLE);
            onCreate(sqLiteDatabase);

        } catch (SQLException e) {
            Toast.makeText(mContext, "" + e, Toast.LENGTH_LONG).show();
        }
    }

    public void deleteDatabase(){
        mContext.deleteDatabase(DATABASE_NAME);
    }

}

