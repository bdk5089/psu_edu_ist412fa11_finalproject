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
	
	private CardSuit s;
	private CardValue v;
	private Card c;
	
	public CardTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		try{
			s = new CardSuit(CardSuit.HEARTS);
			v = new CardValue(CardValue.TEN);
			c = new Card(s,v);
		}catch(Exception e){
			assertTrue(false);
		}
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
		assertTrue(c.getImageBack()instanceof ImageIcon);
	}
	
	/**
	 * test CardSuit
	 */	
	public void testSuit(){
		assertTrue(s.getValue().equals("Hearts"));
		assertTrue(s.getAbbrv().equals("H"));
		assertTrue(s.toString().equals(s.getAbbrv()));
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
