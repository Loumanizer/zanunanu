package molly.shrestha.edu.oakland.tictactoe;

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

public class Game extends AppCompatActivity {
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
            String msg = "%$$^ : Tic-Tac-Toe : SELECTED : " + tablelocConvert.toSting();
            Game_CURRENT_STATUS = Game_STATUS_MOVE_WAITING;
            CheckWiner();
            tvGameStatus.setText("Move Send");
            Game game = Game.this;
            if (Game.this.currentPlayer == 0) {
                i = Game.Player2;
            }
            game.currentPlayer = i;
            Game.this.tvCurrentPlayer.setText(Game.this.game.getPlayer(Game.this.currentPlayer).name);
            SmsManager.getDefault().sendTextMessage(Game.this.game.getPlayer(Game.this.currentPlayer).phonenumber, null, msg, null, null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.tv = (TextView) findViewById(R.id.textView);
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
                int bContainerWidth = Game.this.buttonContainer.getWidth();
                int bContainerHeight = Game.this.buttonContainer.getHeight();
                for (int i = Game.Player1; i < Game.this.totalbox; i += Game.Player2) {
                    LayoutParams params = (LayoutParams) Game.this.buttons[i].getLayoutParams();
                    params.rightMargin = 5;
                    params.leftMargin = 5;
                    params.topMargin = 5;
                    params.bottomMargin = 5;
                    params.width = ((bContainerWidth / 3) - params.rightMargin) - params.leftMargin;
                    params.height = ((bContainerHeight / 3) - params.topMargin) - params.bottomMargin;
                    Game.this.buttons[i].setLayoutParams(params);
                }
                ViewTreeObserver vto = Game.this.buttonContainer.getViewTreeObserver();
            }
        });
        if (this.game.getPlayer(Player1).firstplayer == Player2) {
            this.currentPlayer = Player1;
            enablecells(true);

        } else {
            this.currentPlayer = Player2;
            enablecells(false);
        }
        this.tvCurrentPlayer.setText(this.game.getPlayer(this.currentPlayer).name);
    }

    public void UpdateGameTable(String msgString) {
        String[] words = msgString.split(" : ");
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
        SmsManager.getDefault().sendTextMessage(this.game.getPlayer(Player2).phonenumber.toString(), null, "%$$^ : Tic-Tac-Toe : TERMINATED", null, null);
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
        this.buttons[xlocation].setEnabled(false);
        this.buttons[xlocation].setTag(Integer.valueOf(this.currentPlayer));
        this.game.getPlayer(this.currentPlayer).MarkCell(this.buttons[xlocation].index);
        Game.this.enablecells(true);
        tvGameStatus.setText("Move Recerived");
        CheckWiner();
        if (this.currentPlayer != 0) {
            i = Player1;
        }
        this.currentPlayer = i;
        this.tvCurrentPlayer.setText(this.game.getPlayer(this.currentPlayer).name);
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
                }
            });
            ad.show();
            for (i = Player1; i < this.totalbox; i += Player2) {
                this.buttons[i].setEnabled(false);
            }
        }
        if (((((((((((playerinput[0] | this.playerinput[1]) |
                playerinput[2]) | playerinput[3]) | playerinput[4]) | playerinput[5]) |
                playerinput[6]) | playerinput[7]) | playerinput[8]) ) != -1)) {
            this.tvGameStatus.setText("Game Draw");


            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Game Draw");
            alertDialog.setMessage("Please send invitation for next Game");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Game.this.game.getPlayer(Game.this.currentPlayer).firstplayer = Game.Player1;
                            Intent intent = new Intent(Game.this.getBaseContext(), MainScreen.class);
                            Game.this.gamereceiver.setFlag(Game.Player1);
                            Game.this.startActivity(intent);
                        }
                    });
            alertDialog.show();
        }
    }
}
