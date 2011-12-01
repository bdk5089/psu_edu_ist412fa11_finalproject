package edu.psu.ist412.poker;

import java.util.ArrayList;
import java.util.Observable;
/**
 * 
 * @author KennedyBD
 *
 */
public class Deck extends Observable{
	
	private static ArrayList<CardValue> cardValues;
	private static ArrayList<CardSuit> cardSuits;
	
	private ArrayList<Card> cards = new ArrayList<Card>();

	/**
	 * 
	 */
	public Deck(){
		this(true);
	}
	
	public Deck(boolean shuffle) {
		
		if (cardValues == null){
			cardValues = CardValue.getAll();
		}
		if (cardSuits == null){
			cardSuits = CardSuit.getAll();
		}
		
		for (int j=0;j<cardValues.size();j++){
		for (int i=0;i<cardSuits.size();i++){
			cards.add(new Card(cardSuits.get(i), cardValues.get(j)));
		}
		}
		/*
		for (int i=cardSuits.size()-1;i>=0;i--){
		for (int j=cardValues.size()-1;j>=0;j--){
			cards.add(new Card(cardSuits.get(i), cardValues.get(j)));
		}
		}
		*/
		
		if (shuffle){
			for (int i=0;i<10;i++){
				//System.out.println("Shuffle #"+(i+1));
				shuffle();
			}
		}
	}
	
	/**
	 * Returns string representation of Deck, multiple lines, 1 line per card.
	 */
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
			
			try {
				hand.addCard(cardToDeal);
				removeCardAt(i);
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
			
			try {
				table.addCard(cardToDeal);
				removeCardAt(i);
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