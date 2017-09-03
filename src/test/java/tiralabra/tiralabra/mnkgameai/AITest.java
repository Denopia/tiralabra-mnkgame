package tiralabra.tiralabra.mnkgameai;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AITest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void aiReturnsValidMove() {
        Game game = new Game();
        game.setAvailableMoves(9);
        game.setFirstPlayer(1);
        game.setGameBoard(new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}});
        game.setLastMove1(new int[]{-1, -1});
        game.setLastMove2(new int[]{-1, -1});
        game.setTurn(1);
        game.setWincon(3);
        AI ai = new AI(Game.getAlph());
        String move = ai.getNextMove(1, 2, game);
       
        org.junit.Assert.assertEquals("b-2", move);
    }
    
    @Test
    public void aiMakesSmartMoves1() {
        Game game = new Game();
        game.setAvailableMoves(4);
        game.setFirstPlayer(1);
        game.setGameBoard(new int[][]{{2, 0, 1}, {2, 1, 1}, {0, 0, 0}});
        game.setLastMove1(new int[]{1, 2});
        game.setLastMove2(new int[]{1, 0});
        game.setTurn(2);
        game.setWincon(3);
        AI ai = new AI(Game.getAlph());
        String move = ai.getNextMove(2, 1, game);
        
        org.junit.Assert.assertEquals("c-1", move);
    }
    
    @Test
    public void aiMakesSmartMoves2() {
        Game game = new Game();
        game.setAvailableMoves(5);
        game.setFirstPlayer(1);
        game.setGameBoard(new int[][]{{2, 0, 1}, {2, 1, 0}, {0, 0, 0}});
        game.setLastMove1(new int[]{1, 2});
        game.setLastMove2(new int[]{1, 0});
        game.setTurn(1);
        game.setWincon(3);
        AI ai = new AI(Game.getAlph());
        String move = ai.getNextMove(1, 2, game);
        org.junit.Assert.assertEquals("c-1", move);
    }
    
}
