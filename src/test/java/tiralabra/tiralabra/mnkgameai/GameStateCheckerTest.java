package tiralabra.tiralabra.mnkgameai;

import org.junit.Assert;
import org.junit.Test;

public class GameStateCheckerTest {

    @Test
    public void playersLinesWorks() {
        int[][] gb = new int[][]{{0, 1}, {0, 1}};
        Assert.assertTrue(GameStateChecker.playersLines(gb, 2)[1][2] == 1);
        int[][] gb2 = new int[][]{{2, 0, 2}, {0, 2, 0}, {2, 0, 2}};
        Assert.assertTrue(GameStateChecker.playersLines(gb2, 3)[2][3] == 2);
    }

    @Test
    public void gameOverCheckWorks() {
        int[][] gb = new int[][]{{0, 1}, {0, 0}};
        Assert.assertFalse(GameStateChecker.checkGameOver(gb, 2));
        int[][] gb2 = new int[][]{{2, 0, 2}, {0, 2, 0}, {2, 0, 2}};
        Assert.assertTrue(GameStateChecker.checkGameOver(gb2, 3));
    }

}
