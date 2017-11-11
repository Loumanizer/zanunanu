package edu.csi5230.sshresth.tictactoe;

public class TTTGame {
    private static TTTGame _game = null;
    private Player[] players = new Player[2];

    private TTTGame() {
        this.players[0] = new Player();
        this.players[1] = new Player();
    }

    public static TTTGame getAppData() {
        if (_game == null) {
            _game = new TTTGame();
        }
        return _game;
    }

    public Player getPlayer(int id) {
        return this.players[id];
    }
}
