package edu.psu.ist412.test;

import edu.psu.ist412.poker.Card;
import edu.psu.ist412.poker.Deck;
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
	
}
