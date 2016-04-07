package com.harun.offloader002;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.harun.offloader002.data.VehicleContract;
import com.harun.offloader002.data.VehicleDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * Created by HARUN on 4/5/2016.
 */
public class FetchVehicleTask extends AsyncTask<Void, Void, String> {
    public static final String LOG_TAG = FetchVehicleTask.class.getSimpleName();
    private final Context mContext;
    HttpURLConnection httpURLConnection = null;
    BufferedReader bufferedReader = null;
    String json_url;

    private VehicleDbHelper dbHelper;
    private SQLiteDatabase db;

    public FetchVehicleTask(Context context) {
        mContext = context;
        dbHelper = new VehicleDbHelper(mContext);
    }

    @Override
    protected void onPreExecute() {
        json_url = "http://192.168.245.1/Offloader002/getData.php";/**specific to genymotion IPV4 address**/
    }

    @Override
    protected String doInBackground(Void... params) {
        String JSON_STRING;
        String vehicleJsonString = null;
        try {
            URL url = new URL(json_url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            while ((JSON_STRING = bufferedReader.readLine()) != null) {

                stringBuilder.append(JSON_STRING + "\n");
            }

            inputStream.close();

            vehicleJsonString = stringBuilder.toString().trim();
            Log.w(LOG_TAG, "JSON String: " + vehicleJsonString);
            getVehicleDataFromJson(vehicleJsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    private void getVehicleDataFromJson(String vehicleJsonString) throws JSONException {

        //Vehicle information. Each vehicle's detail is an element of "list" array.
        final String VEHICLE_LIST = "list";

        //Vehicle details referenced from JSON
        final String VEHICLE_ID = "id";
        final String VEHICLE_REG = "registration";
        final String VEHICLE_REG_DATE = "sign_up_date";

        try {
            JSONObject vehicleJson = new JSONObject(vehicleJsonString);
            JSONArray vehicleArray = vehicleJson.getJSONArray(VEHICLE_LIST);

            Vector<ContentValues> cVVector = new Vector<ContentValues>(vehicleArray.length());

            for (int i = 0; i < vehicleArray.length(); i++) {
                String vehicleId, vehicleReg, regDate;

                JSONObject vehicleObject = vehicleArray.getJSONObject(i);

                vehicleId = vehicleObject.getString(VEHICLE_ID);
                vehicleReg = vehicleObject.getString(VEHICLE_REG);
                regDate = vehicleObject.getString(VEHICLE_REG_DATE);

                Log.w(LOG_TAG, "From db: " + vehicleId + ", " + vehicleReg + ", " + regDate);

                addToSQLitedb(vehicleId, vehicleReg, regDate);

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    private long addToSQLitedb(String vehicleId, String vehicleReg, String regDate) {

        open();
        ContentValues vehicleValues = new ContentValues();

        vehicleValues.put(VehicleContract.VehicleEntry.COLUMN_VEHICLE_ID, vehicleId);
        vehicleValues.put(VehicleContract.VehicleEntry.COLUMN_VEHICLE_REGISTRATION, vehicleReg);
        vehicleValues.put(VehicleContract.VehicleEntry.COLUMN_VEHICLE_REGISTRATION_DATE, regDate);

        //TODO: Insert actual transaction values in vehicle table
        vehicleValues.put(VehicleContract.VehicleEntry.COLUMN_VEHICLE_AMOUNT, "1500");
        vehicleValues.put(VehicleContract.VehicleEntry.COLUMN_LAST_TRANSACTION_DATE_TIME, regDate);

        //USE THIS for normal entries
//        long vehicleRowId=db.insert(VehicleContract.VehicleEntry.TABLE_NAME,null, vehicleValues);

        //Use this for insert with conflict replace.
//        long vehicleRowId = db.insertWithOnConflict(VehicleContract.VehicleEntry.TABLE_NAME, null, vehicleValues, SQLiteDatabase.CONFLICT_REPLACE);

        Uri vehicleUri = mContext.getContentResolver().insert(VehicleContract.VehicleEntry.CONTENT_URI, vehicleValues);

        long vehicleRowId = ContentUris.parseId(vehicleUri);

        if (vehicleRowId > 0) {
            Log.w(LOG_TAG, "Inserted into SQLitedb: " + vehicleId + ", " + vehicleReg + ", " + regDate);

        } else {
            Log.w(LOG_TAG, ">>>>ERROR Inserting into SQLitedb: ");

        }

        close();

        return vehicleRowId;

    }

    public void close() {
        dbHelper.close();
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}
