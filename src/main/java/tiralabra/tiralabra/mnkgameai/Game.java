package tiralabra.tiralabra.mnkgameai;

import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;
    private char[][] gameBoard;
    private int turn;
    private final Scanner scanner;

    Game() {
        this.scanner = new Scanner(System.in);
    }

    public char[][] getBoard() {
        return this.gameBoard;
    }

    void newSymbol(char symbol, String move) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean gameOver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void nextMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void askSettings() {
        askBoardSize();
        askPlayers();
    }

    private void askBoardSize() {
        //default 15x15 for now
        this.gameBoard = new char[15][15];
        for (int r = 0; r < this.gameBoard.length; r++) {
            for (int c = 0; c < gameBoard[0].length; c++) {
                this.gameBoard[r][c] = ' ';
            }
        }
    }
    

    private void askPlayers() {
        //default player vs ai for now
        this.player1 = new Player(1, 'o', Player.Type.HUMAN);
        this.player2 = new Player(2, 'x', Player.Type.AI);
    }

    int winner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
