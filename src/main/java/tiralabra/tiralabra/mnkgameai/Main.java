package tiralabra.tiralabra.mnkgameai;

public class Main {

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
