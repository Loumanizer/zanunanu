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

public class ManagePlayerActivity extends AppCompatActivity {
    EditText firstName = null, lastName = null;
    Button addButton = null, updateButton = null, deleteButton = null;
    ListView listView = null;
    ArrayAdapter<Player> adapter;
    List<Player> players = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_player);

        firstName = findViewById(R.id.editTextGame);
        lastName = findViewById(R.id.editTextWeight);
        addButton = findViewById(R.id.btnRunPlayer);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        listView = findViewById(R.id.lstPlayerView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = firstName.getText().toString();
                String last_name = lastName.getText().toString();

                Player player = new Player(first_name, last_name);
                player.save();
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
                firstName.setText(split[0]);
                lastName.setText(split[1]);
                firstName.setTag(id);
                deleteButton.setEnabled(true);
                updateButton.setEnabled(true);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = firstName.getText().toString();
                String last_name = lastName.getText().toString();
                int id = (int) firstName.getTag();

                // Update record
                new Update(Player.class)
                        .set("first_name = ?, last_name = ?", first_name, last_name)
                        .where("id = ?", id)
                        .execute();

                updateList();
                clearForm();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) firstName.getTag();

                // delete the record
                Select query = new Select();
                Player player = query.from(Player.class)
                        .where("id = ?", id)
                        .executeSingle();
                player.delete();
                updateList();
                clearForm();
            }
        });
        updateList();
    }

    private void updateList()
    {

        // add it to adapter
        players = Player.getAllPlayers();
        adapter = new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1, players);
        listView.setAdapter(adapter);
    }

    private void clearForm()
    {
        firstName.setTag(-1);
        firstName.setText("");
        lastName.setText("");
        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);
    }
}
