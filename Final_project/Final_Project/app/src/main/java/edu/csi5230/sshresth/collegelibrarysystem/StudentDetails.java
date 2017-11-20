package edu.csi5230.sshresth.collegelibrarysystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StudentDetails extends AppCompatActivity {

    ListView lstbooklist = null;
    StringAdapter adapter = null;
    BooksAdapter bookAdapter = null;

    Button btnback = null;
    Button btnupdate = null;
    TextView txtstdName = null;
    TextView txtstdPhnub = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        Intent intent = getIntent();

        btnback = (Button)findViewById(R.id.btnBack);
        lstbooklist = (ListView) findViewById(R.id.lstBooklist);
        txtstdName = (TextView) findViewById(R.id.txtName);
        txtstdPhnub = (TextView)findViewById(R.id.txtPhone);

        txtstdName.setText(intent.getStringExtra(ListofStudents.EXTRA_STD_NAME));
        txtstdPhnub.setText(intent.getStringExtra(ListofStudents.EXTRA_STD_PH_NUM));
        //txtPhnub = intent.getStringExtra(ListofStudents.EXTRA_STD_PICTURE_LOC);


        adapter = new StringAdapter(this);
        bookAdapter = new BooksAdapter();

        lstbooklist.setAdapter(adapter);
        lstbooklist.setAdapter(bookAdapter);

        bookAdapter.addBook("Book Name 1", "11/25/2017");
        bookAdapter.addBook("Book Name 2", "11/25/2017");

        lstbooklist.setAdapter(bookAdapter);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentDetails.this, ListofStudents.class);
                startActivity(i);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentDetails.this, ListofStudents.class);
                startActivity(i);
            }
        });
    }


    public void RemoveBookfromList(View view){
        View parentRow = (View) view.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        bookAdapter.remove(position);
        listView.setAdapter((bookAdapter));
    }

    public void EditBookfromList(View view){

    }

}
