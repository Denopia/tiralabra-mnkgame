package tiralabra.tiralabra.mnkgameai;

import java.util.Scanner;

/**
 * Ristinollapeliluokka. Huolehtii pelin etenemisesta.
 *
 */
public class Game {

    private static char[] alph = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't'};

    private Player player1;
    private Player player2;
    private int firstPlayer;
    private int[][] gameBoard;
    private int turn;
    private int wincon;
    private int lastMove1[];
    private int lastMove2[];
    private Scanner scanner;
    private int availableMoves;

    /**
     * Konstruktori.
     */
    public Game() {
        this.scanner = new Scanner(System.in);
        this.lastMove1 = new int[2];
        this.lastMove2 = new int[2];
        this.lastMove1[0] = -1;
        this.lastMove1[1] = -1;
        this.lastMove2[0] = -1;
        this.lastMove2[1] = -1;
    }

    /**
     * Kysyy kayttajalta peliasetukset.
     */
    void askSettings() {
        askPlayers();
        askBoardSize();
    }

    /**
     * Kysyy kayttajalta minka kokoisella ruudukolla pelia pelataan. (Talla
     * hetkella kayttaa vain oletusasetuksia.)
     */
    private void askBoardSize() {
        int rows = 10;
        int cols = 10;
        int wincon = 5;
        int gb = askANumber("\nValitse ruudukon koko ja voittosuoran pituus\n 1) 3x3    3\n 2) 6x4    4\n 3) 5x5    5\n 4) 9x9    5\n 5) 10x10  5\n 6) 15x15  5", 6);
        switch (gb) {
            case 1:
                rows = 3;
                cols = 3;
                wincon = 3;
                break;
            case 2:
                rows = 6;
                cols = 4;
                wincon = 4;
                break;
            case 3:
                rows = 5;
                cols = 5;
                wincon = 5;
                break;
            case 4:
                rows = 9;
                cols = 9;
                wincon = 5;
                break;
            case 5:
                rows = 10;
                cols = 10;
                wincon = 5;
                break;
            case 6:
                rows = 15;
                cols = 15;
                wincon = 5;
        }
        this.setGameBoard(new int[rows][cols]);
        this.setWincon(wincon);
        this.setAvailableMoves(rows * cols);
    }

    private int askANumber(String message, int options) {
        System.out.println(message);
        String ans = "valinta";
        int nunbero = -1;
        while (true) {
            ans = scanner.nextLine();
            try {
                nunbero = Integer.parseInt(ans);
            } catch (NumberFormatException e) {

            }
            if (nunbero < 0 || nunbero > options) {
                System.out.println("Yritä valita yksi esitetyistä vaihtoehdoista");
                continue;
            }
            break;
        }
        return nunbero;
    }

    /**
     * Kysyy kayttajalta minkalaiset pelaajat pelaavat pelia. (Talla hetkella
     * kayttaa vain oletusasetuksia.)
     */
    private void askPlayers() {
        AI ai = new AI(alph);
        int gm = askANumber("\nValitse pelimuoto\n 1) Ihminen vastaan tekoäly\n 2) Tekoäly vastaan tekoäly", 2);
        if (gm == 1) {
            this.setPlayer1(new Player(1, 'o', 'O', Player.Type.HUMAN, ai));
            this.setPlayer2(new Player(2, 'x', 'X', Player.Type.AI, ai));
            System.out.println("\nPelaaja 1: Ihminen O");
            System.out.println("Pelaaja 2: Tekoäly X");
            int fp = askANumber("\nValitse pelin aloittaja\n 1) Pelaaja 1\n 2) Pelaaja 2", 2);
            if (fp == 1) {
                this.setTurn(1);
                this.setFirstPlayer(1);
            } else {
                this.setTurn(2);
                this.setFirstPlayer(2);
            }
        } else {
            this.setPlayer1(new Player(1, 'o', 'O', Player.Type.AI, ai));
            this.setPlayer2(new Player(2, 'x', 'X', Player.Type.AI, ai));
            this.setTurn(1);
            this.setFirstPlayer(1);
        }
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
     * Lisaa peliruudukkoon uuden merkin.
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
        if (p == 1) {
            this.lastMove1[0] = row;
            this.lastMove1[1] = col;
        }
        if (p == 2) {
            this.lastMove2[0] = row;
            this.lastMove2[1] = col;
        }
        this.setAvailableMoves(this.getAvailableMoves() - 1);
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
        int column = -1;
        try {
            column = Integer.parseInt(rowcolumn[1]) - 1;
        } catch (NumberFormatException e) {
            return false;
        }
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
     * Palauttaa pelin voittajan.
     *
     * @return 1 jos pelaaja 1 voittaa, 2 jos pelaaja 2 voittaa ja 0 jos
     * tasapeli
     */
    public int getWinner() {
        int[][] lines = GameStateChecker.playersLines(this.gameBoard, this.wincon);
        if (lines[1][wincon] > 0) {
            return 1;
        }
        if (lines[2][wincon] > 0) {
            return 2;
        }
        return 0;
    }

    /**
     * Pelin loppumisen tarkastus
     *
     * @return true jos peli loppuu, false jos ei
     */
    public boolean gameOver() {
        return GameStateChecker.checkGameOver(this.gameBoard, this.wincon) || this.getAvailableMoves() == 0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(int[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getWincon() {
        return wincon;
    }

    public void setWincon(int wincon) {
        this.wincon = wincon;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public static char[] getAlph() {
        return alph;
    }

    public static void setAlph(char[] aAlph) {
        alph = aAlph;
    }

    public int getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(int firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public int[] getLastMove1() {
        return lastMove1;
    }

    public void setLastMove1(int[] lastMove) {
        this.lastMove1 = lastMove;
    }

    public int[] getLastMove2() {
        return lastMove2;
    }

    public void setLastMove2(int[] lastMove2) {
        this.lastMove2 = lastMove2;
    }

    public int getAvailableMoves() {
        return availableMoves;
    }

    public void setAvailableMoves(int availableMoves) {
        this.availableMoves = availableMoves;
    }
}
