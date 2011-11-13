package edu.psu.ist412.poker;

public class CardSuit {
	private String value;
	private String abbrv;
	/**
	 * @param value
	 * @param abbrv
	 */
	public CardSuit(String value, String abbrv) {
		this.value = value;
		this.abbrv = abbrv;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
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