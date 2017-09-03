package tiralabra.tiralabra.mnkgameai;

import java.util.Random;

/**
 * Tama luokka vastuussa tekoalyn tekemista siirroista.
 *
 */
public class AI {

    /**
     * Kaytetaan javan valmista Random-luokkaa, koska ei ollut aikaa toteuttaa
     * sita itse. Tekoaly voisi toimia ilman satunnaisuutta, mutta kaikki pelit
     * etenisivat identtisesti kun tekoaly pelaa itseaan vastaan.
     */
    private Random random;
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
        this.random = new Random();
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
        Drawer.drawThinkingMessage(player);
        int[][] moves;
        if (immediateMoveAndWin(player, opponent, game)) {
            moves = decideNextMove(game.getGameBoard(), player, opponent, 0, game.getWincon(), Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else if (immediateMoveOrLose(player, opponent, game)) {
            moves = decideNextMove(game.getGameBoard(), player, opponent, 1, game.getWincon(), Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else {
            moves = decideNextMove(game.getGameBoard(), player, opponent, game.getDepth(), game.getWincon(), Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        int move = this.random.nextInt(moves.length);
        int row = moves[move][0];
        int col = moves[move][1] + 1;
        this.nextMove = alph[row] + "-" + col;
        Drawer.drawNextMoveMessage(player, this.nextMove);

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
    private int[][] decideNextMove(int[][] gameBoard, int player, int opponent, int depth, int wincon, int alpha, int beta) {
        int[][] movepoints = new int[gameBoard.length][gameBoard[0].length];
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (gameBoard[row][col] != 0 || !GameStateChecker.notLonely(gameBoard, row, col)) {
                    movepoints[row][col] = 0;
                } else {
                    int[][] clone = cloneBoard(gameBoard);
                    clone[row][col] = player;
                    movepoints[row][col] = minValue(clone, wincon, player, opponent, depth, alpha, beta);
                }
            }
        }

        int bestValue = Integer.MIN_VALUE;
        int bestvals = 0;
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (gameBoard[row][col] == 0 && GameStateChecker.notLonely(gameBoard, row, col)) {
                    if (movepoints[row][col] > bestValue) {
                        bestValue = movepoints[row][col];
                        bestvals = 1;
                    } else if (movepoints[row][col] == bestValue) {
                        bestvals++;
                    }

                }
            }
        }
        /*for (int i = 0; i < movepoints.length; i++) {
            for (int j = 0; j < movepoints[0].length; j++) {
                System.out.print(movepoints[i][j] + " = ");
            }
            System.out.println("");
            System.out.println("===========================================================================");
        }*/

        if (bestvals > 0) {
            int[][] bestMoves = new int[bestvals][2];
            int pos = 0;
            for (int row = 0; row < gameBoard.length; row++) {
                for (int col = 0; col < gameBoard[0].length; col++) {
                    if (movepoints[row][col] == bestValue && GameStateChecker.notLonely(gameBoard, row, col) && gameBoard[row][col] == 0) {
                        bestMoves[pos][0] = row;
                        bestMoves[pos][1] = col;
                        pos++;
                    }
                }
            }
            return bestMoves;
        } else {
            int[][] bestMoves = new int[1][2];
            bestMoves[0][0] = gameBoard.length / 2;
            bestMoves[0][1] = gameBoard[0].length / 2;
            return bestMoves;
        }
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
        if (winnerIsDecided(lines, wincon) || depth == 0 || noMovesLeft(gameBoard)) {
            return gameBoardValue(lines, player, opponent, player);
        }
        int value = Integer.MIN_VALUE;
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (gameBoard[row][col] != 0 || !GameStateChecker.notLonely(gameBoard, row, col)) {
                    continue;
                } else {
                    int[][] clone = cloneBoard(gameBoard);
                    clone[row][col] = player;
                    value = biggerValue(value, minValue(clone, wincon, player, opponent, depth - 1, alpha, beta));
                    alpha = biggerValue(alpha, value);
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
        if (winnerIsDecided(lines, wincon) || depth == 0 || noMovesLeft(gameBoard)) {
            return gameBoardValue(lines, player, opponent, opponent);
        }
        int value = Integer.MAX_VALUE;
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                if (gameBoard[row][col] != 0 || !GameStateChecker.notLonely(gameBoard, row, col)) {
                    continue;
                } else {
                    int[][] clone = cloneBoard(gameBoard);
                    clone[row][col] = opponent;
                    value = smallerValue(value, maxValue(clone, wincon, player, opponent, depth - 1, alpha, beta));
                    beta = smallerValue(beta, value);
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
        int value = 1;
        int m = 10;
        if (player == next || true) {
            for (int i = 2; i <= lines.length - 1; i++) {
                value += lines[player][i] * m;
                m = 100 * m;
            }
        }
        m = 20;
        for (int i = 2; i <= lines.length - 1; i++) {
            value -= lines[opponent][i] * m;
            m = m * m;
        }
        if (lines[opponent][lines[0].length - 1] == 0
                && (lines[player][lines[0].length - 1] > 0
                || (lines[player][lines[0].length - 2] > 0 && next == player))) {
            value = Integer.MAX_VALUE;
        }
        if (lines[player][lines[0].length - 1] == 0
                && (lines[opponent][lines[0].length - 1] > 0
                || (lines[opponent][lines[0].length - 2] > 0 && next == opponent))) {
            value = Integer.MIN_VALUE;
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
     * Tarkastaa voittaako pelaaja heti yhdella siirrolla.
     *
     * @param player Vuorossa oleva pelaaja
     * @param opponent Vuorossa olevan pelaajan vastustaja
     * @param game Tutkittava peli
     * @return True jos pelaaja voi voittaa heti, false jos ei
     */
    private boolean immediateMoveAndWin(int player, int opponent, Game game) {
        int[][] lines = GameStateChecker.playersLines(game.getGameBoard(), game.getWincon());
        return lines[player][game.getWincon() - 1] > 0;
    }

    /**
     * Tarkastaa haviaako pelaaja, jos tama ei tee heti tiettya siirtoa.
     *
     * @param player Vuorossa oleva pelaaja
     * @param opponent Vuorossa olevan pelaajan vastustaja
     * @param game Tutkittava peli
     * @return True jos on pakko tehda tietty siirto, false jos ei
     */
    private boolean immediateMoveOrLose(int player, int opponent, Game game) {
        int[][] lines = GameStateChecker.playersLines(game.getGameBoard(), game.getWincon());
        return lines[opponent][game.getWincon() - 1] > 0;
    }

    /**
     * Palauttaa kahdesta luvusta suuremman.
     *
     * @param value1 1. vertailtava arvo
     * @param value2 2. vertailtava arvo
     * @return Suurempi arvo
     */
    private int biggerValue(int value1, int value2) {
        if (value1 >= value2) {
            return value1;
        }
        return value2;
    }

    /**
     * Palauttaa kahdesta luvusta pienemman.
     *
     * @param value1 1. vertailtava arvo
     * @param value2 2. vertailtava arvo
     * @return Pienempi arvo
     */
    private int smallerValue(int value1, int value2) {
        if (value1 <= value2) {
            return value1;
        }
        return value2;
    }

    private boolean noMovesLeft(int[][] gameBoard) {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (gameBoard[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
