package tiralabra.tiralabra.mnkgameai;

/**
 * Pelaajaluokasta luodaan peliin kaksi pelaajaoliota, jotka voivat olla joko
 * ihmispelaajia tai tekoalypelaajia.
 *
 */
public class Player {

    /**
     * Pelaajatyypit.
     */
    public enum Type {
        HUMAN, AI
    }

    private int number;
    private char symbol;
    private Type type;

    /**
     * Konstruktori.
     *
     * @param num Pelaajan id
     * @param sym Pelaajan pelimerkki X tai O
     * @param type Pelaajan tyyppi
     */
    public Player(int num, char sym, Type type) {
        this.number = num;
        this.symbol = sym;
        this.type = type;
    }

    /**
     * Kysyy vuorossa olevalta pelaajalta mihin ruutuun tama haluaa laittaa
     * merkin, ja tekee kyseisen siirron.
     *
     * @param game Peli, johon siirto tehdaan
     */
    public void makeMove(Game game) {
        String move;
        if (this.type == Type.AI) {
            move = AI.getNextMove(this.symbol, game);
        } else {
            move = readMove(game);
        }
        game.newSymbol(this.symbol, move);
    }

    /**
     * Lukee ihmispelaajalta mihin ruutuun tama haluaa tehda siirtonsa.
     *
     * @param game Peli, johon siirto tehdaan
     * @return Ruutu, johon pelaaja haluaa laittaa merkkinsa
     */
    private String readMove(Game game) {
        System.out.println("Sinun vuorosi tehdä siirto (anna ruutu muodossa rivi-sarake esim. e-3)");
        String move = game.getScanner().nextLine();
        while (!game.validMove(move)) {
            System.out.println("Siirto ei ollut oikeassa muodossa");
            move = game.getScanner().nextLine();
        }
        return move;
    }

}
