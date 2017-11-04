package edu.csi5230.npatel.brapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SMSActivity extends AppCompatActivity {
    IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    EditText phoneNumber = null, messageText = null;
    TextView pNumber = null, mText = null;
    Button smsButton = null;
    BroadcastReceiver br = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        phoneNumber = (EditText)findViewById(R.id.editTextPhoneNumber);
        messageText = (EditText) findViewById(R.id.editTextMessage);
        pNumber = (TextView) findViewById(R.id.textViewPhoneNumber);
        mText = (TextView) findViewById(R.id.textViewMessage);
        smsButton = (Button) findViewById(R.id.smsButton);

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = messageText.getText().toString();
                String number = phoneNumber.getText().toString();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, msg, null, null);
            }
        });

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

                    for (SmsMessage m: messages) {
                        String number = m.getDisplayOriginatingAddress();
                        String text = m.getDisplayMessageBody();

                        pNumber.setText(number);
                        mText.setText(text);
                    }
                }
            }
        };
        registerReceiver(br, filter);
    }
}
