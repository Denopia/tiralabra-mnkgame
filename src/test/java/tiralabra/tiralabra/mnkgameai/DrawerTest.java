package tiralabra.tiralabra.mnkgameai;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DrawerTest {
    
    public DrawerTest() {
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
     * Test of drawStartMessage method, of class Drawer.
     */
    @Test
    public void testDrawStartMessage() {
        System.out.println("drawStartMessage");
        Drawer.drawStartMessage();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawGameState method, of class Drawer.
     */
    @Test
    public void testDrawGameState() {
        System.out.println("drawGameState");
        Game game = null;
        Drawer.drawGameState(game);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawGameOverMessage method, of class Drawer.
     */
    @Test
    public void testDrawGameOverMessage() {
        System.out.println("drawGameOverMessage");
        Game game = null;
        Drawer.drawGameOverMessage(game);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
