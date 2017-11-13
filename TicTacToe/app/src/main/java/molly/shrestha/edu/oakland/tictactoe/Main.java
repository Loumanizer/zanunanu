package molly.shrestha.edu.oakland.tictactoe;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    ImageButton[] b = new ImageButton[6];
    Button bSecondPlayer = null;
    EditText editText = null;
    int[] imageResources = new int[]{android.R.drawable.ic_btn_speak_now, android.R.drawable.ic_delete, android.R.drawable.ic_dialog_email, android.R.drawable.ic_lock_idle_lock, android.R.drawable.ic_lock_power_off, android.R.drawable.ic_menu_camera};
    ImageView imageView = null;
    Player player = null;

    class ImageButtonClickListener implements OnClickListener {
        ImageButtonClickListener() {}

        public void onClick(View v) {
            int resourceId = ((Integer) ((ImageButton) v).getTag()).intValue();
            player.symbol = resourceId;
            imageView.setImageResource(resourceId);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bSecondPlayer = (Button) findViewById(R.id.bSecondPlayer);
        this.imageView = (ImageView) findViewById(R.id.imageView);
        this.editText = (EditText) findViewById(R.id.etSMSNum);
        player = TTTGame.getAppData().getPlayer(0);
        b[0] = (ImageButton) findViewById(R.id.b1);
        b[1] = (ImageButton) findViewById(R.id.b2);
        b[2] = (ImageButton) findViewById(R.id.b3);
        b[3] = (ImageButton) findViewById(R.id.b4);
        b[4] = (ImageButton) findViewById(R.id.b5);
        b[5] = (ImageButton) findViewById(R.id.b6);
        this.bSecondPlayer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String name = String.valueOf(Main.this.editText.getText());
                Main.this.player.name = name;
                Main.this.player.phonenumber = null;
                Main.this.player.firstplayer = 0;
                if (name.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Player Name", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(v.getContext(), MainScreen.class);
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
