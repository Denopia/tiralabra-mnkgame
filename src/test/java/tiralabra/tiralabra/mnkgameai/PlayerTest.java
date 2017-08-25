package tiralabra.tiralabra.mnkgameai;

import java.io.ByteArrayInputStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PlayerTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMakeMove() {
        AI ai = mock(AI.class);
        Game game = mock(Game.class);
        Player p = new Player(1, 'a', 'A', Player.Type.AI, ai);
        Player p2 = new Player(2, 'b', 'B', Player.Type.AI, ai);
        p.makeMove(game);
        verify(ai, times(1)).getNextMove(1, 2, game);
        p2.makeMove(game);
        verify(ai, times(1)).getNextMove(2, 1, game);
    }

    @Test
    public void readsMoveCorrectly1() {
        AI ai = mock(AI.class);
        String data = "a-1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        int[][] gameBoard = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        game.setGameBoard(gameBoard);
        Player p = new Player(1, 'a', 'A', Player.Type.HUMAN, ai);
        Player p2 = new Player(2, 'b', 'B', Player.Type.AI, ai);
        p.makeMove(game);
        Assert.assertEquals(1, game.getGameBoard()[0][0]);
    }

    @Test
    public void readsMoveCorrectly2() {
        AI ai = mock(AI.class);
        String data = "a-1\na-2";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game game = new Game();
        int[][] gameBoard = new int[][]{{2, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        game.setGameBoard(gameBoard);
        Player p = new Player(1, 'a', 'A', Player.Type.HUMAN, ai);
        Player p2 = new Player(2, 'b', 'B', Player.Type.AI, ai);
        p.makeMove(game);
        Assert.assertEquals(2, game.getGameBoard()[0][0]);
        Assert.assertEquals(1, game.getGameBoard()[0][1]);
    }

    @Test
    public void settersAndGettersWork() {
        Player p = new Player(1, 'a', 'A', Player.Type.AI, mock(AI.class));
        p.setBigSymbol('B');
        p.setNumber(3);
        p.setSymbol('r');
        p.setType(Player.Type.HUMAN);
        Assert.assertEquals('B', p.getBigSymbol());
        Assert.assertEquals(3, p.getNumber());
        Assert.assertEquals('r', p.getSymbol());
        Assert.assertEquals(Player.Type.HUMAN, p.getType());
    }

}
