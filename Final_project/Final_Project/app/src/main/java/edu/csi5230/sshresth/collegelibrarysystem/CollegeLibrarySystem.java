package edu.csi5230.sshresth.collegelibrarysystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class CollegeLibrarySystem extends AppCompatActivity {
    Button btnLogIn = null;
    EditText txtusername = null;
    EditText txtpwrd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_college_library_system);
            txtusername = (EditText)findViewById(R.id.editLoginId);
            txtpwrd = (EditText)findViewById(R.id.editPwd);
            btnLogIn = (Button)findViewById(R.id.btnLogin);

            btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CollegeLibrarySystem.this, ListofStudents.class);
                startActivity(i);
            }
        });
    }

}
