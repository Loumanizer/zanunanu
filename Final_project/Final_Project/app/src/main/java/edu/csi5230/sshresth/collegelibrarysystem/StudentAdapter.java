package edu.csi5230.sshresth.collegelibrarysystem;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by molly on 11/18/17.
 */

public class StudentAdapter extends BaseAdapter {
    ArrayList<Student> students = new ArrayList<>();

    void addStudent(String pName, String pPrice, String pImage)
    {
        Student product = new Student(pName, pPrice, pImage);
        students.add(product);
    }

    public String getStdName(int i){return  students.get(i).getStdtName();}
    public String getStdPhNum(int i){return  students.get(i).getStdPhNumber();}
    public String getStdPic(int i){return  students.get(i).getStdtName();}

    void updateStudent(int i, String pName, String pPhonenumb, String pImage)
    {
        Student product = new Student(pName, pPhonenumb, pImage);
        students.set(i, product);
    }

    // public int getIndex() {return  products.get()}

    void remove(int i) {
        students.remove(i);
    }

    @Override
    public int getCount() {
        return students.size();
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
            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.activity_student_item,  null, false);

            TextView stdName = (TextView) layout.findViewById(R.id.txtDueDate);
            TextView stdphNumb = (TextView) layout.findViewById(R.id.txtPhnumber);
            ImageView stdPicView = (ImageView) layout.findViewById(R.id.imgStdPic);

            stdName.setText(students.get(i).getStdtName());
            stdphNumb.setText(students.get(i).getStdPhNumber());
            stdPicView.setImageURI(Uri.parse(students.get(i).getStdImage()));

            return layout;
        }
        return view;
    }

    public void add(String s) {
    }
}
