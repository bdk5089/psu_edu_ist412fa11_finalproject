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
	Table tbl = new Table();
	
	public TableTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testTable() {
		CardSuit s = new CardSuit("Hearts","H");
		CardValue v = new CardValue("10","T",10);
		Card c = new Card(s,v);
		System.out.println(tbl.toString());
		assertTrue(tbl.getCards().size() == 0);
		try {
			tbl.addCard(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(tbl.getCards().size() == 1);
//		assertTrue(tbl.getFlop().size() == 3);
//		assertTrue(tbl.getTurn().size() == 4);
//		assertTrue(tbl.getRiver().size() == 5);

		
	}
}
