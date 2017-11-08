package molly.shrestha.edu.oakland.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Main extends AppCompatActivity {
    ImageButton[] b = new ImageButton[6];
    Button bSecondPlayer = null;
    EditText editText = null;
    int[] imageResources = new int[]{17301668, 17301546, 17301676, 17301545, 17301575, 17301536};
    ImageView imageView = null;
    Player player = null;

    class ImageButtonClickListener implements OnClickListener {
        ImageButtonClickListener() {
        }

        public void onClick(View v) {
            int resourceId = ((Integer) ((ImageButton) v).getTag()).intValue();
            Main.this.player.symbol = resourceId;
            Main.this.imageView.setImageResource(resourceId);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.bSecondPlayer = (Button) findViewById(R.id.bSecondPlayer);
        this.imageView = (ImageView) findViewById(R.id.imageView);
        this.editText = (EditText) findViewById(R.id.etSMSNum);
        this.player = TTTGame.getAppData().getPlayer(0);
        this.b[0] = (ImageButton) findViewById(R.id.b1);
        this.b[1] = (ImageButton) findViewById(R.id.b2);
        this.b[2] = (ImageButton) findViewById(R.id.b3);
        this.b[3] = (ImageButton) findViewById(R.id.b4);
        this.b[4] = (ImageButton) findViewById(R.id.b5);
        this.b[5] = (ImageButton) findViewById(R.id.b6);
        this.bSecondPlayer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String name = String.valueOf(Main.this.editText.getText());
                Main.this.player.name = name;
                Main.this.player.phonenumber = null;
                Main.this.player.firstplayer = 0;
                if (name.equals(BuildConfig.FLAVOR)) {
                    AlertDialog adDraw = new Builder(v.getContext()).create();
                    adDraw.setCancelable(false);
                    adDraw.setMessage("Please Enter you Name");
                    adDraw.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    adDraw.show();
                    return;
                }
                Intent i = new Intent(v.getContext(), MainScreen.class);
                i.setFlags(131072);
                v.getContext().startActivity(i);
            }
        });
        ImageButtonClickListener buttonClickListener = new ImageButtonClickListener();
        for (int i = 0; i < 6; i++) {
            this.b[i].setImageResource(this.imageResources[i]);
            this.b[i].setTag(Integer.valueOf(this.imageResources[i]));
            this.b[i].setOnClickListener(buttonClickListener);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {
        super.onResume();
        if (this.player.name != null) {
            this.editText.setText(this.player.name);
        }
        if (this.player.symbol != -1) {
            this.imageView.setImageResource(this.player.symbol);
        }
    }
}
