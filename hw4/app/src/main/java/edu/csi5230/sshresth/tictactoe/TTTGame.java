package edu.csi5230.sshresth.tictactoe;

/**
 * Created by shova on 11/11/2017.
 */

public class TTTGame {
    private Player[] players = new Player[2];
    private static TTTGame _game = null;

    private TTTGame()
    {
        this.players[0] = new Player();
        this.players[1] = new Player();
    }
    public static TTTGame getAppData(){
        if (_game == null){
            _game = new TTTGame();
        }
        return _game;
    }
    public Player getPlayer(int id){
        return this.players[id];
    }
}
