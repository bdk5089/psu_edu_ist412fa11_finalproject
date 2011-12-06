package edu.psu.ist412.test;

import edu.psu.ist412.poker.Game;
import edu.psu.ist412.poker.GameController;
import junit.framework.TestCase;

/**
 * JUnit test for the GameController class/methods
 * @author PEdmonston
 */

public class GameControllerTest extends TestCase {
	GameController gc = new GameController();
	
	public GameControllerTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testGameController() {
		gc.createGame();
		assertTrue(gc.getCurrentGame().getPlayers().size()== 1);
		assertTrue(gc.getCurrentGame() instanceof Game);
		assertNotNull(gc.getCurrentGame().getTable());

	}
}
