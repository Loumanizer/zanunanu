package edu.csi523.sdawood.assignment1;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main extends Applet {

    Panel topPanel = new Panel();
    Panel boardPanel = new Panel();
    Panel bottomPanel = new Panel();
    Label turnLabel = new Label();
    Button startButton = new Button();
    TTTButton[] tttButton = new TTTButton[9];

    Player[] players = new Player[2];
    Player currentPlayer = null;
    int currentTurn;

    void swapPlayer () {

        if (CheckForWinner()){
            turnLabel.setText(currentPlayer.getPlayerName() + " has won the game.");

            for (int i=0; i < 9 ; i++) {
                tttButton[i].setEnabled(false);
            }
        }
        else if (currentTurn > 8){turnLabel.setText("No Winner");}
        else {
            currentTurn++;
            currentPlayer = currentPlayer == players[0] ? players[1] : players[0];

            turnLabel.setText("Player: " + currentPlayer.getPlayerName());
        }

    }

    @Override
    public void init() {
        currentPlayer = players[0] = new Player("Michael Jackson", "X");
        players[1] = new Player("James Bond", "O");
        super.init();
        resize(250, 430);
        add(topPanel);
        add(boardPanel);
        add(bottomPanel);
        // Add top panel and its components
        topPanel.setPreferredSize(new Dimension(250,90));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(turnLabel);
        turnLabel.setText("Game is not started yet!!!");
        // Add Center Board
        boardPanel.setPreferredSize(new Dimension(250, 250));
        boardPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        for (int i = 0; i < 9; i++)
        {
            tttButton[i] = new TTTButton(i);
            players[0].cells[i] = new DataCell("");
            players[0].cells[i].addObserver(tttButton[i]);
            players[1].cells[i] = new DataCell("");
            players[1].cells[i].addObserver(tttButton[i]);
            tttButton[i].setPreferredSize(new Dimension(70,70));
            boardPanel.add(tttButton[i]);
            tttButton[i].setEnabled(false);
            tttButton[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    TTTButton source = (TTTButton)e.getSource();
                    int index = source.getIndex();
                    currentPlayer.cells[index].setValue(currentPlayer.getSymbol());
                    swapPlayer();
                    source.setEnabled(false);
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        //Add bottom Layout
        bottomPanel.setPreferredSize(new Dimension(250, 90));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        startButton.setLabel("Start Game");
        startButton.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                StartGame();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        bottomPanel.add(startButton);

    }

    public void StartGame() {
        turnLabel.setText("Player: " + currentPlayer.getPlayerName());

        for (int i=0; i<9; i++){
            //enable buttons and clear any value from last game
            tttButton[i].setLabel("");
            tttButton[i].setEnabled(true);
            //clear data cell values from previous game played
            players[0].cells[i].setValue("");
            players[1].cells[i].setValue("");

            currentTurn = 1;

        }

    }

    public boolean CheckForWinner() {

        String playerSymbol = currentPlayer.getSymbol();

        if (currentPlayer.cells[0].getValue().equals(playerSymbol) && currentPlayer.cells[1].getValue().equals(playerSymbol) && currentPlayer.cells[2].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[3].getValue().equals(playerSymbol)&& currentPlayer.cells[4].getValue().equals(playerSymbol) && currentPlayer.cells[5].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[6].getValue().equals(playerSymbol)&& currentPlayer.cells[7].getValue().equals(playerSymbol) && currentPlayer.cells[8].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[0].getValue().equals(playerSymbol)&& currentPlayer.cells[3].getValue().equals(playerSymbol) && currentPlayer.cells[6].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[1].getValue().equals(playerSymbol)&& currentPlayer.cells[4].getValue().equals(playerSymbol) && currentPlayer.cells[7].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[2].getValue().equals(playerSymbol)&& currentPlayer.cells[5].getValue().equals(playerSymbol) && currentPlayer.cells[8].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[0].getValue().equals(playerSymbol)&& currentPlayer.cells[4].getValue().equals(playerSymbol) && currentPlayer.cells[8].getValue().equals(playerSymbol))
            return true;
        if (currentPlayer.cells[2].getValue().equals(playerSymbol)&& currentPlayer.cells[4].getValue().equals(playerSymbol) && currentPlayer.cells[6].getValue().equals(playerSymbol))
            return true;

        return false;
    }

}
