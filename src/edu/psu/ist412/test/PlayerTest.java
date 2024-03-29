package edu.psu.ist412.test;

import junit.framework.TestCase;
import edu.psu.ist412.poker.Hand;
import edu.psu.ist412.poker.Player;
import edu.psu.ist412.poker.PlayerStatus;

/**
 * JUnit test for the Player class/methods
 * @author PEdmonston, AaronD
 */

public class PlayerTest extends TestCase {
	private Hand hand;
	private PlayerStatus playerStatus;
	
	
	public PlayerTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testPlayer() {
		Player p = new Player(1,10,true);
		assertTrue(p.getStatus()== playerStatus);
		p.setStatus(playerStatus);
		assertTrue(p.getPosition()== 1);
		p.setPosition(2);
		assertTrue(p.getPosition()== 2);
		assertTrue(p.getAmount()== 10);
		assertTrue(p.isHuman()== true);
		assertTrue(p.getHand()== hand);
		assertTrue(p.clearHand()instanceof Hand);
		assertEquals(p.toString(),"Player [position=2]");
		
		int[] stats =  {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		p.setStats(stats);
		for(int i = 0; i<stats.length;i++){
			assertTrue(p.getStats()[i] == stats[i]);
		}
		p.addStats(stats);
		for(int i=0;i<stats.length;i++){
			assertTrue(p.getStats()[i] == 2);
		}
		p.addGame(false);
		assertTrue(p.getStats()[0]==3);
		p.addGame(true);
		assertTrue(p.getStats()[20] == 3);
		
	}
}
