package com.example.admin.w4weekendamazon;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.admin.w4weekendamazon.amazonmodel.AmazonResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AmazonBooksIntentService extends IntentService {

    private static final String TAG = "AmazonBooksIntentTag";
    private static final String MY_PREF_FILE = "com.example.admin.w4weekendamazon.amazonfile";


    public AmazonBooksIntentService() {
        super("AmazonBooksIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF_FILE, Context.MODE_PRIVATE);

        List<AmazonResponse> amazonResponses = new ArrayList<>();
        String retrievedData = sharedPreferences.getString("keyData", null);
        if (retrievedData == null) {
            final retrofit2.Call<List<AmazonResponse>> callRepos
                    = RetrofitHelper.createCall();
            try {
                amazonResponses = callRepos.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Gson gson = new Gson();
            Log.d(TAG, "onHandleIntent: " + retrievedData);
            Type type = new TypeToken<ArrayList<AmazonResponse>>() {}.getType();
            amazonResponses = (List<AmazonResponse>) gson.fromJson(retrievedData, type);
        }
        
        Intent receiverIntent = new Intent(this, AmazonListReceiver.class);
        receiverIntent.setAction("msg");
        Log.d(TAG, "onHandleIntent: ");
        receiverIntent.putParcelableArrayListExtra("BundleList", (ArrayList<? extends Parcelable>) amazonResponses);
        LocalBroadcastManager.getInstance(this).sendBroadcast(receiverIntent);
//            amazonCallback.updateList(amazonResponses);

    }

}
