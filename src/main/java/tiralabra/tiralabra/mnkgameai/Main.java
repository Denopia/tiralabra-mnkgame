package tiralabra.tiralabra.mnkgameai;

/**
 * Paaluokka, joka on kaynnistaa pelin.
 *
 */
public class Main {

    /**
     * Luo pelin, kaskee piirtoluokkaa piirtamaan pelitilanteen ja jakaa vuoroja
     * pelaajille kunnes voittaja on selvinnyt.
     *
     * @param args
     */
    public static void main(String[] args) {

        Game game = new Game();

        Drawer.drawStartMessage();

        game.askSettings();

        Drawer.drawGameState(game);

        while (!game.gameOver()) {
            game.nextMove();
            Drawer.drawGameState(game);
        }

        Drawer.drawGameOverMessage(game);
    }
}
