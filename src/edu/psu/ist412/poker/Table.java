package edu.psu.ist412.poker;

import java.util.ArrayList;
/**
 * 
 * @author KennedyBD
 *
 */
public class Table {

	private ArrayList<Card> cards = new ArrayList<Card>();
	private int pot = 0;
	private int sidepot = 0;
	private int bet = 0;
	private Player lastBetter;
	
	/**
	 * 
	 */
	public Table() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s="Table: ";
		for(int i=0;i<cards.size();i++){
			s+= cards.get(i)+"\n";
		}
		return s;
	}
	
	public void receiveDealtCard(Card c) throws Exception{
		//TODO
		if (cards.size()<=5){
			cards.add(c);
		}else{
			throw(new Exception("Too many cards."));
		}
	}
	
	public ArrayList<Card> getHand(){
		ArrayList<Card> hand = new ArrayList<Card>();
		for(int i=0;i<5;i++){
			hand.add(cards.get(i));
		}	
		return hand;
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

	public void placeBet(Player player, int amount){
		lastBetter = player;
		bet = amount;
		pot = pot + amount;
		//TODO: Not sure what to do with sidepot yet, need to review the rules.
	}

	/**
	 * @return the pot
	 */
	public int getPot() {
		return pot;
	}

	/**
	 * @return the sidepot
	 */
	public int getSidepot() {
		return sidepot;
	}

	/**
	 * @return the bet
	 */
	public int getBet() {
		return bet;
	}

	/**
	 * @return the lastBetter
	 */
	public Player getLastBetter() {
		return lastBetter;
	}
	
	
}
