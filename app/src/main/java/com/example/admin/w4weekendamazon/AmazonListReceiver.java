package com.example.admin.w4weekendamazon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.w4weekendamazon.amazonmodel.AmazonResponse;

import java.util.List;

public class AmazonListReceiver extends BroadcastReceiver {
    private static final String TAG = "AmazonListReceiverTag" ;
    AmazonCallback amazonCallback;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive: "+intent.getAction());
        //if(intent.getAction() == "BundleList"){
            List<AmazonResponse> amazonResponses = intent.getParcelableArrayListExtra("BundleList");
            amazonCallback.updateList(amazonResponses);
        //}
    }

    public void setAmazonCallback(Context context){
        this.amazonCallback = (AmazonCallback) context;
    }

    public interface AmazonCallback{
        void updateList(List<AmazonResponse> amazonResponses);
    }
}
