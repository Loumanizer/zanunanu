package edu.cse523.npatel.learningorm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.ArrayList;
import java.util.List;

public class ManageRunsActivity extends AppCompatActivity {
    Spinner gameSpinner = null, playerSpinner = null;
    List<Game> games = null;
    List<Player> players = null;
    ArrayAdapter<Game> gameSpinnerAdapter = null;
    ArrayAdapter<Player> playeSpinnerAdapter = null;
    ListView lstRunViw = null;

    ArrayAdapter<Run> adapter;
    List<Run> run = new ArrayList<>();

    Button btnUpdRunPlyr = null;
    Button btndeleterun = null;
    Button btnaddplr = null;

    EditText edtrun = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_runs);

        btnUpdRunPlyr = (Button)findViewById(R.id.btnUpdateRunPlyr);
        btndeleterun = (Button)findViewById(R.id.deleteButton);
        btnaddplr = (Button)findViewById(R.id.btnaddplayerrun);
        edtrun = (EditText) findViewById(R.id.edtPlayerRun);
        lstRunViw = (ListView)findViewById(R.id.lstRunView);


        gameSpinner = findViewById(R.id.spinnerGame);
        playerSpinner = findViewById(R.id.spinnerPlayer);

        games = Game.getAllGames();
        gameSpinnerAdapter = new ArrayAdapter<Game>(this, android.R.layout.simple_list_item_1, games);
        gameSpinner.setAdapter(gameSpinnerAdapter);

        players = Player.getAllPlayers();
        playeSpinnerAdapter = new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1, players);
        playerSpinner.setAdapter(playeSpinnerAdapter);


        lstRunViw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = ((TextView) view).getText().toString();
                String[] split = item.split(":");
                int id = Integer.parseInt(split[0]);
                split = split[1].split(",");
                split[0].trim();
                split[1].trim();
                //playerSpinner.set(Integer.parseInt(split[0].trim()));
                //gameSpinner.setSelection(Integer.parseInt(split[1].trim()));
                edtrun.setText(split[2].trim());
                edtrun.setTag(id);

                btndeleterun.setEnabled(true);
                btnUpdRunPlyr.setEnabled(true);
            }
        });


        btndeleterun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) edtrun.getTag();

                // delete the record
                Select query = new Select();
                Run run = query.from(Run.class)
                        .where("id = ?", id)
                        .executeSingle();
                run.delete();
                updateList();
                clearForm();
            }
        });

        btnUpdRunPlyr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String item = playerSpinner.getSelectedItem().toString();
                String[] split = item.split(":");
                int plrid = Integer.parseInt(split[0]);

                item = gameSpinner.getSelectedItem().toString();
                split = item.split(":");
                int gameid = Integer.parseInt(split[0]);


                int id = (int) edtrun.getTag();

                // Update record
//                new Update(Run.class)
//                        .set("playerId = ?, game_id = ?, run = ?", plrid, gameid, edtrun.getText().toString())
//                        .where("id = ?", id)
//                        .execute();

                updateList();
                clearForm();
            }
        });

        btnaddplr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String item = playerSpinner.getSelectedItem().toString();
                String[] split = item.split(":");
                int plrid = Integer.parseInt(split[0]);

                item = gameSpinner.getSelectedItem().toString();
                split = item.split(":");
                int gameid = Integer.parseInt(split[0]);

                Run run = new Run(plrid, gameid, edtrun.getText().toString());
                //Run run = new Run(4, 7, "ajsdhf");
                run.save();
                updateList();
                clearForm();
            }
        });
        updateList();
    }

    private void updateList()
    {
        // add it to adapter
        run = Run.getAllRuns();
        adapter = new ArrayAdapter<Run>(this, android.R.layout.simple_list_item_1, run);
        lstRunViw.setAdapter(adapter);
    }

    private void clearForm()
    {
        edtrun.setTag(-1);
        edtrun.setText("");
        btndeleterun.setEnabled(false);
        btnUpdRunPlyr.setEnabled(false);
    }

}
