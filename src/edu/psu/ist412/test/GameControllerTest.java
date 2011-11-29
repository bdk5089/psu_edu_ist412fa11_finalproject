package edu.psu.ist412.test;

import java.util.ArrayList;

import edu.psu.ist412.poker.Game;
import edu.psu.ist412.poker.GameController;
import edu.psu.ist412.poker.Player;
import junit.framework.TestCase;

/**
 * JUnit test for the GameController class/methods
 * @author PEdmonston
 */

public class GameControllerTest extends TestCase {
	GameController gc = new GameController();
	ArrayList<Game> games = new ArrayList<Game>();
	ArrayList<Player> players = new ArrayList<Player>();
	
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
		assertTrue(gc.getCurrentGame() instanceof Game);
		System.out.println(games.size());
		System.out.println(players.size());
//		assertFalse(games.size() == 1);
//		assertTrue(players.size() == 1);
	}
}
