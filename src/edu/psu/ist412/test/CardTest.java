package edu.psu.ist412.test;

import edu.psu.ist412.poker.Card;
import edu.psu.ist412.poker.CardSuit;
import edu.psu.ist412.poker.CardValue;
import junit.framework.TestCase;
import javax.swing.ImageIcon;

/**
 * JUnit test for the Card, CardSuit, and CardValue classes/methods
 * @author PEdmonston, BKennedy
 */

public class CardTest extends TestCase {
	CardSuit s = new CardSuit("Hearts","H");
	CardValue v = new CardValue("10","T",10);
	Card c = new Card(s,v);
	
	public CardTest(String name) {
		super(name);
		
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * test Card
	 */	
	public void testCard(){
		assertTrue(c.getSuit()== s);
		assertTrue(c.getValue()== v);
		assertTrue(c.getImage()instanceof ImageIcon);
	}
	
	/**
	 * test CardSuit
	 */	
	public void testSuit(){
		assertTrue(s.getValue() == "Hearts");
		assertTrue(s.getAbbrv()=="H");
		assertTrue(s.toString()== s.getAbbrv());
	}
	
	/**
	 * test CardValue
	 */	
	public void testValue(){
		assertTrue(v.getValue() == "10");
		assertTrue(v.getAbbrv()=="T");
		assertTrue(v.getRank()== 10);
	}
}
