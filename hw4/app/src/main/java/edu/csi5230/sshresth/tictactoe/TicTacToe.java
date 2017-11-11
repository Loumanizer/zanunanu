package edu.csi5230.sshresth.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TicTacToe extends AppCompatActivity {
    Button btnContinue = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(view.getContext(),SettingScreen.class);
                Intent i = new Intent(view.getContext(),GameScreen.class);
                view.getContext().startActivity(i);
            }
        });
    }
}
