package edu.psu.ist412.test;

import edu.psu.ist412.poker.Card;
import edu.psu.ist412.poker.Deck;
import edu.psu.ist412.poker.Hand;
import edu.psu.ist412.poker.Table;
import junit.framework.TestCase;

public class DeckTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDeck(){
		System.out.println("Running test .. testDeck()");
		Deck deck = new Deck();
		assertTrue(deck.getCards().size() == 52);
	}
	
	public void test10Shuffles(){
		for (int i=0;i<10;i++){
			testShuffle();
		}
	}
	
	public void testShuffle(){
		System.out.println("Running test .. testShuffle()");
		Deck deck = new Deck();
		for (int i=0;i<10;i++){
			deck.shuffle();
			Card c11 = deck.getCardAt(1);
			Card c12 = deck.getCardAt(52);
			Card c13 = deck.getCardAt(26);
			deck.shuffle();
			Card c21 = deck.getCardAt(1);
			Card c22 = deck.getCardAt(52);
			Card c23 = deck.getCardAt(26);
			assertFalse(c11 == c21 && c12 == c22 && c13 == c23);
		}
	}
	
	public void testToString(){
		Deck deck = new Deck();
		assertTrue(deck.toString() instanceof String);
	}
	
	public void testGetCards(){
		Deck deck = new Deck();
		assertTrue(deck.getCards().size() == 52);
	}
	
	public void testDealCard(){
		Deck deck = new Deck();
		Hand h = new Hand();
		Table t = new Table();
		
		//tests all 4 versions of the dealCard method.
		deck.dealCard(h);
		deck.dealCard(h,51);
		assertTrue(deck.getCards().size()==50);
		assertTrue(h.getCards().size()==2);

		deck.dealCard(t);
		deck.dealCard(t,49);
		assertTrue(deck.getCards().size()==48);
		assertTrue(t.getCards().size()==2);
	}
	
	public void testGetCardAt(){
		Deck deck = new Deck();
		assertTrue(deck.getCardAt(1) instanceof Card);	
		assertNull(deck.getCardAt(0));
		assertNull(deck.getCardAt(53));
	}
	
	public void testGetCardCount(){
		Deck deck = new Deck();
		assertTrue(deck.getCardCount() == 52);	
		deck.removeCardAt(1);
		assertTrue(deck.getCardCount() == 51);	
	}
	
	public void testRemoveCardAt(){
		Deck deck = new Deck();
		assertTrue(deck.getCardCount() == 52);	
		Card c26_1 = deck.getCardAt(26);
		deck.removeCardAt(26);
		Card c26_2 = deck.getCardAt(26);
		assertTrue(c26_1 != c26_2);
		deck.removeCardAt(27);
		assertTrue(deck.getCardCount() == 50);			
	}
	
}
