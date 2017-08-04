package tiralabra.tiralabra.mnkgameai;

import static org.mockito.Mockito.*;

import junit.framework.Assert;
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
        Game game = mock(Game.class);
        char[][] gameBoard = new char[][]{{' ', ' '}, {' ', ' '}};
        when(game.getBoard()).thenReturn(gameBoard);
        String move = AI.getNextMove('X', game);
        String[] rowColumn = move.split("-");
        Assert.assertTrue(rowColumn.length == 2);
    }

}
