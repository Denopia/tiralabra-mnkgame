package tiralabra.tiralabra.mnkgameai;

import java.util.Random;

/**
 * Tama luokka vastuussa tekoalyn tekemista siirroista.
 *
 */
public class AI {

    static Random random = new Random();

    /**
     * Valitsee tekoalyn seuraavan siirron. (Talla hetkella valitsee vain
     * sattumanvaraisen ruudun)
     *
     * @param symbol Vuorossa olevan pelaajan merkki
     * @param game Peli, johon siirto tehdaan
     * @return Ruutu, johon tekoaly haluaa laittaa merkkinsa
     */
    static String getNextMove(char symbol, Game game) {
        int rowmax = game.getBoard().length;
        int colmax = game.getBoard()[0].length;
        int row = random.nextInt(rowmax);
        int col = random.nextInt(colmax);
        while (game.getBoard()[row][col] != ' ') {
            row = random.nextInt(rowmax);
            col = random.nextInt(colmax);
        }
        col++;
        return Game.alph[row] + "-" + col;

    }

}
