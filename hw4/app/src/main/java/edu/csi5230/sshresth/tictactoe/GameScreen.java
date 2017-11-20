package edu.csi5230.sshresth.tictactoe;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.TextView;

public class GameScreen extends AppCompatActivity {

    public static int Game_CURRENT_STATUS = -1;
    public static final int Game_STATUS_MOVE_WAITING = 9;
    public static final int Game_STATUS_RECEIVED = 2;
    public static final int Player1 = 0;
    public static final int Player2 = 1;
    GridLayout buttonContainer = null;
    public int currentPlayer = Player1;
    public TTTGame game = null;
    SMSGameReceiver gamereceiver;
    public int totalbox = 9;
    int[] playerinput = new int[totalbox];
    public Convert tablelocConvert = new Convert();

    public TimeTracker timeTracker = new TimeTracker();
    TextView tv = null;
    TextView tvCurrentPlayer = null;
    TextView tvGameStatus = null;
    TextView txtP1Time = null;
    TextView txtP2Time = null;

    public TTTButton[] buttons = new TTTButton[totalbox];

    class GameButtonClickHandler implements OnClickListener {
        GameButtonClickHandler() {
        }

        public void onClick(View v) {
            int i = GameScreen.Player1;

            TTTButton b = (TTTButton) v;
            b.setTag(Integer.valueOf(GameScreen.this.currentPlayer));
            GameScreen.this.enablecells(false);
            GameScreen.this.game.getPlayer(GameScreen.this.currentPlayer).MarkCell(b.index);
            GameScreen.this.tablelocConvert.getXYvalue(b.index);
            String msg = TicTacToe.gameId + " : Tic-Tac-Toe : SELECTED : " + tablelocConvert.toSting();
            Game_CURRENT_STATUS = Game_STATUS_MOVE_WAITING;
            timeTracker.setStopTime(currentPlayer);
            txtP1Time.setText(game.getPlayer(GameScreen.this.currentPlayer).name + " : Total Time : " + (timeTracker.getTotalTime(currentPlayer)/1000) + "sec");
            CheckWiner();
            tvGameStatus.setText("MOVE SEND");
            GameScreen game = GameScreen.this;
            if (GameScreen.this.currentPlayer == 0) {
                i = GameScreen.Player2;
            }
            game.currentPlayer = i;
            tvCurrentPlayer.setText(GameScreen.this.game.getPlayer(GameScreen.this.currentPlayer).name);
            SmsManager.getDefault().sendTextMessage(GameScreen.this.game.getPlayer(GameScreen.this.currentPlayer).phoneNumber, null, msg, null, null);
            GameScreen.this.timeTracker.setStartTime();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        //this.tv = (TextView) findViewById(R.id.textView);
        this.tvCurrentPlayer = (TextView) findViewById(R.id.tvCurrentPlayer);
        this.tvGameStatus = (TextView) findViewById(R.id.tvGamestatus);
        this.buttonContainer = (GridLayout) findViewById(R.id.buttonContainer);
        txtP1Time = (TextView) findViewById(R.id.txtP1Time);
        txtP2Time = (TextView) findViewById(R.id.txtP2Time);
        Game_CURRENT_STATUS = Game_STATUS_MOVE_WAITING;
        this.game = TTTGame.getAppData();
        this.tvGameStatus.setText("SEND or WAIT for Request");
        GameButtonClickHandler h = new GameButtonClickHandler();
        this.gamereceiver = new SMSGameReceiver(this);
        registerReceiver(this.gamereceiver, SMSReceiver.filter);
        this.game.getPlayer(Player2).symbol = 17301558;
        this.gamereceiver.setFlag(Player2);
        for (int i = 0; i < totalbox; i += 1) {
            TTTButton b = new TTTButton(this);
            b.setIndex(i);
            b.setTag(-1);
            b.setOnClickListener(h);
            this.game.getPlayer(Player1).register(b, i);
            this.game.getPlayer(Player2).register(b, i);
            this.buttonContainer.addView(b);
            this.buttons[i] = b;
        }
        buttonContainer.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int bContainerWidth = GameScreen.this.buttonContainer.getWidth();
                int bContainerHeight = GameScreen.this.buttonContainer.getHeight();
                for (int i = GameScreen.Player1; i < GameScreen.this.totalbox; i += GameScreen.Player2) {
                    LayoutParams params = (LayoutParams) GameScreen.this.buttons[i].getLayoutParams();
                    params.rightMargin = 5;
                    params.leftMargin = 5;
                    params.topMargin = 5;
                    params.bottomMargin = 5;
                    params.width = ((bContainerWidth / 3) - params.rightMargin) - params.leftMargin;
                    params.height = ((bContainerHeight / 3) - params.topMargin) - params.bottomMargin;
                    GameScreen.this.buttons[i].setLayoutParams(params);
                }
                ViewTreeObserver vto = GameScreen.this.buttonContainer.getViewTreeObserver();
            }
        });
        if (this.game.getPlayer(Player1).firstPlayer == Player2) {
            this.currentPlayer = Player1;
            enablecells(true);
            txtP1Time.setText(this.game.getPlayer(Player1).name + " : Total Time :");
            txtP2Time.setText(this.game.getPlayer(Player2).name + " : Total Time :");
        } else {
            this.currentPlayer = Player2;
            enablecells(false);
            txtP1Time.setText(this.game.getPlayer(Player1).name + " : Total Time :");
            txtP2Time.setText(this.game.getPlayer(Player2).name + " : Total Time :");
        }
        tvCurrentPlayer.setText(this.game.getPlayer(this.currentPlayer).name);
        timeTracker.setStartTime();
    }
    public void UpdateGameTable(String msgString) {
        String[] words = msgString.split(" : ");
        if (words[Game_STATUS_RECEIVED].equals("TERMINATED")) {
            AlertDialog adDraw = new AlertDialog.Builder(this).create();
            adDraw.setCancelable(false);
            adDraw.setMessage("Other Player Terminated the Game");
            adDraw.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    GameScreen.this.game.getPlayer(GameScreen.this.currentPlayer).firstPlayer = GameScreen.Player1;
                    Intent intent = new Intent(GameScreen.this.getBaseContext(), MainScreen.class);
                    GameScreen.this.gamereceiver.setFlag(GameScreen.Player1);
                    GameScreen.this.startActivity(intent);
                }
            });
            adDraw.show();
        } else if (Game_CURRENT_STATUS == Game_STATUS_MOVE_WAITING) {
            Game_CURRENT_STATUS = Game_STATUS_MOVE_WAITING;
            updatetable(tablelocConvert.singleValue(words[3]));
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
        SmsManager.getDefault().sendTextMessage(this.game.getPlayer(Player2).phoneNumber.toString(), null, TicTacToe.gameId + " : Tic-Tac-Toe : TERMINATED", null, null);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void enablecells(boolean cond) {
        for (int i = 0; i < totalbox; i += 1) {
            if (this.buttons[i].getTag().toString().equals("-1")) {
                buttons[i].setEnabled(cond);
            } else {
                buttons[i].setEnabled(false);
            }
        }
    }

    public void updatetable(int xlocation) {
        int i = Player2;
        buttons[xlocation].setEnabled(false);
        buttons[xlocation].setTag(Integer.valueOf(this.currentPlayer));
        game.getPlayer(this.currentPlayer).MarkCell(this.buttons[xlocation].index);
        GameScreen.this.enablecells(true);
        tvGameStatus.setText("MOVE RECEIVED");
        timeTracker.setStopTime(this.currentPlayer);
        txtP2Time.setText(game.getPlayer(this.currentPlayer).name + " : Total Time : " + (timeTracker.getTotalTime(this.currentPlayer)/1000) + " sec");
        CheckWiner();
        if (this.currentPlayer != 0) {
            i = Player1;
        }
        currentPlayer = i;
        tvCurrentPlayer.setText(this.game.getPlayer(this.currentPlayer).name);
        timeTracker.setStartTime();
    }

    public void CheckWiner() {
        int i;
        String Playername = this.game.getPlayer(this.currentPlayer).name;
        int cp = currentPlayer;
        for (i = 0; i < totalbox; i += 1) {

            playerinput[i] = ((Integer) this.buttons[i].getTag()).intValue();

        }
        if ((playerinput[0] == cp && playerinput[1] == cp && playerinput[2] == cp) ||
                (playerinput[3] == cp && playerinput[4] == cp && playerinput[5] == cp) ||
                (playerinput[6] == cp && playerinput[7] == cp && playerinput[8] == cp) ||
                (playerinput[0] == cp && playerinput[3] == cp && playerinput[6] == cp) ||
                (playerinput[1] == cp && playerinput[4] == cp && playerinput[7] == cp) ||
                (playerinput[2] == cp && playerinput[5] == cp && playerinput[8] == cp) ||
                (playerinput[0] == cp && playerinput[4] == cp && playerinput[8] == cp) ||
                (playerinput[2] == cp && playerinput[4] == cp && playerinput[6] == cp) )
        {
            Playername = this.game.getPlayer(this.currentPlayer).name;
            this.tvGameStatus.setText(Playername + " : WINNER");
            AlertDialog ad = new Builder(this).create();
            ad.setCancelable(false);
            ad.setMessage("CONGRATULATIONS " + Playername + ": WINNER");
            ad.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    GameScreen.this.game.getPlayer(GameScreen.this.currentPlayer).firstPlayer = GameScreen.Player1;
                    Intent intent = new Intent(GameScreen.this.getBaseContext(), MainScreen.class);
                    GameScreen.this.gamereceiver.setFlag(GameScreen.Player1);
                    GameScreen.this.startActivity(intent);
                }
            });
            ad.show();
            for (i = Player1; i < this.totalbox; i += Player2) {
                this.buttons[i].setEnabled(false);
            }
        }
        else if (((((((((((playerinput[0] | this.playerinput[1]) |
                playerinput[2]) | playerinput[3]) | playerinput[4]) | playerinput[5]) |
                playerinput[6]) | playerinput[7]) | playerinput[8]) ) != -1))
        {
            this.tvGameStatus.setText("GAME DRAW");
            AlertDialog adDraw = new Builder(this).create();
            adDraw.setCancelable(false);
            adDraw.setMessage("GAME DRAW");
            adDraw.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    GameScreen.this.game.getPlayer(GameScreen.this.currentPlayer).firstPlayer = GameScreen.Player1;
                    Intent intent = new Intent(GameScreen.this.getBaseContext(), MainScreen.class);
                    GameScreen.this.gamereceiver.setFlag(GameScreen.Player1);
                    GameScreen.this.startActivity(intent);
                }
            });
            adDraw.show();
        }
        else
        {

        }
    }
}
