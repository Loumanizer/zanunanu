package edu.csi5230.sshresth.assignment3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_PRICE = "PRICE";
    public static final String EXTRA_LOCATION = "ImageLocation";


    ListView listView = null;
    StringAdapter adapter = null;
    ProductAdapter productAdapter = null;
    int clickCounter=0;
    int ADD_ITEM_OK = 1;
    int EDIT_ITEM_OK = 2;
    int index = 0;
    //ArrayList<String> listItems=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find listview by id
        listView = (ListView) findViewById(R.id.listView);
        adapter = new StringAdapter(this);
        productAdapter = new ProductAdapter();

        listView.setAdapter(adapter);
        listView.setAdapter(productAdapter);

        productAdapter.addProduct("Galaxy", "$600", String.valueOf(R.drawable.galaxy));
        productAdapter.addProduct("Iphone", "$890", String.valueOf(R.drawable.iphone));
        //productAdapter.addProduct("Camera", "$60", String.valueOf(R.drawable.nikon));

        listView.setAdapter(productAdapter);
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ViewGroup group = (ViewGroup) view;
                TextView textView = (TextView) group.getChildAt(0);

                String data = textView.getText().toString();
                Toast toast = Toast.makeText(view.getContext(), data, Toast.LENGTH_LONG);
                toast.show();
            }
        });
        */
    }
        public void addItems(View v) {
            Intent i = new Intent(v.getContext(), addItem.class);
            startActivityForResult(i, ADD_ITEM_OK);
        }
        public void editItems(View view){

            View parentRow = (View) view.getParent();
            ListView listView = (ListView) parentRow.getParent();
            final int position = listView.getPositionForView(parentRow);
            index = position;
            String tname, tprice;
            String tlocation;

            tname = productAdapter.getName(index);
            tprice = productAdapter.getPrice(index);
            tlocation = productAdapter.getImage(index);

            Intent i = new Intent(view.getContext(), addItem.class);

            i.putExtra(EXTRA_NAME, tname);
            i.putExtra(EXTRA_PRICE, tprice);
            i.putExtra(EXTRA_LOCATION, tlocation);

           //setResult(Activity.RESULT_OK, i);

            startActivityForResult(i, EDIT_ITEM_OK);
        }

        public void removeItem(View view){
            View parentRow = (View) view.getParent();
            ListView listView = (ListView) parentRow.getParent();
            final int position = listView.getPositionForView(parentRow);
            productAdapter.remove(position);
            listView.setAdapter((productAdapter));
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String name, price;
        String templocation;

        if(requestCode == ADD_ITEM_OK)
            if(resultCode == Activity.RESULT_OK){
                name = data.getStringExtra("NAME");
                price = data.getStringExtra("PRICE");
                templocation = data.getStringExtra("LOCATION");
                productAdapter.addProduct(name, price, templocation);
                listView.setAdapter(productAdapter);

            }

        if(requestCode == EDIT_ITEM_OK)
            if(resultCode == Activity.RESULT_OK){
                name = data.getStringExtra("NAME");
                price = data.getStringExtra("PRICE");
                templocation = data.getStringExtra("LOCATION");
                productAdapter.updateProduct(index, name, price, templocation);
                listView.setAdapter(productAdapter);

            }
    }
    }

