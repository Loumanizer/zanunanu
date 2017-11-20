package edu.csi5230.sshresth.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingScreen extends AppCompatActivity {
    Button btnNext = null;
    EditText editTextName = null;
    ImageView imgContainer = null;
    Player player = null;

    ImageButton [] btns = new ImageButton[6];
    int[] imageResources = new int[]{android.R.drawable.ic_btn_speak_now, android.R.drawable.ic_lock_lock, android.R.drawable.btn_star_big_on,
                            android.R.drawable.btn_star, android.R.drawable.sym_action_call
                            , android.R.drawable.presence_video_busy};

    ImageButtonClickListner buttonClickListner = new ImageButtonClickListner();


    public class ImageButtonClickListner implements View.OnClickListener{
        ImageButtonClickListner(){}

        @Override
        public void onClick(View v) {
            int resourceId = ((Integer) ((ImageButton) v).getTag()).intValue();
            SettingScreen.this.imgContainer.setImageResource(resourceId);
            SettingScreen.this.player.symbol = resourceId;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);

        btnNext = (Button) findViewById(R.id.btnNext);
        editTextName = (EditText) findViewById(R.id.editTextName);
        this.imgContainer =(ImageView) findViewById(R.id.imgContainer);
        player = TTTGame.getAppData().getPlayer(0);

        btns[0] = (ImageButton) findViewById(R.id.btn1);
        btns[1] = (ImageButton) findViewById(R.id.btn2);
        btns[2] = (ImageButton) findViewById(R.id.btn3);
        btns[3] = (ImageButton) findViewById(R.id.btn4);
        btns[4] = (ImageButton) findViewById(R.id.btn5);
        btns[5] = (ImageButton) findViewById(R.id.btn6);

        SettingScreen.this.imgContainer.setImageResource(imageResources[0]);
        SettingScreen.this.player.symbol = imageResources[0];

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(SettingScreen.this.editTextName.getText());
                 SettingScreen.this.player.name = name;
//                 SettingScreen.this.player.phoneNumber = null;
                 SettingScreen.this.player.firstPlayer = 0;

                if( name.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter your name", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent i = new Intent(v.getContext(), MainScreen.class);
                startActivity(i);
            }
        });
        for (int i = 0; i < 6; i++)
        {
            this.btns[i].setImageResource(this.imageResources[i]);
            this.btns[i].setTag(Integer.valueOf(this.imageResources[i]));
            this.btns[i].setOnClickListener(buttonClickListner);
        }
    }
   protected void onResume(){
        super.onResume();
        if (this.player.name != null){
            this.editTextName.setText(this.player.name);
        }
        if (this.player.symbol != -1){
            this.imgContainer.setImageResource(this.player.symbol);
        }
   }
}
