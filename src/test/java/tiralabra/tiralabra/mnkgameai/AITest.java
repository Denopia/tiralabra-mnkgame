package tiralabra.tiralabra.mnkgameai;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AITest {
    
    public AITest() {
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
     * Test of getNextMove method, of class AI.
     */
    @Test
    public void testGetNextMove() {
        System.out.println("getNextMove");
        char symbol = ' ';
        Game game = null;
        String expResult = "";
        String result = AI.getNextMove(symbol, game);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
