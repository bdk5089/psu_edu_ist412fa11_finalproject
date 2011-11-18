package edu.psu.ist412.poker;

import java.util.ArrayList;
import java.util.Observable;
/**
 * 
 * @author KennedyBD
 *
 */
public class Deck extends Observable{
	
	private static ArrayList<CardValue> cardValues = new ArrayList<CardValue>();
	private static ArrayList<CardSuit> cardSuits = new ArrayList<CardSuit>();
	
	private ArrayList<Card> cards = new ArrayList<Card>();

	/**
	 * 
	 */
	public Deck() {
		
		if (cardValues.size() == 0 ){
			cardValues.add(new CardValue("2","2",2));
			cardValues.add(new CardValue("3","3",3));
			cardValues.add(new CardValue("4","4",4));
			cardValues.add(new CardValue("5","5",5));
			cardValues.add(new CardValue("6","6",6));
			cardValues.add(new CardValue("7","7",7));
			cardValues.add(new CardValue("8","8",8));
			cardValues.add(new CardValue("9","9",9));
			cardValues.add(new CardValue("10","T",10));
			cardValues.add(new CardValue("Jack","J",11));
			cardValues.add(new CardValue("Queen","Q",12));
			cardValues.add(new CardValue("King","K",13));
			cardValues.add(new CardValue("Ace","A",14));
		}
		if (cardSuits.size() == 0){
			cardSuits.add(new CardSuit("Hearts","H"));
			cardSuits.add(new CardSuit("Diamonds","D"));
			cardSuits.add(new CardSuit("Clubs","C"));
			cardSuits.add(new CardSuit("Spades","S"));
		}
		
		for (int i=0;i<cardValues.size();i++){
			for (int j=0;j<cardSuits.size();j++){
				Card card = new Card(cardSuits.get(j), cardValues.get(i));
				cards.add(card);
			}
		}
		
		for (int i=0;i<10;i++){
			//System.out.println("Shuffle #"+(i+1));
			shuffle();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "Deck:\n";
		for (int i=0;i<cards.size();i++){
			s += "  "+ cards.get(i)+"\n";
		}
		return s;
	}

	
	/**
	 * @return the cards
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	/**
	 * 
	 */
	public void shuffle(int idxStart, int idxEnd ){
				
		ArrayList<Card> shuffledCards = new ArrayList<Card>();
		//System.out.println("SHUFFLING....."+idxStart+" to "+idxEnd);		
		while (cards.size() > 0){
			if (idxStart > 1){
				shuffledCards.add(cards.get(0));
				cards.remove(0);
			}else if (idxEnd > 1){
				int rand = (int) Math.floor(Math.random() * (idxEnd - idxStart + 1));
				shuffledCards.add(cards.get(rand));
				cards.remove(rand);
			}else{
				shuffledCards.add(cards.get(0));
				cards.remove(0);
			}
			cards.trimToSize();
			idxStart = Math.max(idxStart-1,1);
			idxEnd = Math.max(idxEnd-1,1);
		}
		
		while (shuffledCards.size() > 0){
			cards.add(shuffledCards.get(0));
			shuffledCards.remove(0);
			shuffledCards.trimToSize();
		}
		
	}
	public void shuffle(){
		shuffle(1,cards.size());
	}
	/**
	 * 
	 * @param hand
	 */
	public void dealCard(Hand hand){
		dealCard(hand,1);
	}
	public void dealCard(Hand hand, int i){
		Card cardToDeal = getCardAt(i);
		if (cardToDeal != null && hand.isReady()){
			removeCardAt(i);
			try {
				hand.addCard(cardToDeal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param hand
	 */
	public void dealCard(Table table){
		dealCard(table,1);
	}
	public void dealCard(Table table, int i){
		Card cardToDeal = getCardAt(i);
		if (cardToDeal != null && table.isReady()){
			removeCardAt(i);
			try {
				table.addCard(cardToDeal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * getCardAt()
	 * @param idx
	 * @return the card at position idx (must be a value from 1 to 52)
	 */
	public Card getCardAt(int idx){
		if (idx > 0 && idx <= cards.size()){
			return cards.get(idx-1);
		}else{
			return null;
		}
	}
	
	public void removeCardAt(int idx){
		if (idx > 0 && idx <= cards.size()){
			cards.remove(idx-1);
			setChanged();
			notifyObservers(this);
		}
	}
	/**
	 * getCardCount()
	 * @return the number of the cards in the deck
	 */
	public int getCardCount(){
		return cards.size();
	}
}
