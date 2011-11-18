package edu.psu.ist412.poker;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Hand extends Object implements Observer{

	private ArrayList<Card> cards = new ArrayList<Card>();
	private double probability = 0;
	private Table table;
	private Deck deck;
	
	public Hand() {
		super();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s="Hand: \n";
		for(int i=0;i<cards.size();i++){
			s+= "  "+cards.get(i)+"\n";
		}
		return s;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Table){
			System.out.println("Table has been added to...");
		}
		if (arg instanceof Deck){
			System.out.println("Deck has been removed from...");
			try {
				calculateProbability();
			} catch (Exception e) {}
		}
	}
		
	public void setDeck(Deck d){
		deck = d;		
		deck.addObserver(this);	
	}
	public void setTable(Table t){
		table = t;	
		table.addObserver(this);
	}
	
	public boolean isReady(){
		if (cards.size()<2){
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
		}else{
			throw(new Exception("Hand is full, cannot receive card."));
		}
	}
	
	public void calculateProbability() throws Exception{
		if (cards.size()<2){
			throw(new Exception("Too few cards in deck to calculate probability."));
		}else{
			System.out.println("CALCULATING PROBABILITIES");
			System.out.println(this);
			System.out.println(table);
			//System.out.println(deck);
			/*
			//Straight Flush
			System.out.println("Calculating... Straight Flush (Same Suit, Sequential)");
			
			//Four of a Kind
			System.out.println("Calculating... Four of a Kind");
			
			//Full House
			System.out.println("Calculating... Full House");
			
			//Flush (Same Suit)
			System.out.println("Calculating... Flush (Same Suit)");
			
			//Straight
			System.out.println("Calculating... Straight (Sequential)");
			
			//Three of a Kind
			System.out.println("Calculating... Three of a Kind");
			
			//Two Pair
			System.out.println("Calculating... Two Pair");
			
			//One Pair
			System.out.println("Calculating... One Pair");
			
			*/
			if (cards.get(0).getValue().equals(cards.get(1).getValue())){
				System.out.println("Already have same value (pair)");
			}
			if (cards.get(0).getSuit().equals(cards.get(1).getSuit())){
				System.out.println("Already have same suit (start of flush)");
			}
			if (Math.abs(cards.get(0).getValue().getRank() - cards.get(1).getValue().getRank())==1){
				System.out.println("Already have sequential values (start of straight)");
			}
			if (!(cards.get(0).getSuit().equals(cards.get(1).getSuit()))
				&& !(cards.get(0).getValue().equals(cards.get(1).getValue()))){
				System.out.println("Different suits and values");
			}
			probability = .99;
		}
	}
	
	public double getProbability(){
		return probability;
	}
	
	public ArrayList<Card> getCards(){
		return cards;
	}


	
	
}
