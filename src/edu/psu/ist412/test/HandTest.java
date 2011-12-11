package edu.psu.ist412.test;

import java.util.ArrayList;
import java.util.Map;
import edu.psu.ist412.poker.Card;
import edu.psu.ist412.poker.CardSuit;
import edu.psu.ist412.poker.CardValue;
import edu.psu.ist412.poker.Deck;
import edu.psu.ist412.poker.Hand;
import edu.psu.ist412.poker.HandData;
import edu.psu.ist412.poker.HandType;
import edu.psu.ist412.poker.Table;
import junit.framework.TestCase;

/**
 * 
 * @author KennedyBD
 *
 */
public class HandTest extends TestCase {

	public void testSetDeck(){
		try{
			Deck d = new Deck();
			Hand h = new Hand();
			h.setDeck(d);
			assertTrue(true);
		}catch(Exception e){
			assertTrue("Error", false);
		}	
	}
	
	public void testSetTable(){
		try{
			Table t = new Table();
			Hand h = new Hand();
			h.setTable(t);
			assertTrue(true);
		}catch(Exception e){
			assertTrue("Error", false);
		}	
	}
	

	public void testIsReady(){
		Hand h = new Hand();
		try {
			assertTrue(h.isReady());
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.TEN)));
			assertTrue(h.isReady());
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.JACK)));
			assertFalse(h.isReady());
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
	}

	public void testGetCards(){
		Table t = new Table();
		Hand h = new Hand();
		h.setTable(t);
		try {
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.TEN)));
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.JACK)));
			ArrayList<Card> cards = h.getCards();
			assertTrue(cards.size()>0);
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	public void testIsGreaterThan(){
		
		System.out.println("-----------------------");
		System.out.println("TESTING IS GREATER THAN");
		System.out.println("-----------------------");
		Map<HandType, HandData> p;
		Hand h1, h2;
		Table t;
		t = new Table();
		
		try {
			h1 = new Hand();
			h1.setTable(t);
			h1.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.TEN)));
			h1.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.TEN)));

			h2 = new Hand();
			h2.setTable(t);
			h2.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.KING)));
			h2.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.KING)));
			
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.FIVE)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.TWO)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.ACE)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.FIVE)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.TEN)));
			
			h1.calculateProbability();
			h2.calculateProbability();
		
			assertTrue(h1.isGreaterThan(h2));
			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		assertTrue(true);
	}
	

	public void testRoyalFlush(){
		System.out.println("-------------------");
		System.out.println("TESTING ROYAL FLUSH");
		System.out.println("-------------------");
		Map<HandType, HandData> p;
		Hand h;
		Table t;
		
		try {
			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.TEN)));
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.JACK)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.QUEEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.ACE)));
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.ROYAL_FLUSH).getProbability() == 1);

			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.TEN)));
			h.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.JACK)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.QUEEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.ACE)));
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.ROYAL_FLUSH).getProbability() == 0);
			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
	}
	

	public void testStraightFlush(){
		System.out.println("----------------------");
		System.out.println("TESTING STRAIGHT FLUSH");
		System.out.println("----------------------");
		
		Map<HandType, HandData> p;
		Hand h;
		Table t;
		
		try {
			
			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.JACK)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.QUEEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TWO)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.JACK)));	
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.STRAIGHT_FLUSH).getProbability() == 1);

			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.JACK)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.QUEEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));	
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TWO)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.JACK)));			
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.STRAIGHT_FLUSH).getProbability() == 0);
			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
	}

	public void test4Kind(){
		System.out.println("-------------------");
		System.out.println("TESTING 4 OF A KIND");
		System.out.println("-------------------");
		
		Map<HandType, HandData> p;
		Hand h;
		Table t;
		
		try {
			
			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.NINE)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.NINE)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.NINE)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.QUEEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));	
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.FOUR_KIND).getProbability() == 1);

			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.JACK)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.QUEEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));			
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.FOUR_KIND).getProbability() == 0);
			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
	}
	
	public void test3Kind(){
		System.out.println("-------------------");
		System.out.println("TESTING 3 OF A KIND");
		System.out.println("-------------------");
		
		Map<HandType, HandData> p;
		Hand h;
		Table t;
		
		try {
			
			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.NINE)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.NINE)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.QUEEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.TWO)));	
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.THREE_KIND).getProbability() == 1);

			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.JACK)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.QUEEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.TWO)));				
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.THREE_KIND).getProbability() == 0);
			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
	}
	
	public void test2Kind(){
		System.out.println("-------------------");
		System.out.println("TESTING 2 OF A KIND");
		System.out.println("-------------------");
		
		Map<HandType, HandData> p;
		Hand h;
		Table t;
		
		try {
			
			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.EIGHT)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.FIVE)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TWO)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.NINE)));
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.TWO_KIND).getProbability() == 1);

			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.NINE)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.FIVE)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.FIVE)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TWO)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.FOUR)));			
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.TWO_KIND).getProbability() == 0);
			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
	}
	
	
	public void testFullHouse(){
		System.out.println("------------------");
		System.out.println("TESTING FULL HOUSE");
		System.out.println("------------------");
		
		Map<HandType, HandData> p;
		Hand h;
		Table t;
		
		try {
			
			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.EIGHT)));	
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.FULL_HOUSE).getProbability() > 0);
			
			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.NINE)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.NINE)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TWO)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.ACE)));
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.FULL_HOUSE).getProbability() == 1);
			assertTrue(""+p.get(HandType.FULL_HOUSE).getRank(),p.get(HandType.FULL_HOUSE).getRank() == 19);

			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.EIGHT)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.FIVE)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TWO)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.FOUR)));			
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.FULL_HOUSE).getProbability() == 0);
			assertTrue(p.get(HandType.FULL_HOUSE).getRank() == 0);
			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
	}	

	public void test2Pair(){
		System.out.println("--------------");
		System.out.println("TESTING 2 PAIR");
		System.out.println("--------------");
		
		Map<HandType, HandData> p;
		Hand h;
		Table t;
		
		try {
			
			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.NINE)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TWO)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.ACE)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.FOUR)));
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.TWO_PAIR).getProbability() == 1);
			assertTrue(p.get(HandType.TWO_PAIR).getRank() == 19);
			
			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.NINE)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.FIVE)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.SIX)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TWO)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.FOUR)));			
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.TWO_PAIR).getProbability() == 0);
			assertTrue(p.get(HandType.TWO_PAIR).getRank() == 0);
			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
	}

	public void testStraight(){
		System.out.println("----------------");
		System.out.println("TESTING STRAIGHT");
		System.out.println("----------------");
		Map<HandType, HandData> p;
		Hand h;
		Table t;
		
		try {
			
			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.THREE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.FOUR)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.FIVE)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.SIX)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.SEVEN)));
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.STRAIGHT).getProbability() == 1);

			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.EIGHT)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.FIVE)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TWO)));			
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.STRAIGHT).getProbability() == 0);
			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
	}

	public void testFlush(){
		System.out.println("-------------");
		System.out.println("TESTING FLUSH");
		System.out.println("-------------");
		
		Map<HandType, HandData> p;
		Hand h;
		Table t;
		
		try {
			
			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.EIGHT)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.FIVE)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TWO)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.JACK)));	
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.FLUSH).getProbability() == 1);

			h = new Hand();
			t = new Table();
			h.setTable(t);
			h.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.NINE)));
			h.addCard(new Card(new CardSuit(CardSuit.DIAMONDS),new CardValue(CardValue.EIGHT)));
			t.addCard(new Card(new CardSuit(CardSuit.CLUBS),new CardValue(CardValue.FIVE)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TEN)));
			t.addCard(new Card(new CardSuit(CardSuit.HEARTS),new CardValue(CardValue.KING)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.TWO)));
			t.addCard(new Card(new CardSuit(CardSuit.SPADES),new CardValue(CardValue.JACK)));			
			//h.calculateProbability();	
			p = h.getProbability();
			assertTrue(p.get(HandType.FLUSH).getProbability() == 0);
			
		} catch (Exception e) {
			assertTrue(false);
			e.printStackTrace();
		}
		
	}

}
