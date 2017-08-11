package tiralabra.tiralabra.mnkgameai;

import java.util.Random;

/**
 * Tama luokka vastuussa tekoalyn tekemista siirroista.
 *
 */
public class AI {

    private static char[] alph = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o'};

    static Random random = new Random();
    private String nextMove;
    private int points;

    /**
     * Konstruktori.
     */
    public AI() {
        this.nextMove = "";
        this.points = 0;
    }

    /**
     * Palauttaa tekoalyn paattaman siirron.
     *
     * @param player Vuorossa oleva pelaaja
     * @param opponent Vuorossa olevan pelaajan vastustaja
     * @param game Peli, johon siirto tehdaan
     * @return Ruutu, johon tekoaly haluaa laittaa merkkinsa
     */
    public String getNextMove(int player, int opponent, Game game) {
        decideNextMove(game.getGameBoard(), player, opponent, 1, game.getWincon());
        return this.nextMove;
    }

    /**
     * Paattaa tekoalyn tekeman siirron.
     *
     * @param gameBoard Peliruudukko
     * @param player Vuorossa oleva pelaaja
     * @param opponent Vuorossa olevan pelaajan vastustaja
     * @param depth Kuinka monta siirtoa eteenpain pelia tutkitaan
     * @param wincon Voittavan suoran pituus
     */
    private void decideNextMove(int[][] gameBoard, int player, int opponent, int depth, int wincon) {
        int[][] movepoints = new int[gameBoard.length][gameBoard[0].length];
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (gameBoard[row][col] != 0) {
                    movepoints[row][col] = 0;
                } else {
                    int[][] clone = cloneBoard(gameBoard);
                    clone[row][col] = player;
                    movepoints[row][col] = maxValue(clone, wincon, player, opponent, depth);
                }
            }
        }
        int bestValue = -9999998;
        int bestRow = -1;
        int bestCol = -1;
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if ((movepoints[row][col] > bestValue || bestValue == -9999998) && gameBoard[row][col] == 0 && notLonely(gameBoard, row, col)) {
                    bestValue = movepoints[row][col];
                    bestRow = row;
                    bestCol = col;
                }
            }
        }
        bestCol++;
        this.nextMove = alph[bestRow] + "-" + bestCol;
    }

    /**
     * Minmax-algoritmin max-osa. Tassa on viela jotain ongelmia.
     *
     * @param gameBoard Peliruudukko
     * @param wincon Voittavan suoran pituus
     * @param player Vuorossa oleva pelaaja
     * @param opponent Vuorossa olevan pelaajan vastustaja
     * @param depth Kuinka monta siirtoa eteenpain pelia tutkitaan
     * @return Siirron pistearvo
     */
    private int maxValue(int[][] gameBoard, int wincon, int player, int opponent, int depth) {
        int[][] lines = GameStateChecker.playersLines(gameBoard, wincon);
        if (winnerIsDecided(lines, wincon) || depth == 0) {
            return gameBoardValue(lines, player, opponent);
        }
        int value = -9999999;
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (gameBoard[row][col] != 0) {
                    continue;
                } else {
                    int[][] clone = cloneBoard(gameBoard);
                    clone[row][col] = opponent;
                    value = Math.max(value, minValue(clone, wincon, player, opponent, depth - 1));
                }
            }
        }
        return value;
    }

    /**
     * Minmax-algoritmin min-osa. Tassa on viela jotain ongelmia.
     *
     * @param gameBoard Peliruudukko
     * @param wincon Voittavan suoran pituus
     * @param player Vuorossa oleva pelaaja
     * @param opponent Vuorossa olevan pelaajan vastustaja
     * @param depth Kuinka monta siirtoa eteenpain pelia tutkitaan
     * @return Siirron pistearvo
     */
    private int minValue(int[][] gameBoard, int wincon, int player, int opponent, int depth) {
        int[][] lines = GameStateChecker.playersLines(gameBoard, wincon);
        if (winnerIsDecided(lines, wincon) || depth == 0) {
            return gameBoardValue(lines, player, opponent);
        }
        int value = 9999999;
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (gameBoard[row][col] != 0) {
                    continue;
                } else {
                    int[][] clone = cloneBoard(gameBoard);
                    clone[row][col] = player;
                    value = Math.min(value, maxValue(clone, wincon, player, opponent, depth - 1));
                }
            }
        }
        return value;
    }

    /**
     * Tarkastaa onko jollain pelaajalla voittava suora.
     *
     * @param lines Pelaajien tamanhetkiset suorat
     * @param wincon Voittavan suoran pituus
     * @return true jos on voittava suora ja false jos ei
     */
    private boolean winnerIsDecided(int[][] lines, int wincon) {
        if (lines[1][wincon] > 0 || lines[2][wincon] > 0) {
            return true;
        }
        return false;
    }

    /**
     * Laskee pelitilanteelle pistearvion. Tata pitaa viela parannella
     *
     * @param lines Pelaajien tamanhetkiset suorat
     * @param player Vuorossa oleva pelaaja
     * @param opponent Vuorossa olevan pelaajan vastustaja
     * @return Pelitilanteen pistearvio
     */
    private int gameBoardValue(int[][] lines, int player, int opponent) {
        int value = 0;
        int m = 1;
        for (int i = 1; i <= lines.length; i++) {
            value += lines[player][i] * m;
            m = 10 * m;
        }
        m = 1;
        for (int i = 1; i <= lines.length; i++) {
            value -= lines[opponent][i] * m;
            m = 10 * m;
        }
        if (lines[player][lines.length - 1] > 0) {
            value = 9999999;
        }
        if (lines[opponent][lines.length - 1] > 0) {
            value = -9999999;
        }
        return value;
    }

    /**
     * Kopioi peliruudukon.
     *
     * @param gameBoard Kopioitava peliruudukko
     * @return Kopio
     */
    private int[][] cloneBoard(int[][] gameBoard) {
        int[][] clone = new int[gameBoard.length][gameBoard[0].length];
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                clone[row][col] = gameBoard[row][col];
            }
        }
        return clone;
    }

    /**
     * Tarkastaa etta ruudun ymparilla ei ole vain tyhjia ruutuja.
     *
     * @param gb Peliruudukko
     * @param row Tarkasteltavan ruudun rivi
     * @param col Tarkasteltavan ruudun sarake
     * @return true jos on yksinainen ruutu, false jos ei ole
     */
    private boolean notLonely(int[][] gb, int row, int col) {
        if (row > 0 && gb[row - 1][col] != 0) {
            return true;
        }
        if (col > 0 && gb[row][col - 1] != 0) {
            return true;
        }
        if (row > 0 && col > 0 && gb[row - 1][col - 1] != 0) {
            return true;
        }
        if (row < gb.length - 1 && gb[row + 1][col] != 0) {
            return true;
        }
        if (col < gb[0].length - 1 && gb[row][col + 1] != 0) {
            return true;
        }
        if (row < gb.length - 1 && col < gb[0].length - 1 && gb[row + 1][col + 1] != 0) {
            return true;
        }
        if (row < gb.length - 1 && col > 0 && gb[row + 1][col - 1] != 0) {
            return true;
        }
        if (row > 0 && col < gb[0].length - 1 && gb[row - 1][col + 1] != 0) {
            return true;
        }
        return false;
    }

}
