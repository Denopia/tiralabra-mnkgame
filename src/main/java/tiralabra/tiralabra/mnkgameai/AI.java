package tiralabra.tiralabra.mnkgameai;

/**
 * Tama luokka vastuussa tekoalyn tekemista siirroista.
 *
 */
public class AI {

    private final char[] alph;
    private String nextMove;

    /**
     * Konstruktori.
     *
     * @param alph Aakkosia
     */
    public AI(char[] alph) {
        this.nextMove = "";
        this.alph = alph;
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
        decideNextMove(game.getGameBoard(), player, opponent, 3, game.getWincon(), -999999999, 999999999);
        return this.nextMove;
    }

    /**
     * Paattaa tekoalyn siirron.
     *
     * @param gameBoard Peliruudukko
     * @param player Vuorossa oleva pelaaja
     * @param opponent Vuorossa olevan pelaajan vastustaja
     * @param depth Kuinka monta siirtoa eteenpain pelia tutkitaan
     * @param wincon Voittavan suoran pituus
     * @param alpha Alphan arvo
     * @param beta Betan arvo
     */
    private void decideNextMove(int[][] gameBoard, int player, int opponent, int depth, int wincon, int alpha, int beta) {
        int[][] movepoints = new int[gameBoard.length][gameBoard[0].length];
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (gameBoard[row][col] != 0 || !notLonely(gameBoard, row, col)) {
                    movepoints[row][col] = 0;
                } else {
                    int[][] clone = cloneBoard(gameBoard);
                    clone[row][col] = player;
                    movepoints[row][col] = minValue(clone, wincon, player, opponent, depth, alpha, beta);
                }
            }
        }
        int bestValue = -9999998;
        int bestRow = -1;
        int bestCol = -1;
        //System.out.println("VALUES");
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                //System.out.print(movepoints[row][col] + ", ");
                if ((movepoints[row][col] > bestValue || bestValue == -9999998) && gameBoard[row][col] == 0 && notLonely(gameBoard, row, col)) {
                    bestValue = movepoints[row][col];
                    bestRow = row;
                    bestCol = col;
                }
            }
            //System.out.println("");
        }
        if (bestRow == -1) {
            bestRow = gameBoard.length / 2;
            bestCol = gameBoard.length / 2;
        }
        bestCol++;
        this.nextMove = alph[bestRow] + "-" + bestCol;
    }

    /**
     * Minmax-algoritmin max-osa.
     *
     * @param gameBoard Peliruudukko
     * @param wincon Voittavan suoran pituus
     * @param player Vuorossa oleva pelaaja
     * @param opponent Vuorossa olevan pelaajan vastustaja
     * @param depth Kuinka monta siirtoa eteenpain pelia tutkitaan
     * @param alpha Alphan arvo
     * @param beta Betan arvo
     * @return Siirron maksimipistearvo
     */
    private int maxValue(int[][] gameBoard, int wincon, int player, int opponent, int depth, int alpha, int beta) {
        int[][] lines = GameStateChecker.playersLines(gameBoard, wincon);
        if (winnerIsDecided(lines, wincon) || depth == 0) {
            return gameBoardValue(lines, player, opponent, player);
        }
        int value = -999999999;
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (gameBoard[row][col] != 0 || !notLonely(gameBoard, row, col)) {
                    continue;
                } else {
                    int[][] clone = cloneBoard(gameBoard);
                    clone[row][col] = player;
                    value = Math.max(value, minValue(clone, wincon, player, opponent, depth - 1, alpha, beta));
                    alpha = Math.max(alpha, value);
                    if (beta <= alpha) {
                        return value;
                    }
                }
            }
        }
        return value;
    }

    /**
     * Minmax-algoritmin min-osa.
     *
     * @param gameBoard Peliruudukko
     * @param wincon Voittavan suoran pituus
     * @param player Vuorossa oleva pelaaja
     * @param opponent Vuorossa olevan pelaajan vastustaja
     * @param depth Kuinka monta siirtoa eteenpain pelia tutkitaan
     * @param alpha Alphan arvo
     * @param beta Betan arvo
     * @return Siirron minimipistearvo
     */
    private int minValue(int[][] gameBoard, int wincon, int player, int opponent, int depth, int alpha, int beta) {
        int[][] lines = GameStateChecker.playersLines(gameBoard, wincon);
        if (winnerIsDecided(lines, wincon) || depth == 0) {
            return gameBoardValue(lines, player, opponent, opponent);
        }
        int value = 999999999;
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (gameBoard[row][col] != 0 || !notLonely(gameBoard, row, col)) {
                    continue;
                } else {
                    int[][] clone = cloneBoard(gameBoard);
                    clone[row][col] = opponent;
                    value = Math.min(value, maxValue(clone, wincon, player, opponent, depth - 1, alpha, beta));
                    beta = Math.min(beta, value);
                    if (beta <= alpha) {
                        return value;
                    }
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
     * Laskee pelitilanteelle pistearvion.
     *
     * @param lines Pelaajien tamanhetkiset suorat
     * @param player Vuorossa oleva pelaaja
     * @param opponent Vuorossa olevan pelaajan vastustaja
     * @param next Seuraavan siirron tekija
     * @return Pelitilanteen pistearvio
     */
    private int gameBoardValue(int[][] lines, int player, int opponent, int next) {
        int value = 0;
        int m = 1;
        if (player == next) {
            for (int i = 1; i <= lines.length; i++) {
                value += lines[player][i] * m;
                m = 10 * m;
            }
        }
        m = 1;
        for (int i = 1; i <= lines.length; i++) {
            value -= lines[opponent][i] * m;
            m = 10 * m;
        }
        if (lines[player][lines[0].length - 1] > 0) {
            value += 9999997;
        }
        if (lines[opponent][lines[0].length - 1] > 0) {
            value += -9999997;
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
