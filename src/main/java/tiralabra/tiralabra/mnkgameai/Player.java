package tiralabra.tiralabra.mnkgameai;

/**
 * Pelaajaluokasta luodaan peliin kaksi pelaajaoliota, jotka voivat olla joko
 * ihmispelaajia tai tekoalypelaajia.
 *
 */
public class Player {

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return the symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Pelaajatyypit.
     */
    public enum Type {
        HUMAN, AI
    }

    private int number;
    private char symbol;
    private Type type;
    private AI ai;

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
        if (type.equals(Type.AI)) {
            this.ai = new AI();
        }

    }

    /**
     * Kysyy vuorossa olevalta pelaajalta mihin ruutuun tama haluaa laittaa
     * merkin, ja tekee kyseisen siirron.
     *
     * @param game Peli, johon siirto tehdaan
     */
    public void makeMove(Game game) {
        String move;
        if (this.getType() == Type.AI) {
            if (this.number == 1) {
                move = this.ai.getNextMove(1, 2, game);
            } else {
                move = this.ai.getNextMove(2, 1, game);
            }
        } else {
            move = readMove(game);
        }
        game.newSymbol(this.getNumber(), move);
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
            System.out.println("Siirto ei ole sallittu");
            move = game.getScanner().nextLine();
        }
        return move;
    }

}
