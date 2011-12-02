package edu.psu.ist412.poker;

import java.util.ArrayList;

public class CardValue {
	public static String TWO = "2";
	public static String THREE = "3";
	public static String FOUR = "4";
	public static String FIVE = "5";
	public static String SIX = "6";
	public static String SEVEN = "7";
	public static String EIGHT="8";
	public static String NINE = "9";
	public static String TEN = "10";
	public static String JACK = "Jack";
	public static String QUEEN = "Queen";
	public static String KING = "King";
	public static String ACE = "Ace";
	
	public final static ArrayList<CardValue> getAll(){
		try{
			ArrayList<CardValue> a = new ArrayList<CardValue>();
			a.add(new CardValue(CardValue.TWO));
			a.add(new CardValue(CardValue.THREE));
			a.add(new CardValue(CardValue.FOUR));
			a.add(new CardValue(CardValue.FIVE));
			a.add(new CardValue(CardValue.SIX));
			a.add(new CardValue(CardValue.SEVEN));
			a.add(new CardValue(CardValue.EIGHT));
			a.add(new CardValue(CardValue.NINE));
			a.add(new CardValue(CardValue.TEN));
			a.add(new CardValue(CardValue.JACK));
			a.add(new CardValue(CardValue.QUEEN));
			a.add(new CardValue(CardValue.KING));
			a.add(new CardValue(CardValue.ACE));
			return a;
		}catch(Exception e){
			return null;
		}
	}
			
	private String value;
	private String abbrv;
	private int rank;

	/**
	 * @param value
	 * @param abbrv
	 * @param rank
	 * @throws Exception 
	 */
	public CardValue(String value) throws Exception{
		this.value = value;
		if (value.equals(CardValue.TWO))	{this.abbrv = value; this.rank = 2;}
		else if (value.equals(CardValue.THREE))	{this.abbrv = value; this.rank = 3;}
		else if (value.equals(CardValue.FOUR))	{this.abbrv = value; this.rank = 4;}
		else if (value.equals(CardValue.FIVE))	{this.abbrv = value; this.rank = 5;}
		else if (value.equals(CardValue.SIX))	{this.abbrv = value; this.rank = 6;}
		else if (value.equals(CardValue.SEVEN))	{this.abbrv = value; this.rank = 7;}
		else if (value.equals(CardValue.EIGHT))	{this.abbrv = value; this.rank = 8;}
		else if (value.equals(CardValue.NINE))	{this.abbrv = value; this.rank = 9;}
		else if (value.equals(CardValue.TEN))	{this.abbrv = "T"; this.rank = 10;}		
		else if (value.equals(CardValue.JACK))	{this.abbrv = "J"; this.rank = 11;}
		else if (value.equals(CardValue.QUEEN))	{this.abbrv = "Q"; this.rank = 12;}
		else if (value.equals(CardValue.KING))	{this.abbrv = "K"; this.rank = 13;}
		else if (value.equals(CardValue.ACE))	{this.abbrv = "A"; this.rank = 14;}
		else {throw(new Exception("Invalid Card Value String"));}
	}
	
	public CardValue(String value, String abbrv, int rank) throws Exception {
		if (value.equals(CardValue.TWO)
				|| value.equals(CardValue.THREE)
				|| value.equals(CardValue.FOUR)
				|| value.equals(CardValue.FIVE)
				|| value.equals(CardValue.SIX)
				|| value.equals(CardValue.SEVEN)
				|| value.equals(CardValue.EIGHT)
				|| value.equals(CardValue.NINE)
				|| value.equals(CardValue.TEN)
				|| value.equals(CardValue.JACK)
				|| value.equals(CardValue.QUEEN)
				|| value.equals(CardValue.KING)
				|| value.equals(CardValue.ACE)){
			this.value = value;
			this.abbrv = abbrv;
			this.rank = rank;
		}else{
			throw(new Exception("Invalid Card Value String"));
		}
	}
	
	/**
	 * Returns string representation of CardVale.
	 */
	@Override
	public String toString() {
		return abbrv;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	
	/**
	 * @return the abbrv
	 */
	public String getAbbrv() {
		return abbrv;
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}
	/**
	 * Set the rank, used to create an ACE with rank 1
	 * @param r
	 */
	public void setRank(int r) {
		rank = r;
	}
	
}