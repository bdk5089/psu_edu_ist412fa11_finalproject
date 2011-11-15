package edu.psu.ist412.poker;

import java.util.ArrayList;

public class Hand {

	private ArrayList<Card> cards = new ArrayList<Card>();

	/**
	 * 
	 */
	public Hand() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s="Hand: ";
		for(int i=0;i<cards.size();i++){
			s+= cards.get(i)+"\n";
		}
		return s;
	}
	
	public void receiveDealtCard(Card c) throws Exception{
		if (cards.size()<2){
			cards.add(c);
			calculateProbability();
		}else{
			throw(new Exception("Hand is full, cannot receive card."));
		}
	}
	
	public void calculateProbability(){
		//TODO
	}
	
	public ArrayList<Card> getHand(){
		ArrayList<Card> hand = new ArrayList<Card>();
		for(int i=0;i<5;i++){
			hand.add(cards.get(i));
		}	
		return hand;
	}		
	
}
