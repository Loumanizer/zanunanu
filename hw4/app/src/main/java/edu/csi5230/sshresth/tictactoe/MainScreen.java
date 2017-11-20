package edu.csi5230.sshresth.tictactoe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {
    public static int Game_CURRENT_STATUS = -1;
    public static final int Game_STATUS_SEND = 1;
    public static final int Game_STATUS_RECEIVED = 2;
    public static final int Game_STATUS_ACEPTED = 3;
    public static final int Game_STATUS_DECLIED = 4;
    public static final int Game_STATUS_REQUEST_WAITING = 7;
    public static final int Game_STATUS_MOVE_WAITING = 9;

    Button btnInvite = null;
    EditText editPhoneNumber = null;
    TextView txtStatus = null;
    TTTGame game = null;
    SMSReceiver receiver = null;

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            String name = MainScreen.this.game.getPlayer(0).name.toString();
            String sendmsg = null;
            switch (which) {
                case -1:
                    MainScreen.Game_CURRENT_STATUS = MainScreen.Game_STATUS_MOVE_WAITING;
                    MainScreen.this.receiver.setFlag(MainScreen.Game_STATUS_SEND);
                    sendmsg = TicTacToe.gameId + " : Tic-Tac-Toe : ACCEPTED : " + MainScreen.this.game.getPlayer(0).name;
                    Intent intent = new Intent(((Dialog) dialog).getContext(), GameScreen.class);
                    MainScreen.this.startActivity(intent);
                    break;
                case -2:
                    MainScreen.Game_CURRENT_STATUS = MainScreen.Game_STATUS_REQUEST_WAITING;
                    sendmsg = TicTacToe.gameId + " : Tic-Tac-Toe : DENIED : " + MainScreen.this.game.getPlayer(0).name;
                    break;
            }
            SmsManager.getDefault().sendTextMessage(MainScreen.this.game.getPlayer(0).phoneNumber, null, sendmsg, null, null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        this.editPhoneNumber = (EditText) findViewById(R.id.editPhoneNumber);
        this.btnInvite = (Button) findViewById(R.id.btnInvite);
        this.txtStatus = (TextView) findViewById(R.id.txtStatus);
        this.game = TTTGame.getAppData();
        this.receiver = new SMSReceiver(this);
        registerReceiver(this.receiver, SMSReceiver.filter);
        this.receiver.setFlag(0);

        this.txtStatus.setText("Send or Wait Game Request");
        Game_CURRENT_STATUS = Game_STATUS_REQUEST_WAITING;

        this.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = MainScreen.this.editPhoneNumber.getText().toString();
                if( phoneNo.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG).show();
                    return;
                }

                MainScreen.Game_CURRENT_STATUS = MainScreen.Game_STATUS_SEND;
                MainScreen.this.txtStatus.setText("Wait for Game Request");
                MainScreen.this.game.getPlayer(MainScreen.Game_STATUS_SEND).phoneNumber = phoneNo;
                SmsManager.getDefault().sendTextMessage(phoneNo, null, TicTacToe.gameId + " : GameName : Tic-Tac-Toe : INVITE : " + MainScreen.this.game.getPlayer(0).name.toString(), null, null);
            }
        });
    }

    private void registerReceiver() {
    }

    @SuppressLint("WrongConstant")
    public void DisplaySmsMessage(String senderNum, String message) {
        String[] words = message.split(" : ");
        if (Game_CURRENT_STATUS == Game_STATUS_SEND) {
            if (words[Game_STATUS_RECEIVED].equals("ACCEPTED")) {
                Game_CURRENT_STATUS = Game_STATUS_RECEIVED;
                this.receiver.setFlag(Game_STATUS_SEND);
                this.txtStatus.setText("Game Accepted");
                this.game.getPlayer(Game_STATUS_SEND).phoneNumber = senderNum;
                this.game.getPlayer(Game_STATUS_SEND).name = words[Game_STATUS_ACEPTED];
                this.game.getPlayer(0).firstPlayer = Game_STATUS_SEND;
                Intent i = new Intent(this, GameScreen.class);
                startActivity(i);
            } else if (words[Game_STATUS_RECEIVED].equals("DENIED")) {
                Game_CURRENT_STATUS = Game_STATUS_REQUEST_WAITING;
                this.txtStatus.setText("Request Denied, send request again or wait for request");
            }
        } else if (Game_CURRENT_STATUS == Game_STATUS_REQUEST_WAITING) {
            String phnumber = senderNum;
            this.game.getPlayer(Game_STATUS_SEND).name = words[Game_STATUS_DECLIED];
            this.game.getPlayer(Game_STATUS_SEND).phoneNumber = senderNum;
            this.game.getPlayer(0).phoneNumber = senderNum;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String displaymsg = "You are invited by " + words[Game_STATUS_DECLIED] + " to play Tic-Tac-Toe " + "Game. Do you want to accept this invitation?";
            builder.setTitle("Confirm");
            builder.setMessage(displaymsg).setPositiveButton("Yes", this.dialogClickListener).setNegativeButton("No", this.dialogClickListener).show();
        }
    }
}
