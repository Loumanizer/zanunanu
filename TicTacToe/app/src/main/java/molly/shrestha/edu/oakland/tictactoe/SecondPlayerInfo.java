package molly.shrestha.edu.oakland.tictactoe;

import android.support.v7.app.AppCompatActivity;
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

public class SecondPlayerInfo extends AppCompatActivity {
    ImageButton[] b = new ImageButton[6];
    Button bSecondPlayer = null;
    EditText editText = null;
    int[] imageResources = new int[]{17301668, 17301546, 17301676, 17301545, 17301575, 17301544};
    ImageView imageView = null;
    Player player = null;

    class ImageButtonClickListener implements OnClickListener {
        ImageButtonClickListener() {
        }

        public void onClick(View v) {
            int resourceId = ((Integer) ((ImageButton) v).getTag()).intValue();
            SecondPlayerInfo.this.player.symbol = resourceId;
            SecondPlayerInfo.this.imageView.setImageResource(resourceId);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_player_info);
        this.bSecondPlayer = (Button) findViewById(R.id.bSecondPlayer);
        this.imageView = (ImageView) findViewById(R.id.imageView);
        this.editText = (EditText) findViewById(R.id.etSMSNum);
        this.player = TTTGame.getAppData().getPlayer(1);
        this.b[0] = (ImageButton) findViewById(R.id.b1);
        this.b[1] = (ImageButton) findViewById(R.id.b2);
        this.b[2] = (ImageButton) findViewById(R.id.b3);
        this.b[3] = (ImageButton) findViewById(R.id.b4);
        this.b[4] = (ImageButton) findViewById(R.id.b5);
        this.b[5] = (ImageButton) findViewById(R.id.b6);
        this.bSecondPlayer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String name = String.valueOf(SecondPlayerInfo.this.editText.getText());
                Intent i = new Intent();
                i.putExtra("name", name);
                i.putExtra("symbol", SecondPlayerInfo.this.player.symbol);
                SecondPlayerInfo.this.setResult(-1, i);
                SecondPlayerInfo.this.finish();
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
        //getMenuInflater().inflate(R.menu.secon_player_info, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //if (item.getItemId() == R.id.action_settings) {
         //   return true;
        //}
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
