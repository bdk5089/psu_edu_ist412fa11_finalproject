package edu.psu.ist412.test;

import junit.framework.TestCase;
import edu.psu.ist412.poker.Card;
import edu.psu.ist412.poker.CardSuit;
import edu.psu.ist412.poker.CardValue;
import edu.psu.ist412.poker.Table;
/**
 * JUnit test for the Table class/methods
 * @author PEdmonston
 */
public class TableTest extends TestCase {
	public Table tbl;
	
	public TableTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		tbl = new Table();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testTable() {
		try{
			assertTrue(tbl.getCards().size() == 0);
			
			tbl.addCard(new Card(CardSuit.CLUBS,CardValue.TWO));
			tbl.addCard(new Card(CardSuit.CLUBS,CardValue.THREE));
			tbl.addCard(new Card(CardSuit.CLUBS,CardValue.FOUR));
			assertTrue(tbl.getCards().size() == 3);
			assertTrue(tbl.getFlop().size() == 3);
			
			tbl.addCard(new Card(CardSuit.CLUBS,CardValue.FIVE));
			assertTrue(tbl.getCards().size() == 4);
			assertTrue(tbl.getTurn().size() == 1);
			assertTrue(tbl.getTurn().get(0).getValue().getAbbrv().equals("5"));
			assertTrue(tbl.getTurn().get(0).getSuit().getAbbrv().equals("C"));
			
			tbl.addCard(new Card(CardSuit.CLUBS,CardValue.SIX));
			assertTrue(tbl.getCards().size() == 5);
			assertTrue(tbl.getRiver().size() == 1);
			assertTrue(tbl.getRiver().get(0).getValue().getAbbrv().equals("6"));
			assertTrue(tbl.getRiver().get(0).getSuit().getAbbrv().equals("C"));
			
			
		}catch(Exception e){
			assertTrue("ERROR", false);
		}
		
	}
}
