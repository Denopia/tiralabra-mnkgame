package tiralabra.tiralabra.mnkgameai;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
    public void makingAMovePassesTurn() {
        Game game = new Game();
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        game.setPlayer1(p1);
        game.setPlayer2(p2);
        game.setTurn(1);
        game.nextMove();
        Assert.assertTrue(game.getTurn() == 2);
        game.nextMove();
        Assert.assertTrue(game.getTurn() == 1);
        game.nextMove();
        Assert.assertTrue(game.getTurn() == 2);
    }

    @Test
    public void addingNewSymbolDecreasesTotalMovesAvailable() {
        Game game = new Game();
        game.setAvailableMoves(10);
        game.setGameBoard(new int[2][2]);
        game.newSymbol(1, "a-1");
        Assert.assertTrue(game.getAvailableMoves() == 9);
    }

    @Test
    public void addingNewSymbolUpdatesLatestMove() {
        Game game = new Game();
        game.setAvailableMoves(10);
        game.setGameBoard(new int[2][2]);
        game.newSymbol(1, "a-2");
        Assert.assertTrue(game.getLastMove1()[0] == 0);
        Assert.assertTrue(game.getLastMove1()[1] == 1);
        game.newSymbol(2, "b-2");
        Assert.assertTrue(game.getLastMove2()[0] == 1);
        Assert.assertTrue(game.getLastMove2()[1] == 1);
    }

    @Test
    public void testWinner() {
        Game game = new Game();

    }

    @Test
    public void testMoveValidity() {
        Game game = new Game();
        int[][] gb = new int[3][3];
        gb[1][1] = 1;
        game.setGameBoard(gb);
        Assert.assertTrue(game.validMove("a-2"));
        Assert.assertFalse(game.validMove("a-4"));
        Assert.assertFalse(game.validMove("f-2"));
        Assert.assertFalse(game.validMove("b-2"));
    }

    @Test
    public void testGameOver() {
        Game game = new Game();
        int[][] gb = new int[3][3];
        game.setAvailableMoves(3);
        gb[0][1] = 1;
        gb[1][1] = 1;
        game.setGameBoard(gb);
        game.setWincon(3);
        Assert.assertFalse(game.gameOver());
        game.setAvailableMoves(0);
        Assert.assertTrue(game.gameOver());
        game.setAvailableMoves(3);
        Assert.assertFalse(game.gameOver());
        game.getGameBoard()[2][1] = 1;
        Assert.assertTrue(game.gameOver());
    }

    @Test
    public void testGetWinner() {
        Game game = new Game();
        int[][] gb = new int[3][3];
        game.setAvailableMoves(3);
        gb[0][1] = 1;
        gb[1][1] = 1;
        game.setGameBoard(gb);
        game.setWincon(3);
        Assert.assertEquals(0, game.getWinner());
        game.getGameBoard()[2][1] = 1;
        Assert.assertEquals(1, game.getWinner());
        int[][] gb2 = new int[3][3];
        gb2[0][1] = 2;
        gb2[1][1] = 2;
        gb2[2][1] = 2;
        game.setGameBoard(gb2);
        Assert.assertEquals(2, game.getWinner());
    }

    @Test
    public void askingSettingsWorks1() {
        String data = "1\n1\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        game.changeSettings(false);
        Assert.assertEquals(3, game.getGameBoard().length);
        Assert.assertEquals(3, game.getGameBoard()[0].length);
        Assert.assertEquals(1, game.getFirstPlayer());
    }

    @Test
    public void askingSettingsWorks2() {
        String data = "-1\n1\n1\n3";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        game.changeSettings(false);
        Assert.assertEquals(5, game.getGameBoard().length);
        Assert.assertEquals(5, game.getGameBoard()[0].length);
        Assert.assertEquals(1, game.getFirstPlayer());
    }

    @Test
    public void askingSettingsWorks3() {
        String data = "2\n2";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        game.changeSettings(false);
        Assert.assertEquals(6, game.getGameBoard().length);
        Assert.assertEquals(4, game.getGameBoard()[0].length);
        Assert.assertEquals(1, game.getFirstPlayer());
        Assert.assertEquals(Player.Type.AI, game.getPlayer1().getType());
        Assert.assertEquals(24, game.getAvailableMoves());
    }

    @Test
    public void askingSettingsWorks4() {
        String data = "1\n2\n4";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        game.changeSettings(false);
        Assert.assertEquals(9, game.getGameBoard().length);
        Assert.assertEquals(9, game.getGameBoard()[0].length);
        Assert.assertEquals(2, game.getFirstPlayer());
        Assert.assertEquals(Player.Type.HUMAN, game.getPlayer1().getType());
        Assert.assertEquals(5, game.getWincon());

    }

}
