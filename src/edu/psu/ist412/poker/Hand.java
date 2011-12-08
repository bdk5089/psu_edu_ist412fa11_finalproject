package edu.psu.ist412.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author KennedyBD
 *
 */
public class Hand extends Object implements Observer{
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	private Map<HandType, HandData> probability;
	private Table table;
	private Deck deck;
	
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
	@Override
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
	public Map<HandType, HandData> getProbability(){
		try {
			calculateProbability();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return probability;
	}
	
	/**
	 * 
	 * @param hand
	 * @return
	 */
	public boolean isGreaterThan(Hand hand){
		//TODO determine if this.hand is greater than hand
		return false;
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
			System.out.println("HAND: "+this);
			System.out.println("TABLE: "+ table);
			
			for(HandType handType : HandType.values()) {
				HandType h = HandType.ROYAL_FLUSH;
				
				switch(h) {
				case ROYAL_FLUSH:
					HandData.setProbability(calculateRoyalFlush());
					break;
				case STRAIGHT_FLUSH:
					HandData.setProbability(Math.max(0,calculateStraightFlush()-calculateRoyalFlush()));
					break;
				case FOUR_KIND:
					HandData.setProbability(calculate4Kind());
					break;
				case FULL_HOUSE:
					HandData.setProbability(calculateFullHouse());
					break;
				case FLUSH:
					HandData.setProbability(Math.max(0,calculateFlush()-calculateStraightFlush()));
					break;
				case STRAIGHT:
					HandData.setProbability(Math.max(0,calculateStraight()-calculateStraightFlush()));
					break;
				case THREE_KIND:
					HandData.setProbability(Math.max(0,calculate3Kind()-calculate4Kind()));
					break;
				case TWO_PAIR:
					HandData.setProbability(calculate2Pair());
					break;
				case TWO_KIND:
					HandData.setProbability(Math.max(0,calculate2Kind()-calculate3Kind()-calculate2Pair()));
					break;
				}
			}
			probability = new HashMap<HandType, HandData>(9);
			probability.put(HandType.ROYAL_FLUSH, HandData(HandType.ROYAL_FLUSH).getProbability();
			
			probability.put(HandType.STRAIGHT_FLUSH, HandData.setProbability(Math.max(0,calculateStraightFlush()-calculateRoyalFlush())));
			probability.put(HandType.FOUR_KIND, HandData.setProbability(calculate4Kind()));
			probability.put(HandType.FULL_HOUSE, HandData.setProbability(calculateFullHouse()));
			probability.put(HandType.FLUSH, HandData.setProbability(Math.max(0,calculateFlush()-calculateStraightFlush())));
			probability.put(HandType.STRAIGHT, HandData.setProbability(Math.max(0,calculateStraight()-calculateStraightFlush())));
			probability.put(HandType.THREE_KIND, HandData.setProbability(Math.max(0,calculate3Kind()-calculate4Kind())));
			probability.put(HandType.TWO_PAIR, HandData.setProbability(calculate2Pair()));
			probability.put(HandType.TWO_KIND, HandData.setProbability(Math.max(0,calculate2Kind()-calculate3Kind()-calculate2Pair())));
			
			
			
		    for (Map.Entry<HandType, HandData> entry: probability.entrySet()) {
		        String padding = "";
		    	for (int p=0;p<15-entry.getKey().toString().length();p++){
		    		 padding+=" ";
		    	}
		    	if(entry.getValue() != 0)
		    	System.out.println("Probability of "+ entry.getKey() +padding+  " : " + entry.getValue() );
		    }
		    System.out.println("");
		}
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
		
		int numValue = 0;
		double temp = 0;
		double sumProbability = 0;
		for (int j=0;j<cardSuits.size();j++){
		
			if (dealt == 2){
				numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue(CardValue.ACE),cardSuits.get(j));
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
			}else if (dealt == 3){
				numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue(CardValue.ACE),cardSuits.get(j));
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
			}else if (dealt == 4){
				numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue(CardValue.ACE),cardSuits.get(j));
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
			}else if (dealt == 5){
				numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue(CardValue.ACE),cardSuits.get(j));
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
			}else if (dealt == 6){
				numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue(CardValue.ACE),cardSuits.get(j));
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
			}else if (dealt == 7){
				numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue(CardValue.ACE),cardSuits.get(j));
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
	 * @return 0 if does not have Royal Flush, return value
	 * greater than 0 pertaining to high card of Royal Flush.
	 */
	private int hasRoyalFlush() throws Exception{
		ArrayList<Card> sorted = sortByValue(getCombined());
		ArrayList<CardSuit> cardSuits = CardSuit.getAll();
		
		int numValue = 0;
		
		for (int j=0;j<cardSuits.size();j++){
			numValue = countValueRange(sorted, new CardValue(CardValue.TEN), new CardValue(CardValue.ACE),cardSuits.get(j));
			if (numValue >= 5){
				return  sorted.get(0).getValue().getRank();
			}		
		}
		return 0;
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
		cardValues.add(0,new CardValue(CardValue.ACE));
		cardValues.get(0).setRank(1);
		
		int numValue = 0;
		double temp = 0;
		double sumProbability = 0;
		
		for (int i=0;i<cardValues.size()-4;i++){
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
	 * @return 0 if does not have Straight Flush, return value
	 * greater than 0 pertaining to high card of Straigh Flush.
	 */
	private int hasStraightFlush() throws Exception{
		ArrayList<Card> sorted = sortByValue(getCombined());
		ArrayList<CardSuit> cardSuits = CardSuit.getAll();
		ArrayList<CardValue> cardValues = CardValue.getAll();
		cardValues.add(0,new CardValue(CardValue.ACE));
		cardValues.get(0).setRank(1);
		
		int numValue = 0;
		for (int i=0;i<cardValues.size()-4;i++){
			CardValue startCard = cardValues.get(i);
			CardValue endCard = cardValues.get(i+4);
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, startCard, endCard,cardSuits.get(j));
				if (numValue >= 5){
					return endCard.getRank();
				}
			}
		}
		return 0;
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
	 * @return 0 if does not have 4 of a Kind, return value
	 * greater than 0 pertaining to high card of 4 of a Kind.
	 */
	private int has4Kind(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		ArrayList<CardValue> cardValues = CardValue.getAll();
		
		int numValue = 0;
		for (int i=0;i<cardValues.size();i++){
			numValue = countValue(sorted, cardValues.get(i));
			//System.out.println(cardValues.get(i)+" "+num);
			if (numValue == 4){
				return cardValues.get(i).getRank();
			}
		}
		return 0;
	}	
	
	/*
	 * Calculates the probability of having a full house -
	 * 3 of one kind and two of another kind, potential to have
	 * 3,2,2 or 3,3,1 or 3,2,1,1
	 */
	private double calculateFullHouse() throws Exception {
		//System.out.println("Calculating... Full House");
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		int numValue = 0;
		double temp = 0;			
		double sumProbability = 0;
		ArrayList<Integer> dist = getCountDistribution(getCombined());
		
		if (dealt == 2){
				if (dist.toString().equals("[2]")){
					temp = (double)(0
							//2 triples + 1 kicker
							+ DMath.combination(2,1)*DMath.combination(12,1)*DMath.combination(4,3)*DMath.combination(11,1)*DMath.combination(4,1)
							//1 triple + 1 pair + 2 kickers
							+ DMath.combination(2,1)*DMath.combination(12,1)*DMath.combination(4,2)*DMath.combination(11,2)*Math.pow(DMath.combination(4,1),2)
							+ DMath.combination(12,1)*DMath.combination(4,3)*DMath.combination(11,2)*Math.pow(DMath.combination(4,1),2)
							//1 triple + 2 pairs
							+ DMath.combination(2,1)*DMath.combination(12,1)*DMath.combination(4,2)*DMath.combination(11,1)*DMath.combination(4,2)
							+ DMath.combination(12,1)*DMath.combination(4,3)*DMath.combination(11,1)*Math.pow(DMath.combination(4,2),1)
							)/(double)DMath.combination(decksize, remaining);
				}else if (dist.toString().equals("[1, 1]")){
					temp = (double)(0
							//2 triples + 1 kicker
							+ DMath.combination(3,2)*DMath.combination(3,2)*DMath.combination(11,1)*DMath.combination(4,1)
							//1 triple + 1 pair + 2 kickers
							+ DMath.combination(2,1)*DMath.combination(3,2)*DMath.combination(3,1)*DMath.combination(11,2)*Math.pow(DMath.combination(4,1),2)
							+ DMath.combination(2,1)*DMath.combination(3,2)*DMath.combination(12,1)*DMath.combination(4,2)*DMath.combination(11,1)*DMath.combination(4,1)
							//1 triple + 2 pairs
							+ DMath.combination(2,1)*DMath.combination(3,2)*DMath.combination(3,1)*DMath.combination(11,1)*DMath.combination(4,2)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
		}else if (dealt == 3){
			if (dist.toString().equals("[3]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ DMath.combination(12,1)*DMath.combination(4,3)*DMath.combination(11,1)*DMath.combination(4,1)
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(12,1)*DMath.combination(4,2)*DMath.combination(11,2)*Math.pow(DMath.combination(4,1),2)
						//1 triple + 2 pairs
						+ DMath.combination(12,2)*Math.pow(DMath.combination(4,2),2)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[2, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ DMath.combination(2,1)*DMath.combination(3,2)*DMath.combination(11,1)*DMath.combination(4,1)
						+ DMath.combination(2,1)*DMath.combination(3,2)*DMath.combination(11,1)*DMath.combination(4,1)
						+ DMath.combination(2,1)*DMath.combination(11,1)*DMath.combination(4,3)
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(2,1)*DMath.combination(3,1)*DMath.combination(11,2)*Math.pow(DMath.combination(4,1),2)
						+ DMath.combination(3,2)*DMath.combination(11,2)*Math.pow(DMath.combination(4,1),2)
						+ DMath.combination(11,2)*DMath.combination(4,1)*DMath.combination(4,3)
						//1 triple + 2 pairs
						+ DMath.combination(2,1)*DMath.combination(3,2)*DMath.combination(11,1)*DMath.combination(4,2)
						+ DMath.combination(3,1)*DMath.combination(11,1)*DMath.combination(4,3)
						+ DMath.combination(3,2)*DMath.combination(11,1)*DMath.combination(4,2)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[1, 1, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ DMath.combination(3,1)*Math.pow(DMath.combination(3,2),2)
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(3,1)*DMath.combination(3,2)*DMath.combination(2,1)*DMath.combination(3,1)*DMath.combination(11,1)*DMath.combination(4,1)
						+ DMath.combination(3,1)*DMath.combination(3,2)*DMath.combination(11,1)*DMath.combination(4,2)
						+ DMath.combination(3,1)*DMath.combination(3,1)*DMath.combination(11,1)*DMath.combination(4,3)
						//1 triple + 2 pairs
						+ DMath.combination(3,1)*DMath.combination(3,2)*Math.pow(DMath.combination(3,1),2)
						)/(double)DMath.combination(decksize, remaining);
			}else{
				temp = 0;
			}
			//System.out.println(temp);
			sumProbability = sumProbability + temp;			
		}else if (dealt == 4){
			if (dist.toString().equals("[3, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ DMath.combination(11,1)*DMath.combination(4,3)
						+ DMath.combination(3,2)*DMath.combination(11,1)*DMath.combination(4,1)
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(3,1)*DMath.combination(11,2)*Math.pow(DMath.combination(4,1),2)
						+ DMath.combination(11,2)*DMath.combination(4,1)*DMath.combination(4,2)
						//1 triple + 2 pairs
						+ DMath.combination(3,1)*DMath.combination(11,1)*DMath.combination(4,2)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[2, 2]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ DMath.combination(2,1)*DMath.combination(2,1)*DMath.combination(11,1)*DMath.combination(4,1)
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(2,1)*DMath.combination(3,1)*DMath.combination(11,2)*Math.pow(DMath.combination(4,1),2)
						//1 triple + 2 pairs
						+ DMath.combination(2,1)*DMath.combination(2,1)*DMath.combination(11,1)*DMath.combination(4,2)
						+ DMath.combination(11,1)*DMath.combination(4,3)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[2, 1, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ DMath.combination(2,1)*DMath.combination(3,2)*DMath.combination(2,1)
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(10,1)*DMath.combination(4,3)
						+ DMath.combination(2,1)*DMath.combination(3,2)*DMath.combination(10,1)*DMath.combination(4,1)
						+ DMath.combination(2,1)*DMath.combination(10,1)*DMath.combination(4,2)
						+ DMath.combination(2,1)*DMath.combination(2,1)*DMath.combination(3,1)*DMath.combination(10,1)*DMath.combination(4,1)
						//1 triple + 2 pairs
						+ DMath.combination(2,1)*DMath.combination(3,1)*DMath.combination(3,2)
						+ DMath.combination(2,1)*DMath.combination(3,1)*DMath.combination(3,1)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[1, 1, 1, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ 0
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(4,1)*DMath.combination(3,2)*DMath.combination(3,1)*DMath.combination(3,1)
						//1 triple + 2 pairs
						+ 0
						)/(double)DMath.combination(decksize, remaining);
			}else{
				temp = 0;
			}
			//System.out.println(temp);
			sumProbability = sumProbability + temp;	
		}else if (dealt == 5){
			if (dist.toString().equals("[3, 2]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ DMath.combination(2,1)*DMath.combination(11,1)*DMath.combination(4,1)
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(11,2)*Math.pow(DMath.combination(4,1),2)
						//1 triple + 2 pairs
						+ DMath.combination(11,1)*DMath.combination(4,2)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[3, 1, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ DMath.combination(2,1)*DMath.combination(3,2)
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(2,1)*DMath.combination(3,1)*DMath.combination(10,1)*DMath.combination(4,1)
						+ DMath.combination(10,1)*DMath.combination(4,2)
						//1 triple + 2 pairs
						+ DMath.combination(2,1)*DMath.combination(2,1)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[2, 2, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ DMath.combination(2,1)*DMath.combination(2,1)
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(2,1)*DMath.combination(2,1)*DMath.combination(10,1)*DMath.combination(4,1)
						//1 triple + 2 pairs
						+ DMath.combination(2,1)*DMath.combination(2,1)*DMath.combination(3,1)
						+ DMath.combination(3,2)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[2, 1, 1, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ 0
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(3,1)*DMath.combination(3,2)
						+ DMath.combination(2,1)*DMath.combination(3,1)*DMath.combination(3,1)
						//1 triple + 2 pairs
						+ 0
						)/(double)DMath.combination(decksize, remaining);
			}else{
				temp = 0;
			}
			//System.out.println(temp);
			sumProbability = sumProbability + temp;				
		}else if (dealt == 6){
			if (dist.toString().equals("[3, 3]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ DMath.combination(11,1)*DMath.combination(4,1)
						//1 triple + 1 pair + 2 kickers
						+ 0
						//1 triple + 2 pairs
						+ 0
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[3, 2, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ DMath.combination(2,1)
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(10,1)*DMath.combination(4,1)
						//1 triple + 2 pairs
						+ DMath.combination(3,1)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[3, 1, 1, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ 0
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(3,1)*DMath.combination(3,1)
						//1 triple + 2 pairs
						+ 0
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[2, 2, 2]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ 0
						//1 triple + 1 pair + 2 kickers
						+ 0
						//1 triple + 2 pairs
						+ DMath.combination(3,1)*DMath.combination(2,1)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[2, 2, 1, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ 0
						//1 triple + 1 pair + 2 kickers
						+ DMath.combination(2,1)*DMath.combination(2,1)
						//1 triple + 2 pairs
						+ 0
						)/(double)DMath.combination(decksize, remaining);
			}else{
				temp = 0;
			}
			//System.out.println(temp);
			sumProbability = sumProbability + temp;					
		}else if (dealt == 7){
			if (dist.toString().equals("[3, 3, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ 1
						//1 triple + 1 pair + 2 kickers
						+ 0
						//1 triple + 2 pairs
						+ 0
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[3, 2, 2]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ 0
						//1 triple + 1 pair + 2 kickers
						+ 0
						//1 triple + 2 pairs
						+ 1
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[3, 2, 1, 1]")){
				temp = (double)(0
						//2 triples + 1 kicker
						+ 0
						//1 triple + 1 pair + 2 kickers
						+ 1
						//1 triple + 2 pairs
						+ 0
						)/(double)DMath.combination(decksize, remaining);
			}else{
				temp = 0;
			}
			//System.out.println(temp);
			sumProbability = sumProbability + temp;		
		}	
		return sumProbability;
	}

	/*
	 * @return 0 if does not have Full House, return value
	 * greater than 0 pertaining to high card of Full House.
	 */
	private int hasFullHouse(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		ArrayList<Integer> dist = getCountDistribution(getCombined());
		
		int pos1 = -1;
		int counter = 0;
		int sum = 0;
		for (int i=0;i<dist.size();i++){
			if (dist.get(i)==3 && pos1==-1){
				sum = sum + sorted.get(counter).getValue().getRank();
			}else if(dist.get(i)>=2 && pos1!=-1){
				sum = sum + sorted.get(counter).getValue().getRank();
				return sum;
			}
			counter = counter + dist.size();
		}
				
		return 0;
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
	 * @return 0 if does not have Flush, return value
	 * greater than 0 pertaining to high card of Flush.
	 */
	private int hasFlush(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		ArrayList<CardSuit> cardSuits = CardSuit.getAll();
		
		int numSuit = 0;
		for (int i=0;i<cardSuits.size();i++){
			numSuit = countSuit(sorted, cardSuits.get(i));
			if (numSuit >= 5){
				for (int j=0;j<sorted.size();j++){
					if (sorted.get(j).getSuit().getAbbrv().equals(cardSuits.get(i).getAbbrv())){
						return sorted.get(j).getValue().getRank();
					}
				}
			}
		}
		return 0;
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
		}else if (dealt == 3){
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
		}else if (dealt == 4){
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
		}else if (dealt == 5){
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
		}else if (dealt == 6){
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
		}else if (dealt == 7){
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
		cardValues.remove(0);
		cardValues.trimToSize();
		//System.out.println("Probability for Straight is "+sumProbability);
		return sumProbability;
	}

	/*
	 * @return 0 if does not have Straight, return value
	 * greater than 0 pertaining to high card of Straight.
	 */
	private int hasStraight() throws Exception{
		ArrayList<Card> sorted = sortByValue(getCombined());
		ArrayList<CardValue> cardValues = CardValue.getAll();
		cardValues.add(0,new CardValue(CardValue.ACE));
		cardValues.get(0).setRank(1);
		
		int numValue = 0;
		for (int i=0;i<cardValues.size()-4;i++){
			CardValue startCard = cardValues.get(i);
			CardValue endCard = cardValues.get(i+4);
			numValue = countValueRange(sorted, startCard, endCard);
			if (numValue >= 5){
				return endCard.getRank();
			}
		}
		return 0;
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
	 * @return 0 if does not have 3 Kind, return value
	 * greater than 0 pertaining to high card of 3 Kind.
	 */
	private int has3Kind(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		ArrayList<CardValue> cardValues = CardValue.getAll();
		
		int numValue = 0;
		for (int i=0;i<cardValues.size();i++){
			numValue = countValue(sorted, cardValues.get(i));
			//System.out.println(cardValues.get(i)+" "+num);
			if (numValue == 3){
				return cardValues.get(i).getRank();
			}
		}
		return 0;
	}	
		
	/*
	 * Calculates the probability of having 2 pair - 
	 * Two sets of two of a kind, 2,2,1,1,1 or 2,2,2,1
	 */
	private double calculate2Pair() throws Exception {
		//System.out.println("Calculating... 2 Pair");
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		int numValue = 0;
		double temp = 0;			
		double sumProbability = 0;
		ArrayList<Integer> dist = getCountDistribution(getCombined());
		
		if (dealt == 2){
				if (dist.toString().equals("[2]")){
					temp = (double)(0
							//3 pairs + 1 kicker
							+ DMath.combination(12,2)*Math.pow(DMath.combination(4,2),2)*DMath.combination(10,1)*DMath.combination(4,1)
							//2 pairs + 3 kickers
							+ DMath.combination(12,1)*DMath.combination(4,2)*DMath.combination(11,3)*Math.pow(DMath.combination(4,1),3)
							)/(double)DMath.combination(decksize, remaining);
				}else if (dist.toString().equals("[1, 1]")){
					temp = (double)(0
							//3 pairs + 1 kicker
							+ Math.pow(DMath.combination(3,1),2)*DMath.combination(11,1)*DMath.combination(4,2)*DMath.combination(10,1)*DMath.combination(4,1)
							+ DMath.combination(2,1)*DMath.combination(3,1)*DMath.combination(11,2)*Math.pow(DMath.combination(4,2),2)
							//2 pairs + 3 kickers
							+ Math.pow(DMath.combination(3,1),2)*DMath.combination(11,3)*Math.pow(DMath.combination(4,1),3)
							+ DMath.combination(2,1)*DMath.combination(3,1)*DMath.combination(11,1)*DMath.combination(4,2)*DMath.combination(10,1)*DMath.combination(4,1)
							+ DMath.combination(11,1)*DMath.combination(3,1)*DMath.combination(10,2)*Math.pow(DMath.combination(4,2),2)
							)/(double)DMath.combination(decksize, remaining);
				}else{
					temp = 0;
				}
				//System.out.println(temp);
				sumProbability = sumProbability + temp;
		}else if (dealt == 3){
			if (dist.toString().equals("[2, 1]")){
				temp = (double)(0
						//3 pairs + 1 kicker
						+DMath.combination(11,2)*Math.pow(DMath.combination(4,2),2)
						+DMath.combination(3,1)*DMath.combination(11,1)*DMath.combination(4,2)*DMath.combination(10,1)*DMath.combination(4,1)
						//2 pairs + 3 kickers
						+DMath.combination(3,1)*DMath.combination(11,3)*Math.pow(DMath.combination(4,1),3)
						+DMath.combination(11,2)*Math.pow(DMath.combination(4,1),2)*DMath.combination(9,1)*DMath.combination(4,2)
						)/(double)DMath.combination(decksize, remaining);	
			}else if (dist.toString().equals("[1, 1, 1]")){
				temp = (double)(0
						//3 pairs + 1 kicker
						+DMath.combination(3,1)*Math.pow(DMath.combination(3,1),2)*DMath.combination(10,1)*DMath.combination(4,2)
						+Math.pow(DMath.combination(3,1),3)*DMath.combination(10,1)*DMath.combination(4,1)
						//2 pairs + 3 kickers
						+DMath.combination(10,2)*Math.pow(DMath.combination(4,2),2)
						+DMath.combination(3,1)*Math.pow(DMath.combination(3,1),2)*DMath.combination(10,2)*Math.pow(DMath.combination(4,1),2)
						+DMath.combination(3,1)*DMath.combination(3,1)*DMath.combination(10,1)*DMath.combination(4,2)
						)/(double)DMath.combination(decksize, remaining);				
			}else{
				temp = 0;
			}
			//System.out.println(temp);
			sumProbability = sumProbability + temp;			
		}else if (dealt == 4){
			if (dist.toString().equals("[2, 2]")){
				temp = (double)(0
						//3 pairs + 1 kicker
						+DMath.combination(11,1)*DMath.combination(4,2)*DMath.combination(10,2)*Math.pow(DMath.combination(4,1),2)
						//2 pairs + 3 kickers
						+DMath.combination(11,3)*Math.pow(DMath.combination(4,1),3)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[2, 1, 1]")){
				temp = (double)(0
						//3 pairs + 1 kicker
						+ Math.pow(DMath.combination(3,1),2)*DMath.combination(10,1)*DMath.combination(4,1)
						+ DMath.combination(2,1)*DMath.combination(3,1)*DMath.combination(10,1)*DMath.combination(4,2)
						//2 pairs + 3 kickers
						+ DMath.combination(2,1)*DMath.combination(3,1)*DMath.combination(10,2)*Math.pow(DMath.combination(4,1),2)
						+ DMath.combination(10,1)*DMath.combination(4,2)*DMath.combination(9,1)*DMath.combination(4,1)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[1, 1, 1, 1]")){
				temp = (double)(0
						//3 pairs + 1 kicker
						+ DMath.combination(4,3)*Math.pow(DMath.combination(3,1),3)
						//2 pairs + 3 kickers
						+ DMath.combination(4,2)*Math.pow(DMath.combination(3,1),2)*DMath.combination(9,1)*DMath.combination(4,1)
						+ DMath.combination(4,1)*DMath.combination(3,1)*DMath.combination(9,1)*DMath.combination(4,2)
						)/(double)DMath.combination(decksize, remaining);
			}else{
				temp = 0;
			}
			//System.out.println(temp);
			sumProbability = sumProbability + temp;	
		}else if (dealt == 5){
			if (dist.toString().equals("[2, 2, 1]")){
				temp = (double)(0
						//3 pairs + 1 kicker
						+ DMath.combination(3,1)*DMath.combination(10,1)*DMath.combination(4,1)
						+ DMath.combination(10,1)*DMath.combination(4,2)
						//2 pairs + 3 kickers
						+ DMath.combination(10,2)*Math.pow(DMath.combination(4,1),2)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[2, 1, 1, 1]")){
				temp = (double)(0
						//3 pairs + 1 kicker
						+ DMath.combination(3,2)*DMath.combination(4,1)
						//2 pairs + 3 kickers
						+ DMath.combination(3,1)*DMath.combination(3,1)
						+ DMath.combination(9,1)*DMath.combination(4,2)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[1, 1, 1, 1, 1]")){
				temp = (double)(0
						//3 pairs + 1 kicker
						+ 0
						//2 pairs + 3 kickers
						+ DMath.combination(5,2)*DMath.combination(3,1)
						)/(double)DMath.combination(decksize, remaining);
			}else{
				temp = 0;
			}
			//System.out.println(temp);
			sumProbability = sumProbability + temp;				
		}else if (dealt == 6){
			if (dist.toString().equals("[2, 2, 2]")){
				temp = (double)(0
						//3 pairs + 1 kicker
						+ DMath.combination(10,1)*DMath.combination(4,1)
						//2 pairs + 3 kickers
						+ 0
						)/(double)DMath.combination(decksize, remaining);				
			}else if (dist.toString().equals("[2, 2, 1, 1]")){
				temp = (double)(0
						//3 pairs + 1 kicker
						+ DMath.combination(2,1)*DMath.combination(2,1)
						//2 pairs + 3 kickers
						+ DMath.combination(9,1)*DMath.combination(4,1)
						)/(double)DMath.combination(decksize, remaining);
			}else if (dist.toString().equals("[2, 1, 1, 1, 1]")){

			}else{
				temp = 0;
			}
			//System.out.println(temp);
			sumProbability = sumProbability + temp;					
		}else if (dealt == 7){
			if (dist.toString().equals("[2, 2, 2, 1]")){
				temp = 1;
			}else if (dist.toString().equals("[2, 2, 1, 1, 1]")){
				temp = 1;
			}else{
				temp = 0;
			}
			//System.out.println(temp);
			sumProbability = sumProbability + temp;			
		}	
		return sumProbability;
	}

	/*
	 * @return 0 if does not have 2 Pair Flush, return value
	 * greater than 0 pertaining to the sum of the card ranks of the
	 * two pair values.
	 */
	private int has2Pair(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		ArrayList<Integer> dist = getCountDistribution(getCombined());
		
		int pos1 = -1;
		int counter = 0;
		int sum = 0;
		for (int i=0;i<dist.size();i++){
			if (dist.get(i)==2 && pos1==-1){
				sum = sum + sorted.get(counter).getValue().getRank();
			}else if(dist.get(i)==2 && pos1!=-1){
				sum = sum + sorted.get(counter).getValue().getRank();
				return sum;
			}
			counter = counter + dist.size();
		}
				
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
	 * @return 0 if does not have 2 of a Kind, return value
	 * greater than 0 pertaining to high card of 2 of a Kind.
	 */
	private int has2Kind(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		ArrayList<CardValue> cardValues = CardValue.getAll();
		
		int numValue = 0;
		for (int i=0;i<cardValues.size();i++){
			numValue = countValue(sorted, cardValues.get(i));
			if (numValue == 2){
				return cardValues.get(i).getRank();
			}
		}
		return 0;
	}	

	/*
	 * @return value of the highest card in hand to resolve any 
	 * other tie breaker.
	 */
	private int hasHighCard(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		return sorted.get(0).getValue().getRank();
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
	 * and are of the same suit. Used in straight/flush calculations.
	 */	
	private int countValueRange(ArrayList<Card> collection, CardValue vStart, CardValue vEnd) throws Exception{
		//non-suited
		int counter = 0;
		
		ArrayList<CardValue> cardValues = CardValue.getAll();
		if (vStart.getValue().equals(CardValue.ACE)){
			vStart.setRank(1);
			cardValues.add(0,new CardValue(CardValue.ACE));
			cardValues.get(0).setRank(1);
			cardValues.remove(cardValues.size()-1);
		}
				
		for (int i=0;i<cardValues.size();i++){
			CardValue card = cardValues.get(i);
			if (card.getRank()<vStart.getRank() || vEnd.getRank()<card.getRank()){
				cardValues.remove(i);
				i--;
			}
		}
		
		for (int i=0;i<cardValues.size();i++){
			int num = countValue(collection, cardValues.get(i));
			if (num > 0){
				counter++;
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
		if (vStart.getValue().equals(CardValue.ACE)){
			vStart.setRank(1);
			cardValues.add(0,new CardValue(CardValue.ACE));
			cardValues.get(0).setRank(1);
			cardValues.remove(cardValues.size()-1);
		}
		
		for (int i=0;i<cardValues.size();i++){
			CardValue card = cardValues.get(i);
			if (card.getRank()<vStart.getRank() || vEnd.getRank()<card.getRank()){
				cardValues.remove(i);
				i--;
			}
		}
		
		for (int i=0;i<cardValues.size();i++){
			int num = countValue(collection, cardValues.get(i), suit);
			if (num > 0){
				counter++;
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

	/*
	 * Used to sort the collection by number of cards of a particular value.
	 * The card counts with the most is listed first.
	 */
	private ArrayList<Integer> getCountDistribution(ArrayList<Card> combined){
		HashMap<String, Integer> map = new HashMap<String,Integer>();
		combined = sortByCount(combined);
		for (int i=0;i<combined.size();i++){
			Card c = combined.get(i);
			Integer count = (Integer) map.get(c.getValue().getValue());
			if (count == null){
				count = 0;
			}
			map.put(c.getValue().getValue(),++count);
		}
		map = sortHashMap(map, "desc");
		
		ArrayList<Integer> dist = new ArrayList<Integer>();
		for (Map.Entry<String, Integer> entry: map.entrySet()) {
			dist.add(entry.getValue());
	    }
	    return dist;
	}	
	
	private HashMap<String, Integer> sortHashMap(HashMap<String, Integer> passedMap, String type){

		List mapKeys = new ArrayList(passedMap.keySet());
		List mapValues = new ArrayList(passedMap.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);
		
		if (type.equals("desc")){
			Collections.reverse(mapValues);
		}

		LinkedHashMap someMap = new LinkedHashMap();
		Iterator valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator keyIt = mapKeys.iterator();
			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				if (passedMap.get(key).toString().equals(val.toString())) {
					passedMap.remove(key);
					mapKeys.remove(key);
					someMap.put(key, val);
					break;
				}
			}
		}
		return someMap;
	}
	
}