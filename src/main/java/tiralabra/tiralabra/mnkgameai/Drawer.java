package tiralabra.tiralabra.mnkgameai;

public class Drawer {

    private static char[] alph = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o'};

    static void drawStartMessage() {
        System.out.println(
                "  ____  _     _   _             _ _                  _ _ \n"
                + " |  _ \\(_)___| |_(_)_ __   ___ | | | __ _ _ __   ___| (_)\n"
                + " | |_) | / __| __| | '_ \\ / _ \\| | |/ _` | '_ \\ / _ \\ | |\n"
                + " |  _ <| \\__ \\ |_| | | | | (_) | | | (_| | |_) |  __/ | |\n"
                + " |_| \\_\\_|___/\\__|_|_| |_|\\___/|_|_|\\__,_| .__/ \\___|_|_|\n"
                + "                                         |_|             ");
    }

    static void drawGameState(Game game) {
        char[][] board = game.getBoard();
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
            System.out.print(" ---");
        }
        System.out.print("\n");
        for (int r = 0; r < board.length; r++) {
            System.out.print(alph[r] + " ");
            for (int c = 0; c < board[0].length; c++) {
                System.out.print("| " + board[r][c] + " ");
            }
            System.out.print("|\n  ");
            for (int i = 0; i < board[0].length; i++) {
                System.out.print(" ---");
            }
            System.out.print("\n");
        }
    }

    static void drawGameOverMessage(Game game) {
        int winner = game.winner();
        System.out.println("Pelin voitti pelaaja " + winner);
    }

}
