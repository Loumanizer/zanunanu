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

public class Main2Activity extends AppCompatActivity {
    TextView textView = null;
    EditText editText = null;
    Button button = null;
    String actionA = "edu.oakland.brApplication.MSGA";
    String actionB = "edu.oakland.brApplication.MSGB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView = (TextView) findViewById(R.id.textView2);
        editText = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                Intent intent = new Intent(actionB);
                intent.putExtra("message", message);
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent);

                // Start second Activity
                Intent i = new Intent(view.getContext(), MainActivity.class);
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
        IntentFilter filter = new IntentFilter(actionA);
        registerReceiver(br, filter);
    }
}

