package edu.psu.ist412.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import edu.psu.ist412.poker.Game;
import edu.psu.ist412.poker.Player;
/**
 * JUnit test for the Game classes/methods
 * @author PEdmonston
 */
public class GameTest extends TestCase {

	Player p1 = new Player(0,10,true);
	Player p2 = new Player(1,10,false);		
	ArrayList<Player> players = new ArrayList<Player>();
//? players.add(p1);
//?	players.add(p2);
	Game g = new Game(players);
	public GameTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
	}

	protected void tearDown() throws Exception {
	}
	public void testGame() {
//		System.out.println("Game = " + g.toString());
//		System.out.println("Players = " + g.getPlayers());
//		System.out.println("Table before start = " + g.getTable());
		g.startGame();
		g.startRound();
//		System.out.println("Table after startRound = " + g.getTable().toString());
		g.dealHands();
//		System.out.println("TableCards after dealHands = " + g.getTableCards().toString());
//		System.out.println("Table after dealHands = " + g.getTable().toString());
		assertTrue(g.getTable().getCards().size() == 0);
		g.dealFlop();
//		System.out.println("Table after dealFlop = " + g.getTable().toString());
		assertTrue(g.getTable().getCards().size() == 3);
		g.dealTurn();
//		System.out.println("Table after dealTurn = " + g.getTable().toString());
		assertTrue(g.getTable().getCards().size() == 4);
		g.dealRiver();
//		System.out.println("Table after dealRiver = " + g.getTable().toString());
		assertTrue(g.getTable().getCards().size() == 5);
		assertTrue(g.getState().toString() == "START");

	}
}
