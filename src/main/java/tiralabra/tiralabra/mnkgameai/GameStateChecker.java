package tiralabra.tiralabra.mnkgameai;

/**
 * Luokka joka kasittelee peliruudukkoa.
 *
 */
public class GameStateChecker {

    /**
     * Tarkastaa onko peli loppunut.
     *
     * @param gameBoard Peliruudukko
     * @param wincon Voittavan suoran pituus
     * @return true jos peli loppuu, false jos ei
     */
    public static boolean checkGameOver(int[][] gameBoard, int wincon) {
        int[][] lines = playersLines(gameBoard, wincon);
        if (lines[1][wincon] > 0 || lines[2][wincon] > 0) {
            return true;
        }
        return false;
    }

    /**
     * Palauttaa pelaajien tamanhetkiset suorat.
     *
     * @param gameBoard Peliruudukko
     * @param wincon Voittavan suoran pituus
     * @return taulukko suorista
     */
    public static int[][] playersLines(int[][] gameBoard, int wincon) {
        int[][] lines = new int[3][wincon + 1];
        checkWtoE(gameBoard, lines, wincon);
        checkWtoE(shiftDown(mirrorVertical(gameBoard)), lines, wincon);
        checkWtoE(mirrorDiagonal(gameBoard), lines, wincon);
        checkWtoE(shiftDown(gameBoard), lines, wincon);
       /* System.out.println("pelaaja 1");
        for (int i = 1; i <= wincon; i++) {
            System.out.println(i + " mittaisia " + lines[1][i]);
        }
        System.out.println("pelaaja 2");
        for (int i = 1; i <= wincon; i++) {
            System.out.println(i + " mittaisia " + lines[2][i]);
        }*/
        return lines;
    }

    /**
     * Tarkastaa vaakarivilta samojen merkkien jonoja.
     *
     * @param gameBoard Peliruudukko
     * @param lines Taulukko johon jonot tallennetaan
     * @param wincon Voittavan suoran pituus
     */
    private static void checkWtoE(int[][] gameBoard, int[][] lines, int wincon) {
        for (int r = 0; r < gameBoard.length; r++) {
            int empty = 0;
            int player1 = 0;
            int player2 = 0;
            int filler = 0;
            for (int i = 0; i < wincon; i++) {
                if (gameBoard[r][i] == -1) {
                    filler++;
                }
                if (gameBoard[r][i] == 0) {
                    empty++;
                }
                if (gameBoard[r][i] == 1) {
                    player1++;
                }
                if (gameBoard[r][i] == 2) {
                    player2++;
                }
            }
            int pointer1 = 0;
            int pointer2 = wincon - 1;
            while (pointer2 < gameBoard[r].length) {
                if (player2 == 0 && player1 > 0 && filler == 0) {
                    lines[1][player1]++;
                }
                if (player1 == 0 && player2 > 0 && filler == 0) {
                    lines[2][player2]++;
                }
                if (gameBoard[r][pointer1] == -1) {
                    filler--;
                }
                if (gameBoard[r][pointer1] == 0) {
                    empty--;
                }
                if (gameBoard[r][pointer1] == 1) {
                    player1--;
                }
                if (gameBoard[r][pointer1] == 2) {
                    player2--;
                }
                pointer1++;
                pointer2++;
                if (pointer2 < gameBoard[r].length) {
                    if (gameBoard[r][pointer2] == -1) {
                        filler++;
                    }
                    if (gameBoard[r][pointer2] == 0) {
                        empty++;
                    }
                    if (gameBoard[r][pointer2] == 1) {
                        player1++;
                    }
                    if (gameBoard[r][pointer2] == 2) {
                        player2++;
                    }
                }
            }
        }
    }

    /**
     * Palauttaa taulukon pelikuvan valistajan suhteen.
     *
     * @param gameBoard Peilattava taulukko
     * @return Peilattu taulukko
     */
    private static int[][] mirrorDiagonal(int[][] gameBoard) {
        int[][] rotatedGameBoard = new int[gameBoard[0].length][gameBoard.length];
        for (int r = 0; r < gameBoard.length; r++) {
            for (int c = 0; c < gameBoard[0].length; c++) {
                rotatedGameBoard[c][r] = gameBoard[r][c];
            }
        }
        return rotatedGameBoard;
    }

    /**
     * Liikuttaa taulukon sarakkeita sarakkeen indeksin verran alaspain
     *
     * @param gameBoard Muokattava taulukko
     * @return Muokattu taulukko
     */
    private static int[][] shiftDown(int[][] gameBoard) {
        int[][] shiftedGameBoard = new int[gameBoard.length * 2][gameBoard[0].length];
        for (int a = 0; a < shiftedGameBoard.length; a++) {
            for (int b = 0; b < shiftedGameBoard[0].length; b++) {
                shiftedGameBoard[a][b] = -1;
            }
        }
        int shift = -1;
        for (int col = 0; col < gameBoard[0].length; col++) {
            shift++;
            for (int row = 0; row < gameBoard.length; row++) {
                shiftedGameBoard[row + shift][col] = gameBoard[row][col];
            }
        }
        return shiftedGameBoard;
    }

    /**
     * Palauttaa taulukon pelikuvan pystyakselin suhteen.
     *
     * @param gameBoard Peilikuvattava taulukko
     * @return Peilattu taulukko
     */
    private static int[][] mirrorVertical(int[][] gameBoard) {
        int[][] mirroredGameBoard = new int[gameBoard.length][gameBoard[0].length];
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                mirroredGameBoard[row][col] = gameBoard[row][gameBoard[0].length - col - 1];
            }
        }
        return mirroredGameBoard;
    }

    /**
     * Tarkastaa etta ruudun ymparilla ei ole vain tyhjia ruutuja.
     *
     * @param gb Peliruudukko
     * @param row Tarkasteltavan ruudun rivi
     * @param col Tarkasteltavan ruudun sarake
     * @return true jos on yksinainen ruutu, false jos ei ole
     */
    public static boolean notLonely(int[][] gb, int row, int col) {

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
