package edu.csi5230.sshresth.tictactoe;

/**
 * Created by shova on 11/12/2017.
 */

public interface Observable {
    void notifyListner();

    void registerObserver(Observer observer);
}
