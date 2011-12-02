package edu.psu.ist412.poker;

import java.util.ArrayList;

public class CardSuit {
	
	public static String HEARTS = "Hearts";
	public static String DIAMONDS = "Diamonds";
	public static String CLUBS = "Clubs";
	public static String SPADES = "Spades";
	
	public final static ArrayList<CardSuit> getAll(){
		try{
			ArrayList<CardSuit> a = new ArrayList<CardSuit>();
			a.add(new CardSuit(CardSuit.HEARTS));
			a.add(new CardSuit(CardSuit.DIAMONDS));
			a.add(new CardSuit(CardSuit.CLUBS));
			a.add(new CardSuit(CardSuit.SPADES));
			return a;
		}catch(Exception e){
			return null;
		}
	}
	
	private String value;
	private String abbrv;
	/**
	 * @param value
	 * @param abbrv
	 * @throws Exception 
	 */
	public CardSuit(String value, String abbrv) throws Exception {
		if (value.equals(CardSuit.HEARTS)
				|| value.equals(CardSuit.DIAMONDS)
				|| value.equals(CardSuit.CLUBS)
				|| value.equals(CardSuit.SPADES)){
			this.value = value;
			this.abbrv = (String) abbrv.substring(0,1);
		}else{
			throw(new Exception("Invalid Suit String"));
		}
	}
	public CardSuit(String value) throws Exception {
		if (value.equals(CardSuit.HEARTS)
				|| value.equals(CardSuit.DIAMONDS)
				|| value.equals(CardSuit.CLUBS)
				|| value.equals(CardSuit.SPADES)){
			this.value = value;
			this.abbrv = (String) value.substring(0,1);
		}else{
			throw(new Exception("Invalid Suit String"));
		}
	}
	
	/**
	 * Returns string representation of CardSuit.
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
	
	
}