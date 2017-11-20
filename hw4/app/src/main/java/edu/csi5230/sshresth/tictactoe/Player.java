package edu.csi5230.sshresth.tictactoe;

/**
 * Created by shova on 11/11/2017.
 */

public class Player {
    public DataCell[] cells = new DataCell[9];
    int firstPlayer = -1;
    String name = null;
    String phoneNumber = null;
    int symbol = -1;

    public Player() {
        //this.playerName = playerName;
        for (int i = 0; i< 9; i++){
            this.cells[i] = new DataCell();
        }

    }

    void MarkCell(int _index)
    {
        this.cells[_index].setSymbol(this.symbol);
    }
    void register(Observer o, int _index){
        this.cells[_index].registerObserver(o);
    }
}
