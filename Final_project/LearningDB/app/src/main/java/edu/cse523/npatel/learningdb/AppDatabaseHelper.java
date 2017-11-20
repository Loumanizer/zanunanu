package edu.cse523.npatel.learningdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by npatel on 11/13/17.
 */

public class AppDatabaseHelper extends SQLiteOpenHelper {
    Context context = null;
    public static final String DATABASE_NAME = "game.db";
    public static final String PLAYERS_TABLE_NAME = "players";

    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    public AppDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( "create table players " +
                        "(id integer primary key autoincrement, firstName text not null, lastName text not null)"
		);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(sqLiteDatabase);
    }

    // Create CRUD
    public ArrayList<String> getAllPlayers(){
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from players", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            String id = res.getString(0);
            String firstName = res.getString(1);
            String lastName = res.getString(2);
            array_list.add(id + ": " + firstName + ", " + lastName);
            res.moveToNext();
        }
        return array_list;

    }
    public boolean createPlayer(String first_name, String last_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstName", first_name);
        contentValues.put("lastName", last_name);
        db.insert("players", null, contentValues);
        return true;

    }
    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PLAYERS_TABLE_NAME);
        return numRows;

    }
    public boolean updatePlayer(int id, String first_name, String last_name){
        SQLiteDatabase db = 	this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(); 	contentValues.put("firstName", first_name);
        contentValues.put("lastName", last_name);
        db.update("players", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public int deletePlayer(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("players", "id = ? ", new String[] { Integer.toString(id) });
    }
}
