package edu.psu.ist412.test;

import junit.framework.TestCase;
import edu.psu.ist412.poker.Hand;
import edu.psu.ist412.poker.Player;
import edu.psu.ist412.poker.PlayerStatus;

/**
 * JUnit test for the Player class/methods
 * @author PEdmonston
 */

public class PlayerTest extends TestCase {
//	private Hand hand;
	private PlayerStatus playerStatus;
	Player p = new Player(1,10,true);
	
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
//??		p.setStatus(ACTIVE);
		assertTrue(p.getStatus()== playerStatus);		
		assertTrue(p.getPosition()== 1);
		assertTrue(p.getAmount()== 10);
		assertTrue(p.isHuman()== true);
		assertTrue(p.clearHand()instanceof Hand);
	}
}
