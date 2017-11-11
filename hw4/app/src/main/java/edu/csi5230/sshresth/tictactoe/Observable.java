package edu.csi5230.sshresth.tictactoe;

public interface Observable {
    void notifyListeners();

    void registerObserver(Observer observer);
}
