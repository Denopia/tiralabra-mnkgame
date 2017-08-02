package tiralabra.tiralabra.mnkgameai;

public class Player {

    public enum Type {
        HUMAN, AI
    }

    private int number;
    private char symbol;
    private Type type;

    public Player(int num, char sym, Type type) {
        this.number = num;
        this.symbol = sym;
        this.type = type;
    }

    public void makeMove(Game game) {
        String move;
        if (this.type == Type.AI) {
            move = AI.getNextMove(this.symbol);
        } else {
            move = readMove();
        }
        game.newSymbol(this.symbol, move);
    }

    private String readMove() {
        return "";
    }

}
