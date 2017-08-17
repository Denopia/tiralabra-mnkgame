package tiralabra.tiralabra.mnkgameai;

/**
 * Luokka on vastuussa pelitilanteiden piirtamisesta
 *
 */
public class Drawer {

    /**
     * Piirtaa pelin aloitusviestin.
     */
    static void drawStartMessage() {
        System.out.println(
                "  ____  _     _   _             _ _                  _ _ \n"
                + " |  _ \\(_)___| |_(_)_ __   ___ | | | __ _ _ __   ___| (_)\n"
                + " | |_) | / __| __| | '_ \\ / _ \\| | |/ _` | '_ \\ / _ \\ | |\n"
                + " |  _ <| \\__ \\ |_| | | | | (_) | | | (_| | |_) |  __/ | |\n"
                + " |_| \\_\\_|___/\\__|_|_| |_|\\___/|_|_|\\__,_| .__/ \\___|_|_|\n"
                + "                                         |_|             ");
    }

    /**
     * Piirtaa pelitilanteen.
     *
     * @param game Peli, jonka tilanne piirretaan
     */
    static void drawGameState(Game game) {
        int[][] board = game.getGameBoard();
        System.out.print("  ");
        for (int i = 0; i < board[0].length; i++) {
            int n = i + 1;
            if (n < 10) {
                System.out.print("  " + n + " ");
            } else {
                System.out.print("  " + n + "");
            }
        }
        System.out.print("\n  ");
        for (int i = 0; i < board[0].length; i++) {
            System.out.print("+---");
        }
        System.out.print("+\n");
        for (int r = 0; r < board.length; r++) {
            System.out.print(Game.getAlph()[r] + " ");
            for (int c = 0; c < board[0].length; c++) {
                int s = board[r][c];
                if (s == 0) {
                    System.out.print("| " + " " + " ");
                }
                if (s == 1) {
                    if (game.getLastMove1()[0] == r && game.getLastMove1()[1] == c) {
                        System.out.print("| " + game.getPlayer1().getBigSymbol() + " ");
                    } else {
                        System.out.print("| " + game.getPlayer1().getSymbol() + " ");
                    }
                }
                if (s == 2) {
                    if (game.getLastMove2()[0] == r && game.getLastMove2()[1] == c) {
                        System.out.print("| " + game.getPlayer2().getBigSymbol() + " ");
                    } else {
                        System.out.print("| " + game.getPlayer2().getSymbol() + " ");
                    }
                }
            }
            System.out.print("|\n  ");
            for (int i = 0; i < board[0].length; i++) {
                System.out.print("+---");
            }
            System.out.print("+\n");
        }
    }

    /**
     * Piirtaa pelin loppumisviestin.
     *
     * @param game Peli, jonka loppumisviesti piirretaan
     */
    static void drawGameOverMessage(Game game) {
        int winner = game.getWinner();
        if (winner == 0) {
            System.out.println("Peli päättyi tasapeliin.");
        } else {
            System.out.println("Pelin voitti pelaaja " + winner + ".");
        }

    }

}
