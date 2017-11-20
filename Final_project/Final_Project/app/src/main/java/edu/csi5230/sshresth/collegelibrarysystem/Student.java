package edu.csi5230.sshresth.collegelibrarysystem;

/**
 * Created by molly on 11/18/17.
 */

public class Student {
    public String getStdtName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getStdPhNumber() {
        return stdPhNumber;
    }

    public void setstdPhNumber(String stdPhNumber) {
        this.stdPhNumber = stdPhNumber;
    }

    public String getStdImage() {
        return stdPicture;
    }

    public void setStdImage(String stdPicture) {
        this.stdPicture = stdPicture;
    }

    private String stdName = null;
    private String stdPhNumber = null;
    private String  stdPicture = null;

    public Student(String stdName, String stdPhNumber, String stdPicture) {
        this.stdName = stdName;
        this.stdPhNumber = stdPhNumber;
        this.stdPicture = stdPicture;
    }
}
