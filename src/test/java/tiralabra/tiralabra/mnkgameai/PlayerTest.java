package tiralabra.tiralabra.mnkgameai;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMakeMove() {
        Player player1 = new Player(1, 'X', Player.Type.AI);
        Game game = new Game();
        game.setGameBoard(new char[][]{{'X', ' '}, {'X', 'X'}});
        player1.makeMove(game);
        Assert.assertTrue(game.getBoard()[0][1] == 'X');
    }

}
