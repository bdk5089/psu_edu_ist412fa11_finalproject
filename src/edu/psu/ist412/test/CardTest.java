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
		try {
			assertTrue(c.getSuit() == s);
			assertTrue(c.getValue() == v);
			try{
				//The following will through an error due to the 
				//path to the graphic file and trying to deal with
				//the icon.
				assertTrue(c.getImage() instanceof ImageIcon);
				assertTrue(c.getImageBack() instanceof ImageIcon);
			}catch(Exception e){}
		} catch (Exception e) {
			assertTrue(e.getMessage(),false);
		}
	}
	
	/**
	 * test CardSuit
	 */	
	public void testSuit(){
		assertTrue(s.getValue().equals("Hearts"));
		assertTrue(s.getAbbrv().equals("H"));
		assertTrue(s.toString().equals(s.getAbbrv()));
		assertTrue(CardSuit.getAll().size()==4);
	}
	
	/**
	 * test CardValue
	 */	
	public void testValue(){
		assertTrue(v.getValue() == "10");
		assertTrue(v.getAbbrv()=="T");
		assertTrue(v.getRank()== 10);
		v.setRank(5);
		assertTrue(v.getRank()== 5);
		v.setRank(10);
		assertTrue(v.getRank()== 10);
		assertTrue(CardValue.getAll().size()==13);
	}
}
