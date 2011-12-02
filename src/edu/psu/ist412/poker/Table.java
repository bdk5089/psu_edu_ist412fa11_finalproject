package edu.psu.ist412.poker;

import java.util.ArrayList;
import java.util.Observable;

/**
* 
* @author KennedyBD
*
*/
public class Table extends Observable {

	private ArrayList<Card> cards;
	
	/**
	 * Instantiates an ArrayList of card objects 
	 * that is empty.  Must use addCard method to 
	 * build the contents of the table. 
	 */
	public Table() {
		super();
		cards = new ArrayList<Card>();
	}

	/**
	 * Returns string representation of Table. Multiple lines output,
	 * one line for each card on the table.
	 */
	@Override
	public String toString() {
		String s="Table: \n";
		for(int i=0;i<cards.size();i++){
			s+= "  "+cards.get(i)+"\n";
		}
		return s;
	}
	
	/**
	 * Determines if the table is ready to receive a card.
	 * If the table has 5 cards it can no longer receive cards and
	 * therefore is no longer ready.
	 * @return
	 */
	public boolean isReady(){
		if (cards.size()<5){
			return true;
		}
		return false;
	}
	
	/**
	 * Add card to the table, if card added makes
	 * the table of size 3 or greater than all objects
	 * observing this object are notified that it 
	 * has been changed.
	 * @param c
	 * @throws Exception
	 */
	public void addCard(Card c) throws Exception{
		if (c == null){
			throw(new Exception("Hand cannot receive null card."));
		}
		if (isReady()){
			cards.add(c);
			if (cards.size()>=3){
				setChanged();
				notifyObservers(this);
			}
		}else{
			throw(new Exception("Hand is full, cannot receive card."));
		}
	}
	
	/**
	 * Return the cards ArrayList
	 * @return
	 */
	public ArrayList<Card> getCards(){
		return cards;
	}	
	
	/**
	 * Return the first 3 cards on the table.
	 * @return
	 */
	public ArrayList<Card> getFlop(){
		ArrayList<Card> flop = new ArrayList<Card>();
		for(int i=0;i<3;i++){
			flop.add(cards.get(i));
		}	
		return flop;
	}
	
	/**
	 * Return the 4th card of the cards on the table
	 * as a single element ArrayList
	 * @return
	 */
	public ArrayList<Card> getTurn(){
		ArrayList<Card> turn = new ArrayList<Card>();
		turn.add(cards.get(3));	
		return turn;
	}
	
	/**
	 * Return the 5th card of the 5 cards in the list
	 * as a single element ArrayList
	 * @return
	 */
	public ArrayList<Card> getRiver(){
		ArrayList<Card> river = new ArrayList<Card>();
		river.add(cards.get(4));	
		return river;
	}
}
