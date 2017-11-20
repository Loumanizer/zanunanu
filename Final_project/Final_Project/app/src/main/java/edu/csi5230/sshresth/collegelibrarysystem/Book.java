package edu.csi5230.sshresth.collegelibrarysystem;

import java.sql.Date;

/**
 * Created by molly on 11/18/17.
 */

public class Book {
    public String getBooktName() {
        return BookName;
    }

    public void setBookName(String BookName) {
        this.BookName = BookName;
    }

    public String getDueDate() {
        return duedate;
    }

    public void setDueDate(String duedate) {
        this.duedate = duedate;
    }

    private String BookName = null;
    private String duedate = null;


    public Book(String BookName, String duedate) {
        this.BookName = BookName;
        this.duedate = duedate;
    }
}