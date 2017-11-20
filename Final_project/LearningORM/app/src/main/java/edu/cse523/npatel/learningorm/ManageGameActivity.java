package edu.cse523.npatel.learningorm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.ArrayList;
import java.util.List;

public class ManageGameActivity extends AppCompatActivity {
    EditText gameName = null, weight = null;
    Button addButton = null, updateButton = null, deleteButton = null;
    ListView listView = null;
    ArrayAdapter<Game> adapter;
    List<Game> games = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_game);

        gameName = findViewById(R.id.editTextGame);
        weight = findViewById(R.id.editTextWeight);
        addButton = findViewById(R.id.btnRunPlayer);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        listView = findViewById(R.id.lstGameView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String game_name = gameName.getText().toString();
                double game_weight = Double.parseDouble(weight.getText().toString());

                Game game = new Game(game_name, game_weight);
                game.save();
                updateList();
                clearForm();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = ((TextView) view).getText().toString();
                String[] split = item.split(":");
                int id = Integer.parseInt(split[0]);
                split = split[1].split(",");
                split[0].trim();
                split[1].trim();
                gameName.setText(split[0]);
                weight.setText(split[1]);
                gameName.setTag(id);
                deleteButton.setEnabled(true);
                updateButton.setEnabled(true);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String game_name = gameName.getText().toString();
                double game_weight = Double.parseDouble(weight.getText().toString());
                int id = (int) gameName.getTag();

                // Update record
                new Update(Game.class)
                        .set("game_name = ?, weight = ?", game_name, game_weight)
                        .where("id = ?", id)
                        .execute();

                updateList();
                clearForm();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) gameName.getTag();

                // delete the record
                Select query = new Select();
                Game game = query.from(Game.class)
                        .where("id = ?", id)
                        .executeSingle();
                game.delete();
                updateList();
                clearForm();
            }
        });
        updateList();
    }

    private void updateList()
    {
        // add it to adapter
        games = Game.getAllGames();
        adapter = new ArrayAdapter<Game>(this, android.R.layout.simple_list_item_1, games);
        listView.setAdapter(adapter);
    }

    private void clearForm()
    {
        gameName.setTag(-1);
        gameName.setText("");
        weight.setText("");
        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);
    }

}
