package edu.csi5230.sshresth.collegelibrarysystem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListofStudents extends AppCompatActivity {
    public static final String EXTRA_STD_NAME = "STD_NAME";
    public static final String EXTRA_STD_PH_NUM = "STD_PH_NUM";
    public static final String EXTRA_STD_PICTURE_LOC = "STD_PICTURE_LOC";

    ListView lstStdlist = null;
    //StringAdapter adapter = null;
    StudentAdapter stdAdapter = null;


    ArrayList<String> alStudent = null;
    LibraryDatabaseHelper dblib = null;
    ArrayAdapter<String> adapter = null;


    Button btnAddtolist = null;

    int ADD_STD_TO_DB = 1;
    int EDIT_STD_TO_DB = 2;
    int index = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_students);

        lstStdlist = (ListView) findViewById(R.id.lstStudentItem);
        btnAddtolist = (Button) findViewById(R.id.btnAddStd);

        //adapter = new StringAdapter(this);
        //stdAdapter = new StudentAdapter();

        //lstStdlist.setAdapter(adapter);
        //lstStdlist.setAdapter(stdAdapter);

        //stdAdapter.addStudent("Pranab", "2484649775", "afasd");
        //stdAdapter.addStudent("Shova", "2482024666", "afasd");
        //productAdapter.addProduct("Iphone", "$890", String.valueOf(R.drawable.iphone));
        //productAdapter.addProduct("Camera", "$60", String.valueOf(R.drawable.nikon));

        //lstStdlist.setAdapter(stdAdapter);

        btnAddtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListofStudents.this, AddStudent.class);
                startActivityForResult(i, ADD_STD_TO_DB);
            }
        });
        dblib = new LibraryDatabaseHelper(this);
        alStudent = dblib.getAllStudent();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alStudent);
        lstStdlist.setAdapter(adapter);

        lstStdlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ListofStudents.this, StudentDetails.class);
                String testname = dblib.getStudentName(position);
                i.putExtra(EXTRA_STD_NAME, dblib.getStudentName(position));
                i.putExtra(EXTRA_STD_PH_NUM, dblib.getStudentPhNumb(position));

                startActivity(i);
            }
        });
    }


    public void RemoveStdfromList(View view){
        View parentRow = (View) view.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        stdAdapter.remove(position);
        listView.setAdapter((stdAdapter));
    }

    public void EditStdfromList(View view){

        View parentRow = (View) view.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        index = position;
        String tname, tprice;
        String tlocation;

        tname = stdAdapter.getStdName(index);
        tprice = stdAdapter.getStdPhNum(index);
        tlocation = stdAdapter.getStdPic(index);

        Intent i = new Intent(view.getContext(), AddStudent.class);

        i.putExtra(EXTRA_STD_NAME, tname);
        i.putExtra(EXTRA_STD_PH_NUM, tprice);
        i.putExtra(EXTRA_STD_PICTURE_LOC, tlocation);

        startActivityForResult(i, EDIT_STD_TO_DB);

    }

    public void InfoStdfromList(View view){


        View parentRow = (View) view.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        index = position;
        String tname, tprice;
        String tlocation;

        tname = stdAdapter.getStdName(index);
        tprice = stdAdapter.getStdPhNum(index);
        tlocation = stdAdapter.getStdPic(index);

        Intent i = new Intent(ListofStudents.this, StudentDetails.class);
        i.putExtra(EXTRA_STD_NAME, tname);
        i.putExtra(EXTRA_STD_PH_NUM, tprice);
        i.putExtra(EXTRA_STD_PICTURE_LOC, tlocation);

        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String name, price;
        String templocation;

        if(requestCode == ADD_STD_TO_DB)
            if(resultCode == Activity.RESULT_OK){
                String stdname = data.getStringExtra("NAME");
                String stdphnumb = data.getStringExtra("PhNumber");

                dblib.createStudent(stdname, stdphnumb);
                alStudent = dblib.getAllStudent();
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alStudent);
                lstStdlist.setAdapter(adapter);
            }


    }





}
