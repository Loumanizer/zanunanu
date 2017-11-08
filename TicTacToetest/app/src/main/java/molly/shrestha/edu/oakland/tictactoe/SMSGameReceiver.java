package molly.shrestha.edu.oakland.tictactoe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSGameReceiver extends BroadcastReceiver {
    static IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    int flag = 0;
    Game game = null;

    SMSGameReceiver(Game _game) {
        this.game = _game;
    }

    void setFlag(int _flag) {
        this.flag = _flag;
    }

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            try {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object obj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) obj);
                    String senderNum = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();
                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    if (message.split(" : ")[0].equals("0978")) {
                        this.game.UpdateGameTable(senderNum, message);
                    }
                }
            } catch (Exception e) {
                Log.e("SmsReceiver", "Exception smsReceiver" + e);
            }
        }
    }
}
