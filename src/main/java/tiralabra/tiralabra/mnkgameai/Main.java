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

        //Testaukseen liittyvia muuttujia
        int runs = 1;
        long totalTime = 0;
        long turnTotalTime = 0;
        long turns = 0;

        int[] stats = new int[3];

        for (int i = 0; i < runs; i++) {

            long startTime = System.currentTimeMillis();

            Game game = new Game();

            Drawer.drawStartMessage();

            game.changeSettings(false);

            Drawer.drawGameState(game);

            while (!game.gameOver()) {
                //long turnStartTime = System.currentTimeMillis();
                game.nextMove();
                //long turnEndTime = System.currentTimeMillis();
                //turnTotalTime += turnEndTime - turnStartTime;
                //turns++;
                Drawer.drawGameState(game);
            }

            Drawer.drawGameOverMessage(game);

            /*
            long endTime = System.currentTimeMillis();
            totalTime += endTime - startTime;
            stats[game.getWinner()]++;
            */
        }
        /*double avgTurnTime = 1.0 * turnTotalTime / turns;
        System.out.println("VUOROON KULUI KESKIMÄÄRIN: " + avgTurnTime + "ms.");
        */
        /*
        double avgTime = 1.0 * totalTime / runs;
        System.out.println("Keskimäärin aikaa kului: " + avgTime + "ms.");
        System.out.println("Pelaaja 1 voitti " + stats[1] + " kertaa.");
        System.out.println("Pelaaja 2 voitti " + stats[2] + " kertaa.");
        System.out.println("Tasapeliin päädyttiin " + stats[0] + " kertaa.");
         */
    }
}
