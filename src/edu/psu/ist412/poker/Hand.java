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
			
			ArrayList<Card> sorted = sortByValue(getCombined());
			int dealt = sorted.size();
			int remaining = 7-dealt;
			int decksize = 52-dealt;
			
			//Straight Flush
			//System.out.println("Calculating... Straight Flush (Same Suit, Sequential)");
			
			//Four of a Kind
			//System.out.println("Calculating... Four of a Kind");
			
			//Full House
			//System.out.println("Calculating... Full House");
			
			//Flush (Same Suit)
			//System.out.println("Calculating... Flush (Same Suit)");
			
			//Straight
			//System.out.println("Calculating... Straight (Sequential)");
			
			//Three of a Kind
			//System.out.println("Calculating... Three of a Kind");
			
			//Two Pair
			//System.out.println("Calculating... Two Pair");

			//One Pair
			//System.out.println("Calculating... One Pair");
						
			probability = .99;
		}
	}
	
	public double getProbability(){
		return probability;
	}
	
	public ArrayList<Card> getCards(){
		return cards;
	}


	private ArrayList<Card> getCombined(){
		ArrayList<Card> combined = new ArrayList<Card>();
		for (int i=0;i<this.getCards().size();i++){
			combined.add(this.getCards().get(i));
		}
		for (int i=2;i<table.getCards().size()+2;i++){
			combined.add(table.getCards().get(i-2));
		}	
		return combined;
	}
	
	private int countSuit(ArrayList<Card> collection, CardSuit s){
		int counter = 0;
		CardSuit suit;
		for (int i=0;i<collection.size();i++){
			suit = collection.get(i).getSuit();
			if (suit.getValue().equals(s.getValue())){
				counter++;
			}
		}
		return counter;
	}
	
	private int countValue(ArrayList<Card> collection, CardValue v){
		int counter = 0;
		CardValue rank;
		for (int i=0;i<collection.size();i++){
			rank = collection.get(i).getValue();
			if (rank.getRank()==v.getRank()){
				counter++;
			}
		}
		return counter;
	}
	
	private ArrayList<Card> sortByValue(ArrayList<Card> combined){
		ArrayList<Card> sorted = new ArrayList<Card>();
		for (int i=0;i<combined.size();i++){
			if (i==0){
				sorted.add(combined.get(i));
			}else{
				int j=0;
				for (j=0;j<sorted.size();j++){
					if (combined.get(i).getValue().getRank() < sorted.get(j).getValue().getRank()){
						sorted.add(j,combined.get(i));
						sorted.trimToSize();
						break;
					}
				}
				if (j==sorted.size()){
					sorted.add(combined.get(i));
					sorted.trimToSize();					
				}
			}
		}
		return sorted;
	}
	
}
