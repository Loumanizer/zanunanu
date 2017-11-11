package edu.csi5230.sshresth.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainScreen extends AppCompatActivity {

    public static int Game_CURRENT_STATUS = -1;
    public static final int Game_STATUS_ACEPTED = 3;
    public static final int Game_STATUS_DECLIED = 4;
    public static final int Game_STATUS_MOVE_SEND = 9;
    public static final int Game_STATUS_MOVE_WAITING = 9;
    public static final int Game_STATUS_PlAYER1 = 5;
    public static final int Game_STATUS_PlAYER2 = 8;
    public static final int Game_STATUS_RECEIVED = 2;
    public static final int Game_STATUS_REQUEST_WAITING = 7;
    public static final int Game_STATUS_SEND = 1;
    public static final int Game_STATUS_TERMINATED = 6;
    int INTENT_FLAG = 131072;
    Button btInvitegame = null;
    Button btSetting = null;
    OnClickListener dialogClickListener = new OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            String name = MainScreen.this.game.getPlayer(0).name.toString();
            String sendmsg = null;
            switch (which) {
                case -2:
                    MainScreen.Game_CURRENT_STATUS = MainScreen.Game_STATUS_REQUEST_WAITING;
                    sendmsg = "0978 : Tic-Tac-Toe : DENIED : " + MainScreen.this.game.getPlayer(0).name;
                    break;
                case -1:
                    MainScreen.Game_CURRENT_STATUS = MainScreen.Game_STATUS_MOVE_WAITING;
                    MainScreen.this.receriver.setFlag(MainScreen.Game_STATUS_SEND);
                    sendmsg = "0978 : Tic-Tac-Toe : ACCEPTED : " + MainScreen.this.game.getPlayer(0).name;
                    Intent intent = new Intent(((Dialog) dialog).getContext(), GameScreen.class);
                    MainScreen.this.startActivity(intent);
                    break;
            }
            SmsManager.getDefault().sendTextMessage(MainScreen.this.game.getPlayer(0).phonenumber, null, sendmsg, null, null);
        }
    };
    EditText editPhonenumber = null;
    TTTGame game = null;
    SMSReceiver receriver;
    TextView textGameStatus = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        this.editPhonenumber = (EditText) findViewById(R.id.editPhoneNumber);
        this.btInvitegame = (Button) findViewById(R.id.btnInvite);
        this.textGameStatus = (TextView) findViewById(R.id.txtStatus);
        //this.btSetting = (Button) findViewById(R.id.btSetting);
        this.game = TTTGame.getAppData();
        this.receriver = new SMSReceiver(this);
        registerReceiver(this.receriver, SMSReceiver.filter);
        this.receriver.setFlag(0);
        this.textGameStatus.setText("Send or wait for Game Request");
        Game_CURRENT_STATUS = Game_STATUS_REQUEST_WAITING;
        this.btInvitegame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String phnumber = MainScreen.this.editPhonenumber.getText().toString();
                if (phnumber.equals(BuildConfig.FLAVOR)) {
                    AlertDialog adDraw = new Builder(view.getContext()).create();
                    adDraw.setCancelable(false);
                    adDraw.setMessage("Please Enter Phone Number");
                    adDraw.setButton("OK", new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    adDraw.show();
                    return;
                }
                MainScreen.Game_CURRENT_STATUS = MainScreen.Game_STATUS_SEND;
                MainScreen.this.textGameStatus.setText("Wait for Game Request");
                MainScreen.this.game.getPlayer(MainScreen.Game_STATUS_SEND).phonenumber = phnumber;
                SmsManager.getDefault().sendTextMessage(phnumber, null, "0978 : GameName : Tic-Tac-Toe : INVITE : " + MainScreen.this.game.getPlayer(0).name.toString(), null, null);
            }
        });
        //this.btSetting.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View view) {
        //        Intent i = new Intent(view.getContext(), MainScreen.class);
        //        view.getContext().startActivity(i);
        //    }
        //});
    }

    public void DisplaySmsMessage(String senderNum, String message) {
        String[] words = message.split(" : ");
        if (Game_CURRENT_STATUS == Game_STATUS_SEND) {
            if (words[Game_STATUS_RECEIVED].equals("ACCEPTED")) {
                Game_CURRENT_STATUS = Game_STATUS_RECEIVED;
                this.receriver.setFlag(Game_STATUS_SEND);
                this.textGameStatus.setText("Game Accepted");
                this.game.getPlayer(Game_STATUS_SEND).phonenumber = senderNum;
                this.game.getPlayer(Game_STATUS_SEND).name = words[Game_STATUS_ACEPTED];
                this.game.getPlayer(0).firstplayer = Game_STATUS_SEND;
                Intent i = new Intent(this, GameScreen.class);
                startActivity(i);
            } else if (words[Game_STATUS_RECEIVED].equals("DENIED")) {
                Game_CURRENT_STATUS = Game_STATUS_REQUEST_WAITING;
                this.textGameStatus.setText("Request Denied, send request again or wait for request");
            }
        } else if (Game_CURRENT_STATUS == Game_STATUS_REQUEST_WAITING) {
            String phnumber = senderNum;
            this.game.getPlayer(Game_STATUS_SEND).name = words[Game_STATUS_DECLIED];
            this.game.getPlayer(Game_STATUS_SEND).phonenumber = senderNum;
            this.game.getPlayer(0).phonenumber = senderNum;
            Builder builder = new Builder(this);
            String displaymsg = "You are invited by " + words[Game_STATUS_DECLIED] + " to play Tic-Tac-Toe " + "Game. Do you accept this invitation?";
            builder.setTitle("Confirm");
            builder.setMessage(displaymsg).setPositiveButton("Yes", this.dialogClickListener).setNegativeButton("No", this.dialogClickListener).show();
        }
    }
}
