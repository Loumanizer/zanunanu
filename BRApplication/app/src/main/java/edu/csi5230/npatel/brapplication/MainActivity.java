package edu.csi5230.npatel.brapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    TextView textView = null;
    EditText editText = null;
    Button button = null, smsButton = null;
    String actionA = "edu.oakland.brApplication.MSGA";
    String actionB = "edu.oakland.brApplication.MSGB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView1);
        editText = (EditText) findViewById(R.id.editText1);
        button = (Button) findViewById(R.id.button1);
        smsButton = (Button) findViewById(R.id.smsButton);

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start second Activity
                Intent i = new Intent(view.getContext(), SMSActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                Intent intent = new Intent(actionA);
                intent.putExtra("message", message);
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent);

                // Start second Activity
                Intent i = new Intent(view.getContext(), Main2Activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
            }
        });

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.hasExtra("message"))
                {
                    String msg = intent.getStringExtra("message");
                    textView.setText(msg);
                }
            }
        };
        IntentFilter filter = new IntentFilter(actionB);
        registerReceiver(br, filter);
    }
}
