package molly.shrestha.edu.oakland.tictactoe;

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

public class Game extends AppCompatActivity {
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
    public static final int Player1 = 0;
    public static final int Player2 = 1;
    public static int Player_Turn = -1;
    GridLayout buttonContainer = null;
    public int currentPlayer = Player1;
    public TTTGame game = null;
    SMSGameReceiver gamereceiver;
    int[] playerinput = new int[this.totalbox];
    public Convert tablelocConvert = new Convert();
    TextView textPlayer1Time = null;
    TextView textPlayer2Time = null;
    public TimeTracker timeTracker = new TimeTracker();
    public int totalbox = 9;
    TextView tv = null;
    TextView tvCurrentPlayer = null;
    TextView tvGameStatus = null;
    public TTTButton[] buttons = new TTTButton[totalbox];


    class GameButtonClickHandler implements OnClickListener {
        GameButtonClickHandler() {
        }

        public void onClick(View v) {
            int i = Game.Player1;
            TTTButton b = (TTTButton) v;
            b.setTag(Integer.valueOf(Game.this.currentPlayer));
            Game.this.enablecells(false);
            Game.this.game.getPlayer(Game.this.currentPlayer).MarkCell(b.index);
            Game.this.tablelocConvert.getXYvalue(b.index);
            String msg = "0978 : Tic-Tac-Toe : SELECTED : " + Game.this.tablelocConvert.toSting();
            Game.Game_CURRENT_STATUS = Game.Game_STATUS_MOVE_WAITING;
            Game.this.timeTracker.setStopTime(Game.this.currentPlayer);
            Game.this.textPlayer1Time.setText(Game.this.game.getPlayer(Game.this.currentPlayer).name + " : Total Time : " + Game.this.timeTracker.getTotalTime(Game.this.currentPlayer));
            Game.this.CheckWiner();
            Game.this.tvGameStatus.setText("Move Send");
            Game game = Game.this;
            if (Game.this.currentPlayer == 0) {
                i = Game.Player2;
            }
            game.currentPlayer = i;
            Game.this.tvCurrentPlayer.setText(Game.this.game.getPlayer(Game.this.currentPlayer).name);
            SmsManager.getDefault().sendTextMessage(Game.this.game.getPlayer(Game.this.currentPlayer).phonenumber, null, msg, null, null);
            Game.this.timeTracker.setStartTime();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.tv = (TextView) findViewById(R.id.textView);
        this.textPlayer1Time = (TextView) findViewById(R.id.textPlayer1Time);
        //this.textPlayer2Time = (TextView) findViewById(R.id.textPlayer2Time);
        this.tvCurrentPlayer = (TextView) findViewById(R.id.tvCurrentPlayer);
        this.tvGameStatus = (TextView) findViewById(R.id.tvGamestatus);
        this.buttonContainer = (GridLayout) findViewById(R.id.buttonContainer);
        Game_CURRENT_STATUS = Game_STATUS_MOVE_WAITING;
        this.game = TTTGame.getAppData();
        this.tvGameStatus.setText("Send or Wait for Request");
        GameButtonClickHandler h = new GameButtonClickHandler();
        this.gamereceiver = new SMSGameReceiver(this);
        registerReceiver(this.gamereceiver, SMSReceiver.filter);
        this.game.getPlayer(Player2).symbol = 17301558;
        this.gamereceiver.setFlag(Player2);
        for (int i = Player1; i < this.totalbox; i += Player2) {
            TTTButton b = new TTTButton(this);
            b.setIndex(i);
            b.setTag(Integer.valueOf(-1));
            b.setOnClickListener(h);
            this.game.getPlayer(Player1).register(b, i);
            this.game.getPlayer(Player2).register(b, i);
            this.buttonContainer.addView(b);
            this.buttons[i] = b;
        }
        this.buttonContainer.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int bContainerWidth = Game.this.buttonContainer.getWidth();
                int bContainerHeight = Game.this.buttonContainer.getHeight();
                for (int i = Game.Player1; i < Game.this.totalbox; i += Game.Player2) {
                    LayoutParams params = (LayoutParams) Game.this.buttons[i].getLayoutParams();
                    params.rightMargin = Game.Game_STATUS_PlAYER1;
                    params.leftMargin = Game.Game_STATUS_PlAYER1;
                    params.topMargin = Game.Game_STATUS_PlAYER1;
                    params.bottomMargin = Game.Game_STATUS_PlAYER1;
                    params.width = ((bContainerWidth / Game.Game_STATUS_DECLIED) - params.rightMargin) - params.leftMargin;
                    params.height = ((bContainerHeight / Game.Game_STATUS_DECLIED) - params.topMargin) - params.bottomMargin;
                    Game.this.buttons[i].setLayoutParams(params);
                }
                ViewTreeObserver vto = Game.this.buttonContainer.getViewTreeObserver();
            }
        });
        if (this.game.getPlayer(Player1).firstplayer == Player2) {
            this.currentPlayer = Player1;
            enablecells(true);
            this.textPlayer1Time.setText(this.game.getPlayer(Player1).name + " : Total Time :");
            this.textPlayer2Time.setText(this.game.getPlayer(Player2).name + " : Total Time :");
        } else {
            this.currentPlayer = Player2;
            enablecells(false);
            this.textPlayer1Time.setText(this.game.getPlayer(Player1).name + " : Total Time :");
            this.textPlayer2Time.setText(this.game.getPlayer(Player2).name + " : Total Time :");
        }
        this.tvCurrentPlayer.setText(this.game.getPlayer(this.currentPlayer).name);
        this.timeTracker.setStartTime();
    }

    public void UpdateGameTable(String phonenumber, String msgString) {
        String[] words = msgString.split(" : ");
        String _phonenumber = phonenumber;
        if (words[Game_STATUS_RECEIVED].equals("TERMINATED")) {
            AlertDialog adDraw = new Builder(this).create();
            adDraw.setCancelable(false);
            adDraw.setMessage("Other Player Game Terminated");
            adDraw.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Game.this.game.getPlayer(Game.this.currentPlayer).firstplayer = Game.Player1;
                    Intent intent = new Intent(Game.this.getBaseContext(), MainScreen.class);
                    Game.this.gamereceiver.setFlag(Game.Player1);
                    Game.this.startActivity(intent);
                    intent.setFlags(131072);
                    Game.this.startActivity(intent);
                    Game.this.finish();
                    dialog.dismiss();
                }
            });
            adDraw.show();
        } else if (Game_CURRENT_STATUS == Game_STATUS_MOVE_WAITING) {
            Game_CURRENT_STATUS = Game_STATUS_MOVE_WAITING;
            updatetable(this.tablelocConvert.singleValue(words[Game_STATUS_ACEPTED]), Player1);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
        SmsManager.getDefault().sendTextMessage(this.game.getPlayer(Player2).phonenumber.toString(), null, "0978 : Tic-Tac-Toe : TERMINATED", null, null);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void enablecells(boolean cond) {
        for (int i = Player1; i < this.totalbox; i += Player2) {
            if (this.buttons[i].getTag().toString().equals("-1")) {
                this.buttons[i].setEnabled(cond);
            } else {
                this.buttons[i].setEnabled(false);
            }
        }
    }

    public void updatetable(int xlocation, int ylocation) {
        int i = Player2;
        this.buttons[xlocation].setEnabled(false);
        this.buttons[xlocation].setTag(Integer.valueOf(this.currentPlayer));
        this.game.getPlayer(this.currentPlayer).MarkCell(this.buttons[xlocation].index);
        enablecells(true);
        this.tvGameStatus.setText("Move Recerived");
        this.timeTracker.setStopTime(this.currentPlayer);
        this.textPlayer2Time.setText(this.game.getPlayer(this.currentPlayer).name + " : Total Time : " + this.timeTracker.getTotalTime(this.currentPlayer));
        CheckWiner();
        if (this.currentPlayer != 0) {
            i = Player1;
        }
        this.currentPlayer = i;
        this.tvCurrentPlayer.setText(this.game.getPlayer(this.currentPlayer).name);
        this.timeTracker.setStartTime();
    }

    public void CheckWiner() {
        int i;
        String Playername = this.game.getPlayer(this.currentPlayer).name;
        int cp = this.currentPlayer;
        int playercur = this.currentPlayer;
        for (i = Player1; i < this.totalbox; i += Player2) {
            this.playerinput[i] = ((Integer) this.buttons[i].getTag()).intValue();
        }
        if ((this.playerinput[Player1] == cp && this.playerinput[Player2] == cp && this.playerinput[Game_STATUS_RECEIVED] == cp && this.playerinput[Game_STATUS_ACEPTED] == cp) || ((this.playerinput[Game_STATUS_DECLIED] == cp && this.playerinput[Game_STATUS_PlAYER1] == cp && this.playerinput[Game_STATUS_TERMINATED] == cp && this.playerinput[Game_STATUS_REQUEST_WAITING] == cp) || ((this.playerinput[Game_STATUS_PlAYER2] == cp && this.playerinput[Game_STATUS_MOVE_WAITING] == cp && this.playerinput[10] == cp && this.playerinput[11] == cp) || ((this.playerinput[12] == cp && this.playerinput[13] == cp && this.playerinput[14] == cp && this.playerinput[15] == cp) || ((this.playerinput[Player1] == cp && this.playerinput[Game_STATUS_DECLIED] == cp && this.playerinput[Game_STATUS_PlAYER2] == cp && this.playerinput[12] == cp) || ((this.playerinput[Player2] == cp && this.playerinput[Game_STATUS_PlAYER1] == cp && this.playerinput[Game_STATUS_MOVE_WAITING] == cp && this.playerinput[13] == cp) || ((this.playerinput[Game_STATUS_RECEIVED] == cp && this.playerinput[Game_STATUS_TERMINATED] == cp && this.playerinput[10] == cp && this.playerinput[14] == cp) || ((this.playerinput[Game_STATUS_ACEPTED] == cp && this.playerinput[Game_STATUS_REQUEST_WAITING] == cp && this.playerinput[11] == cp && this.playerinput[15] == cp) || ((this.playerinput[Player1] == cp && this.playerinput[Game_STATUS_PlAYER1] == cp && this.playerinput[10] == cp && this.playerinput[15] == cp) || (this.playerinput[Game_STATUS_ACEPTED] == cp && this.playerinput[Game_STATUS_TERMINATED] == cp && this.playerinput[Game_STATUS_MOVE_WAITING] == cp && this.playerinput[12] == cp)))))))))) {
            Playername = this.game.getPlayer(this.currentPlayer).name;
            this.tvGameStatus.setText(Playername + " : winner");
            AlertDialog ad = new Builder(this).create();
            ad.setCancelable(false);
            ad.setMessage("Congratulation " + Playername + ": Winner");
            ad.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Game.this.game.getPlayer(Game.this.currentPlayer).firstplayer = Game.Player1;
                    Intent intent = new Intent(Game.this.getBaseContext(), MainScreen.class);
                    Game.this.gamereceiver.setFlag(Game.Player1);
                    Game.this.startActivity(intent);
                    intent.setFlags(131072);
                    Game.this.startActivity(intent);
                    Game.this.finish();
                    dialog.dismiss();
                }
            });
            ad.show();
            for (i = Player1; i < this.totalbox; i += Player2) {
                this.buttons[i].setEnabled(false);
            }
        }
        if ((((((((((((((((this.playerinput[Player1] | this.playerinput[Player2]) | this.playerinput[Game_STATUS_RECEIVED]) | this.playerinput[Game_STATUS_ACEPTED]) | this.playerinput[Game_STATUS_DECLIED]) | this.playerinput[Game_STATUS_PlAYER1]) | this.playerinput[Game_STATUS_TERMINATED]) | this.playerinput[Game_STATUS_REQUEST_WAITING]) | this.playerinput[Game_STATUS_PlAYER2]) | this.playerinput[Game_STATUS_MOVE_WAITING]) | this.playerinput[10]) | this.playerinput[11]) | this.playerinput[12]) | this.playerinput[13]) | this.playerinput[14]) | this.playerinput[15]) != -1) {
            this.tvGameStatus.setText("Game Draw");
            AlertDialog adDraw = new Builder(this).create();
            adDraw.setCancelable(false);
            adDraw.setMessage("Game Draw");
            adDraw.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Game.this.game.getPlayer(Game.this.currentPlayer).firstplayer = Game.Player1;
                    Intent intent = new Intent(Game.this.getBaseContext(), MainScreen.class);
                    Game.this.gamereceiver.setFlag(Game.Player1);
                    Game.this.startActivity(intent);
                    intent.setFlags(131072);
                    Game.this.startActivity(intent);
                    Game.this.finish();
                    dialog.dismiss();
                }
            });
            adDraw.show();
        }
    }
}
