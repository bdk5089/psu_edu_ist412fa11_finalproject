package edu.psu.ist412.poker;

import java.util.ArrayList;
import java.util.Observable;
/**
 * 
 * @author KennedyBD
 *
 */
public class Table extends Observable{

	private ArrayList<Card> cards = new ArrayList<Card>();
	/**
	 * 
	 */
	public Table() {
		super();
	}

	/* (non-Javadoc)s
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s="Table: \n";
		for(int i=0;i<cards.size();i++){
			s+= "  "+cards.get(i)+"\n";
		}
		return s;
	}

	public boolean isReady(){
		if (cards.size()<5){
			return true;
		}
		return false;
	}
	
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
	
	public ArrayList<Card> getCards(){
		return cards;
	}	
	
	public ArrayList<Card> getFlop(){
		ArrayList<Card> flop = new ArrayList<Card>();
		for(int i=0;i<3;i++){
			flop.add(cards.get(i));
		}	
		return flop;
	}
	
	public ArrayList<Card> getTurn(){
		ArrayList<Card> turn = new ArrayList<Card>();
		turn.add(cards.get(3));	
		return turn;
	}
	
	public ArrayList<Card> getRiver(){
		ArrayList<Card> river = new ArrayList<Card>();
		river.add(cards.get(4));	
		return river;
	}

	
}
