package edu.psu.ist412.poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * 
 * @author KennedyBD
 *
 */
public class Hand extends Object implements Observer{
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	private Map<String, Double> probability;
	private Table table;
	private Deck deck;
	
	public static String ROYAL_FLUSH = "Royal Flush";
	public static String STRAIGHT_FLUSH = "Straight Flush";
	public static String FULL_HOUSE = "Full House";
	public static String STRAIGHT = "Straight";
	public static String FOUR_KIND = "4 of a Kind";
	public static String THREE_KIND = "3 of a Kind";
	public static String TWO_KIND = "2 of a Kind";
	public static String TWO_PAIR = "2 Pair";
	public static String FLUSH = "Flush";
	public static String HIGH_CARD = "High Card"; //Not in probability calculation

	/**
	 * Constructor for the Hand class, used as a container
	 * for dealt cards and to calculate probabilities of 
	 * winning a particular poker hand combinations.
	 */
	public Hand() {
		super();
	}
	
	/**
	 * Returns string representation of the Hand, output of multiple lines,
	 * one for each card in the hand.
	 */
	public String toString() {
		String s="Hand: \n";
		for(int i=0;i<cards.size();i++){
			s+= "  "+cards.get(i)+"\n";
		}
		return s;
	}
	
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
	
	/**
	 * setDeck registers a deck with the hand so that when
	 * the deck deals a card, the hand will observe the change\
	 * and update its probability calculation accordingly.
	 * 	
	 * @param d
	 */
	public void setDeck(Deck d){
		deck = d;		
		deck.addObserver(this);	
	}
	
	/**
	 * setTable registers the table with the hand so that when
	 * the deck deals a card, the hand will observe the change 
	 * and update its probability calculation accordingly using
	 * the table to complete the probability calculation.
	 * 	
	 * @param d
	 */
	public void setTable(Table t){
		table = t;	
		table.addObserver(this);
	}
	
	/**
	 * isReady is a determination if the hand is ready to receive
	 * a card from a deck of cards.  If the hand has two cards then
	 * it is no longer ready to receive cards.
	 * @return
	 */
	public boolean isReady(){
		if (cards.size()<2){
			return true;
		}
		return false;
	}
	
