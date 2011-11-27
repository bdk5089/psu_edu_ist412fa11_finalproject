package edu.psu.ist412.poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Hand extends Object implements Observer{

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
			probability.put("Royal Flush", calculateRoyalFlush());
			probability.put("Straight Flush", calculateStraightFlush());
			probability.put("4 of a Kind", calculate4Kind());
			probability.put("Full House", calculateFullHouse());
			probability.put("Flush", calculateFlush());
			probability.put("Straight", calculateStraight());
			probability.put("3 of a Kind", calculate3Kind());
			probability.put("2 Pair", calculate2Pair());
			probability.put("2 of a Kind", calculate2Kind());
			
			System.out.println(probability);
		}
		System.out.println("");
	}

	private double calculateRoyalFlush(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = new ArrayList<CardSuit>();
		cardSuits.add(new CardSuit("Hearts"));
		cardSuits.add(new CardSuit("Diamonds"));
		cardSuits.add(new CardSuit("Clubs"));
		cardSuits.add(new CardSuit("Spades"));
		
		ArrayList<CardValue> cardValues = new ArrayList<CardValue>();
		cardValues.add(new CardValue("2","2",2));
		cardValues.add(new CardValue("3","3",3));
		cardValues.add(new CardValue("4","4",4));
		cardValues.add(new CardValue("5","5",5));
		cardValues.add(new CardValue("6","6",6));
		cardValues.add(new CardValue("7","7",7));
		cardValues.add(new CardValue("8","8",8));
		cardValues.add(new CardValue("9","9",9));
		cardValues.add(new CardValue("10","T",10));
		cardValues.add(new CardValue("Jack","J",11));
		cardValues.add(new CardValue("Queen","Q",12));
		cardValues.add(new CardValue("King","K",13));
		cardValues.add(new CardValue("Ace","A",14,1));
		
		int numValue = 0;
		int numSuit = 0;
		int n = 0;
		double temp = 0;
		
		//Royal Straight Flush
		//System.out.println("Calculating... Royal Straight Flush (Same Suit, T-A, Sequential)");
		double sumProbability = 0;
		temp = 0;
		if (dealt == 2){
			for (int j=0;j<cardSuits.size();j++){
				numValue = countValueRange(sorted, new CardValue("10","T",10), new CardValue("Ace","A",14),cardSuits.get(j));
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
				numValue = countValueRange(sorted, new CardValue("10","T",10), new CardValue("Ace","A",14),cardSuits.get(j));
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
				numValue = countValueRange(sorted, new CardValue("10","T",10), new CardValue("Ace","A",14),cardSuits.get(j));
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
				numValue = countValueRange(sorted, new CardValue("10","T",10), new CardValue("Ace","A",14),cardSuits.get(j));
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
				numValue = countValueRange(sorted, new CardValue("10","T",10), new CardValue("Ace","A",14),cardSuits.get(j));
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
				numValue = countValueRange(sorted, new CardValue("10","T",10), new CardValue("Ace","A",14),cardSuits.get(j));
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
		System.out.println("Probability for Royal Flush is "+sumProbability);	
		return sumProbability;
	}
	
	private double calculateStraightFlush(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = new ArrayList<CardSuit>();
		cardSuits.add(new CardSuit("Hearts"));
		cardSuits.add(new CardSuit("Diamonds"));
		cardSuits.add(new CardSuit("Clubs"));
		cardSuits.add(new CardSuit("Spades"));
		
		ArrayList<CardValue> cardValues = new ArrayList<CardValue>();
		cardValues.add(new CardValue("2","2",2));
		cardValues.add(new CardValue("3","3",3));
		cardValues.add(new CardValue("4","4",4));
		cardValues.add(new CardValue("5","5",5));
		cardValues.add(new CardValue("6","6",6));
		cardValues.add(new CardValue("7","7",7));
		cardValues.add(new CardValue("8","8",8));
		cardValues.add(new CardValue("9","9",9));
		cardValues.add(new CardValue("10","T",10));
		cardValues.add(new CardValue("Jack","J",11));
		cardValues.add(new CardValue("Queen","Q",12));
		cardValues.add(new CardValue("King","K",13));
		cardValues.add(new CardValue("Ace","A",14,1));
		
		int numValue = 0;
		int numSuit = 0;
		int n = 0;
		double temp = 0;
				
		//Straight Flush
		//System.out.println("Calculating... Straight Flush (Same Suit, Sequential)");
		double sumProbability = 0;
		temp = 0;
		cardValues.add(0,new CardValue("Ace","A",14,1));
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
		System.out.println("Probability for Straight Flush is "+sumProbability);						
		return sumProbability;	
	}
	
	private double calculate4Kind(){
		
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = new ArrayList<CardSuit>();
		cardSuits.add(new CardSuit("Hearts"));
		cardSuits.add(new CardSuit("Diamonds"));
		cardSuits.add(new CardSuit("Clubs"));
		cardSuits.add(new CardSuit("Spades"));
		
		ArrayList<CardValue> cardValues = new ArrayList<CardValue>();
		cardValues.add(new CardValue("2","2",2));
		cardValues.add(new CardValue("3","3",3));
		cardValues.add(new CardValue("4","4",4));
		cardValues.add(new CardValue("5","5",5));
		cardValues.add(new CardValue("6","6",6));
		cardValues.add(new CardValue("7","7",7));
		cardValues.add(new CardValue("8","8",8));
		cardValues.add(new CardValue("9","9",9));
		cardValues.add(new CardValue("10","T",10));
		cardValues.add(new CardValue("Jack","J",11));
		cardValues.add(new CardValue("Queen","Q",12));
		cardValues.add(new CardValue("King","K",13));
		cardValues.add(new CardValue("Ace","A",14,1));
		
		int numValue = 0;
		int numSuit = 0;
		int n = 0;
		double temp = 0;
				
		//Four of a Kind
		//System.out.println("Calculating... Four of a Kind");
	
		double sumProbability = 0;
		temp = 0;
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
		System.out.println("Probability for 4 of Kind is "+sumProbability);	
		return sumProbability;
	}
	
	//TODO
	private double calculateFullHouse(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = new ArrayList<CardSuit>();
		cardSuits.add(new CardSuit("Hearts"));
		cardSuits.add(new CardSuit("Diamonds"));
		cardSuits.add(new CardSuit("Clubs"));
		cardSuits.add(new CardSuit("Spades"));
		
		ArrayList<CardValue> cardValues = new ArrayList<CardValue>();
		cardValues.add(new CardValue("2","2",2));
		cardValues.add(new CardValue("3","3",3));
		cardValues.add(new CardValue("4","4",4));
		cardValues.add(new CardValue("5","5",5));
		cardValues.add(new CardValue("6","6",6));
		cardValues.add(new CardValue("7","7",7));
		cardValues.add(new CardValue("8","8",8));
		cardValues.add(new CardValue("9","9",9));
		cardValues.add(new CardValue("10","T",10));
		cardValues.add(new CardValue("Jack","J",11));
		cardValues.add(new CardValue("Queen","Q",12));
		cardValues.add(new CardValue("King","K",13));
		cardValues.add(new CardValue("Ace","A",14,1));
		
		int numValue = 0;
		int numSuit = 0;
		int n = 0;
		double temp = 0;			
		//Full House
		//System.out.println("Calculating... Full House");

		double sumProbability = 0;
		temp = 0;
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
		System.out.println("Probability for Full House is "+sumProbability);		
		return sumProbability;
	}
	
	private double calculateFlush(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = new ArrayList<CardSuit>();
		cardSuits.add(new CardSuit("Hearts"));
		cardSuits.add(new CardSuit("Diamonds"));
		cardSuits.add(new CardSuit("Clubs"));
		cardSuits.add(new CardSuit("Spades"));
		
		ArrayList<CardValue> cardValues = new ArrayList<CardValue>();
		cardValues.add(new CardValue("2","2",2));
		cardValues.add(new CardValue("3","3",3));
		cardValues.add(new CardValue("4","4",4));
		cardValues.add(new CardValue("5","5",5));
		cardValues.add(new CardValue("6","6",6));
		cardValues.add(new CardValue("7","7",7));
		cardValues.add(new CardValue("8","8",8));
		cardValues.add(new CardValue("9","9",9));
		cardValues.add(new CardValue("10","T",10));
		cardValues.add(new CardValue("Jack","J",11));
		cardValues.add(new CardValue("Queen","Q",12));
		cardValues.add(new CardValue("King","K",13));
		cardValues.add(new CardValue("Ace","A",14,1));
		
		int numValue = 0;
		int numSuit = 0;
		int n = 0;
		double temp = 0;		
		//Flush (Same Suit)
		//System.out.println("Calculating... Flush");
		double sumProbability = 0;
		temp = 0;
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
		System.out.println("Probability for Flush is "+sumProbability);		
		return sumProbability;
	}
	
	private double calculateStraight(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = new ArrayList<CardSuit>();
		cardSuits.add(new CardSuit("Hearts"));
		cardSuits.add(new CardSuit("Diamonds"));
		cardSuits.add(new CardSuit("Clubs"));
		cardSuits.add(new CardSuit("Spades"));
		
		ArrayList<CardValue> cardValues = new ArrayList<CardValue>();
		cardValues.add(new CardValue("2","2",2));
		cardValues.add(new CardValue("3","3",3));
		cardValues.add(new CardValue("4","4",4));
		cardValues.add(new CardValue("5","5",5));
		cardValues.add(new CardValue("6","6",6));
		cardValues.add(new CardValue("7","7",7));
		cardValues.add(new CardValue("8","8",8));
		cardValues.add(new CardValue("9","9",9));
		cardValues.add(new CardValue("10","T",10));
		cardValues.add(new CardValue("Jack","J",11));
		cardValues.add(new CardValue("Queen","Q",12));
		cardValues.add(new CardValue("King","K",13));
		cardValues.add(new CardValue("Ace","A",14,1));
		
		int numValue = 0;
		int numSuit = 0;
		int n = 0;
		double temp = 0;		

		double sumProbability= 0;
		temp = 0;
		cardValues.add(0,new CardValue("Ace","A",14,1));
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
		System.out.println("Probability for Straight is "+sumProbability);
		
		return sumProbability;
	}
	
	private double calculate3Kind(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = new ArrayList<CardSuit>();
		cardSuits.add(new CardSuit("Hearts"));
		cardSuits.add(new CardSuit("Diamonds"));
		cardSuits.add(new CardSuit("Clubs"));
		cardSuits.add(new CardSuit("Spades"));
		
		ArrayList<CardValue> cardValues = new ArrayList<CardValue>();
		cardValues.add(new CardValue("2","2",2));
		cardValues.add(new CardValue("3","3",3));
		cardValues.add(new CardValue("4","4",4));
		cardValues.add(new CardValue("5","5",5));
		cardValues.add(new CardValue("6","6",6));
		cardValues.add(new CardValue("7","7",7));
		cardValues.add(new CardValue("8","8",8));
		cardValues.add(new CardValue("9","9",9));
		cardValues.add(new CardValue("10","T",10));
		cardValues.add(new CardValue("Jack","J",11));
		cardValues.add(new CardValue("Queen","Q",12));
		cardValues.add(new CardValue("King","K",13));
		cardValues.add(new CardValue("Ace","A",14,1));
		
		int numValue = 0;
		int numSuit = 0;
		int n = 0;
		double temp = 0;	
		
		//Three of a Kind
		//System.out.println("Calculating... Three of a Kind");
		
		double sumProbability = 0;
		temp = 0;
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
		System.out.println("Probability for 3 of Kind is "+sumProbability);
		return sumProbability;
	}
	
	//TODO	
	private double calculate2Pair(){
		return 1;
	}
	
	private double calculate2Kind(){
		ArrayList<Card> sorted = sortByValue(getCombined());
		int dealt = sorted.size();
		int remaining = 7-dealt;
		int decksize = 52-dealt;
		
		ArrayList<CardSuit> cardSuits = new ArrayList<CardSuit>();
		cardSuits.add(new CardSuit("Hearts"));
		cardSuits.add(new CardSuit("Diamonds"));
		cardSuits.add(new CardSuit("Clubs"));
		cardSuits.add(new CardSuit("Spades"));
		
		ArrayList<CardValue> cardValues = new ArrayList<CardValue>();
		cardValues.add(new CardValue("2","2",2));
		cardValues.add(new CardValue("3","3",3));
		cardValues.add(new CardValue("4","4",4));
		cardValues.add(new CardValue("5","5",5));
		cardValues.add(new CardValue("6","6",6));
		cardValues.add(new CardValue("7","7",7));
		cardValues.add(new CardValue("8","8",8));
		cardValues.add(new CardValue("9","9",9));
		cardValues.add(new CardValue("10","T",10));
		cardValues.add(new CardValue("Jack","J",11));
		cardValues.add(new CardValue("Queen","Q",12));
		cardValues.add(new CardValue("King","K",13));
		cardValues.add(new CardValue("Ace","A",14,1));
		
		int numValue = 0;
		int numSuit = 0;
		int n = 0;
		double temp = 0;
		
		//One Pair
		//System.out.println("Calculating... One Pair");
		double sumProbability = 0;
		temp = 0;
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
		System.out.println("Probability for 2 of Kind is "+sumProbability);	
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
	
	private int countValueRange(ArrayList<Card> collection, CardValue vStart, CardValue vEnd){
		//non-suited
		int counter = 0;
	
		ArrayList<CardValue> cardValues = new ArrayList<CardValue>();
		cardValues.add(new CardValue("Ace","A",14,1));
		cardValues.add(new CardValue("2","2",2));
		cardValues.add(new CardValue("3","3",3));
		cardValues.add(new CardValue("4","4",4));
		cardValues.add(new CardValue("5","5",5));
		cardValues.add(new CardValue("6","6",6));
		cardValues.add(new CardValue("7","7",7));
		cardValues.add(new CardValue("8","8",8));
		cardValues.add(new CardValue("9","9",9));
		cardValues.add(new CardValue("10","T",10));
		cardValues.add(new CardValue("Jack","J",11));
		cardValues.add(new CardValue("Queen","Q",12));
		cardValues.add(new CardValue("King","K",13));
		cardValues.add(new CardValue("Ace","A",14,1));
		
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
	
	private int countValueRange(ArrayList<Card> collection, CardValue vStart, CardValue vEnd, CardSuit suit){
		int counter = 0;
		
		ArrayList<CardValue> cardValues = new ArrayList<CardValue>();
		cardValues.add(new CardValue("Ace","A",14,1));
		cardValues.add(new CardValue("2","2",2));
		cardValues.add(new CardValue("3","3",3));
		cardValues.add(new CardValue("4","4",4));
		cardValues.add(new CardValue("5","5",5));
		cardValues.add(new CardValue("6","6",6));
		cardValues.add(new CardValue("7","7",7));
		cardValues.add(new CardValue("8","8",8));
		cardValues.add(new CardValue("9","9",9));
		cardValues.add(new CardValue("10","T",10));
		cardValues.add(new CardValue("Jack","J",11));
		cardValues.add(new CardValue("Queen","Q",12));
		cardValues.add(new CardValue("King","K",13));
		cardValues.add(new CardValue("Ace","A",14,1));
		
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
