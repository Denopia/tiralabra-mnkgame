package tiralabra.tiralabra.mnkgameai;

import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mlein
 */
public class GameTest {
    
    public GameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getScanner method, of class Game.
     */
    @Test
    public void testGetScanner() {
        System.out.println("getScanner");
        Game instance = new Game();
        Scanner expResult = null;
        Scanner result = instance.getScanner();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoard method, of class Game.
     */
    @Test
    public void testGetBoard() {
        System.out.println("getBoard");
        Game instance = new Game();
        char[][] expResult = null;
        char[][] result = instance.getBoard();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of newSymbol method, of class Game.
     */
    @Test
    public void testNewSymbol() {
        System.out.println("newSymbol");
        char symbol = ' ';
        String move = "";
        Game instance = new Game();
        instance.newSymbol(symbol, move);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextMove method, of class Game.
     */
    @Test
    public void testNextMove() {
        System.out.println("nextMove");
        Game instance = new Game();
        instance.nextMove();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of askSettings method, of class Game.
     */
    @Test
    public void testAskSettings() {
        System.out.println("askSettings");
        Game instance = new Game();
        instance.askSettings();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of winner method, of class Game.
     */
    @Test
    public void testWinner() {
        System.out.println("winner");
        Game instance = new Game();
        int expResult = 0;
        int result = instance.winner();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validMove method, of class Game.
     */
    @Test
    public void testValidMove() {
        System.out.println("validMove");
        String move = "";
        Game instance = new Game();
        boolean expResult = false;
        boolean result = instance.validMove(move);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of gameOver method, of class Game.
     */
    @Test
    public void testGameOver() {
        System.out.println("gameOver");
        Game instance = new Game();
        boolean expResult = false;
        boolean result = instance.gameOver();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
