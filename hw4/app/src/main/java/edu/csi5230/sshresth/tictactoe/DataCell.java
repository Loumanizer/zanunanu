package edu.csi5230.sshresth.tictactoe;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by shova on 11/11/2017.
 */
public class DataCell implements Observable {

    private ArrayList<Observer> listObserver = new ArrayList();
    private int symbol = -1;

    public void notifyListner(){
        Iterator iterator = this.listObserver.iterator();
        while (iterator.hasNext()){
            ((Observer) iterator.next()).update(this.symbol);
        }
    }
    public void registerObserver(Observer o){this.listObserver.add(o);}

    void setSymbol(int _symbol){
        this.symbol = _symbol;
        notifyListner();
    }
}
