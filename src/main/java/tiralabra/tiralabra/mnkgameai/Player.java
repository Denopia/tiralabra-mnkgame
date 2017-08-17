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
    private char bigSymbol;
    private Type type;
    private AI ai;

    /**
     * Konstruktori.
     *
     * @param num Pelaajan id
     * @param sym Pelaajan pelimerkki x tai o
     * @param bsym Pelaajan iso pelimerkki X tai O
     * @param type Pelaajan tyyppi
     * @param ai Pelaajalle annettava tekoaly
     */
    public Player(int num, char sym, char bsym, Type type, AI ai) {
        this.number = num;
        this.symbol = sym;
        this.type = type;
        this.bigSymbol = bsym;
        this.ai = ai;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public char getBigSymbol() {
        return bigSymbol;
    }

    public void setBigSymbol(char bigSymbol) {
        this.bigSymbol = bigSymbol;
    }

}
