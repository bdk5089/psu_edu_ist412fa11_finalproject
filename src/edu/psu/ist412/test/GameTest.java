package edu.psu.ist412.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import edu.psu.ist412.poker.Game;
import edu.psu.ist412.poker.Player;
import edu.psu.ist412.poker.Game.GameState;
/**
 * JUnit test for the Game class/methods
 * @author PEdmonston
 */
public class GameTest extends TestCase {

	Player p1 = new Player(0,10,true);
	Player p2 = new Player(1,10,false);		
	ArrayList<Player> players = new ArrayList<Player>();
	Game g = new Game(players);

	
	public GameTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		players.add(p1);
		players.add(p2);
	}

	protected void tearDown() throws Exception {
	}
	
	public void testToString(){
		assertTrue(g.toString() instanceof String);
	}
	
	public void testGetPlayers(){
		assertTrue(g.getPlayers().size()==2);
	}
	
	public void testGame() {
		assertTrue(g.getState().toString() == "START");
		assertEquals(g.toString(),"Game [players=[Player [position=0], Player [position=1]], table=null, currentPlayer=null]");
		assertTrue(g.getPlayers().size() == 2);
		g.startGame();
		assertTrue(g.getPlayers().get(0).getHand().getCards().size() == 2);
		assertTrue(g.getPlayers().get(1).getHand().getCards().size() == 2);
		assertTrue(g.getTable().getCards().size() == 0);
		assertTrue(g.getTableCards().size()==5);
		g.dealFlop();
		assertTrue(g.getTable().getCards().size() == 3);
		g.dealTurn();
		assertTrue(g.getTable().getCards().size() == 4);
		g.dealRiver();
		assertTrue(g.getTable().getCards().size() == 5);
		assertTrue(g.getTableCards().size()==5);
		g.setState(GameState.RIVER);
		assertTrue(g.getState().toString() == "RIVER");
	}
	
	public void testGetWinner(){
		testGame();
		try {
			assertTrue(g.getWinner() == p1 || g.getWinner() == p2);
		} catch (Exception e) {
			assertTrue("Exception: "+e.getMessage(), false);
			e.printStackTrace();
		}
	}
}
