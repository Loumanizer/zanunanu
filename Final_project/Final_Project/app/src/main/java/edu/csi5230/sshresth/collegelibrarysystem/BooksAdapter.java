package edu.csi5230.sshresth.collegelibrarysystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by molly on 11/18/17.
 */

public class BooksAdapter extends BaseAdapter {
    ArrayList<Book> books = new ArrayList<>();

    void addBook(String pBook, String pdueDate)
    {
        Book book = new Book(pBook, pdueDate);
        books.add(book);
    }

    public String getName(int i){return  books.get(i).getBooktName();}


    void updateBook(int i, String pBook, String pdueDate)
    {
        Book book = new Book(pBook, pdueDate);
        books.set(i, book);
    }

    void remove(int i) {
        books.remove(i);
    }

    @Override
    public int getCount() {
        return books.size();
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
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.activity_book_item,  null, false);

            TextView bookName = (TextView) layout.findViewById(R.id.txtBookName);
            TextView bookduedate = (TextView) layout.findViewById(R.id.txtDueDate);

            bookName.setText(books.get(i).getBooktName());
            bookduedate.setText(books.get(i).getDueDate().toString());

            return layout;
        }
        return view;
    }

    public void add(String s) {
    }
}
