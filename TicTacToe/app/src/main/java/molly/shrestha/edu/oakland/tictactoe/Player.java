package molly.shrestha.edu.oakland.tictactoe;

public class Player {
    DataCell[] cells = new DataCell[16];
    int firstplayer = -1;
    String name = null;
    String phonenumber = null;
    int symbol = -1;

    Player() {
        for (int i = 0; i < 16; i++) {
            this.cells[i] = new DataCell();
        }
    }

    void MarkCell(int _index) {
        this.cells[_index].setSymbol(this.symbol);
    }

    void register(Observer o, int _index) {
        this.cells[_index].registerObserver(o);
    }
}
