package edu.csi5230.salamdawood.csi5230assignment1;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    public static final String EXTRA_PHONE_NUMBER = "PHONE_NUMBER";


    int INVITE_REQUEST = 1;

    TextView turnLabel = null;
    Button startButton = null;
    TTTButton[] tttButton = new TTTButton[9];
    Player[] players = new Player[2];
    Player currentPlayer = null;
    int currentTurn;
    BroadcastReceiver br = null;

    String RequestCode = "RequestCode";
    String SelectCode = "SelectCode";
    String AcceptCode = "AcceptCode";
    String DeclineCode = "DeclineCode";
    String SepCode = "::";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        turnLabel = (TextView) findViewById(R.id.player_turn);
        startButton = (Button) findViewById(R.id.start_game);

        tttButton[0] = (TTTButton) findViewById(R.id.button0);
        tttButton[1] = (TTTButton) findViewById(R.id.button1);
        tttButton[2] = (TTTButton) findViewById(R.id.button2);
        tttButton[3] = (TTTButton) findViewById(R.id.button3);
        tttButton[4] = (TTTButton) findViewById(R.id.button4);
        tttButton[5] = (TTTButton) findViewById(R.id.button5);
        tttButton[6] = (TTTButton) findViewById(R.id.button6);
        tttButton[7] = (TTTButton) findViewById(R.id.button7);
        tttButton[8] = (TTTButton) findViewById(R.id.button8);

        init();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String phnumber = null;
        if(requestCode == INVITE_REQUEST)
            if(resultCode == Activity.RESULT_OK) {
                phnumber = data.getStringExtra("EXTRA_PHONE_NUMBER");
                // send request to 2nd player

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phnumber, null, RequestCode + SepCode + "FromUser1", null, null);
                //wait for 2nd user response
                turnLabel.setText(phnumber + " Waiting for Response");
            }

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

                    for (SmsMessage m: messages) {
                        String number = m.getDisplayOriginatingAddress();
                        String text = m.getDisplayMessageBody();
                        StringTokenizer tokens = new StringTokenizer(text, "::");
                        String firsttxt = tokens.nextToken();
                        String secondtxt = tokens.nextToken();
                            if (firsttxt.matches(RequestCode)){
                                turnLabel.setText(number + " Waiting for Response");
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(context.getApplicationContext(), WaitResponse.class);
                                startActivityForResult(i, INVITE_REQUEST);
                        }
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        //pNumber.setText(number);
                        //mText.setText(text);
                    }
                }
            }
        };
        registerReceiver(br, filter);

    }


    void swapPlayer () {

        if (CheckForWinner()){
            turnLabel.setText(currentPlayer.getPlayerName() + " has won the game.");

            for (int i=0; i < 9 ; i++) {
                tttButton[i].setEnabled(false);
            }
        }
        else if (currentTurn > 8){turnLabel.setText("No Winner");}
        else {
            currentTurn++;
            currentPlayer = currentPlayer == players[0] ? players[1] : players[0];

            turnLabel.setText("Player: " + currentPlayer.getPlayerName());
        }

    }

    public void init() {

        currentPlayer = players[0] = new Player("Michael Jackson", "X");
        players[1] = new Player("James Bond", "O");

        turnLabel.setText("Game is not started yet!!!");

        for (int i = 0; i < 9; i++)
        {
            String buttonId = "button" + i;

            tttButton[i].setIndex(i);
            players[0].cells[i] = new DataCell("");
            players[0].cells[i].addObserver(tttButton[i]);
            players[1].cells[i] = new DataCell("");
            players[1].cells[i].addObserver(tttButton[i]);

            tttButton[i].setEnabled(false);

            tttButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TTTButton source = (TTTButton) view;
                    int index = source.getIndex();
                    currentPlayer.cells[index].setValue(currentPlayer.getSymbol());
                    swapPlayer();
                    source.setEnabled(false);
                }
            });
        }
        startButton.setText("Start Game");
        startButton.setOnClickListener(new View.OnClickListener() {

             @Override
            public void onClick(View view) {
                 Intent i = new Intent(view.getContext(), Invited_Request.class);
                 startActivityForResult(i, INVITE_REQUEST);

                     //StartGame();
            }
        });
    }

    public void StartGame() {
        turnLabel.setText("Player: " + currentPlayer.getPlayerName());

        for (int i=0; i<9; i++){
            //enable buttons and clear any value from last game
            tttButton[i].setText("");
            tttButton[i].setEnabled(true);
            //clear data cell values from previous game played
            players[0].cells[i].setValue("");
            players[1].cells[i].setValue("");

            currentTurn = 1;

        }

    }

    public boolean CheckForWinner() {

        String playerSymbol = currentPlayer.getSymbol();

        if (currentPlayer.cells[0].getValue().equals(playerSymbol) && currentPlayer.cells[1].getValue().equals(playerSymbol) && currentPlayer.cells[2].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[3].getValue().equals(playerSymbol)&& currentPlayer.cells[4].getValue().equals(playerSymbol) && currentPlayer.cells[5].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[6].getValue().equals(playerSymbol)&& currentPlayer.cells[7].getValue().equals(playerSymbol) && currentPlayer.cells[8].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[0].getValue().equals(playerSymbol)&& currentPlayer.cells[3].getValue().equals(playerSymbol) && currentPlayer.cells[6].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[1].getValue().equals(playerSymbol)&& currentPlayer.cells[4].getValue().equals(playerSymbol) && currentPlayer.cells[7].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[2].getValue().equals(playerSymbol)&& currentPlayer.cells[5].getValue().equals(playerSymbol) && currentPlayer.cells[8].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[0].getValue().equals(playerSymbol)&& currentPlayer.cells[4].getValue().equals(playerSymbol) && currentPlayer.cells[8].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[2].getValue().equals(playerSymbol)&& currentPlayer.cells[4].getValue().equals(playerSymbol) && currentPlayer.cells[6].getValue().equals(playerSymbol))
            return true;

        return false;
    }
}
