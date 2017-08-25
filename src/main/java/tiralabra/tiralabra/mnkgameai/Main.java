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

        int runs = 1;
        long totalTime = 0;

        for (int i = 0; i < runs; i++) {
            long aikaAlussa = System.currentTimeMillis();

            Game game = new Game();

            Drawer.drawStartMessage();

            game.askSettings();

            Drawer.drawGameState(game);

            while (!game.gameOver()) {
                game.nextMove();
                Drawer.drawGameState(game);
            }

            Drawer.drawGameOverMessage(game);

            long aikaLopussa = System.currentTimeMillis();
            totalTime += aikaLopussa - aikaAlussa;
        }
        double avgTime = 1.0 * totalTime / runs;
        //System.out.println("Keskimäärin aikaa kului: " + avgTime + "ms.");
    }
}
