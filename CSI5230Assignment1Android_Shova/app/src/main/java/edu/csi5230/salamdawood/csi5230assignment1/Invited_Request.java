package edu.csi5230.salamdawood.csi5230assignment1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Invited_Request extends AppCompatActivity {

    EditText editPhNumb = null;
    Button btnSendReq = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited__request);

        editPhNumb = (EditText) findViewById(R.id.editPhNumber);
        btnSendReq = (Button) findViewById(R.id.btnSend);

        btnSendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("EXTRA_PHONE_NUMBER", editPhNumb.getText().toString());
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });


    }
}
