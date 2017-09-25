package com.example.admin.w4weekendamazon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;


public class SMSReceiver extends BroadcastReceiver {

    private static final String TAG = "SMSRECEIVERTAG" ;
    public static final String confirmation = "com.example.admin.w4weekendamazon:";
    private OnSMSReceivedListener listener = null;


    @Override
    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
//        listener = (OnSMSReceivedListener) context;


        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.d(TAG, "senderNum: "+ senderNum + "; message: " + message);


                    // Show Alert
                    if(message.contains(confirmation)) {
                        Toast toast = Toast.makeText(context,
                                "senderNum: " + senderNum + ", message: " + message, Toast.LENGTH_SHORT);
                        toast.show();
                        message = message.substring(message.indexOf(":")+2);
                        Log.d(TAG, "onReceive: " + message);
                        if (listener != null) {
                            Log.d(TAG, "onReceive: ");
                            listener.smsReceived(message);
                        }
                    }

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }

    public void setOnSmsReceivedListener(Context context) {
        this.listener = (OnSMSReceivedListener) context;
    }

    public interface OnSMSReceivedListener{
        void smsReceived(String msg);
    }
}