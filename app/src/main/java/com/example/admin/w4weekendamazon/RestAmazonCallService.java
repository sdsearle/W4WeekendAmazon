package com.example.admin.w4weekendamazon;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.w4weekendamazon.amazonmodel.AmazonResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

public class RestAmazonCallService extends IntentService {
    public static final String MY_PREF_FILE = "com.example.admin.w4weekendamazon.amazonfile";
    private static final String TAG = "RestAmazonCallService";

    public RestAmazonCallService() {
        super("RestAmazonCallService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final retrofit2.Call<List<AmazonResponse>> callRepos
                = RetrofitHelper.createCall();
        Log.d(TAG, "onHandleIntent: ");
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        try {
            List<AmazonResponse> amazonResponses = callRepos.execute().body();

            String s = gson.toJson(amazonResponses);
            Log.d(TAG, "onHandleIntent: " + s);
            editor.putString("keyData",s);
            boolean isSaved = editor.commit();
            if(isSaved) {
                Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(this, "Data NOT saved", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
