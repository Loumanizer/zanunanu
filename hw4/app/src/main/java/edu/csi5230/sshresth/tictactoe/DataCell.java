package edu.csi5230.sshresth.tictactoe;

import java.util.ArrayList;
import java.util.Iterator;

public class DataCell implements Observable {
    private ArrayList<Observer> listObserver = new ArrayList();
    private int symbol = -1;

    public void notifyListeners() {
        Iterator it = this.listObserver.iterator();
        while (it.hasNext()) {
            ((Observer) it.next()).update(this.symbol);
        }
    }

    public void registerObserver(Observer o) {
        this.listObserver.add(o);
    }

    void setSymbol(int _symbol) {
        this.symbol = _symbol;
        notifyListeners();
    }
}
