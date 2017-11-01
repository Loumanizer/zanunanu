package edu.csi5230.sshresth.assignment3;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.net.Uri;
import java.util.ArrayList;


public class ProductAdapter extends BaseAdapter {
    ArrayList<Product> products = new ArrayList<>();

    void addProduct(String pName, String pPrice, String pImage)
    {
        Product product = new Product(pName, pPrice, pImage);
        products.add(product);
    }

    public String getName(int i){return  products.get(i).getName();}
    public String getPrice(int i){return  products.get(i).getPrice();}
    public String getImage(int i){return  products.get(i).getImage();}

    void updateProduct(int i, String pName, String pPrice, String pImage)
    {
        Product product = new Product(pName, pPrice, pImage);
        products.set(i, product);
    }

   // public int getIndex() {return  products.get()}

    void remove(int i) {
        products.remove(i);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.product_item,  null, false);

            TextView name = (TextView) layout.findViewById(R.id.name);
            TextView price = (TextView) layout.findViewById(R.id.price);
            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);

            name.setText(products.get(i).getName());
            price.setText(products.get(i).getPrice());
            imageView.setImageURI(Uri.parse(products.get(i).getImage()));

            return layout;
        }
        return view;
    }

    public void add(String s) {
    }
}
