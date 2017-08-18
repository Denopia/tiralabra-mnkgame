package tiralabra.tiralabra.mnkgameai;

import org.junit.After;
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

}
