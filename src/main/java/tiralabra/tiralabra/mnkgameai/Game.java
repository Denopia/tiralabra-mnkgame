package tiralabra.tiralabra.mnkgameai;

import java.util.Scanner;

public class Game {

    private static char[] alph = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o'};

    private Player player1;
    private Player player2;
    private int[][] gameBoard;
    private int turn;
    private int wincon;
    private Scanner scanner;

    public Game() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Tekee peliin seuraavan siirron.
     */
    void nextMove() {
        if (getTurn() == 1) {
            this.getPlayer1().makeMove(this);
            setTurn(2);
        } else {
            this.getPlayer2().makeMove(this);
            setTurn(1);
        }
    }

    /**
     * Kysyy kayttajalta peliasetukset.
     */
    void askSettings() {
        askBoardSize();
        askPlayers();
    }

    /**
     * Kysyy kayttajalta minka kokoisella ruudukolla pelia pelataan. (Talla
     * hetkella kayttaa vain oletusasetuksia.)
     */
    private void askBoardSize() {
        //default 15x15 for now
        this.setGameBoard(new int[15][15]);
        this.setWincon(5);
    }

    /**
     * Kysyy kayttajalta minkalaiset pelaajat pelaavat pelia. (Talla hetkella
     * kayttaa vain oletusasetuksia.)
     */
    private void askPlayers() {
        //default player vs ai for now
        this.setPlayer1(new Player(1, 'o', Player.Type.HUMAN));
        this.setPlayer2(new Player(2, 'x', Player.Type.AI));
        this.setTurn(1);
    }

    /**
     * Tarkastaa etta siirto on laillinen
     *
     * @param move Tarkastettava siirto
     * @return true jos siirron saa tehda, false jos ei
     */
    boolean validMove(String move) {
        String[] rowcolumn = move.split("-");
        if (rowcolumn.length != 2) {
            return false;
        }
        int column = Integer.parseInt(rowcolumn[1]) - 1;
        int row = -1;
        for (int i = 0; i < getAlph().length; i++) {
            if (rowcolumn[0].equals("" + getAlph()[i])) {
                row = i;
                break;
            }
        }
        if (row < 0 || row >= this.getGameBoard().length) {
            return false;
        }
        if (column < 0 || column >= this.getGameBoard()[0].length) {
            return false;
        }
        if (getGameBoard()[row][column] != 0) {
            return false;
        }
        return true;
    }

    /**
     * @return the player1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * @param player1 the player1 to set
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    /**
     * @return the player2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * @param player2 the player2 to set
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * @return the gameBoard
     */
    public int[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * @param gameBoard the gameBoard to set
     */
    public void setGameBoard(int[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * @return the turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * @param turn the turn to set
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * @return the wincon
     */
    public int getWincon() {
        return wincon;
    }

    /**
     * @param wincon the wincon to set
     */
    public void setWincon(int wincon) {
        this.wincon = wincon;
    }

    /**
     * @return the scanner
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * @param scanner the scanner to set
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * @return the alph
     */
    public static char[] getAlph() {
        return alph;
    }

    /**
     * @param aAlph the alph to set
     */
    public static void setAlph(char[] aAlph) {
        alph = aAlph;
    }

    /**
     * Lisaa ruudukkoon uuden merkin.
     *
     * @param p Lisattava merkki
     * @param move Lisattavan merkin paikka
     */
    public void newSymbol(int p, String move) {
        int row = 0;
        int col = 0;
        String[] rowcol = move.split("-");
        for (int i = 0; i < alph.length; i++) {
            if (rowcol[0].equals("" + alph[i])) {
                row = i;
                break;
            }
        }
        col = Integer.parseInt(rowcol[1]) - 1;
        this.gameBoard[row][col] = p;
    }

    /**
     * Pelin loppumisen tarkastus
     *
     * @return true jos peli loppuu, false jos ei
     */
    public boolean gameOver() {
        return GameStateChecker.checkGameOver(this.gameBoard, this.wincon);
    }

}
