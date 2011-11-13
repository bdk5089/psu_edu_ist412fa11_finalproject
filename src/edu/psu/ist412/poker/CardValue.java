package edu.psu.ist412.poker;

public class CardValue {
	private String value;
	private String abbrv;
	private int rank;
	/**
	 * @param value
	 * @param abbrv
	 * @param rank
	 */
	public CardValue(String value, String abbrv, int rank) {
		this.value = value;
		this.abbrv = abbrv;
		this.rank = rank;
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

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}	
	
	
}
