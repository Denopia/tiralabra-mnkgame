package tiralabra.tiralabra.mnkgameai;

import java.util.Scanner;

/**
 * Tama luokasta luotu peliolio on vastuussa pelitilanteen muistamisesta.
 *
 */
public class Game {

    /**
     * Aakkosia, joita kaytetaan rivien tunnistamiseen.
     */
    public static char[] alph = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o'};

    private Player player1;
    private Player player2;
    private char[][] gameBoard;
    private int turn;
    private int wincon;
    private final Scanner scanner;
    private int[] latestMove;

    /**
     * Konstruktori.
     */
    public Game() {
        this.scanner = new Scanner(System.in);
        this.latestMove = new int[]{-1, -1};
    }

    /**
     * Antaa kayttoon pelissa luotavan skanneriolion.
     *
     * @return Skanneri
     */
    public Scanner getScanner() {
        return this.scanner;
    }

    /**
     * Palauttaa peliruudukon.
     *
     * @return Taulukko peliruudukon merkeista
     */
    public char[][] getBoard() {
        return this.gameBoard;
    }

    /**
     * Lisaa peliruudukkoon merkin.
     *
     * @param symbol Pelimerkki X tai O
     * @param move Ruutu, johon merkki laitetaan
     */
    void newSymbol(char symbol, String move) {
        String[] rowcolumn = move.split("-");
        int column = Integer.parseInt(rowcolumn[1]) - 1;
        int row = 0;
        for (int i = 0; i < alph.length; i++) {
            if (rowcolumn[0].equals("" + alph[i])) {
                row = i;
                break;
            }
        }
        this.gameBoard[row][column] = symbol;
        this.latestMove[0] = row;
        this.latestMove[1] = column;
    }

