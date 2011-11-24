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
			cardValues.add(new CardValue("Ace","A",14));
			
			int numValue = 0;
			int numSuit = 0;
			int n = 0;
			double temp = 0;
			
			//Royal Straight Flush
			//System.out.println("Calculating... Royal Straight Flush (Same Suit, T-A, Sequential)");
			double royalFlush = 0;
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
					royalFlush = royalFlush + temp;
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
					royalFlush = royalFlush + temp;
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
					royalFlush = royalFlush + temp;
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
					royalFlush = royalFlush + temp;
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
					royalFlush = royalFlush + temp;
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
					royalFlush = royalFlush + temp;
				}
			}
			System.out.println("Probability for Royal Flush is "+royalFlush);			
			
			//Straight Flush
			//System.out.println("Calculating... Straight Flush (Same Suit, Sequential)");
			
			
			//Four of a Kind
			//System.out.println("Calculating... Four of a Kind");
		
			double kind4 = 0;
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
					kind4 = kind4 + temp;
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
					kind4 = kind4 + temp;
				}
			}else if (dealt == 4){
				for (int i=0;i<cardValues.size();i++){
					numValue = countValue(sorted, cardValues.get(i));
					//System.out.println(cardValues.get(i)+" "+num);
					if (numValue == 4){
						temp = 1;
					}else if (numValue == 3){
						temp = (double)(DMath.combination(4-numValue,1)*DMath.combination(decksize-(13-numValue),2)
								)/(double)DMath.combination(decksize, remaining);
					}else if (numValue == 2){
						temp = (double)(DMath.combination(4-numValue,2)*DMath.combination(decksize-(13-numValue),1)
								)/(double)DMath.combination(decksize, remaining);
					}else if (numValue == 1){
						temp = (double)(DMath.combination(4-numValue,4)*DMath.combination(decksize-(4-numValue),0)
								)/(double)DMath.combination(decksize, remaining);
					}else{
						temp = 0;
					}
					//System.out.println(temp);
					kind4 = kind4 + temp;
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
					kind4 = kind4 + temp;
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
					kind4 = kind4 + temp;
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
					kind4 = kind4 + temp;
				}
			}
			System.out.println("Probability for 4 of Kind is "+kind4);
			
			//Full House
			//System.out.println("Calculating... Full House");
			
			
			
			//Flush (Same Suit)
			double flush = 0;
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
					flush = flush + temp;
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
					flush = flush + temp;
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
					flush = flush + temp;
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
					flush = flush + temp;
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
					flush = flush + temp;
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
					flush = flush + temp;
				}
			}
			System.out.println("Probability for Flush is "+flush);
			
			
			/*
			num = 0;
			n = 0;
			double flush_abs = 0;
			double flush = 0;

			long possible_combinations = DMath.combination(decksize, remaining);
			System.out.println("Number of possible hands remaining = "+ possible_combinations);
			
			long sum_total = 0;
			for (int i=0;i<cardSuits.size();i++){
				//System.out.println("Calculating Probability for "+cardSuits.get(i));
				num = countSuit(sorted, cardSuits.get(i));
				CardSuit suit = cardSuits.get(i);
				if (num >= 5){
					flush = 1;
					break;
				}else if (remaining+num>=5){
					n++;
					long sum = 0;
					for (int j=0;j<remaining;j++){
						sum = sum + DMath.combination(13-num,remaining-j) * DMath.combination(((52-dealt)-(13-num)),j);
					}
					sum_total = sum_total + sum;
					System.out.println("Sum = "+sum);
					flush = ((double) sum)/((double) possible_combinations);
					System.out.println("Probability for "+suit.getValue()+" Flush is "+flush);
				}else{
					flush = 0;
				}	
			}
			if (flush == 1){
				flush_abs = flush;
			}else{
				flush_abs =  ((double) sum_total)/((double) possible_combinations);
			}
			System.out.println("Probability for  Flush is "+flush_abs);
			*/
			
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
		System.out.println("");
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
	
	private int countValueRange(ArrayList<Card> collection, CardValue vStart, CardValue vEnd){
		int counter = 0;
		CardValue rank;
		for (int i=0;i<collection.size();i++){
			rank = collection.get(i).getValue();
			if (rank.getRank()>=vStart.getRank() && rank.getRank()<=vEnd.getRank()){
					counter++;
			}
		}
		return counter;
	}
	private int countValueRange(ArrayList<Card> collection, CardValue vStart, CardValue vEnd, CardSuit suit){
		int counter = 0;
		CardValue rank;
		for (int i=0;i<collection.size();i++){
			rank = collection.get(i).getValue();
			if (rank.getRank()>=vStart.getRank() && rank.getRank()<=vEnd.getRank()){
				if (suit.getValue().equals(collection.get(i).getSuit().getValue())){
					counter++;
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
	
}
