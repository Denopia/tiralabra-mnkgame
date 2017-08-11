package tiralabra.tiralabra.mnkgameai;

import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GameTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetScanner() {
        Game game = new Game();
        Assert.assertNotNull(game.getScanner());
    }

    @Test
    public void testGetBoard() {
        Game game = new Game();

    }

    @Test
    public void testNewSymbol() {
        Game game = new Game();

    }

    /**
     * Test of nextMove method, of class Game.
     */
    @Test
    public void testNextMove() {
        Game game = new Game();
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        game.setPlayer1(p1);
        game.setPlayer2(p2);
        game.setTurn(1);
        game.nextMove();
        Assert.assertTrue(game.getTurn() == 2);
    }

    @Test
    public void testAskSettings() {
        Assert.assertTrue(true);
    }

    @Test
    public void testWinner() {
        Game game = new Game();

    }

    @Test
    public void testValidMove() {

        Game game = new Game();
        Assert.assertTrue(game.validMove("a-2"));
        Assert.assertFalse(game.validMove("a-1"));
    }

    @Test
    public void testGameOver() {
        Game game = new Game();
        Assert.assertTrue(game.gameOver());
    }

}
