package com.example.admin.w4weekendamazon;

import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SMSReceiver.OnSMSReceivedListener {

    private FloatingActionButton fab, fabAmazon;
    private static  final String TAG = "MAINACTIVITYTAG";
    private SMSReceiver smsReceiver = new SMSReceiver();
    IntentFilter intentFilter = new IntentFilter();
    TextView tvTextMsg;
    private NavigationView nvNav;

    @Override
    protected void onStart() {
        super.onStart();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver,intentFilter);
        smsReceiver.setOnSmsReceivedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTextMsg = (TextView) findViewById(R.id.tvTextMsg);
        nvNav = (NavigationView) findViewById(R.id.navigation);

        nvNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.iAmazonBookList){
                    startAmazonActivity();
                }
                return true;
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fabSMSSend);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something!
                Snackbar.make(v,"Clicked",Snackbar.LENGTH_SHORT).show();
                SmsManager sms = SmsManager.getDefault();
                int secretnum = (new Random().nextInt(9000))+1000;
                String phoneNumber = "4802681516";
                String message = "com.example.admin.w4weekendamazon: " + secretnum;
                sms.sendTextMessage(phoneNumber,null,message,null,null);
            }
        });

        fabAmazon = (FloatingActionButton) findViewById(R.id.fabAmazonBooks);
        fabAmazon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something!
                Snackbar.make(v,"Amazon",Snackbar.LENGTH_SHORT).show();
                startAmazonActivity();
            }
        });


    }

    private void startAmazonActivity() {
        Intent amazonIntent = new Intent(MainActivity.this,AmazonActivity.class);
        startActivity(amazonIntent);
    }

    @Override
    public void smsReceived(String msg) {
        tvTextMsg.setText(msg);
        Log.d(TAG, "smsReceived: " + msg);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(smsReceiver);
        }catch (Exception e){
            Log.d(TAG, "onStop: " + e.getMessage());
        }
    }
}
