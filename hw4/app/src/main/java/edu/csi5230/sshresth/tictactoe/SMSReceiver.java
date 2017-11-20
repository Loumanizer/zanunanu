package edu.csi5230.sshresth.tictactoe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by shova on 11/12/2017.
 */

public class SMSReceiver extends BroadcastReceiver {
   static IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
   int flag = 0;
   MainScreen mainScreen = null;

   SMSReceiver(MainScreen main){
       this.mainScreen = main;
   }

   void setFlag(int flag){
       this.flag = flag;
   }
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            try {
                Object[] pdusobj = (Object[]) bundle.get("pdus");
                for (Object obj : pdusobj) {
                    SmsMessage currentMsg = SmsMessage.createFromPdu((byte[]) obj);
                    String senderNo = currentMsg.getDisplayOriginatingAddress();
                    String msg = currentMsg.getDisplayMessageBody();
                    Log.i("SmsReceiver","senderNo:" + senderNo + ";message:" + msg );
                    if(msg.split(" : ")[0].toString().equals(TicTacToe.gameId)){
                        this.mainScreen.DisplaySmsMessage(senderNo, msg);
                    }
            }
        } catch(Exception e){
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
        }
    }
}
