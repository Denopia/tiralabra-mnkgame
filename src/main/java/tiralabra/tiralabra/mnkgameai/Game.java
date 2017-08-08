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
    private Square[][] squares;
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
     * Asettaa annetun ruudukon pelin ruudukoksi.
     *
     * @param gb Ruudukko
     */
    public void setGameBoard(char[][] gb) {
        this.gameBoard = gb;
    }

    /**
     * Asettaa vuoron annetulle pelaajalle.
     *
     * @param turn Pelaaja, jolle annetaan vuoro
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * Asettaa annetun luvun voittoon johtavan suoran pituudeksi.
     *
     * @param wincon Voittosuoran pituus
     */
    public void setWinCon(int wincon) {
        this.wincon = wincon;
    }

    /**
     * Asettaa annetun ruudun viimeisimmaksi siirtoruuduksi.
     *
     * @param move Ruutu johon viimeisin siirto on tehty
     */
    public void setLatestMove(int[] move) {
        this.latestMove = move;
    }

    /**
     * Palauttaa vuorossa olevan pelaajan
     *
     * @return Vuorossa oleva pelaaja
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * Asettaa annetun pelaajan pelaajaksi 1
     *
     * @param player Pelaaja 1
     */
    public void setPlayer1(Player player) {
        this.player1 = player;
    }

    /**
     * Asettaa annetun pelaajan pelaajaksi 2
     *
     * @param player Pelaaja 2
     */
    public void setPlayer2(Player player) {
        this.player2 = player;
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
        this.squares[row][column].newSymbol(symbol, this.squares);
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
        this.squares = new Square[15][15];
        for (int r = 0; r < this.gameBoard.length; r++) {
            for (int c = 0; c < gameBoard[0].length; c++) {
                this.gameBoard[r][c] = ' ';
                Square s = new Square(r, c, ' ');
                squares[r][c] = s;
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
        int bestrow = 1;
        for (int row = 0; row < squares.length; row++) {
            for (int column = 0; column < squares[0].length; column++) {
                Square s = squares[row][column];
                bestrow = Math.max(bestrow, s.getSameSymbolN());
                bestrow = Math.max(bestrow, s.getSameSymbolNW());
                bestrow = Math.max(bestrow, s.getSameSymbolW());
                bestrow = Math.max(bestrow, s.getSameSymbolSW());
                int c = column + 1;
                System.out.println("RUUTU " + alph[row] + "-" + c + " N:" + s.getSameSymbolN() + " NW:" + s.getSameSymbolNW() + " W:" + s.getSameSymbolW() + " SW:" + s.getSameSymbolSW());
                if (s.getSameSymbolN() >= wincon - 1
                        || s.getSameSymbolNW() >= wincon - 1
                        || s.getSameSymbolW() >= wincon - 1
                        || s.getSameSymbolSW() >= wincon - 1) {
                    return true;
                }
            }
        }
        /*return checkDiaTopLeftToBottomRight()
                || checkDiaBottomLeftToTopRight()
                || checkHorMidLeftToMidRight()
                || checkVerTopMidToBottomMid();*/
        System.out.println("PISIN RIVI TÄLLÄ HETKELLÄ ON " + bestrow + " MERKIN MITTAINEN");
        return false;
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

    /**
     * @return the squares
     */
    public Square[][] getSquares() {
        return squares;
    }

    /**
     * @param squares the squares to set
     */
    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

}