    /**
     * Antaa vuorossa olevalle pelaajalle mahdollisuuden tehda siirron, minka
     * jalkeen vaihtaa vuorossa olevaa pelaajaa.
     */
    void nextMove() {
        if (turn == 1) {
            this.player1.makeMove(this);
            turn = 2;
        } else {
            this.player2.makeMove(this);
            turn = 1;
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
        this.gameBoard = new char[15][15];
        this.wincon = 5;
        for (int r = 0; r < this.gameBoard.length; r++) {
            for (int c = 0; c < gameBoard[0].length; c++) {
                this.gameBoard[r][c] = ' ';
            }
        }
    }

    /**
     * Kysyy kayttajalta minkalaiset pelaajat pelaavat pelia. (Talla hetkella
     * kayttaa vain oletusasetuksia.)
     */
    private void askPlayers() {
        //default player vs ai for now
        this.player1 = new Player(1, 'O', Player.Type.HUMAN);
        this.player2 = new Player(2, 'X', Player.Type.AI);
        this.turn = 1;
    }

    /**
     * Palauttaa pelaajan, joka on voittanut pelin. (Ei toteutettu oikein talla
     * hetkella.)
     *
     * @return Voittanut pelaaja
     */
    int winner() {
        return 999;
    }

    /**
     * Tarkastaa, etta tehtava siirto on sallittu.
     *
     * @param move Ruutu, johon siirto halutaan tehda
     * @return Totuusarvo siirron hyvaksyttavyydesta
     */
    boolean validMove(String move) {
        String[] rowcolumn = move.split("-");
        if (rowcolumn.length != 2) {
            return false;
        }
        int column = Integer.parseInt(rowcolumn[1]) - 1;
        int row = -1;
        for (int i = 0; i < alph.length; i++) {
            if (rowcolumn[0].equals("" + alph[i])) {
                row = i;
                break;
            }
        }
        if (row < 0 || row >= this.gameBoard.length) {
            return false;
        }
        if (column < 0 || column >= this.gameBoard[0].length) {
            return false;
        }
        if (gameBoard[row][column] != ' ') {
            return false;
        }
        return true;
    }

    /**
     * Tarkistaa onko peli paattynyt.
     *
     * @return True jos peli on paattynyt ja false jos ei ole
     */
    boolean gameOver() {
        return checkDiaTopLeftToBottomRight()
                || checkDiaBottomLeftToTopRight()
                || checkHorMidLeftToMidRight()
                || checkVerTopMidToBottomMid();
    }

    /**
     * Tarkkistaa onko viimeisin siirto johtanut pelin voittoon.
     *
     * @return True jos viimeisin lisatty merkki on osana voittoon johtavaa
     * suoraa ja false jos ei ole
     */
    private boolean checkDiaTopLeftToBottomRight() {
        int row = latestMove[0] - wincon;
        int col = latestMove[1] - wincon;
        char latestSymbol = ' ';
        int streak = 0;
        for (int i = 0; i < 2 * wincon - 1; i++) {
            row++;
            col++;
            if (row < 0 || col < 0) {
                continue;
            }
            if (row >= gameBoard.length || col >= gameBoard[0].length) {
                continue;
            }
            char sym = gameBoard[row][col];
            if (sym != ' ') {
                if (latestSymbol == sym) {
                    streak++;
                } else {
                    streak = 1;
                    latestSymbol = sym;
                }
            } else {
                streak = 0;
                latestSymbol = ' ';
            }
            if (streak == wincon) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tarkkistaa onko viimeisin siirto johtanut pelin voittoon.
     *
     * @return True jos viimeisin lisatty merkki on osana voittoon johtavaa
     * suoraa ja false jos ei ole
     */
    private boolean checkHorMidLeftToMidRight() {
        int row = latestMove[0];
        int col = latestMove[1] - wincon;
        char latestSymbol = ' ';
        int streak = 0;
        for (int i = 0; i < 2 * wincon - 1; i++) {
            col++;
            if (row < 0 || col < 0) {
                continue;
            }
            if (row >= gameBoard.length || col >= gameBoard[0].length) {
                continue;
            }
            char sym = gameBoard[row][col];
            if (sym != ' ') {
                if (latestSymbol == sym) {
                    streak++;
                } else {
                    streak = 1;
                    latestSymbol = sym;
                }
            } else {
                streak = 0;
                latestSymbol = ' ';
            }
            if (streak == wincon) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tarkkistaa onko viimeisin siirto johtanut pelin voittoon.
     *
     * @return True jos viimeisin lisatty merkki on osana voittoon johtavaa
     * suoraa ja false jos ei ole
     */
    private boolean checkVerTopMidToBottomMid() {
        int row = latestMove[0] - wincon;
        int col = latestMove[1];
        char latestSymbol = ' ';
        int streak = 0;
        for (int i = 0; i < 2 * wincon - 1; i++) {
            row++;
            if (row < 0 || col < 0) {
                continue;
            }
            if (row >= gameBoard.length || col >= gameBoard[0].length) {
                continue;
            }
            char sym = gameBoard[row][col];
            if (sym != ' ') {
                if (latestSymbol == sym) {
                    streak++;
                } else {
                    streak = 1;
                    latestSymbol = sym;
                }
            } else {
                streak = 0;
                latestSymbol = ' ';
            }
            if (streak == wincon) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tarkkistaa onko viimeisin siirto johtanut pelin voittoon.
     *
     * @return True jos viimeisin lisatty merkki on osana voittoon johtavaa
     * suoraa ja false jos ei ole
     */
    private boolean checkDiaBottomLeftToTopRight() {
        int row = latestMove[0] + wincon;
        int col = latestMove[1] - wincon;
        char latestSymbol = ' ';
        int streak = 0;
        for (int i = 0; i < 2 * wincon - 1; i++) {
            row--;
            col++;
            if (row < 0 || col < 0) {
                continue;
            }
            if (row >= gameBoard.length || col >= gameBoard[0].length) {
                continue;
            }
            char sym = gameBoard[row][col];
            if (sym != ' ') {
                if (latestSymbol == sym) {
                    streak++;
                } else {
                    streak = 1;
                    latestSymbol = sym;
                }
            } else {
                streak = 0;
                latestSymbol = ' ';
            }
            if (streak == wincon) {
                return true;
            }
        }
        return false;
    }

}
