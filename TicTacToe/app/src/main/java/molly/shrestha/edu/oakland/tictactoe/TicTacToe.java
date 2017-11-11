package molly.shrestha.edu.oakland.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TicTacToe extends AppCompatActivity {

    Button btCont = null;
    TextView tv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        btCont = (Button) findViewById(R.id.btContinue);
        tv = (TextView) findViewById(R.id.textView);
        btCont.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Main.class);
                //Intent i = new Intent(v.getContext(), Game.class);
                //i.setFlags(131072);
                v.getContext().startActivity(i);
            }
        });
    }

}
