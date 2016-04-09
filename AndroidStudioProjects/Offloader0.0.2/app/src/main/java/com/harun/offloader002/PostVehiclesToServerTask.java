package com.harun.offloader002;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by HARUN on 4/4/2016.
 */
public class PostVehiclesToServerTask extends AsyncTask<String, Void, String> {
    public static final String LOG_TAG = PostVehiclesToServerTask.class.getSimpleName();
    Context mContext;

    public PostVehiclesToServerTask(Context context) {
        this.mContext = context;
        Log.w(LOG_TAG, "PostToServerTask called");

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.w(LOG_TAG, "onPreExecute called");

    }

    @Override
    protected String doInBackground(String... params) {
        Log.w(LOG_TAG, "doInBackground called");
        String post_url = "http://192.168.245.1/Offloader002/input.php";/**specific to genymotion IPV4 address**/
///        String post_url = "http://10.0.2.2/Offloader002/input.php";/**specific to android virtual device**/
        String method = params[0];

        if (method.equals("post")) {
            Log.w(LOG_TAG, "doInBackground post");
            String vehicleReg = params[1];
            String dateTime = params[2];

            try {
                URL url = new URL(post_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data = URLEncoder.encode("registration", "UTF-8") + "=" + URLEncoder.encode(vehicleReg, "UTF-8") + "&" +
                        URLEncoder.encode("sign_up_date", "UTF-8") + "=" + URLEncoder.encode(dateTime, "UTF-8");

                Log.w(LOG_TAG, "doInBackground called "+ vehicleReg +": "+dateTime);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                httpURLConnection.disconnect();

                return "Post Vehicle success";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
    }
}
