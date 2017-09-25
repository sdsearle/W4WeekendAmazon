package com.example.admin.w4weekendamazon;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.w4weekendamazon.amazonmodel.AmazonResponse;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class AmazonActivity extends AppCompatActivity implements AmazonResponseFragment.OnListFragmentInteractionListener, AmazonListReceiver.AmazonCallback{

    private static WeakReference<AmazonActivity> wrActivity = null;
    private static final String TAG = "AmazonActivityTAG" ;
    AmazonListReceiver alr = new AmazonListReceiver();
    IntentFilter alrif = new IntentFilter();
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onStart() {
        super.onStart();
        alrif.addAction("msg");
        LocalBroadcastManager.getInstance(this).registerReceiver(alr,alrif);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon);
        wrActivity = new WeakReference<>(this);

        alr.setAmazonCallback(this);

        Intent amazonBooksService = new Intent(this, AmazonBooksIntentService.class);
        startService(amazonBooksService);

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, RestAmazonCallService.class);
        alarmIntent = PendingIntent.getService(this, 0, intent, 0);

        /*alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        10000, alarmIntent);*/

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,0,60000*30,alarmIntent);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onListFragmentInteraction(AmazonResponse item) {

    }

    @Override
    public void updateList(List<AmazonResponse> amazonResponses) {

        Activity activity = wrActivity.get();
        if(activity != null && !isFinishing()) {
            AmazonResponseFragment amazonResponseFragment = AmazonResponseFragment.newInstance(1, amazonResponses);
            getSupportFragmentManager().beginTransaction().replace(R.id.flAmazonFrag, amazonResponseFragment).commitAllowingStateLoss();
        }
    }
}
