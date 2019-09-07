package com.example.hp.beginnerproject2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class IncomingSms extends BroadcastReceiver {

    private static final String TAG = "Message received";




    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if(bundle!=null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");


                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0]);


//                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String currentDateTimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
                        , Locale.ENGLISH).format(Calendar.getInstance().getTime());




                Intent smsIntent = new Intent(context, MainActivity.class);
                smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                smsIntent.putExtra("MessageNumber", currentMessage.getOriginatingAddress());
                smsIntent.putExtra("Message",currentMessage.getMessageBody());
                smsIntent.putExtra("current_data_time",currentDateTimeString);
                context.startActivity(smsIntent);
            //    Toast.makeText(context,"SMS Received FROM :"+ currentMessage.getOriginatingAddress()+"\n"+currentMessage.getMessageBody(),Toast.LENGTH_LONG).show();

            }// bundle is null

        }catch (Exception e){
            Log.e("SmsReceiver", "Exception smsReceiver"+e);

        }

    }
}
