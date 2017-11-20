package edu.csi5230.sshresth.collegelibrarysystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by molly on 11/19/17.
 */

public class LibraryDatabaseHelper extends SQLiteOpenHelper {
    Context context = null;
    public static final String DATABASE_NAME = "library.db";
    public static final String STUDENT_TABLE_NAME = "dbstudent";
    public static final String BOOK_TABLE_NAME = "dbBook";
    public static final String CHECKOUT_TABLE_NAME = "dbCheckOut";
    public static final int dbVERSION = 1;

    public LibraryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, dbVERSION);
    }

    public LibraryDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        onCreateStudent(sqLiteDatabase);
        onCreateBook(sqLiteDatabase);
        onCreateCheckout(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void onCreateStudent(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( "create table dbstudent " +
                "(id integer primary key autoincrement, Name text not null, PhNum text not null)"
        );
    }

    public void onCreateBook(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table dbBook " +
                "(id integer primary key autoincrement, BookName text not null)"
        );
    }

    public void onCreateCheckout(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table dbCheckOut " +
                "(id integer primary key autoincrement, stdId int not null, bookId int not null)"
        );
    }

    public void onUpgradeStudent(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
            onCreateStudent(sqLiteDatabase);
    }

    public void onUpgradeBook(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
        onCreateBook(sqLiteDatabase);
    }

    public void onUpgradeCheckout(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
        onCreateCheckout(sqLiteDatabase);
    }

    // Create CRUD
    public ArrayList<String> getAllStudent(){
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from dbstudent", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            String id = res.getString(0);
            String stdName = res.getString(1);
            String stdPhnumber = res.getString(2);
            array_list.add(id + ": " + stdName + ", " + stdPhnumber);
            res.moveToNext();
        }
        return array_list;

    }

    public ArrayList<String> getAllBook(){
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from dbBook", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            String id = res.getString(0);
            String bookName = res.getString(1);
            array_list.add(id + ": " + bookName);
            res.moveToNext();
        }
        return array_list;

    }


    public boolean createStudent(String name, String phnumber)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("PhNum", phnumber);
        db.insert("dbstudent", null, contentValues);
        return true;

    }

    public boolean createBook(String bookname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BookName", bookname);
        db.insert("dbBook", null, contentValues);
        return true;

    }

    public String getStudentName(int stdid){
        String stdname = null;
        SQLiteDatabase db=this.getReadableDatabase();
        //Cursor res = db.rawQuery( "select * from dbstudent where id = stdid", null );
        Cursor res =  db.rawQuery("select * from " + STUDENT_TABLE_NAME + " where " + stdid + "='" + stdid + "'" , null);

        while(res.isAfterLast() == false) {
            stdname = res.getString(1);
        }
        return stdname;
    }

    public String getStudentPhNumb(int stdid){
        String stdname = null;
        SQLiteDatabase db = this.getReadableDatabase();

        return stdname;
    }

    public int getStudentCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, STUDENT_TABLE_NAME);
        return numRows;

    }

    public int getBookCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, BOOK_TABLE_NAME);
        return numRows;

    }

    public boolean updateStudent(int id, String name, String phnumber){
        SQLiteDatabase db = 	this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(); 	contentValues.put("Name", name);
        contentValues.put("PhNum", phnumber);
        db.update("dbstudent", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public boolean updateBook(int id, String bookname){
        SQLiteDatabase db = 	this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(); 	contentValues.put("BookName", bookname);
        db.update("dbBook", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public int deleteStudent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("dbstudent", "id = ? ", new String[] { Integer.toString(id) });
    }

    public int deleteBook(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("dbBook", "id = ? ", new String[] { Integer.toString(id) });
    }
}