	/**
	 * addCard allows the hand to be added to.  It must have less
	 * than two cards in the hand in order to allow a card to be added.
	 * If an attempt is made to deal a card or deal a null value to the
	 * hand then an exception is thrown.
	 * @param c
	 * @throws Exception
	 */
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
	/**
	 * Returns all the cards in the hand.  Length of the 
	 * ArrayList will be 0,1,or2.
	 * @return
	 */
	public ArrayList<Card> getCards(){
		return cards;
	}	
	/**
	 * Returns the probability map for all the types of 
	 * hands that can be formed by the hand and the table cards.
	 * @return
	 */
	public Map<String, Double> getProbability(){
		try {
			calculateProbability();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return probability;
	}
	/**
	 * Calculates the probability for nine hand formations.
	 * @throws Exception
	 */
	public void calculateProbability() throws Exception{
		if (cards.size()<2){
			throw(new Exception("Too few cards in deck to calculate probability."));
		}else{
			System.out.println("CALCULATING PROBABILITIES");
			System.out.println(this);
			System.out.println(table);
			
			probability = new HashMap<String, Double>(9);
			probability.put(Hand.ROYAL_FLUSH, calculateRoyalFlush());
			probability.put(Hand.STRAIGHT_FLUSH, calculateStraightFlush());
			probability.put(Hand.FOUR_KIND, calculate4Kind());
			probability.put(Hand.FULL_HOUSE, calculateFullHouse());
			probability.put(Hand.FLUSH, calculateFlush());
			probability.put(Hand.STRAIGHT, calculateStraight());
			probability.put(Hand.THREE_KIND, calculate3Kind());
			probability.put(Hand.TWO_PAIR, calculate2Pair());
			probability.put(Hand.TWO_KIND, calculate2Kind());
			
			Set set = probability.entrySet();
			Iterator i = set.iterator();
		    while(i.hasNext()){
		    	 Map.Entry me = (Map.Entry)i.next();
		    	 String padding = "";
		    	 for (int p=0;p<15-me.getKey().toString().length();p++){
		    		 padding+=" ";
		    	 }
		    	 System.out.println("Probability of "+ me.getKey() +padding+  " : " + me.getValue() );
		    }

		}
		System.out.println("");
	}
	
	/*
	 * Calculates the probability of forming a Royal Straight Flush, 
	 * aka, Royal Flush - 
	 * 5 sequential cards, Ten through Ace, of the same suit.
	 */
	private double calculateRoyalFlush() throws Exception{
		//System.out.println("Calculating... Royal Straight Flush (Same Suit, T-A, Sequential)");
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = CardSuit.getAll();
		ArrayList<CardValue> cardValues = CardValue.getAll();
		
		int numValue = 0;
		double temp = 0;
		double sumProbability = 0;
		if (dealt == 2){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue("Ace","A",14),cardSuits.get(j));
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue == 2){
					temp = (double)(1*DMath.combination(decksize-3,2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue ==1){
					temp = (double)(1*DMath.combination(decksize-4,1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue ==0){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 3){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue("Ace","A",14),cardSuits.get(j));
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue == 3){
					temp = (double)(1*DMath.combination(decksize-2,2)
							)/(double)DMath.combination(decksize, remaining);
				} else if (numValue == 2){
					temp = (double)(1*DMath.combination(decksize-3,1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue ==1){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 4){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue("Ace","A",14),cardSuits.get(j));
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue == 4){
					temp = (double)(1*DMath.combination(decksize-1,2)
							)/(double)DMath.combination(decksize, remaining);
				} else if (numValue == 3){
					temp = (double)(1*DMath.combination(decksize-2,1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue ==2){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 5){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue("Ace","A",14),cardSuits.get(j));
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue >= 5){
					temp = 1;
				} else if (numValue == 4){
					temp = (double)(1*DMath.combination(decksize-1,1)
							)/(double)DMath.combination(decksize, remaining);
				} else if (numValue == 3){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 6){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue("Ace","A",14),cardSuits.get(j));
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue >= 5){
					temp = 1;
				} else if (numValue == 4){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 7){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue("Ace","A",14),cardSuits.get(j));
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue >= 5){
					temp = 1;
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}
		//System.out.println("Probability for Royal Flush is "+sumProbability);	
		return sumProbability;
	}
	
	/*
	 * Calculates the probability of forming a Straight Flush - 
	 * 5 sequential cards of the same suit.
	 */
	private double calculateStraightFlush() throws Exception{
		//System.out.println("Calculating... Straight Flush (Same Suit, Sequential)");
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = CardSuit.getAll();
		ArrayList<CardValue> cardValues = CardValue.getAll();
		
		int numValue = 0;
		double temp = 0;
		double sumProbability = 0;

		cardValues.add(0,new CardValue(CardValue.ACE));
		for (int i=0;i<cardValues.size()-5;i++){
		CardValue startCard = cardValues.get(i);
		CardValue endCard = cardValues.get(i+4);
		//System.out.println(startCard.getValue()+" to "+endCard.getValue());
		if (dealt == 2){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard,cardSuits.get(j));
				//System.out.println(cardSuits.get(j).getValue()+" "+startCard.getValue()+" to "+endCard.getValue()+" :"+numValue);
				if (numValue == 2){
					temp = (double)(1*DMath.combination(decksize-3,2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue ==1){
					temp = (double)(1*DMath.combination(decksize-4,1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue ==0){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 3){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard,cardSuits.get(j));
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue == 3){
					temp = (double)(1*DMath.combination(decksize-2,2)
							)/(double)DMath.combination(decksize, remaining);
				} else if (numValue == 2){
					temp = (double)(1*DMath.combination(decksize-3,1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue ==1){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 4){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard,cardSuits.get(j));
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue == 4){
					temp = (double)(1*DMath.combination(decksize-1,2)
							)/(double)DMath.combination(decksize, remaining);
				} else if (numValue == 3){
					temp = (double)(1*DMath.combination(decksize-2,1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue ==2){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 5){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard,cardSuits.get(j));
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue >= 5){
					temp = 1;
				} else if (numValue == 4){
					temp = (double)(1*DMath.combination(decksize-1,1)
							)/(double)DMath.combination(decksize, remaining);
				} else if (numValue == 3){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 6){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard,cardSuits.get(j));
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue >= 5){
					temp = 1;
				} else if (numValue == 4){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 7){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard,cardSuits.get(j));
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue >= 5){
					temp = 1;
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}
		}
		cardValues.remove(0);
		cardValues.trimToSize();
		//System.out.println("Probability for Straight Flush is "+sumProbability);						
		return sumProbability;	
	}
	
	/*
	 * Calculates the probability of having 4 of a kind -
	 * 4 cards of the same value all different suits.
	 */
	private double calculate4Kind() throws Exception {
		//System.out.println("Calculating... Four of a Kind");
		
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = CardSuit.getAll();
		ArrayList<CardValue> cardValues = CardValue.getAll();
		
		int numValue = 0;
		double temp = 0;
		double sumProbability = 0;

		if (dealt == 2){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),3)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,3)*DMath.combination(decksize-(4-numValue),2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 0){
					temp = (double)(DMath.combination(4-numValue,4)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 3){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue == 3){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),3)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,3)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 0){
					temp = (double)(DMath.combination(4-numValue,4)*DMath.combination(decksize-(4-numValue),0)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 4){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue == 4){
					temp = 1;
				}else if (numValue == 3){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,4)*DMath.combination(decksize-(4-numValue),0)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 5){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 4){
					temp = 1;
				}else if (numValue == 3){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,2)
							)/(double)DMath.combination(decksize, remaining);
				}else {
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 6){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 4){
					temp = 1;
				}else if (numValue == 3){
					temp = ((double)DMath.combination((4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 7){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 4){
					temp = 1;
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}
		//System.out.println("Probability for 4 of Kind is "+sumProbability);	
		return sumProbability;
	}
	
	/*
	 * Calculates the probability of having a full house -
	 * 3 of one kind and two of another kind, potential to have
	 * 3,2,2 or 3,3,1 or 3,2,1,1
	 */
	//TODO
	private double calculateFullHouse() throws Exception {
		//System.out.println("Calculating... Full House");
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = CardSuit.getAll();
		ArrayList<CardValue> cardValues = CardValue.getAll();
		
		int numValue = 0;
		double temp = 0;			
		double sumProbability = 0;

		if (dealt == 2){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),3)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,3)*DMath.combination(decksize-(4-numValue),2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 0){
					temp = (double)(DMath.combination(4-numValue,4)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 3){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue == 3){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),3)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,3)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 0){
					temp = (double)(DMath.combination(4-numValue,4)*DMath.combination(decksize-(4-numValue),0)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 4){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue == 4){
					temp = 1;
				}else if (numValue == 3){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,4)*DMath.combination(decksize-(4-numValue),0)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 5){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 4){
					temp = 1;
				}else if (numValue == 3){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,2)
							)/(double)DMath.combination(decksize, remaining);
				}else {
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 6){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 4){
					temp = 1;
				}else if (numValue == 3){
					temp = ((double)DMath.combination((4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 7){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 4){
					temp = 1;
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}
		//System.out.println("Probability for Full House is "+sumProbability);		
		return sumProbability;
	}
	
	/*
	 * Calculates the probability of having a flush -
	 * 5 cards of the same suit, all different values.
	 */
	private double calculateFlush() throws Exception {
		//System.out.println("Calculating... Flush");
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = CardSuit.getAll();
		ArrayList<CardValue> cardValues = CardValue.getAll();
		
		int numSuit = 0;
		double temp = 0;		
		double sumProbability = 0;

		if (dealt == 2){
			for (int i=0;i<cardSuits.size();i++){
				numSuit = countSuit(sorted, cardSuits.get(i));
				//System.out.println(cardSuits.get(i));
				if (numSuit == 2){
					temp = (double)(DMath.combination(13-numSuit,3)*DMath.combination(decksize-(13-numSuit),2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numSuit == 1){
					temp = (double)(DMath.combination(13-numSuit,4)*DMath.combination(decksize-(13-numSuit),1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numSuit == 0){
					temp = (double)(DMath.combination(13-numSuit,5))/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 3){
			for (int i=0;i<cardSuits.size();i++){
				numSuit = countSuit(sorted, cardSuits.get(i));
				//System.out.println(cardSuits.get(i));
				if (numSuit == 3){
					temp = (double)(DMath.combination(13-numSuit,2)*DMath.combination(decksize-(13-numSuit),2)
							+ DMath.combination(13-numSuit,3)*DMath.combination(decksize-(13-numSuit),1)
							+ DMath.combination(13-numSuit,4)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numSuit == 2){
					temp = (double)(DMath.combination(13-numSuit,3)*DMath.combination(decksize-(13-numSuit),1)
							+ DMath.combination(13-numSuit,4)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numSuit == 1){
					temp = (double)(DMath.combination(13-numSuit,4)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 4){
			for (int i=0;i<cardSuits.size();i++){
				numSuit = countSuit(sorted, cardSuits.get(i));
				//System.out.println(cardSuits.get(i));
				if (numSuit == 4){
					temp = (double)(DMath.combination(13-numSuit,1)*DMath.combination(decksize-(13-numSuit),2)
							+ DMath.combination(13-numSuit,2)*DMath.combination(decksize-(13-numSuit),1)
							+ DMath.combination(13-numSuit,3)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numSuit == 3){
					temp = (double)(DMath.combination(13-numSuit,2)*DMath.combination(decksize-(13-numSuit),1)
							+ DMath.combination(13-numSuit,3)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numSuit == 2){
					temp = (double)(DMath.combination(13-numSuit,3)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 5){
			for (int i=0;i<cardSuits.size();i++){
				numSuit = countSuit(sorted, cardSuits.get(i));
				//System.out.println(cardSuits.get(i));
				if (numSuit >= 5){
					temp = 1;
				}else if (numSuit == 4){
					temp = (double)(DMath.combination(13-numSuit,1)*DMath.combination(decksize-(13-numSuit),1)
							+ DMath.combination(13-numSuit,2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numSuit == 3){
					temp = (double)(DMath.combination(13-numSuit,2)
							)/(double)DMath.combination(decksize, remaining);
				}else {
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 6){
			for (int i=0;i<cardSuits.size();i++){
				numSuit = countSuit(sorted, cardSuits.get(i));
				//System.out.println(cardSuits.get(i));
				if (numSuit >= 5){
					temp = 1;
				}else if (numSuit == 4){
					temp = ((double)DMath.combination((13-numSuit),1)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}else if (dealt == 7){
			for (int i=0;i<cardSuits.size();i++){
				numSuit = countSuit(sorted, cardSuits.get(i));
				//System.out.println(cardSuits.get(i));
				if (numSuit >= 5){
					temp = 1;
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
			}
		}
		//System.out.println("Probability for Flush is "+sumProbability);		
		return sumProbability;
	}
	
	/*
	 * Calculates the probability of having a straight -
	 * 5 sequential cards of different suits.
	 * Ace through 5 ... 10 through Ace
	 */
	private double calculateStraight() throws Exception{
		//System.out.println("Calculating... Straight");
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = CardSuit.getAll();
		ArrayList<CardValue> cardValues = CardValue.getAll();
		
		int numValue = 0;
		double temp = 0;		
		double sumProbability= 0;

		cardValues.add(0,new CardValue(CardValue.ACE));
		for (int i=0;i<cardValues.size()-5;i++){
		CardValue startCard = cardValues.get(i);
		CardValue endCard = cardValues.get(i+4);
		//System.out.println(startCard.getValue()+" to "+endCard.getValue());
		if (dealt == 2){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard);
				//System.out.println(cardSuits.get(j).getValue()+" "+startCard.getValue()+" to "+endCard.getValue()+" :"+numValue);
				if (numValue == 2){
					temp = (double)(1*DMath.combination(decksize-3,2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue ==1){
					temp = (double)(1*DMath.combination(decksize-4,1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue ==0){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 3){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard);
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue == 3){
					temp = (double)(1*DMath.combination(decksize-2,2)
							)/(double)DMath.combination(decksize, remaining);
				} else if (numValue == 2){
					temp = (double)(1*DMath.combination(decksize-3,1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue ==1){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 4){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard);
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue == 4){
					temp = (double)(1*DMath.combination(decksize-1,2)
							)/(double)DMath.combination(decksize, remaining);
				} else if (numValue == 3){
					temp = (double)(1*DMath.combination(decksize-2,1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue ==2){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 5){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard);
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue >= 5){
					temp = 1;
				} else if (numValue == 4){
					temp = (double)(1*DMath.combination(decksize-1,1)
							)/(double)DMath.combination(decksize, remaining);
				} else if (numValue == 3){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 6){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard);
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue >= 5){
					temp = 1;
				} else if (numValue == 4){
					temp = (double)(1
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 7){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard);
				//System.out.println(cardSuits.get(j).getValue()+" 10 to Ace :"+numValue);
				if (numValue >= 5){
					temp = 1;
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}
		}
		cardValues.remove(0);
		cardValues.trimToSize();
		//System.out.println("Probability for Straight is "+sumProbability);
		return sumProbability;
	}
	
	/*
	 * Calculates the probability of having 3 of a kind -
	 * 3 cards of the same value of different suits.
	 * 3,1,1,1,1; if 3,2,1,1,1 then its a full house.
	 */
	private double calculate3Kind() throws Exception {
		//System.out.println("Calculating... Three of a Kind");
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = CardSuit.getAll();
		ArrayList<CardValue> cardValues = CardValue.getAll();
		
		int numValue = 0;
		double temp = 0;	
		double sumProbability = 0;

		if (dealt == 2){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),4)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),3)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 0){
					temp = (double)(DMath.combination(4-numValue,3)*DMath.combination(decksize-(4-numValue),2)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 3){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 3){
					temp = 1;
				}else if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),3)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 0){
					temp = (double)(DMath.combination(4-numValue,3)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 4){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 3){
					temp = 1;
				}else if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 0){
					temp = (double)(DMath.combination(4-numValue,3)*DMath.combination(decksize-(4-numValue),0)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 5){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 3){
					temp = 1;
				}else if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),0)
							)/(double)DMath.combination(decksize, remaining);
				}else {
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 6){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 3){
					temp = 1;
				}else if (numValue == 2){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),0)
							)/(double)DMath.combination(decksize, remaining);
				}else {
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 7){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 3){
					temp = 1;
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}
		//System.out.println("Probability for 3 of Kind is "+sumProbability);
		return sumProbability;
	}
	
	/*
	 * Calculates the probability of having 2 pair - 
	 * Two sets of two of a kind, 2,2,1,1,1 or 2,2,2,1
	 */
	//TODO	
	private double calculate2Pair() throws Exception {
		
		return 0;
	}
	
	/*
	 * Calculates the probability of having 1 set of 2 -
	 * Two cars of the same value, 2,1,1,1,1,1; if 2,2,1,1,1 then
	 * it becomes two pair.
	 */
	private double calculate2Kind() throws Exception {
		//System.out.println("Calculating... One Pair");
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = CardSuit.getAll();
		ArrayList<CardValue> cardValues = CardValue.getAll();
		
		int numValue = 0;
		double temp = 0;
		double sumProbability = 0;

		if (dealt == 2){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 2){
					temp = 1;
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),4)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 0){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),3)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 3){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 2){
					temp = 1;
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),3)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 0){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),2)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 4){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 2){
					temp = 1;
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),2)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 0){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 5){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 2){
					temp = 1;
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (numValue == 0){
					temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(4-numValue),0)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 6){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 2){
					temp = 1;
				}else if (numValue == 1){
					temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(4-numValue),0)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}else if (dealt == 7){
			for (int i=0;i<cardValues.size();i++){
				numValue = countValue(sorted, cardValues.get(i));
				//System.out.println(cardValues.get(i)+" "+num);
				if (numValue >= 2){
					temp = 1;
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = Math.min(1,sumProbability + temp);
			}
		}
		//System.out.println("Probability for 2 of Kind is "+sumProbability);	
		return sumProbability;
	}
	
	/*
	 * Returns an ArrayList of card combined from the 
	 * hand and from the registered table.
	 */
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
	
	/*
	 * Returns a count of how many cards in the collection
	 * that have the same suit s.  Used for Flush calculations.
	 */
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
	
	/*
	 * Returns a count of how many cards in the collection that
	 * have the same value v.  Used in 4, 3, 2 of a kind, full house,
	 * and two pair calculations.
	 */
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
	
	/*
	 * Returns a count of how many cards in the collection that
	 * have the same value v and the same suit.  Used in flush
	 * and straight calculations.
	 */	
	private int countValue(ArrayList<Card> collection, CardValue v, CardSuit suit){
		int counter = 0;
		CardValue rank;
		for (int i=0;i<collection.size();i++){
			rank = collection.get(i).getValue();
			if (rank.getRank()==v.getRank() && collection.get(i).getSuit().getAbbrv().equals(suit.getAbbrv())){
				counter++;
			}
		}
		return counter;
	}
	
	/*
	 * Returns a count of how many cards in the collection that
	 * have are "between" the cardValues vStart and vEnd inclusive
	 * Used in straight calculations.
	 */	
	private int countValueRange(ArrayList<Card> collection, CardValue vStart, CardValue vEnd) throws Exception{
		//non-suited
		int counter = 0;
	
		ArrayList<CardValue> cardValues = CardValue.getAll();
		cardValues.add(0,new CardValue(CardValue.ACE));

		
		for (int i=0;i<cardValues.size();i++){
			CardValue card = cardValues.get(i);
			if (cardValues.get(i).getAbbrv().equals("A")){
				if (card.getRank(true)>=vStart.getRank(true) && card.getRank(true)<=vEnd.getRank()){
					int num = countValue(collection, cardValues.get(i));
					if (num > 0){
						counter++;
					}
				}
			}else{
				if (card.getRank()>=vStart.getRank() && card.getRank()<=vEnd.getRank()){
					int num = countValue(collection, cardValues.get(i));
					if (num > 0){
						counter++;
					}
				}
			}
		}
		return counter;
	}

	/*
	 * Returns a count of how many cards in the collection that
	 * have are "between" the cardValues vStart and vEnd inclusive
	 * and have the same suit s.
	 * Used in straight flush calculations.
	 */		
	private int countValueRange(ArrayList<Card> collection, CardValue vStart, CardValue vEnd, CardSuit suit) throws Exception{
		int counter = 0;
		
		ArrayList<CardValue> cardValues = CardValue.getAll();
		cardValues.add(0,new CardValue(CardValue.ACE));
		
		for (int i=0;i<cardValues.size();i++){
			CardValue card = cardValues.get(i);
			if (cardValues.get(i).getAbbrv().equals("A")){
				if (card.getRank(true)>=vStart.getRank(true) && card.getRank(true)<=vEnd.getRank()){
					int num = countValue(collection, cardValues.get(i), suit);
					if (num > 0){
						counter++;
					}
				}
			}else{
				if (card.getRank()>=vStart.getRank() && card.getRank()<=vEnd.getRank()){
					int num = countValue(collection, cardValues.get(i), suit);
					if (num > 0){
						counter++;
					}
				}
			}
		}
		return counter;
	}	
	
	/*
	 * Used to sort the collection by Value for straight calculations.
	 */
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
	
	/*
	 * Used to sort the collection by number of cards of a particular value.
	 * The card with the most is listed first.
	 */
	private ArrayList<Card> sortByCount(ArrayList<Card> combined){
		ArrayList<Card> sorted = new ArrayList<Card>();
		for (int i=0;i<combined.size();i++){
			if (i==0){
				sorted.add(combined.get(i));
			}else{
				int j=0;
				for (j=0;j<sorted.size();j++){
					if (countValue(combined, combined.get(i).getValue()) > countValue(sorted, sorted.get(j).getValue())){
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