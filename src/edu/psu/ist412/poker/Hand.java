package edu.psu.ist412.poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;


public class Hand extends Object implements Observer{

	public static String ROYAL_FLUSH = "Royal Flush";
	public static String STRAIGHT_FLUSH = "Straight Flush";
	public static String FULL_HOUSE = "Full House";
	public static String STRAIGHT = "Straight";
	public static String FOUR_KIND = "4 of a Kind";
	public static String THREE_KIND = "3 of a Kind";
	public static String TWO_KIND = "2 of a Kind";
	public static String TWO_PAIR = "2 Pair";
	public static String FLUSH = "Flush";
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	private Map<String, Double> probability;
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
	
	public ArrayList<Card> getCards(){
		return cards;
	}	
	
	public Map<String, Double> getProbability(){
		try {
			calculateProbability();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return probability;
	}

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
	
	//TODO	
	private double calculate2Pair() throws Exception {
		
		return 0;
	}
	
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
